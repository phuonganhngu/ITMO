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
public class Bellsprout extends Pokemon{
    public Bellsprout(String name, int level){
        super(name, level);
        this.setStats(50, 75, 35, 70, 30, 40);
        this.setType(new Type[] {Type.GRASS, Type.POISON});
        this.setMove(new Move[] {new PoisonJab(), new EnergyBall()});
        
    }
}
