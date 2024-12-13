package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkill;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateSkillRepository;
import vuquochuy.week05_vuquochuy.backend.services.CandidateSkillService;

@Service
public class CandidateSkillServiceImpl implements CandidateSkillService {
    @Autowired
    CandidateSkillRepository candidateSkillRepository;
    @Override
    public CandidateSkill save(CandidateSkill candidateSkill) {
        return candidateSkillRepository.save(candidateSkill);
    }

    @Override
    public CandidateSkill updateCandidateSkill(CandidateSkill candidateSkill) {
        return candidateSkillRepository.save(candidateSkill);
    }
}
