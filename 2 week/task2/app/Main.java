
package app;

import bank.*;

public class Main {

    public static void transfer(Account from, Account to, double amount) {
        System.out.println("\nTransferring " + amount + " from " + from.getOwner() + " to " + to.getOwner());
        if (from.getBalance() < amount) {
            System.out.println("Transfer failed: insufficient funds");
            return;
        }
        from.withdraw(amount);
        to.deposit(amount);
        System.out.println("Transfer complete");
    }

    public static void main(String[] args) {
        SavingsAccount savings = new SavingsAccount("Alice", 1000, 5);
        CreditAccount credit = new CreditAccount("Bob", 500, 200);

        savings.printInfo();
        System.out.println();
        credit.printInfo();

        savings.deposit(200);
        credit.withdraw(100);

        System.out.println("\n--- Applying interest ---");
        savings.applyInterest();

        transfer(savings, credit, 300);

        System.out.println("\n--- Final state ---");
        savings.printInfo();
        System.out.println();
        credit.printInfo();
    }
}
