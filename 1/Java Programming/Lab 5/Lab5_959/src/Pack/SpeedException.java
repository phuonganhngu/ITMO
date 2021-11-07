
package Pack;


public class SpeedException extends RuntimeException {
 @Override
    public String getMessage(){
        return "Speed value is wrong";
    }
}
