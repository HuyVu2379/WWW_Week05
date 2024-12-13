package vuquochuy.week05_vuquochuy.frontend.controllers;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vuquochuy.week05_vuquochuy.backend.enums.SkillLevel;
import vuquochuy.week05_vuquochuy.backend.models.*;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateRepository;
import vuquochuy.week05_vuquochuy.backend.services.CandidateServices;
import vuquochuy.week05_vuquochuy.backend.services.CandidateSkillService;
import vuquochuy.week05_vuquochuy.backend.services.Impl.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CandidateController {
    @Autowired
    private CandidateServiceImpl candidateServices;
    @Autowired
    private SkillServiceImpl skillServices;
    @Autowired
    private JobServiceImpl jobService;
    @Autowired
    private JobSkillServiceImpl jobSkillService;
    @Autowired
    private ApplicationImpl applicationService;
    @Autowired
    private CandidateSkillService candidateSkillService;

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidateServices.findAll());
        return "candidates/candidates";
    }

    @GetMapping("/candidate/user-profile")
    public String showHomePage(Model model, Authentication authentication) {
        Optional<Candidate> candidate = candidateServices.findCandidateByEmail(authentication.getName());
        List<Skill> skills = skillServices.findAll();
        List<CandidateSkill> candidateSkills = candidateServices.getSkillOfCandidate(candidate.get().getId());
        List<Skill> skillOfCandidate = candidateSkills.stream().map(candidateSkill -> skillServices.findById(candidateSkill.getId().getSkillId())).collect(Collectors.toList());
        List<Skill> skillNeeds = skillServices.getSuggestSkill(candidate.get().getId());
        if (candidate.isPresent()) {
            model.addAttribute("candidate", candidate.get());
            model.addAttribute("skillOfCandidate", skillOfCandidate);
            model.addAttribute("skillLevelOfCandidate", candidateSkills);
            model.addAttribute("skillNeeds",skillNeeds);
            model.addAttribute("candidateSkills", candidateSkills);
        }
        return "candidates/user-profile";
    }

    @GetMapping("/candidate/home")
    public String showHomePage(Model model, HttpSession session, @RequestParam(value = "page", defaultValue = "1") int page,
    @RequestParam(value = "size", defaultValue = "10") int size) {
        // Xác nhận giá trị của page và size để tránh lỗi chỉ số
        int currentPage = Math.max(page, 1);
        int pageSize = Math.max(size, 1);

        // Lấy danh sách công việc theo phân trang
        Page<Job> jobPage = jobService.getJobsWithPageAndCompany(currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("jobPage", jobPage);

        // Tạo danh sách số trang nếu có nhiều hơn 1 trang
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Lấy thông tin candidate từ session
        Candidate candidate = (Candidate) session.getAttribute("candidate");
        if (candidate != null) {
            model.addAttribute("candidate", candidate);
        } else {
            model.addAttribute("candidate", new Candidate()); // Tránh null pointer exception
        }
        return "candidates/home";
    }
    @GetMapping("/candidate/jobInformation/{id}")
    public String showJobInformation(Model model, HttpSession session, @PathVariable("id") long id) {
        Candidate candidate = (Candidate) session.getAttribute("candidate");
        Job job = jobService.getJobById(id);
        List<JobSkill> jobSkills = jobSkillService.getSkillByJobId(id);
        List<Skill> skills = jobSkills.stream().map(jobSkill -> skillServices.findById(jobSkill.getId().getSkillId())).collect(Collectors.toList());
        model.addAttribute("jobSkills", jobSkills);
        model.addAttribute("skills", skills);
        model.addAttribute("job", job);
        model.addAttribute("candidate", candidate);
        // Logic xử lý dựa trên id nếu cần
        return "candidates/jobInformation";
    }


    @GetMapping("/candidate/jobs")
    public String showJobsForCandidate(Model model, HttpSession session,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        // Xác nhận giá trị của page và size để tránh lỗi chỉ số
        int currentPage = Math.max(page, 1);
        int pageSize = Math.max(size, 1);

        // Lấy danh sách công việc theo phân trang
        Page<Job> jobPage = jobService.getJobsWithPageAndCompany(currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("jobPage", jobPage);

        // Tạo danh sách số trang nếu có nhiều hơn 1 trang
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Lấy thông tin candidate từ session
        Candidate candidate = (Candidate) session.getAttribute("candidate");
        if (candidate != null) {
            model.addAttribute("candidate", candidate);
        } else {
            model.addAttribute("candidate", new Candidate()); // Tránh null pointer exception
        }

        // Trả về view
        return "candidates/apply-page";
    }

    @GetMapping("/candidate/applyJob")
    public String applyJob(@RequestParam("candidateId") long candidateId,
                           @RequestParam("JobId") long jobId,
                           Model model) {
        Optional<Application> application = applicationService.applyJob(jobId, candidateId);
        return "redirect:/candidate/home";
    }

    @PostMapping("candidate/update")
    public String updateCandidate(
            @RequestParam("id") Long id,
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("dob") String dob) {
        Optional<Candidate> candidateOpt = Optional.ofNullable(candidateServices.findCandidateById(id));
        if (candidateOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            candidate.setFullName(fullName);
            candidate.setPhone(phone);
            candidate.setDob(LocalDate.parse(dob));
            Candidate can = candidateServices.save(candidate);
            return "redirect:/candidate/home"; // Chuyển hướng về trang home
        }
        return "candidates/user-profile";
    }

    @PostMapping("/candidate/learnSkill")
    public String learnSkill(@RequestParam("skillId") long skillId,
                             @RequestParam("candidateId") long candidateId,
                             Model model) {
        // Tìm ứng viên và kỹ năng từ database
        Optional<Candidate> candidateOpt = Optional.ofNullable(candidateServices.findCandidateById(candidateId));
        Optional<Skill> skillOpt = Optional.ofNullable(skillServices.findById(skillId));

        if (candidateOpt.isPresent() && skillOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            Skill skill = skillOpt.get();
            // Thêm kỹ năng vào danh sách kỹ năng của ứng viên
            CandidateSkill candidateSkill = new CandidateSkill();
            candidateSkill.setId(new CandidateSkillId(skill.getId(),candidate.getId()));
            candidateSkill.setSkillLevel(SkillLevel.BEGINNER);
            candidateSkill.setMoreInfos("");
            candidateSkillService.save(candidateSkill);
        } else {
            model.addAttribute("error", "Invalid candidate or skill ID.");
        }

        // Load lại thông tin ứng viên và các kỹ năng (nếu cần)
        Candidate updatedCandidate = candidateServices.findCandidateById(candidateId);
        List<CandidateSkill> candidateSkills = candidateServices.getSkillOfCandidate(updatedCandidate.getId());
        List<Skill> skillOfCandidate = candidateSkills.stream().map(candidateSkill -> skillServices.findById(candidateSkill.getId().getSkillId())).collect(Collectors.toList());
        List<Skill> skillNeeds = skillServices.getSuggestSkill(updatedCandidate.getId());
        model.addAttribute("candidate", updatedCandidate);
        model.addAttribute("skillOfCandidate", skillOfCandidate);
        model.addAttribute("skillLevelOfCandidate", candidateSkills);
        model.addAttribute("skillNeeds", skillNeeds); // Hàm gợi ý kỹ năng

        return "candidates/user-profile"; // Trả về trang profile
    }
    @PostMapping("/candidate/updateSkill")
    public String learnSkill(@RequestParam("skillId") long skillId,
                             @RequestParam("candidateId") long candidateId,
                             @RequestParam("more_information") String more_information,
                             @RequestParam("skillLevel") String skill_level,
                             Model model) {
        // Tìm ứng viên và kỹ năng từ database
        Optional<Candidate> candidateOpt = Optional.ofNullable(candidateServices.findCandidateById(candidateId));
        Optional<Skill> skillOpt = Optional.ofNullable(skillServices.findById(skillId));

        if (candidateOpt.isPresent() && skillOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            Skill skill = skillOpt.get();
            // Thêm kỹ năng vào danh sách kỹ năng của ứng viên
            CandidateSkill candidateSkill = new CandidateSkill();
            candidateSkill.setId(new CandidateSkillId(skill.getId(),candidate.getId()));
            candidateSkill.setSkillLevel(SkillLevel.fromCode(Integer.parseInt(skill_level)));
            candidateSkill.setMoreInfos(more_information);
            candidateSkillService.updateCandidateSkill(candidateSkill);
        } else {
            model.addAttribute("error", "Invalid candidate or skill ID.");
        }

        // Load lại thông tin ứng viên và các kỹ năng (nếu cần)
        Candidate updatedCandidate = candidateServices.findCandidateById(candidateId);
        List<CandidateSkill> candidateSkills = candidateServices.getSkillOfCandidate(updatedCandidate.getId());
        List<Skill> skillOfCandidate = candidateSkills.stream().map(candidateSkill -> skillServices.findById(candidateSkill.getId().getSkillId())).collect(Collectors.toList());
        List<Skill> skillNeeds = skillServices.getSuggestSkill(updatedCandidate.getId());
        model.addAttribute("candidate", updatedCandidate);
        model.addAttribute("skillOfCandidate", skillOfCandidate);
        model.addAttribute("skillLevelOfCandidate", candidateSkills);
        model.addAttribute("skillNeeds", skillNeeds); // Hàm gợi ý kỹ năng

        return "candidates/user-profile"; // Trả về trang profile
    }



    @GetMapping("/candidates")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage = candidateServices.findAllWithPage(
                currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidates/candidates-paging";
    }
}