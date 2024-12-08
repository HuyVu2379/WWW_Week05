package vuquochuy.week05_vuquochuy.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vuquochuy.week05_vuquochuy.backend.models.Candidate;
import vuquochuy.week05_vuquochuy.backend.models.Company;
import vuquochuy.week05_vuquochuy.backend.services.Impl.CandidateServiceImpl;
import vuquochuy.week05_vuquochuy.backend.services.Impl.CompanyServiceImpl;

import java.util.Collection;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    CandidateServiceImpl candidateService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "authorization/login"; // Trả về view login.html
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication, HttpSession session) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CANDIDATE"))) {
            Optional<Candidate> candidate = candidateService.findCandidateByEmail(username);
            if (candidate.isPresent()) {
                session.setAttribute("candidate", candidate.get());
            }
            return "redirect:/candidate/home"; // Chuyển hướng đến trang dành cho Candidate
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COMPANY"))) {
            Optional<Company> company = companyService.findCompanyByEmail(username);
            if (company.isPresent()) {
                // Lưu thông tin Company vào session
                session.setAttribute("company", company.get());
            }
            return "redirect:/company/cms"; // Chuyển hướng đến trang dành cho Company
        }
        return "redirect:/login"; // Quay lại login nếu không xác định được role
    }


}
