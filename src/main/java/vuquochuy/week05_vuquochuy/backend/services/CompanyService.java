package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Company;

import java.util.List;
import java.util.Optional;

@Service
public interface CompanyService {
    public List<Company> getAllCompanies();
    public Optional<Company> findById(Long id);

}
