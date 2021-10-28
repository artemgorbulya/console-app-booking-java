package booking.DAO;

import booking.ConstEnum.FileUtil;
import booking.Entities.User;
import booking.Exceptions.UserNotFoundException;

import java.io.*;
import java.util.*;

public class CollectionUserDAO implements UserDAO, FileUtil, Serializable {
  Map<String, User> userDB= new HashMap<>();

  public CollectionUserDAO() {
    File source = new File(USERS_FILE_PATH);
    if (source.isFile()) {
      System.out.println("Файл коллекции пользователей найден...");
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE_PATH))) {
        this.userDB = (Map<String, User>) ois.readObject();
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    } else {
      System.out.println("Файл коллекции пользователей отсутствует, создаю новый...");
      this.userDB = new HashMap<String, User>();
      saveFile();
    }
  }

  @Override
  public void add(User u) {
    userDB.put(u.nickname, u);
  }

  @Override
  public List<User> getAll() {
    return new ArrayList<>(userDB.values());
  }

  @Override
  public User getUserByNickname(String n) throws UserNotFoundException {
    Optional<User> lookup = Optional.of(userDB.get(n));
    if(!lookup.isPresent()) throw new UserNotFoundException(String.format("user %s not found", n));
    else return lookup.get();
  }

  @Override
    public void saveFile() {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE_PATH))) {
        oos.writeObject(userDB);
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
}
