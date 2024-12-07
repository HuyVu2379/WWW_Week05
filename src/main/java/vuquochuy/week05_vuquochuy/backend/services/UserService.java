package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import vuquochuy.week05_vuquochuy.backend.models.User;

public interface UserService extends UserDetailsService {
    User save(User user);
}