package booking.DAO;

import booking.Entities.Flight;

import java.util.List;

public interface FlightDAO {

    void save(Flight flight);
    Flight get(int index);
    List<Flight> getAll();
    boolean remove(int index);
    boolean remove(Flight flight);

    void saveData(String filePath);
    void readData(String filePath);
    void loadData(List<Flight> list);
}
