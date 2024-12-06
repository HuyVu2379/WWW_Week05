package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Candidate;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateRepository;

import java.util.Collections;
import java.util.List;
@Service
public interface CandidateServices {
    public Page<Candidate> findAllWithPage(int pageNo, int pageSize, String sortBy,
                                   String sortDirection);
    public List<Candidate> findAll();
}
