package vuquochuy.week05_vuquochuy.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vuquochuy.week05_vuquochuy.backend.enums.ApplyStatus;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int app_id;

    public Application(Job job, Candidate candidate, ApplyStatus status) {
        this.job = job;
        this.candidate = candidate;
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;
    @Enumerated(EnumType.STRING)
    private ApplyStatus status;

    public Candidate getCandidate() {
        return candidate;
    }
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
