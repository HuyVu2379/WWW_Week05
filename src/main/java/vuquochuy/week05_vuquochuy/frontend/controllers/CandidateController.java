package vuquochuy.week05_vuquochuy.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vuquochuy.week05_vuquochuy.backend.models.Candidate;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkill;
import vuquochuy.week05_vuquochuy.backend.models.Skill;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateRepository;
import vuquochuy.week05_vuquochuy.backend.services.CandidateServices;
import vuquochuy.week05_vuquochuy.backend.services.Impl.CandidateServiceImpl;
import vuquochuy.week05_vuquochuy.backend.services.Impl.SkillServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CandidateController {
    private CandidateServiceImpl candidateServices;
    private SkillServiceImpl skillServices;

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidateServices.findAll());
        return "candidates/candidates";
    }

    @GetMapping("/candidate/home")
    public String showUserProfile(Model model) {
//        model.addAttribute("candidates", candidateServices.findAll());
        return "candidates/user-profile";
    }

    @GetMapping("/candidate/user-profile")
    public String showHomePage(Model model, Authentication authentication) {
        Optional<Candidate> candidate = candidateServices.findCandidateByEmail(authentication.getName());
        List<Skill> skills = skillServices.findAll();
//        List<CandidateSkill> skillOfCandidate = candidateServices.find
        if (candidate.isPresent()) {
            model.addAttribute("candidate", candidate.get());
            model.addAttribute("listSkills", skills);
        }
        return "candidates/user-profile";
    }

    @GetMapping("/candidate/home")
    public String showHomePage(Model model) {
//        model.addAttribute("candidates", candidateServices.findAll());
        return "candidates/home";
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