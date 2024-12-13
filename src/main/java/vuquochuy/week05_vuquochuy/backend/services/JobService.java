package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Job;

import java.util.List;

@Service
public interface JobService {
    public List<Job> getJobForCompany(Long companyId);

    public Job save(Job job);
    public Job getJobById(Long id);

    public Page<Job> getJobsWithPage(int pageNo, int pageSize, String sortBy,
                                     String sortDirection);
    public Page<Job> getJobsWithPageAndCompany(int pageNo, int pageSize, String sortBy,
                                              String sortDirection);

}
