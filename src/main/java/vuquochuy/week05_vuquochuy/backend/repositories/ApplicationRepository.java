package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vuquochuy.week05_vuquochuy.backend.models.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByJob_Id(Long id);
    @Query("select a from Application a where a.job.id = :id and a.status = 'SUBMITTED'")
    List<Application> findCandidateSubmitted(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Application a SET a.status = 'APPROVED' WHERE a.candidate.id = :candidateId AND a.job.id = :jobId")
    void approveApplication(@Param("candidateId") Long candidateId, @Param("jobId") Long jobId);

}
