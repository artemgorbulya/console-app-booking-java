package booking.DAO;

import booking.ConstEnum.FileUtil;
import booking.Entities.Flight;
import booking.Entities.FlightGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionFlightDAO implements FlightDAO, Serializable, FileUtil {

    private List<Flight> flightsList = new ArrayList<>();

    public CollectionFlightDAO(){
        File dir = new File(FLIGHTS_FILE_PATH); //для перегенерации коллекции удалить файл ./src/file/flights.dat

        if (dir.isFile()) {
            System.out.println("Файл коллекции полетов найден...");
            readData(FLIGHTS_FILE_PATH);
        } else {
            System.out.println("Файл коллекции полетов не найден...");
            FlightGenerator fg = new FlightGenerator();
            loadData(fg.make());
            saveData(FLIGHTS_FILE_PATH);
        }
    }


    //saving flight to collection
    @Override
    public void save(Flight flight) {
        if (flight != null) {
            if (flightsList.contains(flight)) {
                flightsList.set(flightsList.indexOf(flight), flight);
            } else {
                flightsList.add(flight);
            }
        }
    }

    //getting flight by code
    @Override
    public Flight get(int index) {
        Flight result = null;
        if (index >= 0 && index < flightsList.size())
            result = flightsList.get(index);
        return result;
    }

    //get all flights
    @Override
    public List<Flight> getAll() {
        return flightsList;
    }

    //delete flight by index
    @Override
    public boolean remove(int index) {
        boolean result = false;
        if (index >= 0 && index < flightsList.size()) {
            flightsList.remove(index);
            result = true;
        }
        return result;
    }

    //delete flight by flight
    @Override
    public boolean remove(Flight flight) {
        return flightsList.remove(flight);
    }

    //Saving flights db to file
    @Override
    public void saveData(String filePath) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath)))
        {
            oos.writeObject(flightsList);
            System.out.println("Файл был успешно записан...");
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }

    }

    //Reading flights db from file...
    @Override
    public void readData(String filePath) {
        List<Flight> newFlights = new ArrayList<Flight>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath)))
        {

            newFlights=((ArrayList<Flight>)ois.readObject());
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        this.loadData(newFlights);


    }

    //Loading flights info db..
    @Override
    public void loadData(List<Flight> list) {

        this.flightsList = list;

    }
}
