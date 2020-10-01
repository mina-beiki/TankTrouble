package Client.gui;

import Client.Client;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Online game settings window.From this window user can create new game or join a game that already exist in
 * server.
 */
public class OnlineSettingsWindow extends JFrame {
    private JButton next, play, createGame;
    private ArrayList<JButton> urlButtons;
    private ArrayList<GamePanel> gamePanels;
    private ArrayList<JsonObject> serverGames;
    private JPanel  newGame , prepGame , urls ;
    private JTextField name, minPlayers, currentPlayer;
    private JComboBox endingMode,mode;
    private JTabbedPane tabs ;
    private JScrollPane scrollBarUrls;
    private JPanel games;
    private static OnlineSettingsWindow onlineSettingsWindow = new OnlineSettingsWindow();

    public static OnlineSettingsWindow getInstance(){
        return onlineSettingsWindow;
    }

    /**
     * Constructor for online settings window
     */
    private OnlineSettingsWindow() {
        super("Online Mode Settings");
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLocation(530, 300);
        this.setResizable(true);
        this.setSize(new Dimension(400, 400));
        this.setLayout(new BorderLayout(5, 5));
        urlButtons = new ArrayList<>();
    }

    /**
     * Setup fields and panels in online settings window
     */
    public void setup() {
        this.setLayout(new BorderLayout(5, 5));
        this.setBackground(Color.DARK_GRAY);
        this.setSize(new Dimension(300,300));
        JLabel lbl = new JLabel("List of URLs :");
        setupColor(lbl);
        urls = new JPanel();
        urls.setBackground(Color.DARK_GRAY);
        urls.setLayout(new BoxLayout(urls, BoxLayout.Y_AXIS));
        scrollBarUrls = new JScrollPane(urls,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        for(String url: Client.getInstance().getUser().getSettings().getUrls()){
//            System.out.println(url + "boo");
            addUrlButton(url);
            System.out.println("url : "+url);
        }

        System.out.println( Client.getInstance().getUser().getSettings().getUrls().size());


//        next = new JButton("next");
//        next.addActionListener(new ActionHandler());
//        next.setBackground(Color.GREEN);

        this.add(lbl, BorderLayout.NORTH);
        this.add(scrollBarUrls, BorderLayout.CENTER);

    }

    /**
     * Set up create new game tab and
     * list of previously created tabs
     */
    public void setupTabs() {
        tabs = new JTabbedPane();
        newGame = new JPanel(new BorderLayout(5,5));
        newGame.setBackground(Color.DARK_GRAY);

        JPanel settings = new JPanel(new GridLayout(5, 2));
        settings.setBackground(Color.DARK_GRAY);
        name = new JTextField("");
        String[] modeOptions = {"Solo" ,"Team"};
        mode = new JComboBox(modeOptions);
        minPlayers = new JTextField("");
        String[] endOptions = {"DeathMatch", "LeagueMatch"};
        endingMode = new JComboBox(endOptions);

        JLabel lbl3 = new JLabel("Name :    ");
        JLabel lbl4 = new JLabel("Mode :    ");
        JLabel lbl5 = new JLabel("Ending Mode :    ");
        JLabel lbl6 = new JLabel("Minimum Players Number:    ");

        setupColor(lbl3);
        setupColor(lbl4);
        setupColor(lbl5);
        setupColor(lbl6);


        settings.add(lbl3);
        settings.add(name);
        settings.add(lbl4);
        settings.add(mode);
        settings.add(lbl5);
        settings.add(endingMode);
        settings.add(lbl6);
        settings.add(minPlayers);

        createGame = new JButton("Create this game");
        createGame.addActionListener(new ActionHandler());
        createGame.setBackground(Color.GREEN);

        newGame.add(settings, BorderLayout.CENTER);
        newGame.add(createGame, BorderLayout.SOUTH);

        tabs.add("New Game",newGame);

        prepGame = new JPanel();
        prepGame.setLayout(new BorderLayout(5, 5));
        prepGame.setBackground(Color.DARK_GRAY);
        JLabel lbl2 = new JLabel("list Of Games : ");
        setupColor(lbl2);
        games = new JPanel();
        games.setBackground(Color.DARK_GRAY);
        games.setLayout(new BoxLayout(games, BoxLayout.Y_AXIS));
        JScrollPane scrollBarGames = new JScrollPane(games,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        addGamesToPanel();

//        play = new JButton("LET'S PLAY ! ");
//        play.setBackground(Color.GREEN);

        prepGame.add(lbl2, BorderLayout.NORTH);
        prepGame.add(scrollBarGames, BorderLayout.CENTER);
//        prepGame.add(play, BorderLayout.SOUTH);

        tabs.add("Prepared Games", prepGame);

        this.add(tabs,BorderLayout.CENTER);

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
     * ActionHandler class for createGame button .
     */
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            if (e.getSource() == next) {
//                scrollBarUrls.setVisible(false);
//                setupTabs();
//            }
            if(e.getSource() == createGame){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name",name.getText());
                jsonObject.addProperty("mode",(String) mode.getSelectedItem());
                jsonObject.addProperty("endMode",(String) endingMode.getSelectedItem());
                jsonObject.addProperty("minPlayers",Integer.parseInt(minPlayers.getText()));
                try {
                    serverGames = Client.getInstance().makeGame(jsonObject);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                name.setText("");
                mode.setSelectedItem("Solo");
                endingMode.setSelectedItem("DeathMatch");
                minPlayers.setText("");
                addGamesToPanel();
            }
        }
    }

    private void addGamesToPanel(){
        games.removeAll();
        for(JsonObject jsonObject:serverGames){
            GamePanel panel = new GamePanel(jsonObject.get("name").getAsString(),jsonObject.get("mode").getAsString()
                    ,jsonObject.get("endMode").getAsString(),jsonObject.get("minPlayers").getAsInt(),jsonObject.get("currentPlayer").getAsInt());
            games.add(panel);
        }
        games.revalidate();
        games.repaint();
    }

    /**
     * Shows the window .
     */
    public void showWindow() {
        this.setVisible(true);
    }
    public void hideWindow(){this.setVisible(false);}

    /**
     * This method create url buttons from saved urls from user settings
     * And add ActionListener to these buttons. So with press of the button
     * client connect to a game server.
     * @param str A saved server URL
     */
    private void addUrlButton(String str){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300,50));
        panel.setMaximumSize(new Dimension(300,50));
        panel.setLayout(new GridLayout(1,1));
        JButton btn = new JButton(str);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = btn.getText();
                serverGames = Client.getInstance().ConnectToGameServer(str.split(":")[0],Integer.parseInt(str.split(":")[1]));
                scrollBarUrls.setVisible(false);
                setupTabs();
//                add(next, BorderLayout.SOUTH);
            }
        });
        panel.add(btn);
        urlButtons.add(btn);
        urls.add(panel);
    }



}
