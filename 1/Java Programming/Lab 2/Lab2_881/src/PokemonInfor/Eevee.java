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
public class Eevee extends Pokemon{
    public Eevee(String name, int level){
        super(name, level);
        this.setStats(55, 55, 50, 45, 65, 55);
        this.setType(new Type[] {Type.NORMAL});
        this.setMove(new Move[] {new SandAttack(), new QuickAttack(), new Facade()});
        
    }
}