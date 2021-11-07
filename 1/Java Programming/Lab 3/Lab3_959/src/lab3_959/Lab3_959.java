package lab3_959;
import Pack.*;

public class Lab3_959 {

    public static void main(String[] args) {
        
        
        //Nick
        Position pstNick = new Position(0 , 5);
        UnfamiliarMan Nick = new UnfamiliarMan("Nick", 23, pstNick,5,25);
        House houseN = new House(HouseType.BEAUTIFULL);
        City city3 = new City(Nick, houseN);
        System.out.println(city3);
        Nick.skill();
        Nick.run();
        Nick.punch();
        Nick.jump();
        Nick.Speak();
        
        //Phil
        Position pstPhil = new Position(0 , 5);
        WorkMan Phil = new WorkMan("Phil", 35, pstPhil, 5, 15, "BUSINESSMAN", true);
        House houseP = new House(HouseType.BIG);
        City city1 = new City(Nick, houseP);
        System.out.println(city1);
        Phil.skill();
        Phil.run();
        Phil.punch();
        Phil.jump();
        Phil.WorkAs();
        Phil.Speak();

        //Carnation
        Position pstCarnation = new Position(0 , 14);
        WorkMan Carnation = new WorkMan("Carnation", 27, pstCarnation, 5, 15, "BAKER", false);
        House houseCar = new House(HouseType.SMALL);
        City city2 = new City(Carnation, houseCar);
        System.out.println(city2);
        Carnation.skill();
        Carnation.run();
        Carnation.punch();
        Carnation.jump();
        Carnation.WorkAs();
        Carnation.Speak();

        //equal() and hashCode()
        Check check = new Check();
        check.checkLocation(Nick, Carnation);
        check.checkLocation(Nick, Phil);

        
        Position pstDraco = new Position(0 , 14);
        Dragon Draco = new Dragon("Draco", 27, pstCarnation, 5, 15, 300, "Green City");
        Draco.location();
        Draco.skill();
        Draco.fly();
        Draco.fireBreath();
    }
}
