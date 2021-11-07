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
public class Jolteon extends Eevee{
    public Jolteon(String name, int level){
        super(name, level);
        this.setStats(65, 65, 60, 110, 95, 130);
        this.setType(new Type[] {Type.ELECTRIC});
        this.setMove(new Move[] {new SandAttack(), new QuickAttack(), new Facade(), new ThunderShock()});
        
    }
}
