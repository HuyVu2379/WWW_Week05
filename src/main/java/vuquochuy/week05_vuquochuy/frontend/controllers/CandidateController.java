package vuquochuy.week05_vuquochuy.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vuquochuy.week05_vuquochuy.backend.models.Candidate;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkill;
import vuquochuy.week05_vuquochuy.backend.models.Skill;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateRepository;
import vuquochuy.week05_vuquochuy.backend.services.CandidateServices;
import vuquochuy.week05_vuquochuy.backend.services.Impl.CandidateServiceImpl;
import vuquochuy.week05_vuquochuy.backend.services.Impl.SkillServiceImpl;

import java.time.LocalDate;
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
        if (candidate.isPresent()) {
            model.addAttribute("candidate", candidate.get());
            model.addAttribute("listSkills", skills);
            model.addAttribute("candidateSkills", candidateSkills);
        }
        return "candidates/user-profile";
    }

    @GetMapping("/candidate/home")
    public String showHomePage(Model model, HttpSession session) {
        Candidate candidate = (Candidate) session.getAttribute("candidate");
        model.addAttribute("candidate", candidate);
        return "candidates/home";
    }

    @PostMapping("candidate/update")
    public String updateCandidate(
            @RequestParam("id") Long id,
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("dob") String dob) {
        Optional<Candidate> candidateOpt = Optional.ofNullable(candidateServices.findCandidateById(id));
        System.out.println("id: " + id);
        System.out.println("fullname: " + fullName);
        if (candidateOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();
            candidate.setFullName(fullName);
            candidate.setPhone(phone);
            candidate.setDob(LocalDate.parse(dob));
            Candidate can = candidateServices.save(candidate);
            return "redirect:/candidate/home"; // Chuyển hướng về trang home
        }
        return "candidates/user-profile"; // Hiển thị trang lỗi nếu không tìm thấy
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