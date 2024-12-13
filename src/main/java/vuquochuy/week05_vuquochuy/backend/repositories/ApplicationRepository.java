package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vuquochuy.week05_vuquochuy.backend.models.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
