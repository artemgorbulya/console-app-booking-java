package booking.Services;

import booking.DAO.CollectionUserDAO;
import booking.DAO.UserDAO;
import booking.Entities.Booking;
import booking.Entities.User;
import booking.Exceptions.UserNotFoundException;
import booking.Utils.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class UserService implements Serializable {
  private static final long serialVersionUID = -1285893576729249503L;
  UserDAO users = new CollectionUserDAO();
  Logger log = new Logger();

  public void addNewUser(User u) {
    if(!users.getAll().contains(u)) {
      users.add(u);
      log.addLogString("Добавлен новый пользователь " + u.nickname);
    };
    saveData();
   }

  public List<User> getAllUsers() {
    log.addLogString("Запрошен список всех пользователей");
    return users.getAll();
  }

  public User getUserByNickname(String n) throws UserNotFoundException {
   return users.getUserByNickname(n);
  }

  public void saveData() {
    users.saveFile();
    log.addLogString("коллекция пользователей сохранена");
  }
}
