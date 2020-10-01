package Client.gui;

import Client.Client;
import Client.onlineGUI.GameFrameUpdater;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The panel which shows information of saved games in a game server.
 */
public class GamePanel extends JPanel {

    private JButton play;
    private JLabel name, mode, minPlayers, currentPlayer,endingMode;
    private JFrame frame;

    //TODO : add parameters for making a new game panel

    /**
     * Game panel constructor
     */
    public GamePanel(String gameName,String gameMode,String gameEnd,int gameMinPlayers,int gameCurrentPlayer){
        //TODO: currentplayer update

        super(new BorderLayout(5,5));
        this.setBackground(Color.DARK_GRAY);

        JPanel settings = new JPanel(new GridLayout(5, 2));
        settings.setBackground(Color.DARK_GRAY);
        name = new JLabel(gameName);
        mode = new JLabel(gameMode);
        minPlayers = new JLabel(String.valueOf(gameMinPlayers));
        currentPlayer = new JLabel(String.valueOf(gameCurrentPlayer));
        endingMode = new JLabel(gameEnd);

        setupColor2(name);
        setupColor2(mode);
        setupColor2(minPlayers);
        setupColor2(currentPlayer);
        setupColor2(endingMode);


        JLabel lbl3 = new JLabel("Name :    ");
        JLabel lbl4 = new JLabel("Mode :    ");
        JLabel lbl5 = new JLabel("Ending Mode :    ");
        JLabel lbl6 = new JLabel("Minimum Players Number:    ");
        JLabel lbl7 = new JLabel("Current Players Number :    ");

        setupColor(lbl3);
        setupColor(lbl4);
        setupColor(lbl5);
        setupColor(lbl6);
        setupColor(lbl7);


        settings.add(lbl3);
        settings.add(name);
        settings.add(lbl4);
        settings.add(mode);
        settings.add(lbl5);
        settings.add(endingMode);
        settings.add(lbl6);
        settings.add(minPlayers);
        settings.add(lbl7);
        settings.add(currentPlayer);

        play = new JButton("I'M IN !");
        play.setBackground(Color.GREEN);
        play.addActionListener(new ActionHandler());

        this.add(settings, BorderLayout.CENTER);
        this.add(play, BorderLayout.SOUTH);

    }

    /**
     * Sets up the background , foreground color and font for a JLabel .
     *
     * @param lbl JLabel to be changed
     */
    public void setupColor(JLabel lbl) {
        lbl.setOpaque(true);
        lbl.setFont(new Font("Helvetica", Font.BOLD, 11));
        lbl.setBackground(Color.DARK_GRAY);
        lbl.setForeground(Color.WHITE);
    }

    public void setupColor2(JLabel lbl) {
        lbl.setOpaque(true);
        lbl.setFont(new Font("Helvetica", Font.PLAIN, 11));
        lbl.setBackground(Color.DARK_GRAY);
        lbl.setForeground(Color.WHITE);
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == play){
                System.out.println("play");
                OnlineSettingsWindow.getInstance().hideWindow();
                MainWindow.getInstance().hideWindow();
//                waitFrame();
                frame = new JFrame();
//            frame.getContentPane().setBackground(Color.GREEN);
                frame.setLocation(530, 300);
                frame.setResizable(true);
                frame.setSize(new Dimension(400,400));
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        JOptionPane.showMessageDialog(frame,"waiting ....","MESSAGE",JOptionPane.INFORMATION_MESSAGE);

//                    }
//                }).start();
                //TODO
                Client.getInstance().joinGame(name.getText(),frame);
                Thread thread = new Thread(new GameFrameUpdater(Client.getInstance().getSocket()));
                thread.start();
            }
        }

        private void waitFrame(){
            frame = new JFrame();
//            frame.getContentPane().setBackground(Color.GREEN);
            frame.setLocation(530, 300);
            frame.setResizable(true);
            frame.setSize(new Dimension(300,300));

            frame.setLayout(null);
            JLabel label = new JLabel("Waiting for other players to\njoin the game ...");
            label.setPreferredSize(new Dimension(300,300));
            frame.add(label,BorderLayout.CENTER);

            frame.setVisible(true);
        }
    }

    public void setCurrentPlayer(String num){
        currentPlayer.setText(num);
    }

}
