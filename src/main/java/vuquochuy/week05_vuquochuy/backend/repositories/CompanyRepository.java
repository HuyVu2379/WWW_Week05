package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vuquochuy.week05_vuquochuy.backend.models.Company;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}
