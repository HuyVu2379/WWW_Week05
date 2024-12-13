package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Application;

import java.util.List;
import java.util.Optional;

@Service
public interface ApplicationService {
    public Optional<Application> applyJob(long jobId, long candidateId);

    public List<Application> getCandidateApplications(long jobId);
}
