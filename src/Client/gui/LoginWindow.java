package Client.gui;

import Client.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

/**
 *The first window in game which is for sign up/login . Includes text fields for
 * entering username and password and also has the ability to remember a signed up
 * user's name and password .
 *
 *This class has a singleton design pattern
 * @author Mina Beiki
 */
public class LoginWindow extends JFrame {

    private JTextField userField ;
    private JPasswordField passField ;
    private JCheckBox rememberMe ;
    private JLabel userLabel , passLabel ;
    private JButton signup, login;
    private static LoginWindow loginWindow = new LoginWindow();

    /**
     * Constructs the initials of login window and set the properties such as
     * sizes and background color .
     */
    public LoginWindow(){
        super("Login");
        ImageIcon frameLogo = new ImageIcon("tanklogo.jfif");
        setIconImage(frameLogo.getImage());
        this.setLocation(10,10);
        this.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(300,300));
        userField = new JTextField();
        passField = new JPasswordField();
        userField.setPreferredSize(new Dimension(100,25));
        passField.setPreferredSize(new Dimension(100,25));
        userLabel = new JLabel("username            ");
        passLabel = new JLabel("password            ");
        rememberMe = new JCheckBox("remember me");
        rememberMe.addActionListener(new ActionHandler());
        signup = new JButton("sign up");
        signup.addActionListener(new ActionHandler());
        login = new JButton("log in");
        login.addActionListener(new ActionHandler());
    }


    /**
     * Adds all the components to the main window and set their properties .
     */
    public void setup (){

        getRememberInfo();

        this.setLayout(new BorderLayout(5,5));
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout());
        ImageIcon userImage = new ImageIcon("user.png");
        JLabel userIcon = new JLabel(userImage);
        userPanel.add(userIcon);
        userPanel.add(userLabel);
        userPanel.add(userField);
        userPanel.setBackground(Color.white);
        JPanel passPanel = new JPanel();
        passPanel.setLayout(new FlowLayout());
        passPanel.setBackground(Color.white);
        ImageIcon passImage = new ImageIcon("password.jpeg");
        JLabel passIcon = new JLabel(passImage);
        passPanel.add(passIcon);
        passPanel.add(passLabel);
        passPanel.add(passField);

        JPanel rememberPanel = new JPanel();
        rememberPanel.add(rememberMe);
        rememberPanel.setBackground(Color.white);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5,1));
        centerPanel.add(userPanel);
        centerPanel.add(passPanel);
        centerPanel.add(rememberPanel);
        centerPanel.add(signup);
        centerPanel.add(login);
        ImageIcon logoImage = new ImageIcon("logo.png");
        JLabel logoIcon = new JLabel(logoImage);

        //JPanel southPanel
        this.add(logoIcon,BorderLayout.NORTH);
        this.add(centerPanel,BorderLayout.CENTER);
        signup.setBackground(new Color(76, 152, 76));
        signup.setFont(new Font("Helvetica", Font.PLAIN, 15));

        login.setBackground(new Color(76, 152, 76));
        login.setFont(new Font("Helvetica", Font.PLAIN, 15));


    }

    /**
     * Shows the window .
     */
    public void showWindow (){
        this.setVisible(true);
    }

    /**
     * Inner class for handling action events which here is clicking the
     * "enter" button on window .
     */

    /**
     * hides the window .
     */
    public void hideWindow (){
        this.setVisible(false);
    }

    /**
     * This is a private action listener class for signup, login and rememberme buttons
     */
    private class ActionHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if(e.getSource().equals(rememberMe)){
                if(rememberMe.isSelected()){
                    rememberUserInfo(user,pass);
                }else {
                    clearTheFile();
                }
            }
            if(e.getSource()==login){
                Client.getInstance().connectServer(user,pass,"login");
            }
            if(e.getSource()==signup){
                Client.getInstance().connectServer(user,pass,"signup");
            }
        }
    }

    /**
     * Write user log in information to a remember.txt file
     * @param user username of the user
     * @param pass password of the user
     */
    private void rememberUserInfo(String user,String pass){
        File file;
        file = new File("remember.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clearTheFile();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(user+"\n"+pass);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read saved information from remember.txt file
     * and fill username and password fields with users information
     */
    private void getRememberInfo(){
        File file = new File("remember.txt");
        if(file.exists()){
            try {
                Scanner myReader = new Scanner(file);
                if(myReader.hasNextLine()){
                    String user = myReader.nextLine();
                    userField.setText(user);
                    String pass = myReader.nextLine();
                    passField.setText(pass);
                    rememberMe.setSelected(true);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * clear existed file
     * @throws IOException
     */
    private void clearTheFile()  {
        FileWriter fwOb = null;
        try {
            fwOb = new FileWriter("remember.txt", false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get LoginWindow object
     * @return LoginWindow client login window
     */
    public static LoginWindow getInstance(){
        return loginWindow;
    }
}
