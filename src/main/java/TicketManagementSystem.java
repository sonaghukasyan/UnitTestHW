import java.time.LocalTime;

public class TicketManagementSystem {

    public enum ShowDay
    {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public enum MembershipTier {
        PLATINUM, GOLD, SILVER, NONE
    }

    public static void checkMovieEligibility(int age, boolean isStudent) {
        if (age >= 18 && age <= 120) {
            System.out.println("You are eligible to watch movies.");

            if (isStudent) {
                System.out.println("As a student, you get a discounted ticket.");
            } else {
                System.out.println("You need to purchase a regular ticket.");
            }
        } else if(age >= 0){
            System.out.println("Sorry, you are not eligible to watch our movies.");
        }else{
            throw new IllegalArgumentException("Not a valid age");
        }
    }

    public static void reserveTicketForToday(int age, LocalTime showTime) {
        if(age >= 0 && age <= 120){
            if (age < 18 && showTime.isAfter(LocalTime.parse("20:00"))) {
                System.out.println("Sorry, ticket reservation is not allowed for minors after 8 PM.");
            } else if (age >= 60 && showTime.isAfter(LocalTime.parse("22:00"))) {
                System.out.println("Sorry, ticket reservation is not allowed for seniors after 10 PM.");
            } else {
                System.out.println("Ticket reserved successfully.");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid inputs");
        }
    }

    public static void calculateTicketPrice(double basePrice, ShowDay showDay) {
        if(basePrice > 0 && showDay != null){
            if (showDay == ShowDay.MONDAY || showDay == ShowDay.TUESDAY ||
                    showDay == ShowDay.WEDNESDAY || showDay == ShowDay.THURSDAY) {
                double discountedPrice = basePrice - (basePrice * 0.10);
                System.out.println("Ticket price (with 10% discount): $" + discountedPrice);
            } else {
                System.out.println("Regular ticket price: $" + basePrice);
            }
        }else{
            throw new IllegalArgumentException("Invalid inputs");
        }
    }

    public static void upgradeTicket(MembershipTier membershipTier) {
        if(membershipTier != null){
            switch (membershipTier) {
                case PLATINUM:
                    System.out.println("Congratulations! You qualify for a complimentary upgrade to VIP seating.");
                    break;
                case GOLD:
                    System.out.println("As a gold member, you can upgrade to premium seating at a discounted rate.");
                    break;
                case SILVER:
                    System.out.println("Enjoy a standard upgrade with no additional charge.");
                    break;
                case NONE:
                    System.out.println("Sorry, no upgrade options available for your membership tier.");
                    break;
            }
        } else{
            throw new IllegalArgumentException("Invalid membership tier: " + membershipTier);
        }
    }
}
