package bank;

public abstract class Account implements Printable {

    protected double balance;
    protected String owner;

    public Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount");
            return;
        }
        balance += amount;
        System.out.println(owner + " deposited " + amount + ". Balance: " + balance);
    }

    public abstract void withdraw(double amount);

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }
}
