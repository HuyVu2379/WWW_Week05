package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.enums.ApplyStatus;
import vuquochuy.week05_vuquochuy.backend.models.Application;
import vuquochuy.week05_vuquochuy.backend.models.Candidate;
import vuquochuy.week05_vuquochuy.backend.models.Job;
import vuquochuy.week05_vuquochuy.backend.repositories.ApplicationRepository;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateRepository;
import vuquochuy.week05_vuquochuy.backend.repositories.JobRepository;
import vuquochuy.week05_vuquochuy.backend.services.ApplicationService;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Optional<Application> applyJob(long jobId, long candidateId) {
        Job job = jobRepository.findById(jobId).get();
        Candidate candidate = candidateRepository.findById(candidateId).get();
        ApplyStatus status = ApplyStatus.SUBMITTED;
        Application application = new Application(job, candidate, status);
        application = applicationRepository.save(application);
        if (application != null) {
            return Optional.of(application);
        }
        return Optional.empty();
    }

    @Override
    public List<Application> getCandidateApplications(long jobId) {
        return applicationRepository.findByJob_Id(jobId);
    }

}