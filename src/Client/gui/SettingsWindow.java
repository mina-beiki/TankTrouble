package Client.gui;

import Client.Client;
import Host.logic.Settings;
import Host.logic.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static java.awt.Font.BOLD;

/**
 * Settings window which is included of game , user and server settings .
 * In each part user can observe some settings or change the mutable ones with
 * clicking on apply button .
 *
 * @author Mina Beiki
 */
public class SettingsWindow extends JFrame {

    private JPanel userSet, gameSet, serverSet, listPanel;
    private JTextField hoursPlayed, username, compWins, compLosses, onlineWins, onlineLosses, tankLives, bulletsPower, brkWallsLives;
    private JTabbedPane tabs;
    private JButton apply, addUrl;
    private ArrayList<JTextField> portList, ipList;
    private User user;
    private Settings userSettings;
    private JComboBox colorsComboBox;
    private JLabel tankImage;
    private ImageIcon tankImageIcon;

    /**
     * Generates the settings window by initializing the components of it .
     */
    public SettingsWindow() {
        super("Settings");
        this.setLayout(new BorderLayout(5, 5));
        this.getContentPane().setBackground(Color.white);
        this.setLocation(550, 300);
        this.setResizable(false);
        this.setSize(new Dimension(400, 400));
        user = Client.getInstance().getUser();
        userSettings = user.getSettings();
        portList = new ArrayList<>();
        ipList = new ArrayList<>();
    }

