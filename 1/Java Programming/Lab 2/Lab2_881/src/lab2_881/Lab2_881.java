package lab2_881;

import ru.ifmo.se.pokemon.*;
import PokemonInfor.*;
import Attacks.*;

public class Lab2_881 {

    public static void main(String[] args) {
        
        Bellsprout bel1 = new Bellsprout("England", 12);
        Bellsprout bel2 = new Bellsprout("Finland", 5);

        Comfey com1 = new Comfey("Austria", 5);
        Comfey com2 = new Comfey("France", 6);

        Eevee eev1 = new Eevee("Germany", 8);
        Eevee eev2 = new Eevee("Italy", 3);

        Jolteon jol1 = new Jolteon("Spain", 3);
        Jolteon jol2 = new Jolteon("Russia", 1);

        Victreebel vic1 = new Victreebel("Canada",5);
        Victreebel vic2 = new Victreebel("Mexico",7);

        Weepinbell wee1 = new Weepinbell("Brazil",4);
        Weepinbell wee2 = new Weepinbell("Bolivia",5);

        Battle testBattle = new Battle();

        testBattle.addAlly(bel1);
        testBattle.addAlly(com1);
        testBattle.addAlly(eev1);
        testBattle.addAlly(jol1);
        testBattle.addAlly(vic1);
        testBattle.addAlly(wee1);

        testBattle.addFoe(bel2);
        testBattle.addFoe(com2);
        testBattle.addFoe(eev2);
        testBattle.addFoe(jol2);
        testBattle.addFoe(vic2);
        testBattle.addFoe(wee2);

        testBattle.go();
    }
    
}
