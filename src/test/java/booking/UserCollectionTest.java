package booking;

import booking.ConstEnum.FileUtil;
import booking.DAO.CollectionUserDAO;
import booking.DAO.UserDAO;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class UserCollectionTest {

  @Test
  void saveFileTest() {
    UserDAO us = new CollectionUserDAO();
    us.saveFile();
    assertTrue(new File(FileUtil.USERS_FILE_PATH).isFile());
  }
}
