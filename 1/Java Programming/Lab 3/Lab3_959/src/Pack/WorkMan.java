package Pack;

public class WorkMan extends Human implements WorkSkill, SpeakSkill{
        String job;
        boolean love;
    public WorkMan(String name, int age, Position position, int volum, int speed, String job, boolean love) {
        super(name, age, position, volum, speed);
        this.job = job;
        this.love = love;
    }
       

    @Override
    public void skill() {
        System.out.println("SKILL: Punch, Run, Jump");
    }
    
    @Override
    public void punch(){
        System.out.println("PUNCH WITH " + speed  + "N");
    }

    @Override
    public void jump() {
        System.out.println("JUMP TO " + speed*0.4 + "M");
    }

    @Override
    public void run() {
        System.out.println("RUN WITH THE SPEED OF " + speed*0.8 + "KM/H" );
    }
    
    @Override
    public void WorkAs(){
        System.out.println(name + " IS WORKING AS " + job);
    }
    
    @Override
    public void Speak(){
        if (love)  System.out.println(name + " LOVES WORKING AS " + job); else
            System.out.println(name + " HATES WORKING AS " + job);
    }
}
