package Pack;

public class UnfamiliarMan extends Human implements SpeakSkill{
        public UnfamiliarMan(String name, int age, Position position, int volum, int speed) {
        super(name, age, position, volum, speed);
    }
    
    @Override
    public void skill() {
        System.out.println("SKILL: Punch, Run, Jump");
    }
    @Override
    public void punch(){
        System.out.println("PUNCH WITH " + speed*1.5  + "N");
    }

    @Override
    public void jump() {
        System.out.println("JUMP TO " + speed*0.8 + "M");
    }

    @Override
    public void run() {
        System.out.println("RUN WITH THE SPEED OF " + speed + "KM/H" );
    }

    @Override
    public void Speak(){
        System.out.println("PLEASE HELP ME TO FIND MY FRIEND");
    }

    
}
