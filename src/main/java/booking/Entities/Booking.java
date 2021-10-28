package booking.Entities;

import booking.ConstEnum.BookingStatus;
import booking.ConstEnum.DataUtil;
import booking.Utils.TimeStamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Booking implements DataUtil, Serializable {
  private static final long serialVersionUID = 2658638796689929176L;
  public final String id;
  public final User buyer;
  public final Flight flight;
  public final LocalDateTime requestTime;
  private final Map<String, User> passengers;
  private final int seatsRequested;
  private BookingStatus status;
  private final String comment;

   long stopOrderTime = 45; // minutes


  public Booking(User buyer, Flight flight, int amount) {
    this.id = String.format("%s-%s-%s",flight.getFlightNumber(), buyer.hashCode(), TimeStamp.seconds(LocalDateTime.now()));
    this.buyer = buyer;
    this.passengers = passengerMap();
    this.flight = flight;
    this.requestTime = LocalDateTime.now(ZoneId.of(TIME_ZONE).getRules().getOffset(LocalDateTime.now()));
    this.seatsRequested = amount;
    this.status = isAvailable()? BookingStatus.Pending : BookingStatus.Canceled;
    this.comment = cancellationComment();
  }

  @Override
  public String toString() {
    return new String(String.format("Booking {\n id: %s,\n buyer: %s %s,\n flight: %s,\n seats: %d,\n passengers: %s\n status: %s,\n comment: %s\n}",
            this.id,
            this.buyer.firstName,
            this.buyer.secondName,
            this.flight.prettyFormat(),
            this.seatsRequested,
            passengersList(),
            this.status.name(),
            this.comment
            ));
  }

  public String prettyFormat() {
    return new String(String.format(" id-бронирования: %s,\n покупатель: %s %s,\n рейс: %s,\n мест: %d,\n пассажиры: %s\n статус брони: %s,\n комментарий системы: %s\n ",
          this.id,
          this.buyer.firstName,
          this.buyer.secondName,
          this.flight.prettyFormat(),
          this.seatsRequested,
          passengersList(),
          this.status.name(),
          this.comment
          ));
  }

  public void confirm() {
    if (this.status.equals(BookingStatus.Pending)) changeStatus(BookingStatus.Ordered);
  }

  public void cancel() {
    boolean completed = this.status.equals(BookingStatus.Completed);
    if(!completed) changeStatus(BookingStatus.Canceled);
  }

  public void close() {
    if (!isAvailableByTime()) {
      boolean ordered = this.status.equals(BookingStatus.Ordered);
      if (ordered) changeStatus(BookingStatus.Completed);
      else cancel();
    }
  }

  public void close(String args) {
    if (args.equals("--force")) {
      boolean ordered = this.status.equals(BookingStatus.Ordered);
      if (ordered) changeStatus(BookingStatus.Completed);
      else cancel();
    }
  }

  public void addPassengers(List<User> ps) {
    for(User p : ps) {
     this.passengers.put(p.nickname, p);
    }
  }

  public Map<String, User> getPassengers() {
    return this.passengers;
  }

  public Map<String, User> passengerMap() {
    return new HashMap<>() {{
      put(buyer.nickname, buyer);
    }};
  }

  public String passengersList() {
    String s = new String();
    for(User u : this.passengers.values()) {
      s+= u.firstName + " " + u.secondName + ", ";
    }
    return s;
  }

  private void changeStatus(BookingStatus s) {
    this.status = s;
  }

  public BookingStatus checkStatus() {
    return this.status;
  }

  public int getSeatsRequested() {
    return this.seatsRequested;
  }

  public boolean isAvailable() {
    if(!isAvailableByTime()) return false;
    return isAvailableBySeats();
  }

  public boolean isAvailableBySeats() {
    return this.seatsRequested < this.flight.getMaxNumSeats();
  }

  public boolean isAvailableByTime() {
    long departure = flight.getDepartureDateTime();
    long now = TimeStamp.seconds(requestTime);
    long timeLeft = (departure - now) / 60;
    return timeLeft > stopOrderTime;
  }

  public Optional<String> getComment() {
    return Optional.ofNullable(comment);
  }

  private String cancellationComment() {
    if (!isAvailableBySeats()) {
      return "cancelled due to lack of seats available";
    }
    if (!isAvailableByTime()) {
      return  "cancelled due to time stop";
    }
    else return "";
  }

}
