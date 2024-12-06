package vuquochuy.week05_vuquochuy.backend.enums;

public enum SkillLevel {
    BEGINNER(0), ADVANCED(1), PROFESSIONAL(2), INTERMEDIATE(3), MASTER(4);
    private final int code;

    SkillLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}