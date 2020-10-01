package GameServer;

import java.util.Objects;


/**
 * A wall piece object in the game which consists of its location start and ending
 * point , its lives , type ( which is smp or brk ) and lineType which means if it is
 * horizontal or vertical .
 *
 */
public class Wall {
    private int x1 , x2 , y1 ,y2 , lives;
    private String type , lineType ; //brk , smp


    /**
     * Generates a new wall with the given start and ending point and its type and line
     * type .
     * @param x1 int , starting point x
     * @param y1 int , starting point y
     * @param x2 int , ending point x
     * @param y2 int , ending point y
     * @param type String , brk or smp
     * @param lineType string , ver or hor
     */
    public Wall(int x1, int y1, int x2, int y2 , String type , String lineType) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.type = type ;
        this.lineType = lineType ;
        if(lineType.equals("brk")){
            //TODO : set this from settings
            lives = 0;
        }
    }


    /**
     * gets the tank x1 .
     * @return int , x1
     */
    public int getX1() {
        return x1;
    }

    /**
     * Gets ending point x of wall line .
     * @return int , x2
     */
    public int getX2() {
        return x2;
    }


    /**
     * Gets starting point y1 of wall line .
     * @return int , y1
     */
    public int getY1() {
        return y1;
    }


    /**
     * Gets ending point y of wall line .
     * @return int , y2
     */
    public int getY2() {
        return y2;
    }


    /**
     * Gets line type which is ver ( vertical ) or hor ( horizontal ) .
     * @return String , line type
     */
    public String getLineType() {
        return lineType;
    }

    /**
     * Gets the type of a wall , which is smp ( simple ) or brk ( breakable ) .
     * @return String , type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the lives number of a wall .
     * @return int , lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Checks if 2 wall are the same or not.
     * @param o object to be checked
     * @return true if the are the same and false if they are not .
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wall)) return false;
        Wall wall = (Wall) o;
        return getX1() == wall.getX1() &&
                getX2() == wall.getX2() &&
                getY1() == wall.getY1() &&
                getY2() == wall.getY2() &&
                getLives() == wall.getLives() &&
                getType().equals(wall.getType()) &&
                getLineType().equals(wall.getLineType());
    }

    /**
     * Generates hashcode for each wall .
     * @return int , hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX1(), getX2(), getY1(), getY2(), getLives(), getType(), getLineType());
    }
}
