import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;

public class TicketManagementSystemTest {

    //Unit tests for checkMovieEligibility method
    @Test
    public void testCheckMovieEligibilityForAdultNonStudent() {
        int age = 25;
        boolean isStudent = false;
        String expectedOutput = "You are eligible to watch movies.\r\nYou need to purchase a regular ticket.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.checkMovieEligibility(age, isStudent));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCheckMovieEligibilityForAdultStudent() {
        int age = 95;
        boolean isStudent = true;
        String expectedOutput = "You are eligible to watch movies.\r\nAs a student, you get a discounted ticket.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.checkMovieEligibility(age, isStudent));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCheckMovieEligibilityNonAdultStudent() {
        int age = 10;
        boolean isStudent = true;
        String expectedOutput = "Sorry, you are not eligible to watch our movies.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.checkMovieEligibility(age, isStudent));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckMovieEligibilityWithNonValidAge() {
        int age = -500;
        boolean isStudent = true;
        TicketManagementSystem.checkMovieEligibility(age, isStudent);
    }

    //Unit tests for reserveTicketForToday method
    @Test
    public void testReserveTicketForMinorsAfter8PM() {
        int age = 15;
        LocalTime showTime = LocalTime.parse("20:30");
        String expectedOutput = "Sorry, ticket reservation is not allowed for minors after 8 PM.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.reserveTicketForToday(age, showTime));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testReserveTicketForSeniorsAfter10PM() {
        int age = 70;
        LocalTime showTime = LocalTime.parse("23:30");
        String expectedOutput = "Sorry, ticket reservation is not allowed for seniors after 10 PM.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.reserveTicketForToday(age, showTime));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testReserveTicketForSuccessfulReservation() {
        int age = 25;
        LocalTime showTime = LocalTime.parse("23:30");
        String expectedOutput = "Ticket reserved successfully.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.reserveTicketForToday(age, showTime));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReserveTicketWithInvalidInputs() {
        TicketManagementSystem.reserveTicketForToday(-10, null);
    }

    //Unit tests for calculateTicketPrice method

    @Test
    public void testCalculateTicketPriceWithDiscount() {
        double basePrice = 100.0;
        TicketManagementSystem.ShowDay showDay = TicketManagementSystem.ShowDay.MONDAY;
        double discountedPrice = basePrice - (basePrice * 0.10);
        String expectedOutput = "Ticket price (with 10% discount): $" + discountedPrice;
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.calculateTicketPrice(basePrice, showDay));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCalculateTicketPriceWithoutDiscount() {
        double basePrice = 100.0;
        TicketManagementSystem.ShowDay showDay = TicketManagementSystem.ShowDay.FRIDAY;
        String expectedOutput = "Regular ticket price: $" + basePrice;
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.calculateTicketPrice(basePrice, showDay));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateTicketPriceWithInvalidInputs() {
        TicketManagementSystem.calculateTicketPrice(0, null);
    }

    //Unit tests for upgradeTicket method
    @Test
    public void testUpgradeTicketForPlatinum() {
        TicketManagementSystem.MembershipTier membershipTier = TicketManagementSystem.MembershipTier.PLATINUM;
        String expectedOutput = "Congratulations! You qualify for a complimentary upgrade to VIP seating.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.upgradeTicket(membershipTier));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpgradeTicketForGold() {
        TicketManagementSystem.MembershipTier membershipTier = TicketManagementSystem.MembershipTier.GOLD;
        String expectedOutput = "As a gold member, you can upgrade to premium seating at a discounted rate.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.upgradeTicket(membershipTier));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpgradeTicketForSilver() {
        TicketManagementSystem.MembershipTier membershipTier = TicketManagementSystem.MembershipTier.SILVER;
        String expectedOutput = "Enjoy a standard upgrade with no additional charge.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.upgradeTicket(membershipTier));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpgradeTicketForNone() {
        TicketManagementSystem.MembershipTier membershipTier = TicketManagementSystem.MembershipTier.NONE;
        String expectedOutput = "Sorry, no upgrade options available for your membership tier.";
        String actualOutput = getConsoleOutput(() -> TicketManagementSystem.upgradeTicket(membershipTier));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpgradeTicketForInvalidTier() {
        TicketManagementSystem.MembershipTier membershipTier = null;
        TicketManagementSystem.upgradeTicket(membershipTier); // Expecting IllegalArgumentException
    }

    // Method to capture console output for assertions
    private String getConsoleOutput(Runnable task) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        task.run();
        System.setOut(originalOut);
        return outputStream.toString().trim();
    }

}