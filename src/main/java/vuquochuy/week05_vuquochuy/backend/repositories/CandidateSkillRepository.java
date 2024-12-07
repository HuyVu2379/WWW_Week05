package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkill;
import vuquochuy.week05_vuquochuy.backend.models.CandidateSkillId;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
}
