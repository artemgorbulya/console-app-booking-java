package booking.Utils;

import booking.Entities.Flight;
import booking.ConstEnum.DataUtil;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightsPrinter implements DataUtil {
    public static void printFlights(List<Flight> fs, boolean isIndexPrinted) {
      fs.forEach(
              x -> {
                String departureDate = Instant.ofEpochSecond(x.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(DATE_FORMAT));
                String departureTime = Instant.ofEpochSecond(x.getDepartureDateTime())
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(TIME_FORMAT));

                String fDuration = LocalTime.ofSecondOfDay(x.getEstFlightDuration())
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT));

                String fNumber          = x.getFlightNumber();
                String departureAirport = x.getOrigin().getName();
                String arrivalAirport   = x.getDestination().getName();
                int seatsAvailable      = x.getMaxNumSeats();

                if(!isIndexPrinted) {
                  System.out.printf("| %6s | %10s | %5s | %s -> %-13s  | %5s | %3s |%n" ,
                          fNumber,
                          departureDate,
                          departureTime,
                          departureAirport,
                          arrivalAirport,
                          departureAirport,
                          seatsAvailable
                  );
                }
                else if (isIndexPrinted) {
                  System.out.printf("%5s | %6s | %10s | %5s | %s -> %-13s  | %5s | %3s |%n" ,
                          fs.indexOf(x) + 1,
                          fNumber,
                          departureDate,
                          departureTime,
                          departureAirport,
                          arrivalAirport,
                          departureAirport,
                          seatsAvailable
                  );
                }
              }
      );
    }
}

