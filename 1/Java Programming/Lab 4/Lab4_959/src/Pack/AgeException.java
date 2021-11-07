
package Pack;

public class AgeException extends Exception{
     
    @Override
    public String getMessage(){
        return "Age value is wrong";
    }
}