    /**
     * Generates tab for user settings and adds its components . Consists of textFields
     * for hours played , user name , wins and losses in 2 modes of the game .
     */
    public void setupUserSettings() {
        tabs = new JTabbedPane();
        userSet = new JPanel();
        userSet.setLayout(new BoxLayout(userSet, BoxLayout.Y_AXIS));
        //panel 1 in user settings :
        JPanel userSetPanel1 = new JPanel();
        userSetPanel1.setLayout(new GridLayout(2, 2));
        //hours played :
        JLabel hoursPlayedLbl = new JLabel("Hours Played :     ");
        hoursPlayed = new JTextField(String.valueOf(user.getHoursPlayed()));
        hoursPlayed.setEditable(false);
        setupColor(hoursPlayedLbl);
        //username :
        JLabel usernameLbl = new JLabel("Username :     ");
        setupColor(usernameLbl);
        username = new JTextField(user.getUsername());
        username.setEditable(false);

        userSetPanel1.add(usernameLbl);
        userSetPanel1.add(username);
        userSetPanel1.add(hoursPlayedLbl);
        userSetPanel1.add(hoursPlayed);

        //panel 2 in user settings :

        JPanel userSetPanel2 = new JPanel();
        userSetPanel2.setLayout(new BoxLayout(userSetPanel2, BoxLayout.Y_AXIS));
        JLabel compSetLbl = new JLabel("Game With Computers : ");
        setupColor(compSetLbl);
        JPanel lblPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPanel.add(compSetLbl);
        JLabel compWinsLbl = new JLabel("Wins : ");
        setupColor(compWinsLbl);
        compWins = new JTextField(String.valueOf(user.getNumOfWinsComputer()));
        compWins.setPreferredSize(new Dimension(70, 20));
        compWins.setEditable(false);
        JLabel compLossesLbl = new JLabel("Losses : ");
        setupColor(compLossesLbl);
        compLosses = new JTextField(String.valueOf(user.getNumOfLossesComputer()));
        compLosses.setEditable(false);
        JPanel panel2Grid = new JPanel(new GridLayout(2, 2));
        panel2Grid.add(compWinsLbl);
        panel2Grid.add(compWins);
        panel2Grid.add(compLossesLbl);
        panel2Grid.add(compLosses);

        userSetPanel2.add(lblPanel);
        userSetPanel2.add(panel2Grid);

        //panel 3 in user settings :

        JPanel userSetPanel3 = new JPanel();
        userSetPanel3.setLayout(new BoxLayout(userSetPanel3, BoxLayout.Y_AXIS));
        JLabel onlineSetLbl = new JLabel("Game With Online Players : ");
        setupColor(onlineSetLbl);
        JPanel lblPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPanel2.add(onlineSetLbl);
        lblPanel2.setFont(new Font("Dialog", Font.BOLD, 12));
        JLabel onlineWinsLbl = new JLabel("Wins : ");
        setupColor(onlineWinsLbl);

        onlineWins = new JTextField(String.valueOf(user.getNumOfWinsOnline()));
        onlineWins.setEditable(false);
        JLabel onlineLossesLbl = new JLabel("Losses : ");
        setupColor(onlineLossesLbl);
        onlineLosses = new JTextField(String.valueOf(user.getNumOfLossesOnline()));
        onlineLosses.setEditable(false);
        JPanel panel3Grid = new JPanel(new GridLayout(2, 2));
        panel3Grid.add(onlineWinsLbl);
        panel3Grid.add(onlineWins);
        panel3Grid.add(onlineLossesLbl);
        panel3Grid.add(onlineLosses);

        userSetPanel3.add(lblPanel2);
        userSetPanel3.add(panel3Grid);

        //panel 4 in user settings :


        JPanel userSetPanel4 = new JPanel(new GridLayout(2, 2));
        JLabel tankColor = new JLabel("Tank Color : ");
        setupColor(tankColor);
        String[] colors = {"Green", "Red", "Blue", "Black", "White"};
        colorsComboBox = new JComboBox(colors);

        JLabel tankShape = new JLabel("Tank Shape : ");
        setupColor(tankShape);

        tankImageIcon = new ImageIcon("tanks/white.png");
        tankImage = new JLabel(tankImageIcon);

        colorsComboBox.setSelectedItem(userSettings.getTank());

        userSetPanel4.add(tankColor);
        userSetPanel4.add(colorsComboBox);
        userSetPanel4.add(tankShape);
        userSetPanel4.add(tankImage);

        userSet.add(userSetPanel1);
        JSeparator sep1 = new JSeparator();
        userSet.add(sep1);
        userSet.add(userSetPanel2);
        JSeparator sep2 = new JSeparator();
        userSet.add(sep2);
        userSet.add(userSetPanel3);
        JSeparator sep3 = new JSeparator();
        userSet.add(sep3);
        userSet.add(userSetPanel4);

        userSetPanel1.setBackground(Color.DARK_GRAY);
        userSetPanel2.setBackground(Color.DARK_GRAY);
        userSetPanel3.setBackground(Color.DARK_GRAY);
        userSetPanel4.setBackground(Color.DARK_GRAY);
        lblPanel.setBackground(Color.DARK_GRAY);
        lblPanel2.setBackground(Color.DARK_GRAY);
        panel2Grid.setBackground(Color.DARK_GRAY);
        panel3Grid.setBackground(Color.DARK_GRAY);
        userSet.setBackground(Color.DARK_GRAY);
        sep1.setBackground(Color.BLACK);
        sep2.setBackground(Color.BLACK);
        sep3.setBackground(Color.BLACK);

        tabs.add("User Settings", userSet);
        this.add(tabs);

    }

