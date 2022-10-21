package pricingstrategy;

public class HourlyPricingStrategy implements PricingStrategy {
    private final Float hourlyRate;

    public HourlyPricingStrategy(Float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public Float apply(BookingContext bookingContext) {
        return bookingContext.numberOfHours() * hourlyRate;
    }
}
