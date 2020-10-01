package Client.logic;


import Client.gui.EndingWindow;


/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;

    private GameFrame canvas;
    private GameState state;
    private GameInfo gameInfo;
    private boolean gameOver;

    public GameLoop(GameFrame frame, GameInfo gameInfo) {
        canvas = frame;
        this.gameInfo = gameInfo;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        // Perform all initializations ...
        state = new GameState();
        canvas.addKeyListener(state.getUserState().getKeyListener());
    }

    /**
     * Runs and executes a game by rendering the game state and updating it each time .
     */
    @Override
    public void run() {
        gameOver = false;
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                //
                state.update();
                canvas.render(state);

                //
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);

                if (state.isGameOverComp() || state.isGameOverUser()) {
                    gameOver = true ;
                }
                //checkGameOver();

            } catch (InterruptedException ex) {
            }
        }

        //sleep 2 seconds :
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //hide window :
        GameFrame.getInstance().setVisible(false);
        if(gameOver) {
            if (gameInfo.getRoundCtr() == gameInfo.getRound()) {
                System.out.println("finish");
                //do nothing
            } else {
                ThreadPool.shutdown();
                EndingWindow endingWindow = new EndingWindow(gameInfo);
                if (state.isGameOverComp()) {
                    endingWindow.announceWin(state.getUserState().getUserName(), gameInfo.getGameMode());
                }
                if (state.isGameOverUser()) {
                    endingWindow.announceLose(state.getUserState().getUserName(), gameInfo.getGameMode());
                }
                if (gameInfo.getGameMode().equals("leagueMatch")) {
                    gameInfo.updateRoundCtr();
                }

                endingWindow.showWindow();
            }
        }


    }

}
