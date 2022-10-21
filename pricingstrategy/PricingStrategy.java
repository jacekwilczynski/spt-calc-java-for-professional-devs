package pricingstrategy;

import java.util.function.Function;

public interface PricingStrategy extends Function<BookingContext, Float> {
}
