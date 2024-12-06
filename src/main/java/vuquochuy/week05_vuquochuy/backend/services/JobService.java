package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Job;

import java.util.List;
@Service
public interface JobService {
    public List<Job> getJobForCompany(Long companyId);
    public Job save(Job job);
}
