/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Attacks;
import ru.ifmo.se.pokemon.*;
/**
 *
 * @author phany
 */
public class SweetScent extends StatusMove{
    public SweetScent(){
        super(Type.NORMAL, 0, 0);
    }
    @Override protected void applyOppEffects(Pokemon p)
    {
        Effect decEvasion = new Effect();
        decEvasion.stat(Stat.EVASION, -1);
        p.addEffect(decEvasion);
    }
}
