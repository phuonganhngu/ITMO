package PokemonInfor;
import Attacks.*;
import ru.ifmo.se.pokemon.*;

public class Victreebel extends Weepinbell{
    public Victreebel(String name, int level){
        super(name, level);
        this.setStats(80, 105, 65, 100, 70, 70);
        this.setType(new Type[] {Type.GRASS, Type.POISON});
        this.setMove(new Move[] {new PoisonJab(), new EnergyBall(), new StunSpore(), new SweetScent()});
        
    }
}