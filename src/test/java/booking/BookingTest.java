package booking;

import booking.ConstEnum.BookingStatus;
import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.ConstEnum.DataUtil;
import booking.Entities.Booking;
import booking.Entities.Flight;
import booking.Entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingTest {

  int beforeStop = 46 * 60;
  int afterStop = 45 * 60;

  long now = LocalDateTime.now().toEpochSecond(ZoneId.of(DataUtil.TIME_ZONE).getRules().getOffset(LocalDateTime.now()));
  long t1 = now + beforeStop;
  long t2 = now + afterStop;
  long t3 = now + afterStop;

  int seats1 = 10;
  int seats2= 9;
  int seats3= 3;

  User person1 = new User("Nick", "Cage");
  User person2 = new User("Alex", "Tsu");
  User person3 = new User("Hu", "Dzan");

  Flight f1 = new Flight("AA1234", t1, 7200, CityDeparture.KYIV, CityArrival.AMSTERDAM, seats1);
  Flight f2 = new Flight("AA1244", t2, 7200, CityDeparture.KYIV, CityArrival.AMSTERDAM, seats2);
  Flight f3 = new Flight("AA1244", t3, 7200, CityDeparture.KYIV, CityArrival.AMSTERDAM, seats3);

  @Test
  void isAvailableByTimeTest() {
    Booking x1 = new Booking(person1, f1, 5);
    Booking x2 = new Booking(person1, f2, 5);
    assertTrue(x1.isAvailableByTime());
    assertFalse(x2.isAvailableByTime());
  }

  @Test
  void isAvailableBySeatsTest() {
    Booking x1 = new Booking(person1, f1, 9);
    Booking x2 = new Booking(person1, f2, 10);

    assertTrue(x1.isAvailableBySeats());
    assertFalse(x2.isAvailableBySeats());
  }

  @Test void isAvailableTest () {
    Booking x1 = new Booking(person1, f1, 11);
    Booking x2 = new Booking(person1, f2, 10);

  }

  @Test
  void changeStatusTest () {
    Booking x1 = new Booking(person1, f1, 2);
    Booking x2 = new Booking(person1, f2, 10);

    assertEquals(BookingStatus.Pending, x1.checkStatus());
    assertEquals(BookingStatus.Canceled, x2.checkStatus());
  }
  @Test
  void cancellationComment () {
    Booking x1 = new Booking(person1, f1, 9);
    Booking x2 = new Booking(person1, f2, 10);
    Booking x3 = new Booking(person1, f3, 2);

    assertEquals("cancelled due to lack of seats available", x2.getComment().get());
    assertEquals("cancelled due to time stop", x3.getComment().get());
    assertEquals("", x1.getComment().get());
  }

  @Test
  void confirmTest() {
    Booking x1 = new Booking(person1, f1, 9);
    Booking x2 = new Booking(person1, f2, 10);
    x1.confirm();
    x2.confirm();
    assertTrue(x1.checkStatus().equals(BookingStatus.Ordered));
    assertTrue(x2.checkStatus().equals(BookingStatus.Canceled));
  }

  @Test
  void closeTest() {
    Flight testFlight1 = new Flight("AA1234", t1, 7200, CityDeparture.KYIV, CityArrival.AMSTERDAM, seats1);
    Booking x1 = new Booking(person1, testFlight1, 9);
    Booking x2 = new Booking(person1, f1, 9);
    Booking x3 = new Booking(person1, f2, 12);

    x1.confirm();
    x1.close("--force");
    x2.confirm();
    x2.close();
    x3.confirm();
    x3.close("--force");


    assertTrue(x1.checkStatus().equals(BookingStatus.Completed));
    assertTrue(x2.checkStatus().equals(BookingStatus.Ordered));
    assertTrue(x3.checkStatus().equals(BookingStatus.Canceled));
  }

  @Test
  void cancelTest() {
    Flight testFlight1 = new Flight("AA1234", t1, 7200, CityDeparture.KYIV, CityArrival.AMSTERDAM, seats1);
    Booking x1 = new Booking(person1, testFlight1, 9);
    Booking x2 = new Booking(person1, f1, 9);
    Booking x3 = new Booking(person1, f2, 12);

    x1.confirm();
    x1.close("--force");
    x1.cancel();
    x2.confirm();
    x2.cancel();

    assertTrue(x1.checkStatus().equals(BookingStatus.Completed));
    assertTrue(x2.checkStatus().equals(BookingStatus.Canceled));
  }

  @Test
  void addPassengersTest() {
    Flight testFlight1 = new Flight("AA1234", t1, 7200, CityDeparture.KYIV, CityArrival.AMSTERDAM, seats1);
    Booking x1 = new Booking(person1, testFlight1, 2);

    x1.addPassengers(new ArrayList<>(Arrays.asList(person2, person3)));

    assertTrue(x1.getPassengers().containsKey(person1.nickname));
    assertTrue(x1.getPassengers().containsKey(person2.nickname));
    assertTrue(x1.getPassengers().containsKey(person3.nickname));
  }
}
