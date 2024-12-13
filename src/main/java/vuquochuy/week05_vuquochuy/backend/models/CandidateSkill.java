package vuquochuy.week05_vuquochuy.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vuquochuy.week05_vuquochuy.backend.enums.SkillLevel;

@Getter
@Setter
@Entity
@Table(name = "candidate_skill")
public class CandidateSkill {
    @EmbeddedId
    private CandidateSkillId id;

    @Column(name = "more_infos", length = 1000)
    private String moreInfos;

    @Column(name = "skill_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

}