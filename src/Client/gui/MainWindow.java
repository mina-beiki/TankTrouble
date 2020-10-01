package Client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Font.BOLD;

/**
 * Main window in game which is consisted of buttons for observing settings ,
 * playing mode with computer and with online players ( with the help of server ).
 *
 * This class has a singleton design pattern
 * @author Hedieh Pourghasem
 */
public class MainWindow extends JFrame {
    private JButton settings,pcPlay,onlinePlay;
    private static MainWindow mainWindow = new MainWindow();
    /**
     * Constructs the main window by adding the different components to it and
     * shows the window .
     * Also set properties such as font , background color , logo image icon and
     * others .
     */
    public MainWindow (){
        super("Menu");

        ImageIcon frameLogo = new ImageIcon("tanklogo.jfif");
        setIconImage(frameLogo.getImage());

        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);
        JPanel buttonPanel = new JPanel(new GridLayout(4,1));
        buttonPanel.setBackground(Color.white);

        this.getContentPane().setBackground(Color.white);
        this.setLocation(530,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(300,300));

        Font  f1  = new Font(Font.SERIF, BOLD,  16);
        settings = new JButton("Settings");

        settings.setBackground(new Color(76, 152, 76));
        settings.addActionListener(new ActionHandler());
        settings.setFont(new Font("Helvetica", Font.PLAIN, 15));
        //settings.setFont(f1);
        pcPlay = new JButton("Play with computer");
        pcPlay.addActionListener(new ActionHandler());
        pcPlay.setBackground(new Color(76, 152, 76));
        pcPlay.setFont(new Font("Helvetica", Font.PLAIN, 15));
        //pcPlay.setFont(f1);
        onlinePlay = new JButton("Play with online players");
        onlinePlay.addActionListener(new ActionHandler());
        onlinePlay.setBackground(new Color(76, 152, 76));
        onlinePlay.setFont(new Font("Helvetica", Font.PLAIN, 15));
        //onlinePlay.setFont(f1);

        ImageIcon logoImage = new ImageIcon("logo.png");
        JLabel logoIcon = new JLabel(logoImage);

        add(logoIcon,mainLayout.NORTH);
        for (int i = 0; i <1 ; i++) {
            buttonPanel.add(new JLabel(""));
        }
        buttonPanel.add(settings);
        buttonPanel.add(pcPlay);
        buttonPanel.add(onlinePlay);
        add(buttonPanel,mainLayout.CENTER);

        setVisible(false);
    }

    /**
     * Mae main window visible
     */
    public void showWindow(){
        setVisible(true);
    }

    /**
     * Hide main window
     */
    public void hideWindow() { setVisible(false);}


    /**
     * ActionHandler class for settings, play with computer and online game buttons
     */
    private class ActionHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==settings){
                //show the settings window :
                SettingsWindow settingsWindow = new SettingsWindow();
                settingsWindow.showWindow();

            }
            if(e.getSource()==pcPlay){
                ComputerSettingsWindow compSet = new ComputerSettingsWindow();
                compSet.setup();
                compSet.showWindow();
            }
            if(e.getSource()==onlinePlay){
                OnlineSettingsWindow.getInstance().setup();
                OnlineSettingsWindow.getInstance().showWindow();

            }
        }
    }

    /**
     * Get MainWindow object
     * @return MainWindow of game
     */
    public static MainWindow getInstance(){
        return mainWindow;
    }
}
