package vuquochuy.week05_vuquochuy.frontend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vuquochuy.week05_vuquochuy.backend.models.Company;
import vuquochuy.week05_vuquochuy.backend.models.Job;
import vuquochuy.week05_vuquochuy.backend.models.JobSkill;
import vuquochuy.week05_vuquochuy.backend.models.JobSkillId;
import vuquochuy.week05_vuquochuy.backend.services.Impl.CompanyServiceImpl;
import vuquochuy.week05_vuquochuy.backend.services.Impl.JobServiceImpl;
import vuquochuy.week05_vuquochuy.backend.services.Impl.JobSkillServiceImpl;
import vuquochuy.week05_vuquochuy.backend.services.Impl.SkillServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public CMSControllers(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @GetMapping("/cms")
    public String showCMS(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("skills", skillService.findAll());
        return "cms/cmsForCompany";
    }

    @PostMapping("/cms/postjob")
    public String postJob(
            @RequestParam("company") String company,
            @RequestParam("jobDescription") String jobDescription,
            @RequestParam("jobName") String jobName,
            @RequestParam("skillsNeed") String skillsNeedJson,
            Model model
    ) {
        try {
            Company companyFind = companyService.findById(Long.parseLong(company))
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            Job job = new Job(jobDescription, jobName, companyFind);
            jobService.save(job);
            Long jobId = job.getId();

            List<Map<String, Object>> selectedSkills = parseSelectedSkills(skillsNeedJson);
            System.out.println(selectedSkills);
            for (Map<String, Object> skill : selectedSkills) {
                Long skillId = Long.parseLong(skill.get("id").toString());
                JobSkillId jobSkillId = new JobSkillId(jobId, skillId);
                JobSkill jobSkill = new JobSkill(
                        jobSkillId,
                        skill.get("moreInfo").toString(),
                        Byte.parseByte(skill.get("level").toString())
                );
                jobSkillService.save(jobSkill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("skills", skillService.findAll());

        return "cms/cmsForCompany";
    }

    @GetMapping("cms/company/{companyId}/listJob")
    public String showJobForCompany(@PathVariable Long companyId, Model model) {    

        return "cms/viewJob";
    }

    @GetMapping("cms/viewJob")
    public String showJob(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("jobs", null);
        return "cms/viewJob";
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
