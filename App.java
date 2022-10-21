import pricingstrategy.*;

import java.util.List;

public class App {
    /**
     * Prepares an example order with hard-coded items and displays the total.
     */
    public static void main(String[] args) {
        var pricingStrategy = new CompositePricingStrategy(List.of(
            getRoomPricingStrategy(), // room 1
            new HourlyPricingStrategy(2f), // crash cymbal
            new FixedPricingStrategy(2f), // double bass drum pedal
            new FixedPricingStrategy(15f) // piano
        ));

        System.out.println(pricingStrategy.apply(new BookingContext(
            3, // 3 people
            3.5f // 3.5 hours
        )));
    }

    private static PricingStrategy getRoomPricingStrategy() {
        var roomPricesForFirstTwoHours = new RoomPriceTable.PricesByNumberOfPeople();
        roomPricesForFirstTwoHours.put(1, 50f);
        roomPricesForFirstTwoHours.put(2, 70f);
        roomPricesForFirstTwoHours.put(3, 90f);
        roomPricesForFirstTwoHours.put(4, 120f);

        var roomPricesForEveryNextHour = new RoomPriceTable.PricesByNumberOfPeople();
        roomPricesForEveryNextHour.put(1, 20f);
        roomPricesForEveryNextHour.put(2, 30f);
        roomPricesForEveryNextHour.put(3, 35f);
        roomPricesForEveryNextHour.put(4, 40f);

        RoomPriceTable roomPriceTable = new RoomPriceTable(roomPricesForFirstTwoHours, roomPricesForEveryNextHour);

        return new RoomPricingStrategyFactory().createFor(roomPriceTable);
    }
}
