package Pack;

public class Check {
    public void checkLocation(Human Man1, Human Man2){
        //equal() and hashCode()
        if (Man2.position.equals(Man1.position)) 
         System.out.println(Man1.name + " AND " + Man2.name + " ARE AT THE SAME PLACE"); else
            System.out.println(Man1.name + " AND " + Man2.name + " ARE NOT AT THE SAME PLACE");   
    }   

    public Check() {
    }
}
