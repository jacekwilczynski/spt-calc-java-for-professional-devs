import pricingstrategy.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class RoomPricingStrategyFactory {

    public static final int MIN_NUMBER_OF_HOURS = 2;

    public PricingStrategy createFor(RoomPriceTable roomPriceTable) {
        return new CompositePricingStrategy(List.of(
            new ConditionalPricingStrategy(createRulesFromPriceMap(
                roomPriceTable.pricesForFirstTwoHours(),
                FixedPricingStrategy::new
            )),
            new ConditionalPricingStrategy(createRulesFromPriceMap(
                roomPriceTable.pricesForEveryNextHour(),
                getHourlyStrategyFactoryWithThresholdDeduction()
            ))
        ));
    }

    private List<ConditionalPricingStrategy.PricingRule> createRulesFromPriceMap(
        RoomPriceTable.PricesByNumberOfPeople pricesByNumberOfPeople,
        Function<Float, PricingStrategy> strategyFactory
    ) {
        List<ConditionalPricingStrategy.PricingRule> rules = new LinkedList<>();

        for (var entry : pricesByNumberOfPeople.entrySet()) {
            var isLast = rules.size() == pricesByNumberOfPeople.size() - 1;

            var comparison = isLast
                ? isGreaterThanOrEqualTo(entry.getKey())
                : isEqualTo(entry.getKey());

            rules.add(new ConditionalPricingStrategy.PricingRule(
                bookingContext -> comparison.test(bookingContext.numberOfPeople()),
                strategyFactory.apply(entry.getValue())
            ));
        }

        return rules;
    }

    private static Function<Float, PricingStrategy> getHourlyStrategyFactoryWithThresholdDeduction() {
        return hourlyRate -> new HourlyPricingStrategy(hourlyRate) {
            @Override
            public Float apply(BookingContext bookingContext) {
                return super.apply(new BookingContext(
                    bookingContext.numberOfPeople(),
                    bookingContext.numberOfHours() - MIN_NUMBER_OF_HOURS
                ));
            }
        };
    }

    private interface Comparison extends Predicate<Integer> {
    }

    private Comparison isEqualTo(Integer expected) {
        return expected::equals;
    }

    private Comparison isGreaterThanOrEqualTo(Integer expected) {
        return actual -> actual >= expected;
    }
}
