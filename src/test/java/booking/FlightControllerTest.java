package booking;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;
import booking.Controllers.FlightController;
import booking.Entities.Flight;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlightControllerTest {

    @Test
    public void getAllFlightsPositive(){

        FlightController controller = new FlightController();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        Flight flight3 = new Flight( "AA4456", 1616525935, 5000, CityDeparture.KYIV, CityArrival.BOSTON, 20);

        controller.saveFlight(flight1);
        controller.saveFlight(flight2);
        controller.saveFlight(flight3);

        assertNotNull(controller.getAllFlights());
    }

    @Test
    public void saveFlightPositive(){

        FlightController controller = new FlightController();

        int startSize = controller.getAllFlights().size();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        Flight flight3 = new Flight( "AA4456", 1616525935, 5000, CityDeparture.KYIV, CityArrival.BOSTON, 20);

        controller.saveFlight(flight1);
        controller.saveFlight(flight2);
        controller.saveFlight(flight3);

        int finishSize = controller.getAllFlights().size();

        assertEquals(startSize+3, finishSize);
    }

    @Test
    public void saveFlightPositiveDouble(){

        FlightController controller = new FlightController();

        int startSize = controller.getAllFlights().size();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);

        controller.saveFlight(flight1);
        controller.saveFlight(flight1);
        controller.saveFlight(flight1);

        int finishSize = controller.getAllFlights().size();

        assertEquals(startSize+1, finishSize);
    }

    @Test
    public void deleteFlightByIndexPositive(){
        FlightController controller = new FlightController();
        int startSize = controller.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        controller.saveFlight(flight1);
        int sizeAdd = controller.getAllFlights().size();
        controller.deleteFlightByIndex(startSize);
        int sizeFinish = controller.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd-1,sizeFinish);
    }

    @Test
    public void deleteFlightByIndexNegative(){
        FlightController controller = new FlightController();
        int startSize = controller.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        controller.saveFlight(flight1);
        int sizeAdd = controller.getAllFlights().size();
        controller.deleteFlightByIndex(startSize+10);
        int sizeFinish = controller.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd,sizeFinish);
    }

    @Test
    public void deleteFlightByObjectPositive(){
        FlightController controller = new FlightController();
        int startSize = controller.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        controller.saveFlight(flight1);
        int sizeAdd = controller.getAllFlights().size();
        controller.deleteFlightByObject(flight1);
        int sizeFinish = controller.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd-1,sizeFinish);
    }

    @Test
    public void deleteFlightByObjectNegative(){
        FlightController controller = new FlightController();
        int startSize = controller.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        controller.saveFlight(flight1);
        int sizeAdd = controller.getAllFlights().size();
        controller.deleteFlightByObject(flight2);
        int sizeFinish = controller.getAllFlights().size();
        assertEquals(startSize+1,sizeAdd);
        assertEquals(sizeAdd,sizeFinish);
    }

    @Test
    public void getFlightByIdPositive(){
        FlightController controller = new FlightController();
        int startSize = controller.getAllFlights().size();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        controller.saveFlight(flight1);
        Flight flight2 = controller.getFlightById(startSize);
        assertEquals(flight1, flight2);
    }

    @Test
    public void getFlightByIdNegative(){
        FlightController controller = new FlightController();
        int startSize = controller.getAllFlights().size();
        Flight flight2 = controller.getFlightById(startSize+10);
        assertNull(flight2);
    }

    @Test
    public void getFlightByFlightNumberPositive(){
        FlightController controller = new FlightController();
        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        controller.saveFlight(flight1);
        Flight flight2 = controller.getFlightByFlightNumber("AA1234");
        assertEquals(flight1, flight2);
    }

    @Test
    public void getFlightByFlightNumberNegative(){
        FlightController controller = new FlightController();
        Flight flight2 = controller.getFlightByFlightNumber("!@#$%^");
        assertNull(flight2);
    }

    @Test
    public void getFlightCountPositive(){
        FlightController controller = new FlightController();

        int startSize = controller.getFlightCount();

        Flight flight1 = new Flight( "AA1234", 1616527935, 5200, CityDeparture.KYIV, CityArrival.BOSTON, 100);
        Flight flight2 = new Flight( "AA5678", 1616526935, 5100, CityDeparture.KYIV, CityArrival.BOSTON, 50);
        Flight flight3 = new Flight( "AA4456", 1616525935, 5000, CityDeparture.KYIV, CityArrival.BOSTON, 20);

        controller.saveFlight(flight1);
        controller.saveFlight(flight2);
        controller.saveFlight(flight3);

        int finishSize = controller.getFlightCount();

        assertEquals(startSize+3, finishSize);


    }
}
