package bank;

public class CreditAccount extends Account {

    private double overdraftLimit;

    public CreditAccount(String owner, double balance, double overdraftLimit) {
        super(owner, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
            return;
        }
        if (amount > balance + overdraftLimit) {
            System.out.println(owner + " exceeds overdraft limit");
            return;
        }
        balance -= amount;
        System.out.println(owner + " withdrew " + amount + ". Balance: " + balance);
    }

    @Override
    public void printInfo() {
        System.out.println("=== Credit Account ===");
        System.out.println("Owner: " + owner);
        System.out.println("Balance: " + balance);
        System.out.println("Overdraft limit: " + overdraftLimit);
    }
}
