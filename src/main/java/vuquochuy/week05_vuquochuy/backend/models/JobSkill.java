package vuquochuy.week05_vuquochuy.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_skill")
public class JobSkill {
    @EmbeddedId
    private JobSkillId id;
    @Column(name = "more_infos", length = 1000)
    private String moreInfos;

    @Column(name = "skill_level", nullable = false)
    private Byte skillLevel;

    public JobSkill(JobSkillId id, String moreInfos, Byte skillLevel) {
        this.id = id;
        this.moreInfos = moreInfos;
        this.skillLevel = skillLevel;
    }

    public JobSkill() {
    }
}