package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.JobSkill;

@Service
public interface JobSkillService {
    public void save(JobSkill jobSkill);
    public JobSkill addSkillForJob(JobSkill jobSkill);

}
