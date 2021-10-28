package booking;

import booking.ConstEnum.BookingStatus;
import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.ConstEnum.DataUtil;
import booking.Controllers.FlightController;
import booking.Entities.Booking;
import booking.Entities.Flight;
import booking.Entities.User;
import booking.Exceptions.BookingNotFoundException;
import booking.Services.BookingService;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class BookingServiceTest implements DataUtil {
  @Test
  void getFlightsByCriteriaTest() {
    BookingService bs = new BookingService();
    FlightController fc = new FlightController();
    List<Flight> all = fc.getAllFlights();
    Map<Flight, String> allFlights = new HashMap<>();
    all.forEach(f -> {
      String d =  Instant.ofEpochSecond(f.getDepartureDateTime())
              .atZone(ZoneId.of(TIME_ZONE))
              .toLocalDateTime()
              .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
      allFlights.put(f, d);
    });

    String targetDestination = "San_Francisco";
    String targetDate = "23/03/2021";
    int targetSeats = 0;

    Map<Flight, String> targetCollection = allFlights.entrySet()
              .stream()
              .filter(f -> f.getValue().equals(targetDate))
              .filter(f -> f.getKey().getDestination().getName().equalsIgnoreCase(targetDestination))
              .collect(Collectors. toMap(Map.Entry::getKey, Map.Entry::getValue));

    List<Flight> result = bs.getFlightsByCriteria(fc.getAllFlights(), targetDestination, targetDate, targetSeats);
    assertEquals(targetCollection.size(), result.size());
  }

  @Test
  void getByUserTest() throws BookingNotFoundException {
    BookingService service = new BookingService();
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

    service.add(expected);
    service.add(x3);

    List<Booking> test = service.getByUser(u1.nickname);

    assertTrue(test.contains(x1));
    assertTrue(test.contains(x2));
    assertFalse(test.contains(x3));
  }

  @Test
  void confirmBookingTest() throws Exception {
    BookingService service = new BookingService();
    int beforeStop = 46 * 60;
    long now = LocalDateTime.now().toEpochSecond(ZoneId.of(DataUtil.TIME_ZONE).getRules().getOffset(LocalDateTime.now()));
    long time1 = now + beforeStop;

    User u1 = new User("Uasia", "Popov");

    Booking x1 = new Booking(u1, new Flight("12345",time1, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
            5);

    Booking x2 = new Booking(u1, new Flight("12345",time1, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
            5);

    List expected = new ArrayList<>(Arrays.asList(x1, x2));

    service.add(expected);
    service.add(x1);

    service.confirmBooking(x1.id);
    assertTrue(BookingStatus.Ordered.equals(x1.checkStatus()));
    assertFalse(BookingStatus.Ordered.equals(x2.checkStatus()));
  }

  @Test
  void cancelBookingTest() {
    BookingService service = new BookingService();
    int beforeStop = 46 * 60;
    long now = LocalDateTime.now().toEpochSecond(ZoneId.of(DataUtil.TIME_ZONE).getRules().getOffset(LocalDateTime.now()));
    long time1 = now + beforeStop;

    User u1 = new User("Uasia", "Popov");

    Booking x1 = new Booking(u1, new Flight("12345",time1, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
            5);

    Booking x2 = new Booking(u1, new Flight("12345",time1, 7220, CityDeparture.KYIV, CityArrival.AMSTERDAM,360),
            5);

    List expected = new ArrayList<>(Arrays.asList(x1, x2));

    service.add(expected);
    service.add(x1);

    service.cancelBooking(x1.id);
    assertTrue(BookingStatus.Canceled.equals(x1.checkStatus()));
    assertTrue(BookingStatus.Pending.equals(x2.checkStatus()));
  }
}
