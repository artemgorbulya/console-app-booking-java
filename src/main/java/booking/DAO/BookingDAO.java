package booking.DAO;

import booking.Entities.Booking;
import booking.Entities.Flight;
import booking.Entities.User;
import booking.Exceptions.BookingNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookingDAO {
  Optional<List> getAll();
  List<Booking> getByUser(String username) throws BookingNotFoundException;
  Optional<Booking> getById(String id);
  void add(Booking b);
  void add(List<Booking> bs);
  void remove(Booking b);
  void update();
  void update(String args);
  void saveFile();
  void removeFile();
}
