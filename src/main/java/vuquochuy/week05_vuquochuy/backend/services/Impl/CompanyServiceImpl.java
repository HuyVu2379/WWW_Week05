package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Company;
import vuquochuy.week05_vuquochuy.backend.repositories.CompanyRepository;
import vuquochuy.week05_vuquochuy.backend.services.CompanyService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        if (!companyRepository.existsById(id)) {
            return Optional.empty();
        } else {
            return companyRepository.findById(id);
        }
    }

    @Override
    public Optional<Company> findCompanyByEmail(String email) {
        if (companyRepository.existsByEmail(email)) {
            return Optional.ofNullable(companyRepository.findByEmail(email));
        }
        return Optional.empty();
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }
}
