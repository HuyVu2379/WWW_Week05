package vuquochuy.week05_vuquochuy.backend.services;


import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkill;

@Service
public interface CandidateSkillService {
    public CandidateSkill save(CandidateSkill candidateSkill);

    public CandidateSkill updateCandidateSkill(CandidateSkill candidateSkill);
}
