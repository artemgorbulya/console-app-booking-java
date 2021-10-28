package booking.Controllers;

import booking.Entities.User;
import booking.Exceptions.UserNotFoundException;
import booking.Services.UserService;

import java.io.Serializable;
import java.util.List;

public class UserController implements Serializable {

  UserService userService = new UserService();

  public List<User> userList() {
    return userService.getAllUsers();
  }

  public void addUser(User u) {
    userService.addNewUser(u);
  }

  public User getUser(String nickname) throws UserNotFoundException {
    return userService.getUserByNickname(nickname);
  }

}
