package vuquochuy.week05_vuquochuy.backend.enums;

public enum ApplyStatus {
    SUBMITTED(0),APPROVED(1);
    private final int code;

    ApplyStatus(int code) {
        this.code = code;
    }
    public static ApplyStatus fromCode(int code) {
        for (ApplyStatus level : ApplyStatus.values()) {
            if (level.getCode() == code) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid ApplyStatus code: " + code);
    }
    public int getCode() {
        return code;
    }
}
