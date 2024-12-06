package vuquochuy.week05_vuquochuy.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuquochuy.week05_vuquochuy.backend.models.Skill;
import vuquochuy.week05_vuquochuy.backend.repositories.SkillRepository;
import vuquochuy.week05_vuquochuy.backend.services.SkillService;

import java.util.List;
@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }
}