    /**
     * Generates tab for game settings which includes textFields for
     * breakable walls lives , tank walls and bullets power .
     */
    public void setupGameSettings() {
        JPanel gameSet = new JPanel();
        JPanel bars = new JPanel(new GridLayout(3, 1));
        tankLives = new JTextField();
        tankLives.setText(String.valueOf(userSettings.getTankLives()));
        bulletsPower = new JTextField(String.valueOf(userSettings.getBulletsPower()));
        brkWallsLives = new JTextField(String.valueOf(userSettings.getBrkWallsLives()));
        JLabel lbl1 = new JLabel("Tank Lives :    ");
        JLabel lbl2 = new JLabel("Bullets Destructibility :    ");
        JLabel lbl3 = new JLabel("Breakable Walls Lives :    ");
        setupColor(lbl1);
        setupColor(lbl2);
        setupColor(lbl3);

        bars.add(lbl1);
        bars.add(tankLives);
        bars.add(lbl2);
        bars.add(bulletsPower);
        bars.add(lbl3);
        bars.add(brkWallsLives);

        bars.setBackground(Color.DARK_GRAY);
        gameSet.setBackground(Color.DARK_GRAY);

        gameSet.add(bars);
        tabs.add("Game Settings", gameSet);
    }

    /**
     * Generates server settings tab which includes the list of urls used in the game .
     */
    public void setupServerSettings() {
        JPanel serverSet = new JPanel();

        serverSet.setLayout(new BorderLayout());

        JPanel lblPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lbl = new JLabel("URLs list : ");
        lblPanel.add(lbl);
        setupColor(lbl);

        addUrl = new JButton("add new url");
        addUrl.addActionListener(new AddUrlHandler());


        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollBarUrls = new JScrollPane(listPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        portList.clear();
        ipList.clear();
        for (String str : user.getUrls()) {
            addUrl(str.split(":")[0], str.split(":")[1]);
        }

        serverSet.add(lblPanel, BorderLayout.NORTH);
        serverSet.add(addUrl, BorderLayout.SOUTH);
        serverSet.add(scrollBarUrls, BorderLayout.CENTER);

        lblPanel.setBackground(Color.DARK_GRAY);
        serverSet.setBackground(Color.DARK_GRAY);


        tabs.add("Server Settings", serverSet);
        this.add(tabs, BorderLayout.CENTER);

        apply = new JButton("APPLY");
        apply.addActionListener(new applyHandler());
        apply.setBackground(new Color(91, 180, 90));
        this.add(apply, BorderLayout.SOUTH);
    }

    /**
     * Shows the settings windows .
     */
    public void showWindow() {
        this.setupUserSettings();
        this.setupGameSettings();
        this.setupServerSettings();
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
     * ActionListener for addUrl button, to add new url in client settings.
     */
    private class AddUrlHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addUrl) {
                addUrl(null, null);
            }
        }
    }

    /**
     * Add a new panel for adding new url to cliens settings
     *
     * @param ipStr   Server ip
     * @param portStr Server port
     */
    private void addUrl(String ipStr, String portStr) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 40));
        panel.setMaximumSize(new Dimension(350, 40));
        panel.setLayout(new GridLayout(1, 4));
        panel.add(new JLabel("    Server IP:"));
        JTextField ip = new JTextField(ipStr);
        ipList.add(ip);
        panel.add(ip);
        panel.add(new JLabel("          Port:"));
        JTextField port = new JTextField(portStr);
        portList.add(port);
        panel.add(port);
        listPanel.add(panel);
        listPanel.revalidate();
        listPanel.repaint();
    }

    /**
     * ActionListener for apply button. It send new settings to server and update user information
     * in server database
     */
    private class applyHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == apply) {
                Settings settings = new Settings(Integer.parseInt(tankLives.getText()), Integer.parseInt(bulletsPower.getText()), Integer.parseInt(brkWallsLives.getText()), updateUserUrls(), (String) colorsComboBox.getSelectedItem());
                Client.getInstance().changeUserSettings(settings);
                dispose();
            }

        }
    }

    /**
     * Update url information .
     * Read from JTextFields and store urls in an ArrayList .
     *
     * @return ArrayList of String of urls
     */
    public ArrayList<String> updateUserUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0; i < portList.size(); i++) {
            if (!portList.get(i).getText().equals("") && !ipList.get(i).getText().equals("")) {
                urls.add(ipList.get(i).getText() + ":" + portList.get(i).getText());
            }
        }
        return urls;
    }
}
