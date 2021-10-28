package booking.Services;

import booking.ConstEnum.BookingStatus;
import booking.ConstEnum.DataUtil;
import booking.Controllers.FlightController;
import booking.Controllers.UserController;
import booking.DAO.BookingDAO;
import booking.DAO.CollectionBookingDAO;
import booking.Entities.Booking;
import booking.Entities.Flight;
import booking.Entities.User;
import booking.Exceptions.BookingNotFoundException;
import booking.Utils.Logger;
import booking.Utils.Validator;

import java.io.Serializable;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class BookingService implements DataUtil, Serializable {
  private final BookingDAO db = new CollectionBookingDAO();

  private final Logger logger= new Logger();

  public Optional<List> getAll() {
    return db.getAll();
  }

  public Optional<Booking> getById(String id) {
   return db.getById(id);
  }

  public List<Booking> getByUser(String username) throws BookingNotFoundException {
    return db.getByUser(username);
  }

  public void confirmBooking(String id) throws Exception {
    Optional<Booking> currentBooking = db.getById(id);

    if(!currentBooking.isPresent()) {
      logger.addLogString("Не найдено бронирование для подтверждения");
      throw new Exception(String.format("No bookings with id %s was found", id));
    }

    currentBooking.ifPresent(Booking::confirm);

    currentBooking.ifPresent(b -> {
      String cancellationReason = new String();

      if(!b.getComment().isPresent()) {
        cancellationReason = "причина не установлена";
      } else {
        cancellationReason = b.getComment().get();
      }

      if(confirmed(b)) {
        logger.addLogString(String.format("Бронирование %s подтверждено", b.id));
      } else if(cancelled(b)) {
        logger.addLogString(String.format("Бронирование %s не подтверждено: %s", b.id, cancellationReason));
      }
    });

    saveData();
  }

  public void cancelBooking(String id) {
    Optional<Booking> currentBooking = db.getById(id);
    if(!currentBooking.isPresent()) System.out.println("бронирование " + id + " не найдено");

    currentBooking.ifPresent( b -> {
      b.cancel();
      System.out.printf("%s успешно отменено!", id);
      logger.addLogString("бронирвание " + id + " отменено пользователем");
    });

    saveData();
  }

  public User buyer(UserController uc) {
    System.out.print("Введите имя покупателя: ");
    String name = Validator.validName();
    System.out.print("Введите фамилию покупателя: ");
    String surname = Validator.validName();
    User buyer = new User(name, surname);
    if(!uc.userList().contains(buyer)) uc.addUser(buyer);

    return buyer;
  }

  public List<User> passengerList(Booking b, UserController uc) {
    List<User> result = new ArrayList<>();
    List<User> users =uc.userList();

    for(int i = 1; i < b.getSeatsRequested(); i++) {
      System.out.printf("Введите имя пассажира %d: ", i + 1);
      String firstName = Validator.validName();
      System.out.printf("Введите фамилию пассажира %d: ", i + 1);
      String secondName = Validator.validName();
      User currentUser = new User(firstName, secondName);
      result.add(currentUser);

      for(User u: users) {
        if(!users.contains(currentUser)) uc.addUser(currentUser);
      }

    }

    return result;
  }

  private boolean confirmed(Booking b) {
    return b.checkStatus().equals(BookingStatus.Ordered);
  };

  private boolean cancelled(Booking b) {
    return b.checkStatus().equals(BookingStatus.Canceled);
  }

  public void add(Booking b) {
    db.add(b);
    boolean success = db.getById(b.id).isPresent();
    String log ="";
    String logSuccess = String.format("Добавлено новое бронирование id: %s", b.id);
    String logTrouble = String.format("Возникла проблема с записью бронирования id: %s", b.id);

    if(success)  log+= logSuccess;
    else log+= logTrouble;

    logger.addLogString(log);
  }

  public void add(List<Booking> bs) {
    db.add(bs);
    List bookingList= db.getAll().get();

    boolean success = bookingList.containsAll(bs);
    StringBuilder logSb = new StringBuilder();
    if(success) {
      logSb.append("Были успешно добавлены бронирования:\n");
      for(int i = 0; i < bs.size(); i++) {
        logSb.append("id: " + bs.get(i).id + "\n");
      }
    } else {
      logSb.append("Возникла проблема с записью бронирований: один или несколько элементов не были добавлены");
    }
    logger.addLogString(logSb.toString());
  }

  public List<Flight> getFlightsByCriteria(List<Flight> flights, String destination, String date, int seats) {
    Map<Flight, String> flightsMap= new HashMap<>();
    flights.forEach(flight -> {
      String dateFormatted =  Instant.ofEpochSecond(flight.getDepartureDateTime())
              .atZone(ZoneId.of(TIME_ZONE))
              .toLocalDateTime()
              .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
      flightsMap.put(flight, dateFormatted);
    });

    Map<Flight, String> filteredFlights = flightsMap.entrySet().stream()
            .filter(f -> f.getValue().equals(date))
            .filter(f -> f.getKey().getDestination().getName().equalsIgnoreCase(destination))
            .filter(f -> f.getKey().getMaxNumSeats() >= seats)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    logger.addLogString("Выполнен поиск рейсов для бронирования");
    return new ArrayList<>(filteredFlights.keySet());
  }

  public Set<String> availableFlightDates(FlightController fc) {
    List<Long> ts = fc.getAllFlights().stream()
            .map(Flight::getDepartureDateTime)
            .collect(Collectors.toList());
    return ts.stream()
            .map(t -> {
              return Instant.ofEpochSecond(t)
                      .atZone(ZoneId.of(TIME_ZONE))
                      .toLocalDateTime()
                      .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
            }).collect(Collectors.toSet());
  }

  public void updateDB() {
    db.update();
    saveData();
  }

  public void saveData() {
    db.saveFile();
    logger.addLogString("коллекция брони сохранена");
  }

}
