import java.util.Scanner;

class BankAccount {
    String name;
    String userName;
    String password;
    String accountNo;
    float balance = 10000f;
    int transactions = 0;
    String transactionHistory = "";

    public void register(Scanner sc) {
        System.out.println("\nEnter your Name: ");
        this.name = sc.nextLine().trim();
        while (this.name.isEmpty()) {
            System.out.println("Name cannot be empty. Please enter your Name: ");
            this.name = sc.nextLine().trim();
        }

        System.out.println("\nEnter your Username: ");
        this.userName = sc.nextLine().trim();
        while (this.userName.isEmpty()) {
            System.out.println("Username cannot be empty. Please enter your Username: ");
            this.userName = sc.nextLine().trim();
        }

        System.out.println("\nEnter your Password: ");
        this.password = sc.nextLine().trim();
        while (this.password.isEmpty()) {
            System.out.println("Password cannot be empty. Please enter your Password: ");
            this.password = sc.nextLine().trim();
        }

        System.out.println("\nEnter your Account Number: ");
        this.accountNo = sc.nextLine().trim();
        while (this.accountNo.isEmpty()) {
            System.out.println("Account Number cannot be empty. Please enter your Account Number: ");
            this.accountNo = sc.nextLine().trim();
        }

        System.out.println("\nRegistration Successful. Please Log in to your Bank Account");
    }

    public boolean login(Scanner sc) {
        boolean isLogin = false;
        while (!isLogin) {
            System.out.println("\nEnter your username: ");
            String inputUsername = sc.nextLine().trim();
            if (inputUsername.equals(userName)) {
                while (!isLogin) {
                    System.out.println("\nEnter your password: ");
                    String inputPassword = sc.nextLine().trim();
                    if (inputPassword.equals(this.password)) {
                        System.out.println("\nLogin Successful");
                        isLogin = true;
                    } else {
                        System.out.println("\nIncorrect Password");
                    }
                }
            } else {
                System.out.println("\nUsername not found");
            }
        }
        return isLogin;
    }

    public void withdraw(Scanner sc) {
        System.out.println("\nEnter Amount to Withdraw: ");
        while (!sc.hasNextFloat()) {
            System.out.println("Invalid input. Please enter a valid amount: ");
            sc.next();
        }
        float amount = sc.nextFloat();
        sc.nextLine(); // Consume newline
        if (balance >= amount) {
            transactions++;
            balance -= amount;
            System.out.println("\nWithdrawal Successful.");
            String str = amount + " Rs Withdrawn\n";
            transactionHistory = transactionHistory.concat(str);
        } else {
            System.out.println("\nInsufficient Balance.");
        }
    }

    public void deposit(Scanner sc) {
        System.out.println("\nEnter Amount to Deposit: ");
        while (!sc.hasNextFloat()) {
            System.out.println("Invalid input. Please enter a valid amount: ");
            sc.next();
        }
        float amount = sc.nextFloat();
        sc.nextLine(); // Consume newline
        if (amount <= 10000f) {
            transactions++;
            balance += amount;
            System.out.println("\nDeposit Successful.");
            String str = amount + " Rs deposited\n";
            transactionHistory = transactionHistory.concat(str);
        } else {
            System.out.println("\nSorry. The limit is 10000.");
        }
    }

    public void transfer(Scanner sc) {
        System.out.println("\nEnter Recipient's Name: ");
        String recipient = sc.nextLine().trim();
        System.out.println("\nEnter Amount to transfer: ");
        while (!sc.hasNextFloat()) {
            System.out.println("Invalid input. Please enter a valid amount: ");
            sc.next();
        }
        float amount = sc.nextFloat();
        sc.nextLine(); // Consume newline
        if (balance >= amount) {
            if (amount <= 50000f) {
                transactions++;
                balance -= amount;
                System.out.println("\nSuccessfully Transferred to " + recipient);
                String str = amount + " Rs transferred to " + recipient + "\n";
                transactionHistory = transactionHistory.concat(str);
            } else {
                System.out.println("\nSorry. The limit is 50000.");
            }
        } else {
            System.out.println("\nInsufficient Balance.");
        }
    }

    public void checkBalance() {
        System.out.println("\n" + balance + " Rs");
    }

    public void transHistory() {
        if (transactions == 0) {
            System.out.println("No Transactions happened");
        } else {
            System.out.print("\n" + transactionHistory);
        }
    }
}

public class ATMInterface {

    public static int takeIntegerInput(Scanner sc, int limit) {
        int input = 0;
        boolean flag = false;

        while (!flag) {
            try {
                input = sc.nextInt();
                flag = true;

                if (input > limit || input < 1) {
                    System.out.println("Choose a number between 1 and " + limit);
                    flag = false;
                }
            } catch (Exception e) {
                System.out.println("Enter only integer value.");
                sc.nextLine(); // Clear the invalid input
                flag = false;
            }
        }
        sc.nextLine(); // Consume newline
        return input;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("\nATM INTERFACE");
            System.out.println("\n1.Register \n2.Exit");
            System.out.println("Choose one option: ");
            int choose = takeIntegerInput(sc, 2);
            
            if (choose == 1) {
                BankAccount b = new BankAccount();
                b.register(sc);
                while (true) {
                    System.out.println("\n1.Login \n2.Exit");
                    System.out.println("Enter your choice: ");
                    int ch = takeIntegerInput(sc, 2);
                    if (ch == 1) {
                        if (b.login(sc)) {
                            System.out.println("\n*******WELCOME BACK " + b.name + "******");
                            boolean isFinished = false;
                            while (!isFinished) {
                                System.out.println("\n1.Withdraw \n2.Deposit \n3.Transfer \n4.Check Balance \n5.Transaction History \n6.Exit");
                                System.out.println("Enter your choice: ");
                                int c = takeIntegerInput(sc, 6);
                                switch (c) {
                                    case 1 -> b.withdraw(sc);
                                    case 2 -> b.deposit(sc);
                                    case 3 -> b.transfer(sc);
                                    case 4 -> b.checkBalance();
                                    case 5 -> b.transHistory();
                                    case 6 -> isFinished = true;
                                }
                            }
                        }
                    } else {
                        System.exit(0);
                    }
                }
            } else {
                System.exit(0);
            }
        }
    }
}
