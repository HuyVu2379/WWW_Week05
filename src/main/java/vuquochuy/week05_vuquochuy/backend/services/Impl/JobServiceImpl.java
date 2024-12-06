package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Job;
import vuquochuy.week05_vuquochuy.backend.repositories.JobRepository;
import vuquochuy.week05_vuquochuy.backend.repositories.JobSkillRepository;
import vuquochuy.week05_vuquochuy.backend.services.JobService;

import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Override
    public List<Job> getJobForCompany(Long companyId) {
        return jobRepository.findByCompany_Id(companyId);
    }

    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }
}
