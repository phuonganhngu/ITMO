
package Pack;

import java.awt.Point;
import java.util.Objects;


public class Position extends Point{
    Point point;
    
    public Position(int x, int y){
        point = new Point(x, y);
    }
    
    public Point getPoint() {
        return point;
    }
    
    @Override
    public int hashCode(){
        return point.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (Objects.equals(this.point, other.point)) {
            return false;
        }
        return true;
    }
}
