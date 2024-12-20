package vuquochuy.week05_vuquochuy.frontend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vuquochuy.week05_vuquochuy.backend.enums.SkillLevel;
import vuquochuy.week05_vuquochuy.backend.models.*;
import vuquochuy.week05_vuquochuy.backend.services.Impl.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CMSControllers {
    @Autowired
    private JobServiceImpl jobService;
    @Autowired
    private JobSkillServiceImpl jobSkillService;
    @Autowired
    private CompanyServiceImpl companyService;
    @Autowired
    private SkillServiceImpl skillService;
    @Autowired
    private final ObjectMapper objectMapper;
    @Autowired
    private ApplicationImpl applicationService;
    @Autowired
    private  CandidateServiceImpl candidateService;
    @Autowired
    private EmailServiceImpl emailService;

    public CMSControllers(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @GetMapping("/company/cms")
    public String showCMS(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        model.addAttribute("ownerCompany", company);
        model.addAttribute("skills", skillService.findAll());
        return "company/cmsForCompany";
    }

    @PostMapping("/company/cms/postjob")
    public String postJob(
            @RequestParam("companyId") Long companyId,
            @RequestParam("jobDescription") String jobDescription,
            @RequestParam("jobName") String jobName,
            @RequestParam("skillsNeed") String skillsNeedJson,
            Model model
    ) {
        try {
            // Tìm kiếm công ty
            Company ownerCompany = companyService.findById(companyId)
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            // Tạo Job
            Job job = new Job(jobDescription, jobName, ownerCompany);
            jobService.save(job);
            Long jobId = job.getId();

            // Parse kỹ năng từ JSON
            List<Map<String, Object>> selectedSkills = parseSelectedSkills(skillsNeedJson);
            for (Map<String, Object> skill : selectedSkills) {
                Long skillId = Long.parseLong(skill.get("id").toString());
                JobSkillId jobSkillId = new JobSkillId(jobId, skillId);
                int skillLevelCode = Integer.parseInt(skill.get("level").toString());
                SkillLevel skillLevel = SkillLevel.fromCode(skillLevelCode);
                System.out.println(skillLevel);
                JobSkill jobSkill = new JobSkill(
                        jobSkillId,
                        skill.get("moreInfo").toString(),
                        skillLevel
                );
                jobSkillService.save(jobSkill);
            }

            // Đưa dữ liệu cần thiết vào model để hiển thị lại form
            model.addAttribute("ownerCompany", ownerCompany);
            model.addAttribute("skills", skillService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Trả về template với dữ liệu đầy đủ
        return "redirect:/company/cms/viewJob";

    }

    @PostMapping("company/cms/update-company")
    public String updateCompany(Model model, HttpSession session,
                                @RequestParam("id") Long id,
                                @RequestParam("name_comp") String name_comp,
                                @RequestParam("phone") String phone,
                                @RequestParam("about") String about,
                                @RequestParam("web-url") String web_url) {
        Optional<Company> companyOpt = companyService.findById(id);
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            company.setCompName(name_comp);
            company.setPhone(phone);
            company.setAbout(about);
            company.setWebUrl(web_url);
            Company updatedCompany = companyService.save(company);

            // Cập nhật lại session
            session.setAttribute("ownerCompany", updatedCompany);

            // Hoặc cập nhật lại model
            model.addAttribute("ownerCompany", updatedCompany);

            return "redirect:/company/cms"; // Chuyển hướng
        }
        return "company/company-profile";
    }


    @GetMapping("company/cms/viewJob")
    public String showJob(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        model.addAttribute("jobs", jobService.getJobForCompany(company.getId()));
        model.addAttribute("ownerCompany", company);
        return "company/viewJob";
    }

    @GetMapping("company/cms/company-profile")
    public String showCompanyProfile(Model model, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        Company company1 = companyService.findById(company.getId()).get();
        session.setAttribute("company", company1);
        model.addAttribute("jobs", jobService.getJobForCompany(company.getId()));
        model.addAttribute("ownerCompany", company1);
        return "company/company-profile";
    }

    @GetMapping("company/cms/viewCandidate")
    public String showCandidate(Model model, HttpSession session,
                                @RequestParam("jobId") long jobId) {
        Company company = (Company) session.getAttribute("company");
        List<Application> applications = applicationService.getCandidateApplicationsSubmitted(jobId);
        model.addAttribute("ownerCompany", company);
        model.addAttribute("applications", applications);
        model.addAttribute("job", jobService.getJobById(jobId));
        return "company/viewCandidate";
    }
    @GetMapping("/company/cms/sendEmail")
    public String sendEmail(@RequestParam("candidateId") long candidateId,@RequestParam("jobId") long jobId, Model model,HttpSession session) {
        // Lấy thông tin của candidate từ candidateId
        Candidate candidate = candidateService.findCandidateById(candidateId);
        Job job = jobService.getJobById(jobId);
        Company company = (Company) session.getAttribute("company");
        // Cấu hình và gửi email
        String subject = "Your Application Status";
        String body = "Dear " + candidate.getFullName() + ",\n\nYour application for the position "
                + job.getJobName() + " is being reviewed. We will get back to you soon.\n\nBest regards,\n"
                + company.getCompName();

        emailService.sendEmail(candidate.getEmail(), subject, body);
        //Sau khi send email, cập nhật trạng thái của application cho candidate
        applicationService.approveApplication(candidateId, jobId);
        model.addAttribute("message", "Email sent successfully to " + candidate.getEmail());
        return "redirect:/company/cms";
    }

    private List<Map<String, Object>> parseSelectedSkills(String skillsNeed) {
        try {
            return objectMapper.readValue(skillsNeed, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
