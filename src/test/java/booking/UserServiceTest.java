package booking;

import booking.Entities.User;
import booking.Exceptions.UserNotFoundException;
import booking.Services.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {

  @Test
  void getUserbyNicknameTest() throws UserNotFoundException {
    UserService us = new UserService();
    User expectedUser = new User("Kirill", "Izotov");
    us.addNewUser(expectedUser);
    assertEquals(expectedUser, us.getUserByNickname(User.makeNickname(expectedUser.firstName, expectedUser.secondName)));
  }
}
