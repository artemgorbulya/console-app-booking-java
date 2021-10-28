package booking;

import booking.Entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

  @Test
  public void makeNicknameTest() {
    String name = "Nick";
    String surname = "Johnson";

    User u = new User(name, surname);

    assertEquals("njohnson",  u.makeNickname(name, surname));
  }
}
