import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    String username;
    String password;
    Wallet wallet;
    static Map<String, User> users = new HashMap<>();

    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();

    }

    public static void saveUserProfiles(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserProfiles(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Ошибка: Файл не найден: " + filename);
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            users = (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}