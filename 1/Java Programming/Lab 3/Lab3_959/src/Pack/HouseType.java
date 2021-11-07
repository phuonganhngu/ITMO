package Pack;

public enum HouseType {
    BIG("LIVE IN BIG HOUSE ON "),
    SMALL("LIVE IN SMALL HOUSE ON "),
    BEAUTIFULL("LIVE IN BEAUTIFUL HOUSE ON "),
    UGLY("LIVE IN UGLY HOUSE ON ");

    private final String tmpName;

    HouseType(String name) {
        this.tmpName = name;
    }

    public String getTmpName() {
        return this.tmpName;
    }
}

