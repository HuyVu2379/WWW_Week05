package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Candidate;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkill;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateRepository;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateSkillRepository;
import vuquochuy.week05_vuquochuy.backend.services.CandidateServices;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateServices {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    public Page<Candidate> findAllWithPage(int pageNo, int pageSize, String sortBy,
                                           String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return candidateRepository.findAll(pageable);
    }

    @Override
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    public Optional<Candidate> findCandidateByEmail(String email) {
        if (candidateRepository.existsByEmail(email)) {
            return Optional.ofNullable(candidateRepository.findByEmail(email));
        }
        return Optional.empty();
    }

    @Override
    public List<CandidateSkill> getSkillOfCandidate(Long candidateId) {
        List<CandidateSkill> candidateSkills = (List<CandidateSkill>) candidateSkillRepository.findCandidateSkillById_CanId(candidateId);
        if (!candidateSkills.isEmpty()) {
            return candidateSkills;
        }
        return null;
    }

    @Override
    public Candidate findCandidateById(Long id){
        Optional<Candidate> candidate = candidateRepository.findById(id);
        return candidate.isEmpty() ? null : candidate.get();
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
