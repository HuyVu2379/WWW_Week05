package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vuquochuy.week05_vuquochuy.backend.models.Job;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompany_Id(Long id);

    @Query("select j from Job j join fetch j.company")
    Page<Job> findJobPage(Pageable pageable);
    @Query("select j from Job j join fetch j.company where j.id = :id")
    Job findJobById(Long id);

}
