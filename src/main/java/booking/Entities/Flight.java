package booking.Entities;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.ConstEnum.DataUtil;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Flight implements DataUtil, Serializable {

    private String flightNumber;//flight number example AA1234
    private long departureDateTime;//departure time and date in Unix timestamp format
    private long estFlightDuration;//flight time in the format - number of seconds
    private CityDeparture origin; //departure city
    private CityArrival destination; //arrival city
    private int maxNumSeats; //the number of free seats on the flight

    public Flight(String flightNumber, long departureDateTime, long estFlightDuration, CityDeparture origin, CityArrival destination, int maxNumSeats){
        this.flightNumber = flightNumber;
        this.departureDateTime = departureDateTime;
        this.estFlightDuration = estFlightDuration;
        this.origin = origin;
        this.destination = destination;
        this.maxNumSeats = maxNumSeats;
    }

    public Flight() {

    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public long getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(long departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public long getEstFlightDuration() {
        return estFlightDuration;
    }

    public void setEstFlightDuration(long estFlightDuration) {
        this.estFlightDuration = estFlightDuration;
    }

    public CityDeparture getOrigin() {
        return origin;
    }

    public void setOrigin(CityDeparture origin) {
        this.origin = origin;
    }

    public CityArrival getDestination() {
        return destination;
    }

    public void setDestination(CityArrival destination) {
        this.destination = destination;
    }

    public int getMaxNumSeats(){
        return maxNumSeats;
    }

    public void setMaxNumSeats(int maxNumSeats) {
        this.maxNumSeats = maxNumSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightNumber.equals(flight.getFlightNumber());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        return PRIME * result + flightNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", departureDate ='" +
                Instant.ofEpochSecond(departureDateTime)
                         .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(DATE_FORMAT)) + '\'' +
                ", departureTime ='" +
                Instant.ofEpochSecond(departureDateTime)
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(TIME_FORMAT)) + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", maxNumSeats=" + maxNumSeats +
                ", duration=" +
                LocalTime.ofSecondOfDay(estFlightDuration)
                        .format(DateTimeFormatter.ofPattern(TIME_FORMAT)) +
                '}';
    }

    //printing in a beautiful line for the booking board
    public String prettyFormat() {
        return flightNumber + " | " +
                Instant.ofEpochSecond(departureDateTime)
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(DATE_FORMAT)) + " | " +
                Instant.ofEpochSecond(departureDateTime)
                        .atZone(ZoneId.of(TIME_ZONE))
                        .toLocalDateTime()
                        .format(DateTimeFormatter
                                .ofPattern(TIME_FORMAT)) + " | " +
                origin.getName() + " -> " +
                destination.getName() + " | " +
                LocalTime.ofSecondOfDay(estFlightDuration)
                    .format(DateTimeFormatter.ofPattern(TIME_FORMAT)) + " | " +
                maxNumSeats
                ;
    }

}
