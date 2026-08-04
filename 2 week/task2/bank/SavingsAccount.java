package bank;

public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String owner, double balance, double interestRate) {
        super(owner, balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
        System.out.println(owner + " earned interest: " + interest + ". Balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
            return;
        }
        if (amount > balance) {
            System.out.println(owner + " insufficient funds for withdrawal");
            return;
        }
        balance -= amount;
        System.out.println(owner + " withdrew " + amount + ". Balance: " + balance);
    }

    @Override
    public void printInfo() {
        System.out.println("=== Savings Account ===");
        System.out.println("Owner: " + owner);
        System.out.println("Balance: " + balance);
        System.out.println("Interest rate: " + interestRate + "%");
    }
}
