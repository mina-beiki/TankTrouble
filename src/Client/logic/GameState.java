package Client.logic;

/**
 * State of the offline game consisting of the user state and computer
 * state . It concludes all the components for one state of the game to be
 * rendered and updated .
 */
public class GameState {
    private boolean gameOverComp , gameOverUser;
    private UserState userState;
    private CompState compState;

    /**
     * Generates a new game state instance .
     */
    public GameState() {
        gameOverComp = false;
        gameOverUser = false;
        userState = new UserState();
        compState = new CompState(userState.getTank());
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        userState.update();
        compState.update();
        checkEnd();

    }

    /**
     * Gets user state.
     * @return UserState
     */
    public UserState getUserState() {
        return userState;
    }

    /**
     * Gets computer state.
     * @return CompState
     */
    public CompState getCompState() {
        return compState;
    }

    /**
     * Checks if the game has finished or not and sets gameOver boolean for both computer and user player .
     */
    public void checkEnd() {
        int compX = compState.getTank().getTankX();
        int compY = compState.getTank().getTankY();
        int userX = userState.getTank().getTankX();
        int userY = userState.getTank().getTankY();

        int bulletsNum = userState.getTank().getBullets().size();
        //check user tank bullets :
        for (int i = 0; i < bulletsNum; i++) {
            int bulletX = userState.getTank().getBullets().get(i).getX();
            int bulletY = userState.getTank().getBullets().get(i).getY();
            if (Math.abs(bulletX - compX)<10 && Math.abs(bulletY - compY) < 10) {
                gameOverComp = true;
            }
            if(Math.abs(bulletX - userX)<5 && Math.abs(bulletY - userY) < 5 ){
                gameOverUser = true ;
            }
        }

        bulletsNum = compState.getTank().getBullets().size();
        //check comp player bullets :
        for (int i = 0; i < bulletsNum; i++) {
            int bulletX = compState.getTank().getBullets().get(i).getX();
            int bulletY = compState.getTank().getBullets().get(i).getY();
            if (Math.abs(bulletX - userX)<10 && Math.abs(bulletY - userY) < 10) {
                gameOverUser = true ;
            }
            if(Math.abs(bulletX - compX)<5 && Math.abs(bulletY - compY)< 5){
                gameOverComp = true ;
            }
        }
    }


    public boolean isGameOverComp() {
        return gameOverComp;
    }

    public boolean isGameOverUser() {
        return gameOverUser;
    }
}