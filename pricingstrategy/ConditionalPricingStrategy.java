package pricingstrategy;

import java.util.List;
import java.util.function.Predicate;

public class ConditionalPricingStrategy implements PricingStrategy {
    private final List<PricingRule> rules;

    public ConditionalPricingStrategy(List<PricingRule> rules) {
        this.rules = rules;
    }

    @Override
    public Float apply(BookingContext bookingContext) {
        for (PricingRule rule : rules) {
            if (rule.condition().test(bookingContext)) {
                return rule.strategy().apply(bookingContext);
            }
        }

        throw new NoApplicableStrategyException(bookingContext);
    }

    public static class NoApplicableStrategyException extends RuntimeException {
        NoApplicableStrategyException(BookingContext bookingContext) {
            super(String.format(
                "The %s context doesn't match any of the given pricing rules.",
                bookingContext
            ));
        }
    }

    public record PricingRule(
        Predicate<BookingContext> condition,
        PricingStrategy strategy
    ) {
    }
}
