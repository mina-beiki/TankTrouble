package Client.gui;

import Client.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndingWindow extends JFrame {
    private JLabel label ;
    private JButton play ;
    private GameInfo gameInfo ;

    public EndingWindow (GameInfo gameInfo){
        super("");
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLocation(530,300);
        this.setResizable(false);
        this.setSize(new Dimension(500,100));
        this.setLayout(new BorderLayout(5,5));
        this.gameInfo = gameInfo ;
        label = new JLabel();
        setupColor(label);
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        play = new JButton("PLAY");
        play.setBackground(new Color(91, 180, 90));
        play.addActionListener(new ActionHandler());

        this.add(label,BorderLayout.CENTER);
        this.add(play,BorderLayout.SOUTH);
    }

    public void announceWin (String userName, String mode) {
        if(mode.equals("deathMatch")){
            label.setText(userName + " YOU WON ! Play Again ? ");

        }else {
            label.setText(userName + " YOU WON ! Play next round :");
        }
    }

    public void announceLose (String userName , String mode){
        if(mode.equals("deathMatch")){
            label.setText(userName+" YOU LOST ! Play Again ? ");
        }else {
            label.setText(userName + " YOU LOST ! Play next round :");
        }
    }

    public void closeWindow (){
        this.setVisible(false);
    }

    public void showWindow (){
        this.setVisible(true);
    }

    private class ActionHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == play){
                ThreadPool.init();
                // After the player clicks 'PLAY' ...
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GameFrame.getInstance().setVisible(true);
                        GameFrame.getInstance().initBufferStrategy();
                        // Create and execute the game-loop
                        GameLoop game = new GameLoop(GameFrame.getInstance(), gameInfo);
                        game.init();
                        ThreadPool.execute(game);
                        // and the game starts ...
                    }
                });

                closeWindow();
            }
        }


    }

    /**
     * Sets up the background , foreground color and font for a JLabel .
     *
     * @param lbl JLabel to be changed
     */
    public void setupColor(JLabel lbl) {
        lbl.setOpaque(true);
        lbl.setFont(new Font("Helvetica", Font.BOLD, 15));
        lbl.setBackground(Color.DARK_GRAY);
        lbl.setForeground(Color.WHITE);
    }

}
