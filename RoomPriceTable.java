import java.util.HashMap;

public record RoomPriceTable(
    PricesByNumberOfPeople pricesForFirstTwoHours,
    PricesByNumberOfPeople pricesForEveryNextHour
) {
    static class PricesByNumberOfPeople extends HashMap<Integer, Float> {
    }
}
