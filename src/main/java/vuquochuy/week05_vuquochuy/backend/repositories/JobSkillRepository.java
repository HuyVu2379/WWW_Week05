package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuquochuy.week05_vuquochuy.backend.models.JobSkill;
import vuquochuy.week05_vuquochuy.backend.models.JobSkillId;

import java.util.List;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    List<JobSkill> findById_JobId(Long jobId);
}
