package se.uu.it.runestone.teamone.map;

/**
 * Created by Daniel Eliassen on 13/05/15.
 */
public class Coordinate {
        private Integer x;
        private Integer y;
        public Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
