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
public class Comfey extends Pokemon{
    public Comfey(String name, int level){
        super(name, level);
        this.setStats(51, 52, 90, 82, 110, 100);
        this.setType(new Type[] {Type.FAIRY});
        this.setMove(new Move[] {new SweetKiss(), new DazzlingGleam(), new Facade(), new Confide()});
        
    }
}
