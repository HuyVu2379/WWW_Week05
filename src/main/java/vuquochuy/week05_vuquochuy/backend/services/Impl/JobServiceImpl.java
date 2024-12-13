package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Job;
import vuquochuy.week05_vuquochuy.backend.repositories.JobRepository;
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

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findJobById(id);
    }

    @Override
    public Page<Job> getJobsWithPage(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        return jobRepository.findAll(pageable);
    }

    @Override
    public Page<Job> getJobsWithPageAndCompany(int pageNo, int pageSize, String sortBy, String sortDirection) {
        return jobRepository.findJobPage(PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.fromString(sortDirection),sortBy)));
    }

}
