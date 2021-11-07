/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonInfor;
import Attacks.*;
import ru.ifmo.se.pokemon.*;

/**
 *
 * @author phany
 */
public class Weepinbell extends Bellsprout{
    public Weepinbell(String name, int level){
        super(name, level);
        this.setStats(65, 90, 50, 85, 45, 55);
        this.setType(new Type[] {Type.GRASS, Type.POISON});
        this.setMove(new Move[] {new PoisonJab(), new EnergyBall(), new StunSpore()});
        
    }
}
