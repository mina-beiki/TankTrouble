package Host.logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * User class that has every needed information of user information
 */
public class User implements Serializable {
    private String username , password ;
    private int hoursPlayed,numOfWinsComputer,numOfWinsOnline,numOfLossesComputer,numOfLossesOnline;
    private Settings settings;

    /**
     * Cunstruct a new User with given username and password
     * @param username User username
     * @param password User password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        hoursPlayed = 0 ;
        numOfLossesComputer = 0 ;
        numOfWinsComputer = 0 ;
        numOfLossesOnline = 0 ;
        numOfWinsOnline = 0 ;
        settings = new Settings();
    }

    /**
     * get username field
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * get password field
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * get hours that user played game
     * @return hourPlayed field
     */
    public int getHoursPlayed() {
        return hoursPlayed;
    }

    /**
     * Get number of wins in game with computer
     * @return numOfWinsComputer field
     */
    public int getNumOfWinsComputer() {
        return numOfWinsComputer;
    }

    /**
     * Get number of wins in game online
     * @return numOfWinsOnline field
     */
    public int getNumOfWinsOnline() {
        return numOfWinsOnline;
    }

    public int getNumOfLossesComputer() {
        return numOfLossesComputer;
    }

    /**
     * Get number of losses in game online
     * @return numOfLossesOnline field
     */
    public int getNumOfLossesOnline() {
        return numOfLossesOnline;
    }

    /**
     * Get user save urls
     * @return arrayList of urls
     */
    public ArrayList<String> getUrls() {
        return settings.getUrls();
    }

    /**
     * Set user settings
     * @param settings settings object
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * Get users settings
     * @return settings field
     */
    public Settings getSettings() {
        return settings;
    }
}
