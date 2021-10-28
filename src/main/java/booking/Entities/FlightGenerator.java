package booking.Entities;

import booking.ConstEnum.CityArrival;
import booking.ConstEnum.CityDeparture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightGenerator {

    public List<Flight> make(){
        ArrayList<Flight> flights = new ArrayList<>();
        List<CityArrival> allCities = Arrays.asList(CityArrival.values());

        allCities.forEach(
                cityArrival -> {

                    long epoch = System.currentTimeMillis()/1000;

                    String flightAbbr = CityDeparture.KYIV.toString().charAt(0) + String.valueOf(cityArrival.getName().charAt(0));

                    for (int i = 0; i < 25; i++) {

                        Flight newFlight1 = new Flight();
                        newFlight1.setFlightNumber(flightAbbr + (1000 + (int) (Math.random() * ((9999 - 1000) + 1))));
                        newFlight1.setDepartureDateTime(epoch + (int) (Math.random() * (21600)));
                        newFlight1.setEstFlightDuration(1800 + (int)(Math.random() * ((7200 - 1800) + 1)));
                        newFlight1.setOrigin(CityDeparture.KYIV);
                        newFlight1.setDestination(cityArrival);
                        newFlight1.setMaxNumSeats((int)(Math.random() * 168));
                        flights.add(newFlight1);
                    }
                }

        );

        System.out.println("Генерация новой коллекции направлений полетов");
        return flights;
    }

}
