package booking.Controllers;

import booking.DAO.FlightDAO;
import booking.Entities.Flight;
import booking.Services.FlightService;

import java.io.Serializable;
import java.util.List;

public class FlightController implements Serializable {

    private FlightService flightService = new FlightService();

    public FlightService getFlightService(){
        return flightService;
    }

    public FlightDAO getFlightDao(){
        return flightService.getFlightDao();
    }

    public List<Flight> getAllFlights(){
        return flightService.getAllFlights();
    }

    public void displayAllFlights(){
        flightService.displayAllFlights();
    }

    public void saveFlight(Flight flight) {
        flightService.saveFlight(flight);
    }

    public void deleteFlightByIndex(int index) {
        flightService.deleteFlightByIndex(index);
    }

    public void deleteFlightByObject(Flight flight) {
        flightService.deleteFlightByObject(flight);
    }

    public Flight getFlightById(int index){
        return flightService.getFlightById(index);
    }

    public Flight getFlightByFlightNumber(String str){
        return flightService.getFlightByFlightNumber(str);
    }

    public void printOnlineBoard(){
        flightService.printOnlineBoard();
    }

    public int getFlightCount() {
        return flightService.getFlightCount();
    }

    public void saveData(String filePath){
        flightService.saveData(filePath);
    }

    public void readData(String filePath){
        flightService.readData(filePath);
    }

    public void loadData(List<Flight> list){
        flightService.loadData(list);
    }





}
