/**
 * 
 * Created on 07/19/2023
 * 
 * ULID: dsboye1
 * Class: IT 168 
 * 
 */
package edu.ilstu;

import java.text.*;
import java.util.*;

public class slotsClass {
    private Scanner kb = new Scanner(System.in);
    private double pot;
    private double bet;
    private double add;
    private Random random = new Random();
    private char choice;
    private String menu;

    // Constructor
    public slotsClass() {
        pot = 0.0;
        bet = 0.0;
    }

    /**
     * Displays the menu
     * 
     * @return menu to user
     */
    public String displayMenu() {
        menu = "\nMenu:                             Bet: " + formatCurrency(getBet()) + " Pot: "
                + formatCurrency(getPot())
                + "\na. Add money to the pot \nb. Change bet\nc. Play\nd. Cash out\nPlease enter your choice (a through d): ";
        return menu;
    }

    /**
     * Starts the program, initially displaying menu
     * 
     */
    public void startGame() {

        do {
            System.out.println(displayMenu());

            choice = kb.next().charAt(0);
            kb.nextLine();

            switch (choice) {
                case 'a':
                    addMoney();
                    break;
                case 'b':
                    changeBet();
                    break;
                case 'c':
                    play();
                    break;
                case 'd':
                    cashOut();
                    break;
                default:
                    System.out.println("\n\nInvalid choice. Please try again");
            }
        } while (choice != 'd');

        kb.close();
    }

    /**
     * Outputs current Pot value
     * 
     * @return pot value
     */
    public double getPot() {
        return pot;
    }

    /**
     * Adds money to pot
     * 
     */
    public void addMoney() {
        System.out.println(
                "Your current pot value is: " + formatCurrency(getPot()) + "\nHow much would you like to add?");
        boolean validAdd = false;

        while (!validAdd) {
            if (kb.hasNextDouble()) {
                add = kb.nextDouble();
                kb.nextLine();

                if (add < 0) {
                    System.out.println("Sorry, you cannot add a negative value to the pot.");
                } else {
                    pot += add;
                    validAdd = true;
                }
            } else {
                System.out.println("Invalid input. Enter only valid numeric inputs.");
                kb.nextLine();
            }
        }
        System.out.println("Your new pot value is: " + formatCurrency(getPot()));

    }

    /**
     * Outputs current Bet value
     * 
     * @return bet value
     */
    public double getBet() {
        return bet;
    }

    /**
     * Changes bet based on input
     * 
     */
    public void changeBet() {
        System.out.println("Current Bet: " + formatCurrency(getBet())
                + "\nWhat would you like to set your bet to(Increments of 25 cents required): ");
        boolean validBet = false;
        while (!validBet) {
            if (kb.hasNextDouble()) {
                double suggBet = kb.nextDouble();

                if (suggBet < 0) {
                    System.out.println("I'm sorry, you cannot have a negative bet. ");
                    kb.nextLine();
                    break;
                } else if (suggBet % .25 != 0) {
                    System.out.println("Please enter a valid numeric input in increments of 25 cents.");
                    kb.nextLine();

                } else {
                    bet = suggBet;
                    validBet = true;
                }
            } else {
                kb.nextLine();
            }
        }
    }

    /**
     * Runs the game itself
     * 
     */
    public void play() {
        while (choice == 'c') {
            if (pot < .25) {
                System.out.println(
                        "Your pot is lower than the minimum bet of 25 cents, please add more to the pot to continue.(Press enter to continue) ");
                kb.nextLine();
                break;
            } else if (bet > pot) {
                System.out.println("I'm sorry, you are betting (Bet: " + formatCurrency(getBet())
                        + ") more money than is in the pot. (Pot: " + formatCurrency(getPot())
                        + ") Please add more to the pot or lower your bet.(Press enter to continue.)");
                kb.nextLine();
                break;
            } else {
                pot -= bet;
                String[] rollOutcomes = { "Cherries", "Oranges", "Plums", "Bells", "Melons", "Bars" };
                int[] rolls = new int[5];

                for (int i = 0; i < rolls.length; i++) {
                    rolls[i] = random.nextInt(6);
                }

                for (int roll : rolls) {
                    System.out.print(rollOutcomes[roll] + " | ");
                }
                System.out.println();

                int counter = 0;
                for (int i = 1; i < rolls.length; i++) {
                    if (rolls[i] == rolls[0]) {
                        counter++;
                    }

                }
                if (counter == 0) {
                    System.out.println("Wow, no matches! No winnings!");
                }
                double winnings = 0;
                switch (counter) { // This was the math to determine winnings amount.
                    case 0:
                        winnings = 0;
                        break;
                    case 1:
                        winnings = bet;
                        break;
                    case 2:
                        winnings = bet * 2;
                        break;
                    case 3:
                        winnings = bet * 3;
                        break;
                    case 4:
                        winnings = bet * 8;
                        break;
                    case 5:
                        winnings = bet * 20;
                        break;
                    default:
                        winnings = bet; // If machine breaks, user gets their bet back.
                }
                pot += winnings;
                System.out.println("Your winnings from this turn are: " + formatCurrency(winnings));
                System.out.println("Would you like to play again? Yes/No(Or hit enter to skip this prompt) \n Pot: "
                        + formatCurrency(getPot()) + " Bet: "
                        + formatCurrency(getBet()) + ".");
                String choice3Cont = kb.nextLine();
                if (choice3Cont.equalsIgnoreCase("Yes") || choice3Cont.equalsIgnoreCase("")) {
                    continue;
                } else {
                    displayMenu();
                }
            }
        }

    }

    /**
     * Cashes out user with current pot value
     * 
     */
    public void cashOut() {
        if (pot == 0) {
            System.out.println("You have nothing to cash out!");
        } else {
            System.out.println("You cashed out " + formatCurrency(getPot()) + "! \n Congratulations!");
        }
    }

    /**
     * Formats the currency with correct decimals
     * 
     * @param amount
     * @return formatted currency
     */
    private String formatCurrency(double amount) {
        if (amount == (int) amount) {
            return NumberFormat.getCurrencyInstance(Locale.US).format(amount).replace(".00", "");
        } else {
            return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
        }
    }
}
