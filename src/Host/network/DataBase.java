package Host.network;

import Host.logic.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Data base of Main server that stores all users
 */
public class DataBase {
    private static ArrayList<User> savedUsers = new ArrayList<>();
    private static File users = new File("users.txt");

    /**
     * Read users from file and add them to savedUsers arrayList
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void loadUsersFromFile() throws IOException, ClassNotFoundException {
        savedUsers.clear();

        ObjectInputStream in;
        User user = null;
        users = new File("users.txt");
        if(!users.exists()){
            try {
                users.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            FileInputStream fileInputStream = new FileInputStream("users.txt");
            in = new ObjectInputStream(fileInputStream);

            int line_count = 0;
            while( fileInputStream.available() > 0) // check if the file stream is at the end
            {
                in.readObject();    // read from the object stream,
                //    which wraps the file stream
                line_count++;
            }
            fileInputStream.close();
            in.close();
            System.out.println(line_count);

            fileInputStream = new FileInputStream("users.txt");
            in = new ObjectInputStream(fileInputStream);

            for (int i = 0; i <line_count ; i++) {
                user = (User) in.readObject();
                savedUsers.add(user);
            }
        }


    }

    /**
     * Return arrayList of saveUsers after loading them from file
     * @return savedUsers filed
     */
    public static ArrayList<User> getSavedUsers() {
        try {
            loadUsersFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return savedUsers;
    }

    /**
     * Getter for savedUser fielr
     * @return savedUsers
     */
    public static ArrayList<User> getArrayList(){
        return savedUsers;
    }

    /**
     * Add new user to arrayList with given username and password
     * @param username new user username
     * @param pass new user password
     * @throws IOException
     */
    public static void addUser(String username,String pass) throws IOException {
        System.out.println(users);
        User user = new User(username,pass);
        ObjectOutputStream out;

        if(users.length() == 0){
            out = new ObjectOutputStream(new FileOutputStream("users.txt"));
        }else {
            out = new ObjectOutputStream(new FileOutputStream("users.txt", true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
        }

        out.writeObject(user);
        out.close();

        try {
            loadUsersFromFile();
            System.out.println(users);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update file from savedUsers
     * @throws IOException
     */
    public static void updateFile() throws IOException {
        ObjectOutputStream out;
        clearTheFile();
        out = new ObjectOutputStream(new FileOutputStream("users.txt"));
        for(User user: savedUsers){
            out.writeObject(user);
        }
        out.close();

    }

    /**
     * Add a User object to savedUsers arrayList
     * @param user user to add
     */
    public static void addUserToArray(User user){
        savedUsers.add(user);
        try {
            updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a given user from arrayList
     * @param user User to remove
     */
    public static void removeUserFromArray(User user){
        savedUsers.remove(user);
    }

    /**
     * This method clear users.txt file
     * @throws IOException
     */
    public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter("users.txt", false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
}
