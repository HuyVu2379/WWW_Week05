package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vuquochuy.week05_vuquochuy.backend.models.Skill;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
    @Query("select s from Skill s where s.id not in (select s.id from Candidate c join CandidateSkill cs on c.id = cs.id.canId join Skill s on s.id = cs.id.skillId where c.id = :candidateId)")
    List<Skill> getSuggestSkill(long candidateId);
}
