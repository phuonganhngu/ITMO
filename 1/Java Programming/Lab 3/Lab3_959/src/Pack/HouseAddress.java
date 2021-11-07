package Pack;

public enum HouseAddress {
    BIG ("No 12_Lombart Street_Francisco Town_USA "),
    SMALL ("No 11_Great Ocean Street_Victoria Town_Australia "),
    BEAUTIFULL("No 10_Las Vegas Boulevard South Street_Nevada Town_USA "),
    UGLY("No 15_Stelvio Street_Sondrio Town_ITALIA ");

    private final String shortCode;

    HouseAddress(String code) {
        this.shortCode = code;
    }

    public String getDirectionCode(){
        return  this.shortCode;
    }
}

