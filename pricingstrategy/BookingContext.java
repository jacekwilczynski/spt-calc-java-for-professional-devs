package pricingstrategy;

public record BookingContext(Integer numberOfPeople, Float numberOfHours) {
    public BookingContext {
        if (numberOfPeople < 1) {
            throw new IllegalArgumentException(String.format(
                "Number of people must be at least 1, got %d.",
                numberOfPeople
            ));
        }

        if (numberOfHours < 0.5) {
            throw new IllegalArgumentException(String.format(
                "Not possible to book for less than half an hour, %.2f hours requested.",
                numberOfHours
            ));
        }

        if (numberOfHours % 0.5 != 0) {
            throw new IllegalArgumentException(String.format(
                "Booking time must be rounded to half hours, got %.2f.",
                numberOfHours
            ));
        }
    }
}
