package vuquochuy.week05_vuquochuy.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vuquochuy.week05_vuquochuy.backend.enums.SkillLevel;

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
    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    public JobSkill(JobSkillId id, String moreInfos, SkillLevel skillLevel) {
        this.id = id;
        this.moreInfos = moreInfos;
        this.skillLevel = skillLevel;
    }

    public JobSkill() {
    }
}