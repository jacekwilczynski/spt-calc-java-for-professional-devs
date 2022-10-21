package pricingstrategy;

import java.util.List;

public class CompositePricingStrategy implements PricingStrategy {
    private final List<PricingStrategy> strategies;

    public CompositePricingStrategy(List<PricingStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public Float apply(BookingContext bookingContext) {
        return strategies
            .stream()
            .map(strategy -> strategy.apply(bookingContext))
            .reduce(0.0f, Float::sum);
    }
}
