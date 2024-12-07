package vuquochuy.week05_vuquochuy.backend.enums;

public enum Role {
    ROLE_COMPANY(0),ROLE_CANDIDATE(1);
    private final int code;
    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
