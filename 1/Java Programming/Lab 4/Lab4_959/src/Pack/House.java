package Pack;

public class House {
    private HouseType type;

    public House(HouseType type) {
        this.type = type;
    }

    public HouseType getType() {
        return type;
    }

    public void setType(HouseType type) {
        this.type = type;
    }

    //Equals
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return  true;
        if (obj instanceof House){
            House house = (House)obj;
            if (house.type == this.type) return true;
        }
        return false;
    }
    
    //HashCode
    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString(){
        HouseAddress houseAdress = null;
        final String sType = type.getTmpName();
        String sAddress = null;
        if (type ==  HouseType.BIG){
            sAddress =  HouseAddress.BIG.getDirectionCode();
        } else {
            if (type == HouseType.BEAUTIFULL){
                sAddress = HouseAddress.BEAUTIFULL.getDirectionCode();
            } else {
                if (type == HouseType.SMALL){
                    sAddress = HouseAddress.SMALL.getDirectionCode();
                } else {
                    if (type == HouseType.UGLY) {
                        sAddress = HouseAddress.UGLY.getDirectionCode();
                    }
                }
            }
        }
        return sType + sAddress;
    }
}
