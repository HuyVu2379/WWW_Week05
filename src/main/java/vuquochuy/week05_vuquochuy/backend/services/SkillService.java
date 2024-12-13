package vuquochuy.week05_vuquochuy.backend.services;

import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Skill;

import java.util.List;

@Service
public interface SkillService {
    public List<Skill> findAll();
    public Skill findById(Long id);

    public List<Skill> getSuggestSkill(long candidateId);
}
