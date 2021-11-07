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
public class EnergyBall extends SpecialMove{
    public EnergyBall(){
        super(Type.GRASS, 90, 100);
    }
    @Override protected void applyOppEffects(Pokemon p)
    {
        Effect decSpecDef = new Effect();
        decSpecDef.stat(Stat.SPECIAL_DEFENSE, -1);
        decSpecDef.chance(0.1);
        p.addEffect(decSpecDef);
    }
    
}
