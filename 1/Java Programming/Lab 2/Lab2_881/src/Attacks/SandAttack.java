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
public class SandAttack extends StatusMove{
    public SandAttack(){
        super(Type.GROUND,0,100);
    }
    @Override protected void applyOppEffects(Pokemon p)
    {
        Effect decAcc = new Effect();
        decAcc.stat(Stat.ACCURACY, -1);
        p.addEffect(decAcc);
    }
    
}
