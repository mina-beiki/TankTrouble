package Client.logic;

/**
 * Stores the information of a game such as mode , tank lives , bullet power and other
 * important settings .
 */
public class GameInfo {
    private String gameMode;
    private int tankLives, bulletPower, brkWallLives, round , roundCtr ;

    /**
     * Generates an instance of GameInfo .
     * @param gameMode String , mode
     * @param tankLives int , number of lives for a tank
     * @param bulletPower int , power chosen for bullets
     * @param brkWallLives int , number of lives for breakable walls
     * @param round int , number of rounds ( used for league games )
     */
    public GameInfo(String gameMode, int tankLives, int bulletPower, int brkWallLives,int round) {
        this.gameMode = gameMode;
        this.tankLives = tankLives;
        this.bulletPower = bulletPower;
        this.round = round;
        this.brkWallLives = brkWallLives;
        roundCtr = 1 ;
    }

    /**
     * Gets rounds .
     * @return int , rounds
     */
    public int getRound() {
        return round;
    }

    /**
     * Gets game mode .
     * @return String , mode
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Gets round counter .
     * @return int , Round counter
     */
    public int getRoundCtr() {
        return roundCtr;
    }

    /**
     * Updates round counter . (Increases it)
     */
    public void updateRoundCtr (){
        roundCtr ++ ;
    }
}
