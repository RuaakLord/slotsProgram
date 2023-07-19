/**
 * 
 * Created on 07/19/2023
 * 
 * ULID: dsboye1
 * Class: IT 168 
 * 
 */
package edu.ilstu;

import java.text.NumberFormat;
import java.util.*;

public class slotsClass {
    Scanner kb = new Scanner(System.in);
    NumberFormat money;
    private double pot;
    private double bet;
    private double add;
    Random random = new Random();
    private char choice;
    private String menu;

    public slotsClass() {
        pot = 0.0;
        bet = 0.0;
        money = NumberFormat.getCurrencyInstance(Locale.US);
        money.setMinimumFractionDigits(0);
        money.setMaximumFractionDigits(2);
    }

    public String displayMenu() {
        menu = "\nMenu:                             Bet: " + money.format(getBet()) + " Pot: "
                + money.format(getPot())
                + "\na. Add money to the pot \nb. Change bet\nc. Play\nd. Cash out\nPlease enter your choice (a through d): ";
        return menu;
    }

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

    public double getPot() {
        return pot;
    }

    public void addMoney() {
        System.out
                .println("Your current pot value is: " + money.format(getPot()) + "\nHow much would you like to add?");
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
        System.out.println("Your new pot value is: " + money.format(getPot()));

    }

    public double getBet() {
        return bet;
    }

    public void changeBet() {
        System.out.println("Current Bet: " + money.format(getBet())
                + "\nWhat would you like to set your bet to(Increments of 25 cents required): ");
        boolean validBet = false;
        while (!validBet) {
            if (kb.hasNextDouble()) {
                double suggBet = kb.nextDouble();

                if (suggBet < 0) {
                    System.out.println("I'm sorry, you cannot have a negative bet. ");
                } else {
                    bet = suggBet;
                    validBet = true;
                }
            } else {
                System.out.println("Please enter a valid numeric input in increments of 25 cents.");
                kb.nextLine();
            }
        }
    }

    public void play() {
        while (choice == 'c') {
            if (bet > pot) {
                System.out.println("I'm sorry, you are betting (Bet: " + money.format(getBet())
                        + ") more money than is in the pot. (Pot: " + money.format(getPot())
                        + ") Please add more to the pot.");
            } else {
                pot -= bet;
                String[] rollOutcomes = { "Cherries", "Oranges", "Plums", "Bells", "Melons", "Bars" };
                int[] rolls = new int[6];

                for (int i = 0; i < rolls.length; i++) {
                    rolls[i] = random.nextInt(5);
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
                System.out.println("Your winnings from this turn are: " + money.format(winnings));
                System.out.println("Would you like to play again? Yes/No(Or hit enter to skip this prompt) \n Pot: "
                        + money.format(getPot()) + " Bet: "
                        + money.format(getBet()) + ".");
                String choice3Cont = kb.nextLine();
                if (choice3Cont.equalsIgnoreCase("Yes") || choice3Cont.equalsIgnoreCase("")) {
                    continue;
                } else {
                    displayMenu();
                }
            }
        }

    }

    public void cashOut() {
        if (pot == 0) {
            System.out.println("You have nothing to cash out!");
        } else {
            System.out.println("You cashed out " + money.format(getPot()) + "! \n Congratulations!");
        }

    }
}
