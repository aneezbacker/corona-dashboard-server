package in.coronainfo.server.constants;

public enum EntityKind {
    GLOBAL_CASES("GlobalCases");

    private final String kind;

    private EntityKind(String s) {
        kind = s;
    }

    public boolean equalsName(String otherName) {
        return kind.equals(otherName);
    }

    @Override
    public String toString() {
        return this.kind;
    }
}
