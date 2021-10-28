package booking;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.ConstEnum.DataUtil;
import booking.Controllers.BookingController;
import booking.Controllers.FlightController;
import booking.Entities.Booking;
import booking.Entities.Flight;
import booking.Entities.User;
import booking.Exceptions.BookingNotFoundException;
import booking.Services.BookingService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookingControllerTest {

  @Test
  void bookingsByUserTest() throws BookingNotFoundException {
    FlightController fc = new FlightController();
    BookingController controller = new BookingController(fc);

    int beforeStop = 46 * 60;
    int afterStop = 44 * 60;

    long now = LocalDateTime.now().toEpochSecond(ZoneId.of(DataUtil.TIME_ZONE).getRules().getOffset(LocalDateTime.now()));
    long time1 = now + beforeStop;
    long time2 = now + afterStop;

    User u1 = new User("Uasia", "Popov");

    Booking x1 = new Booking(u1, new Flight("12345",time1, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
            5);

    Booking x2 = new Booking(u1,
            new Flight("12345456",time2, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
            5);

    Booking x3 = new Booking(new User("Jonn", "Ivanov"),
            new Flight("12345345",time2, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
            5);

    List expected = new ArrayList<>(Arrays.asList(x1, x2));

    controller.saveBooking(x1);
    controller.saveBooking(x2);
    controller.saveBooking(x3);

    List<Booking> test = controller.bookingsByUser(u1.nickname);

    assertTrue(test.contains(x1));
    assertTrue(test.contains(x2));
    assertFalse(test.contains(x3));
  }
}
