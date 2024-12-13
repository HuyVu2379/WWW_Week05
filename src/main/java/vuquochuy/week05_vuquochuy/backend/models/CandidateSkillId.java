package vuquochuy.week05_vuquochuy.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CandidateSkillId implements Serializable {
    private static final long serialVersionUID = -8580516439662447047L;
    @Column(name = "skill_id", nullable = false)
    private Long skillId;

    public CandidateSkillId(Long skillId, Long canId) {
        this.skillId = skillId;
        this.canId = canId;
    }

    @Column(name = "can_id", nullable = false)
    private Long canId;

    public CandidateSkillId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CandidateSkillId entity = (CandidateSkillId) o;
        return Objects.equals(this.skillId, entity.skillId) &&
                Objects.equals(this.canId, entity.canId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, canId);
    }

}