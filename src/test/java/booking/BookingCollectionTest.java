package booking;

import booking.ConstEnum.*;
import booking.DAO.BookingDAO;
import booking.DAO.CollectionBookingDAO;
import booking.Entities.Booking;
import booking.Entities.Flight;
import booking.Entities.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class BookingCollectionTest {
  int beforeStop = 46 * 60;
  int afterStop = 44 * 60;

  long now = LocalDateTime.now().toEpochSecond(ZoneId.of(DataUtil.TIME_ZONE).getRules().getOffset(LocalDateTime.now()));
  long time1 = now + beforeStop;
  long time2 = now + afterStop;

  Booking x1 = new Booking(new User("Uasia", "Popov"),
          new Flight("12345",time1, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
          5);

  Booking x2 = new Booking(new User("Uasia", "Popov"),
          new Flight("12345",time2, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
          5);
  @Test
  void updateTest() {
    BookingDAO db = new CollectionBookingDAO();
    List<Booking> bs = new ArrayList<>(Arrays.asList(x1, x2));
    db.add(bs);
    x1.confirm();
    x2.confirm();

    db.update();
    assertEquals(BookingStatus.Ordered, x1.checkStatus());
    assertEquals(BookingStatus.Canceled, x2.checkStatus());

    db.update("--force");
    assertEquals(BookingStatus.Completed, x1.checkStatus());
    assertEquals(BookingStatus.Canceled, x2.checkStatus());
  }

  @Test
  void saveFile() {
    BookingDAO db = new CollectionBookingDAO();
    db.saveFile();
    assertTrue(new File(FileUtil.BOOKINGS_FILE_PATH).isFile());
  }
  @Test
  void removeFile() {
    BookingDAO db = new CollectionBookingDAO();
    db.removeFile();
    assertFalse(new File(FileUtil.BOOKINGS_FILE_PATH).isFile());
  }
}
