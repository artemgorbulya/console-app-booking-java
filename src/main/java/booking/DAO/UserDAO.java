package booking.DAO;

import booking.Entities.User;
import booking.Exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
  public void add(User u);
  public List<User> getAll();
  User getUserByNickname(String n) throws UserNotFoundException;
  public void saveFile();
}
