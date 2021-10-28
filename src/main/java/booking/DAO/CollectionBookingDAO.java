package booking.DAO;

import booking.ConstEnum.FileUtil;
import booking.Entities.Booking;
import booking.Exceptions.BookingNotFoundException;

import java.io.*;
import java.util.*;

public class CollectionBookingDAO implements BookingDAO, FileUtil, Serializable {
  private Map<String, Booking> data = new HashMap();

  public CollectionBookingDAO(Map<String, Booking> data) {
    this.data = data;
  }

  public CollectionBookingDAO() {
    File source = new File(BOOKINGS_FILE_PATH);
     if(source.isFile()) {
       System.out.println("Файл коллекции бронирований найден...");
       try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE_PATH))) {
         this.data = (Map<String, Booking>) ois.readObject();
       } catch (Exception ex) {
         System.out.println(ex.getMessage());
       }
     } else {
       System.out.println("Файл коллекции бронирований отсутствует, создаю новый...");
       this.data = new HashMap<String, Booking>();
       saveFile();
     }
  }

  @Override
  public Optional<List> getAll() {
    return data.size() == 0? Optional.empty() : Optional.of(new ArrayList<Booking>(data.values()));
  }

  @Override
  public List<Booking> getByUser(String username) throws BookingNotFoundException {
    List<Booking> list = new ArrayList<>();

    data.values().forEach(b -> {
      if(b.getPassengers().get(username) != null) {
        list.add(b);
      }
    });

    if(list.size() < 1) {
      throw new BookingNotFoundException("Bookings for user: " + username + " not found");
    } else {
      return list;
    }
  }

  @Override
  public Optional<Booking> getById(String id) {
    return Optional.ofNullable(data.get(id));
  }

  @Override
  public void add(Booking b) {
    data.put(b.id, b);
    saveFile();
  }

  @Override
  public void add(List<Booking> bs) {
    bs.forEach(b -> data.put(b.id, b));
    saveFile();
  }

  @Override
  public void remove(Booking b) {
    data.remove(b.id);
  }

  @Override
  public void update() {
    data.values().forEach(b -> b.close());
  }

  @Override
  public void update(String args) {
    if (args.equals("--force")) data.values().forEach(b -> b.close(args));
    else update();
  }

  @Override
  public void saveFile() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE_PATH))) {
      oos.writeObject(data);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  @Override
  public void removeFile() {
    File path = new File(BOOKINGS_FILE_PATH);
    path.delete();
  }

}
