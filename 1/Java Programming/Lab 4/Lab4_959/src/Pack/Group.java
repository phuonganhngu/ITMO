package Pack;

import java.util.ArrayList;
import java.util.List;

public class Group  {
    private static Human human;
    //Contructor
    Group() { }

    static List <Human> humanList = new ArrayList<Human>();

    public void add(Human human){
        //Local inner class
        class AddGroup{
            void solve(){
                humanList.add(human);
            }
        }
        AddGroup addGroup = new AddGroup();
        addGroup.solve();
    }
    
    //Nested class
    static class SizeGroup {
        int getSize(){
            return  humanList.size();
        }
        
    }

    public Human getHuman(int counter){
        return humanList.get(counter);
    }
    
}

