package booking;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.ConstEnum.DataUtil;
import booking.Controllers.FlightController;
import booking.Entities.Flight;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FlightTest implements DataUtil {


    @Test
    public void toStringPositive(){

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        String str1 = "Flight{" +
                "flightNumber='" + flight1.getFlightNumber() + '\'' +
                ", departureDate ='" +
                Instant.ofEpochSecond(flight1.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(DATE_FORMAT)) + '\'' +
                ", departureTime ='" +
                Instant.ofEpochSecond(flight1.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(TIME_FORMAT)) + '\'' +
                ", origin='" + flight1.getOrigin() + '\'' +
                ", destination='" + flight1.getDestination() + '\'' +
                ", maxNumSeats=" + flight1.getMaxNumSeats() +
                ", duration=" +
                LocalTime.ofSecondOfDay(flight1.getEstFlightDuration())
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)) +
                '}';

        String str2 = flight1.toString();

        assertEquals(str1, str2);

    }

    @Test
    public void prettyFormatPositive(){

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        String str1 = flight1.getFlightNumber() + " | " +
                Instant.ofEpochSecond(flight1.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(DATE_FORMAT)) + " | " +
                Instant.ofEpochSecond(flight1.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(TIME_FORMAT)) + " | " +
                flight1.getOrigin().getName() + " -> " +
                flight1.getDestination().getName() + " | " +
                LocalTime.ofSecondOfDay(flight1.getEstFlightDuration())
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)) + " | " +
                flight1.getMaxNumSeats()
                ;

        String str2 = flight1.prettyFormat();

        assertEquals(str1, str2);

    }

}
