package booking.Controllers;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.RegUtil;
import booking.Entities.Booking;
import booking.Entities.Flight;
import booking.Entities.User;
import booking.Exceptions.BookingNotFoundException;
import booking.Services.BookingService;
import booking.Utils.Logger;
import booking.Utils.FlightsPrinter;
import booking.Utils.Validator;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class BookingController implements RegUtil {
  private final FlightController fc;
  private final BookingService bs = new BookingService();
  Logger log = new Logger();

  public BookingController(FlightController fc) {
    this.fc = fc;
  }

  public void runBookingRequest(UserController uc) {
    int seats = seatsRequested();
    List<Flight> availableFs = availableFlights(seats);
    String exitAnswer = "0";
    List<String> answersYesNo = new ArrayList<>(Arrays.asList("да", "нет"));
    List<String> answersFlightIndex = answersList(availableFs, exitAnswer);
    Flight selectedFlight;
    Booking currentBooking;

    do {
      if(availableFs.size() < 1) {
        System.out.print("На текущий момент нет доступных рейсов по заданным критериям, повторить поиск? (да / нет): ");
        String a = input().toLowerCase();

        if(a.equalsIgnoreCase("нет")) break;
        while (!answersYesNo.contains(a)) {
          System.out.print("\nПродолжить? (да / нет) : ");
          a = input().toLowerCase();
        }
      } else {
        System.out.println();
        FlightsPrinter.printFlights(availableFs, true);
        break;
      }
    } while (true);

    do {
      System.out.println();
      System.out.print("Введите порядковый номер рейса для брони из списка выше или введите 0 для выхода в главное меню: ");
      String a = input();

      while (!answersFlightIndex.contains(a)) {
        System.out.printf("Введите порядковый номер рейса для брони из списка выше или введите \"%s\": ", exitAnswer);
        a = input();
      }

      if (a.equalsIgnoreCase(exitAnswer)) break;
      else {
        selectedFlight = availableFs.get(Integer.parseInt(a) - 1);
        currentBooking = new Booking(bs.buyer(uc), selectedFlight, seats);
        List<User> ps = bs.passengerList(currentBooking, uc);
        currentBooking.addPassengers(ps);
        saveBooking(currentBooking);;

        System.out.print("Подтверждаете бронирование? (да / нет): ");
        String confirmationAnswer = input();

        while (!answersYesNo.contains(confirmationAnswer)) {
          System.out.print("Некорректный ввод. Подтверждаете бронирование? (да / нет): ");
          confirmationAnswer = input();
        }

        if(confirmationAnswer.equalsIgnoreCase("нет")) break;
        else {
          try {
            bs.confirmBooking(currentBooking.id);
            System.out.println();
            System.out.println("Результат бронирования: ");
            System.out.println(currentBooking.prettyFormat());
            break;
          } catch (Exception ex) {
            System.out.println("Возникла ошибка подтверждения бронирования: " + ex.getMessage() + ", обратитесь к администратору сервиса...");
            break;
          }
        }
      }
    } while (true);
  }

  public void searchBookingsByUser(UserController uc) {
   System.out.print("Введите имя пассажира: ");
   String name = Validator.validName();
   System.out.print("Введите фамилию пассажира: ");
   String surname = Validator.validName();
   String nick = User.makeNickname(name, surname);

   printBookings(nick);
  }

  public void printBookings(String username) {
    try {
      List<Booking> bs = bookingsByUser(username);
      bs.forEach(booking -> System.out.println(booking.prettyFormat()));
    } catch (BookingNotFoundException e) {
      log.addLogString("Поиск брони не увенчался успехом:" + e.getMessage());
      System.out.println("Бронирования не найдены");
    }
  }

  public List<Booking> bookingsByUser(String username) throws BookingNotFoundException {
    return bs.getByUser(username);
  }

  public void cancelBooking(String id) {
    bs.cancelBooking(id);
  }

  private List<String> answersList(List<Flight> list, String exitStr) {
    List<String> r = new ArrayList<>(Arrays.asList(exitStr));
    for(int i = 0; i < list.size(); i++) r.add(Integer.toString(i + 1));
    return r;
  }

  public void saveBooking(Booking b) {
    bs.add(b);
    bs.saveData();
  }

  private List<Flight> availableFlights(int seats) {
    String[] cs = destinationAndDate();
    String destination = cs[0];
    String date = cs[1];

    return bs.getFlightsByCriteria(fc.getAllFlights(), destination, date, seats);
  }

  private String[] destinationAndDate() {
   String[] answers = new String[2];
   do {
     System.out.print("Введите куда летим: ");
     answers[0] = input();

     if(isAirportValid(answers[0])) break;

     System.out.printf("В %s мы не летаем, пожалуйста выберете из списка ниже и повторите попытку!\n", answers[0]);
     airports().forEach(System.out::println);
   } while (true);

   int counter = 0;
   while(!isDateValid(answers[1])) {
     System.out.print("Введите когда летим (в формате ДД/ММ/ГГГГ): ");
     answers[1] = input();

     if(isDateValid(answers[1])) break;
     else {
       System.out.printf("Полётов на %s нет, доступные даты: \n", answers[1]);
       availableDates().forEach(System.out::println);
       if(counter++ > 0) System.out.println("Убедитесь что вводимый формат даты ===> ДД/ММ/ГГГГ <==== и повторите попытку...");
     }
   }
   return answers;
  }

  private int seatsRequested() {
    String seats;
    do {
      System.out.print("Введите сколько нужно мест: ");
      seats = input();
      if(Validator.isInteger(seats)) break;

      System.out.printf("Некорректный ввод: \"%s\" мест быть не может, повторите попытку...\n", seats);
    } while (true);

    return Integer.parseInt(seats);
  }

  private boolean isDateValid(String d) {
    return availableDates().contains(d);
  }

  private Set<String> availableDates() {
    return bs.availableFlightDates(fc);
  }

  private static boolean isAirportValid(String airport) {
    List<String> as = airports().stream().map(a -> a.toLowerCase()).collect(Collectors.toList());
    return as.contains(airport.toLowerCase());
  }

  private static List<String> airports() {
    List<String> as = new ArrayList<>();
    for(CityArrival a : CityArrival.values()) {
      as.add(a.getName());
    }
    return as;
  }

  private String input() {
    return SCANNER.next();
  }

  public void updateData() {
    bs.updateDB();
  }
}
