package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkill;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkillId;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
    CandidateSkill findById_CanId(Long canId);

    List<CandidateSkill> findCandidateSkillById_CanId(Long canId);

}
