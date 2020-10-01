package Client.gui;

import Client.Client;
import Client.logic.GameFrame;
import Client.logic.GameInfo;
import Client.logic.GameLoop;
import Client.logic.ThreadPool;
import Host.logic.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JFrame for game with computer settings.
 */
public class ComputerSettingsWindow extends JFrame {

    private JTextField tankLives, bulletsPower, brkWallsLives;
    private JRadioButton deathMatch, leagueMatch;
    private JButton play;
    private Settings settings;
    private String match, round;

    /**
     * Constructor for game with computer settings window .
     */
    public ComputerSettingsWindow() {
        super("Computer Mode Settings");
        this.getContentPane().setBackground(Color.white);
        this.setLocation(530, 300);
        this.setResizable(false);
        this.setSize(new Dimension(400, 400));
        this.setLayout(new BorderLayout(5, 5));
        this.setBackground(Color.DARK_GRAY);
        match = "deathMatch" ;
        round = "1";
    }

    /**
     * Set up fields and panels in computer settings window .
     */
    public void setup() {
        JTextField intro = new JTextField("Approve or Change these settings : ");
        intro.setEditable(false);
        JPanel panel1 = new JPanel(new GridLayout(1, 2));
        JLabel lbl = new JLabel("Ending mode : ");
        setupColor(lbl);
        JPanel options = new JPanel(new GridLayout(2, 1));
        deathMatch = new JRadioButton("DEATH MATCH");
        deathMatch.setOpaque(true);
        deathMatch.setFont(new Font("Helvetica", Font.BOLD, 15));
        deathMatch.setBackground(Color.DARK_GRAY);
        deathMatch.setForeground(Color.WHITE);

        deathMatch.addActionListener(new JRadioButtonHandler());
        deathMatch.setSelected(true);

        leagueMatch = new JRadioButton("LEAGUE MATCH");
        leagueMatch.setOpaque(true);
        leagueMatch.setFont(new Font("Helvetica", Font.BOLD, 15));
        leagueMatch.setBackground(Color.DARK_GRAY);
        leagueMatch.setForeground(Color.WHITE);

        leagueMatch.addActionListener(new JRadioButtonHandler());

        ButtonGroup bg = new ButtonGroup();
        bg.add(deathMatch);
        bg.add(leagueMatch);

        panel1.add(lbl);
        options.add(deathMatch);
        options.add(leagueMatch);
        panel1.add(options);

        JPanel panel2 = new JPanel(new GridLayout(3, 1));

        settings = Client.getInstance().getUser().getSettings();
        tankLives = new JTextField();
        tankLives.setText(String.valueOf(settings.getTankLives()));
        bulletsPower = new JTextField(String.valueOf(settings.getBulletsPower()));
        brkWallsLives = new JTextField(String.valueOf(settings.getTankLives()));
        JLabel lbl1 = new JLabel("  Tank Lives :    ");
        JLabel lbl2 = new JLabel("  Bullets Destructibility :    ");
        JLabel lbl3 = new JLabel("  Breakable Walls Lives :    ");
        setupColor(lbl1);
        setupColor(lbl2);
        setupColor(lbl3);

        panel2.add(lbl1);
        panel2.add(tankLives);
        panel2.add(lbl2);
        panel2.add(bulletsPower);
        panel2.add(lbl3);
        panel2.add(brkWallsLives);

        JPanel centerPanels = new JPanel();
        centerPanels.setLayout(new BoxLayout(centerPanels, BoxLayout.Y_AXIS));
        centerPanels.add(panel1);
        centerPanels.add(panel2);

        panel1.setBackground(Color.DARK_GRAY);

        play = new JButton("LET'S PLAY !");
        play.setBackground(Color.GREEN);
        play.addActionListener(new ActionHandler());

        this.add(intro, BorderLayout.NORTH);
        this.add(centerPanels, BorderLayout.CENTER);
        this.add(play, BorderLayout.SOUTH);
    }

    /**
     * Shows the settings windows .
     */
    public void showWindow() {
        this.setup();
        this.setVisible(true);
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

    /**
     * ActionHandler for play button, to start initiate game and start game loop .
     */
    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == play) {
                Settings oldSettings = Client.getInstance().getUser().getSettings();
                Settings settings = new Settings(Integer.parseInt(tankLives.getText()), Integer.parseInt(bulletsPower.getText()), Integer.parseInt(brkWallsLives.getText()), oldSettings.getUrls(), oldSettings.getTank());
                Client.getInstance().changeUserSettings(settings);
                dispose();
                MainWindow.getInstance().hideWindow();

                // Initialize the global thread-pool
                ThreadPool.init();

                // Show the game menu ...

                // After the player clicks 'PLAY' ...
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GameFrame.getInstance().setVisible(true);
                        GameFrame.getInstance().initBufferStrategy();
                        GameInfo gameInfo = new GameInfo(match, Integer.parseInt(tankLives.getText()), Integer.parseInt(bulletsPower.getText()), Integer.parseInt(brkWallsLives.getText()), Integer.parseInt(round));
                        // Create and execute the game-loop
                        GameLoop game = new GameLoop(GameFrame.getInstance(), gameInfo);
                        game.init();
                        ThreadPool.execute(game);
                        // and the game starts ...
                    }
                });


            }
        }
    }

    /**
     * ActionListener for JRadioButtons, for selecting league mode.
     */
    private class JRadioButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(deathMatch)) {
                leagueMatch.setSelected(false);
                round = "1";
                match = "deathMatch";
            } else if (e.getSource().equals(leagueMatch)) {
                deathMatch.setSelected(false);
                String input = JOptionPane.showInputDialog("Please enter number of league round number:");
                round = input;
                if (round == null) {
                    round = "1";
                }
                System.out.println(round);
                match = "leagueMatch";
            }
        }
    }
}
