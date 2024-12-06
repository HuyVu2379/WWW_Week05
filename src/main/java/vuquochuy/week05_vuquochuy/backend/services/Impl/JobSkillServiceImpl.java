package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.JobSkill;
import vuquochuy.week05_vuquochuy.backend.repositories.JobSkillRepository;
import vuquochuy.week05_vuquochuy.backend.services.JobSkillService;

@Service
public class JobSkillServiceImpl implements JobSkillService {
    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Override
    public void save(JobSkill jobSkill) {
        jobSkillRepository.save(jobSkill);
    }

    @Override
    public JobSkill addSkillForJob(JobSkill jobSkill) {
        return jobSkillRepository.save(jobSkill);
    }
}
