package Pack;

public abstract class Human extends Animal{
    public Human(String name, int age, int volum, int speed) {
        super(name, age, volum, speed);
        
    }
    
    interface SuperRunSkill{
        public void superrun();
    }
    
    SuperRunSkill redHaste = new SuperRunSkill() {
        @Override
        public void superrun(){
            incSpeed(2);
        }
    };
    
    SuperRunSkill blackHaste = new SuperRunSkill() {
        @Override
        public void superrun(){
            decSpeed(2);
        }
    };
            

    abstract void skill();
    abstract void run();
    abstract void jump();
    abstract void punch();

    @Override
    public String toString() {
        return "*" + name + " " + age + " YEARS OLD ";
    }   
    
     class Haste implements SuperRunSkill {
    @Override
        public void superrun(){
            System.out.println("this is bottle rune special"+name);
        }
    }

    public void setAge(int age) throws AgeException {
        if (age <0) {
            throw new AgeException();
        }
        this.age = age;
    }

    public void setSpeed(int speed) {
        if (speed < 0){
            throw new SpeedException();
        }
        this.speed = speed;
    }
    
    public void decSpeed(int time) {
        this.speed = speed/time;
    }
    
    public void incSpeed(int time) {
        this.speed = speed*time;
    }
         
    protected void test(){
        
    }
}
    


