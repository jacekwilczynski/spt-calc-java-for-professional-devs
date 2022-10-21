package pricingstrategy;

public class FixedPricingStrategy implements PricingStrategy {
    private final Float price;

    public FixedPricingStrategy(Float price) {
        this.price = price;
    }

    @Override
    public Float apply(BookingContext bookingContext) {
        return price;
    }
}
