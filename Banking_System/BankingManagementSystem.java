import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

// Entity Classes
class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String idNumber; // Aadhaar or PAN
    private Date dateOfBirth;
    private Date registrationDate;
    private String customerType; // Regular, Premium, VIP

    public Customer(int customerId, String firstName, String lastName, String email, 
                   String phone, String address, String idNumber, Date dateOfBirth, String customerType) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = new Date();
        this.customerType = customerType;
    }

    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getIdNumber() { return idNumber; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public Date getRegistrationDate() { return registrationDate; }
    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("ID: %-4d Name: %-12s %-12s Email: %-20s Phone: %-12s", 
            customerId, firstName, lastName, email, phone);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

class Account {
    private String accountNumber;
    private int customerId;
    private String accountType; // Savings, Current, Fixed Deposit, Recurring Deposit
    private double balance;
    private double interestRate;
    private Date openingDate;
    private String status; // Active, Inactive, Frozen, Closed

    public Account(String accountNumber, int customerId, String accountType, 
                  double initialBalance, double interestRate) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = initialBalance;
        this.interestRate = interestRate;
        this.openingDate = new Date();
        this.status = "Active";
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public int getCustomerId() { return customerId; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public double getInterestRate() { return interestRate; }
    public Date getOpeningDate() { return openingDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance && "Active".equals(status)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Account: %-12s Type: %-10s Balance: ₹%-12s Status: %-8s", 
            accountNumber, accountType, df.format(balance), status);
    }
}

class Transaction {
    private int transactionId;
    private String accountNumber;
    private String transactionType; // Deposit, Withdrawal, Transfer, Interest
    private double amount;
    private String description;
    private Date transactionDate;
    private String targetAccount; // For transfers
    private double balanceAfter;

    public Transaction(int transactionId, String accountNumber, String transactionType, 
                      double amount, String description, double balanceAfter) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.transactionDate = new Date();
        this.balanceAfter = balanceAfter;
    }

    // Getters
    public int getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public Date getTransactionDate() { return transactionDate; }
    public String getTargetAccount() { return targetAccount; }
    public void setTargetAccount(String targetAccount) { this.targetAccount = targetAccount; }
    public double getBalanceAfter() { return balanceAfter; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String targetInfo = targetAccount != null ? " to " + targetAccount : "";
        return String.format("TX%d | %s | %-10s | ₹%-10s | %s%s | Balance: ₹%s", 
            transactionId, sdf.format(transactionDate), transactionType, 
            df.format(amount), description, targetInfo, df.format(balanceAfter));
    }
}

class Loan {
    private int loanId;
    private int customerId;
    private String loanType; // Personal, Home, Auto, Business, Education
    private double loanAmount;
    private double interestRate;
    private int termMonths;
    private Date startDate;
    private double remainingBalance;
    private String status; // Pending, Approved, Active, Paid, Defaulted

    public Loan(int loanId, int customerId, String loanType, double loanAmount, 
                double interestRate, int termMonths) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
        this.startDate = new Date();
        this.remainingBalance = loanAmount;
        this.status = "Pending";
    }

    // Getters and Setters
    public int getLoanId() { return loanId; }
    public int getCustomerId() { return customerId; }
    public String getLoanType() { return loanType; }
    public double getLoanAmount() { return loanAmount; }
    public double getInterestRate() { return interestRate; }
    public int getTermMonths() { return termMonths; }
    public Date getStartDate() { return startDate; }
    public double getRemainingBalance() { return remainingBalance; }
    public void setRemainingBalance(double remainingBalance) { this.remainingBalance = remainingBalance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double calculateMonthlyPayment() {
        double monthlyRate = interestRate / 12 / 100;
        return (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, termMonths)) 
               / (Math.pow(1 + monthlyRate, termMonths) - 1);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Loan ID: %-4d Type: %-10s Amount: ₹%-12s Balance: ₹%-12s Status: %-10s", 
            loanId, loanType, df.format(loanAmount), df.format(remainingBalance), status);
    }
}

// Main Banking Management System
public class BankingManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");
    
    // In-memory data storage
    private static List<Customer> customers = new ArrayList<>();
    private static List<Account> accounts = new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();
    private static List<Loan> loans = new ArrayList<>();
    
    // ID counters
    private static int customerIdCounter = 1001;
    private static int transactionIdCounter = 1;
    private static int loanIdCounter = 1;

    public static void main(String[] args) {
        // Initialize with sample data
        initializeSampleData();
        
        System.out.println("=== BANKING MANAGEMENT SYSTEM ===");
        System.out.println("           INDIAN RUPEE (₹)           ");
        System.out.println("No Database Required - All Data Stored In Memory");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            System.out.print("Enter your choice (1-9): ");
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    customerManagement();
                    break;
                case 2:
                    accountManagement();
                    break;
                case 3:
                    transactionManagement();
                    break;
                case 4:
                    loanManagement();
                    break;
                case 5:
                    viewBankStatistics();
                    break;
                case 6:
                    searchFunctionality();
                    break;
                case 7:
                    generateReports();
                    break;
                case 8:
                    interestCalculation();
                    break;
                case 9:
                    running = false;
                    System.out.println("Thank you for using Banking Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    // Function 1: Display main menu
    public static void displayMainMenu() {
        System.out.println("\n=== BANKING MANAGEMENT SYSTEM ===");
        System.out.println("1. Customer Management");
        System.out.println("2. Account Management");
        System.out.println("3. Transaction Management");
        System.out.println("4. Loan Management");
        System.out.println("5. Bank Statistics");
        System.out.println("6. Search");
        System.out.println("7. Reports");
        System.out.println("8. Interest Calculation");
        System.out.println("9. Exit");
        System.out.println("================================");
    }

    // Function 2: Customer Management
    public static void customerManagement() {
        System.out.println("\n--- CUSTOMER MANAGEMENT ---");
        System.out.println("1. Register New Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. Find Customer by ID");
        System.out.println("4. Update Customer Information");
        System.out.println("5. View Customer Accounts");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                registerNewCustomer();
                break;
            case 2:
                viewAllCustomers();
                break;
            case 3:
                findCustomerById();
                break;
            case 4:
                updateCustomerInfo();
                break;
            case 5:
                viewCustomerAccounts();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Function 3: Register New Customer
    public static void registerNewCustomer() {
        System.out.println("\n--- REGISTER NEW CUSTOMER ---");
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Aadhaar/PAN Number: ");
        String idNumber = scanner.nextLine();
        
        Date dob = null;
        while (dob == null) {
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            String dobStr = scanner.nextLine();
            try {
                dob = dateFormat.parse(dobStr);
            } catch (Exception e) {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }
        
        System.out.print("Customer Type (Regular/Premium/VIP): ");
        String customerType = scanner.nextLine();
        
        Customer customer = new Customer(customerIdCounter++, firstName, lastName, email, 
                                       phone, address, idNumber, dob, customerType);
        customers.add(customer);
        
        System.out.println("Customer registered successfully!");
        System.out.println("Customer ID: " + customer.getCustomerId());
        
        // Automatically create a savings account for new customer
        createAccountForCustomer(customer.getCustomerId());
    }

    // Function 4: Create account for new customer
    public static void createAccountForCustomer(int customerId) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, customerId, "Savings", 0.0, 3.5); // Indian savings account interest rate
        accounts.add(account);
        System.out.println("Savings account created: " + accountNumber);
    }

    // Function 5: Generate unique account number
    public static String generateAccountNumber() {
        Random random = new Random();
        return "ACC" + (1000000000 + random.nextInt(900000000));
    }

    // Function 6: View All Customers
    public static void viewAllCustomers() {
        System.out.println("\n--- ALL CUSTOMERS ---");
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        
        System.out.printf("%-6s %-15s %-15s %-20s %-15s %-12s%n", 
            "ID", "First Name", "Last Name", "Email", "Phone", "Type");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Customer customer : customers) {
            System.out.printf("%-6d %-15s %-15s %-20s %-15s %-12s%n", 
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerType()
            );
        }
        System.out.println("\nTotal customers: " + customers.size());
    }

    // Function 7: Account Management
    public static void accountManagement() {
        System.out.println("\n--- ACCOUNT MANAGEMENT ---");
        System.out.println("1. View All Accounts");
        System.out.println("2. Open New Account");
        System.out.println("3. Close Account");
        System.out.println("4. View Account Details");
        System.out.println("5. View Account Statement");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                viewAllAccounts();
                break;
            case 2:
                openNewAccount();
                break;
            case 3:
                closeAccount();
                break;
            case 4:
                viewAccountDetails();
                break;
            case 5:
                viewAccountStatement();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Function 8: View All Accounts
    public static void viewAllAccounts() {
        System.out.println("\n--- ALL ACCOUNTS ---");
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }
        
        System.out.printf("%-15s %-8s %-10s %-15s %-10s %-12s%n", 
            "Account No", "Cust ID", "Type", "Balance", "Interest", "Status");
        System.out.println("------------------------------------------------------------------------");
        
        for (Account account : accounts) {
            System.out.printf("%-15s %-8d %-10s ₹%-14s %-10.2f%% %-12s%n", 
                account.getAccountNumber(),
                account.getCustomerId(),
                account.getAccountType(),
                currencyFormat.format(account.getBalance()),
                account.getInterestRate(),
                account.getStatus()
            );
        }
        System.out.println("\nTotal accounts: " + accounts.size());
    }

    // Function 9: Transaction Management
    public static void transactionManagement() {
        System.out.println("\n--- TRANSACTION MANAGEMENT ---");
        System.out.println("1. Deposit Money");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Transfer Funds");
        System.out.println("4. View All Transactions");
        System.out.println("5. View Transaction History");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                depositMoney();
                break;
            case 2:
                withdrawMoney();
                break;
            case 3:
                transferFunds();
                break;
            case 4:
                viewAllTransactions();
                break;
            case 5:
                viewTransactionHistory();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Function 10: Deposit Money
    public static void depositMoney() {
        System.out.println("\n--- DEPOSIT MONEY ---");
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Optional<Account> account = findAccountByNumber(accountNumber);
        if (account.isEmpty()) {
            System.out.println("Account not found!");
            return;
        }
        
        System.out.print("Enter deposit amount: ₹");
        double amount = getDoubleInput();
        
        if (amount <= 0) {
            System.out.println("Invalid amount! Amount must be positive.");
            return;
        }
        
        double oldBalance = account.get().getBalance();
        if (account.get().deposit(amount)) {
            double newBalance = account.get().getBalance();
            Transaction transaction = new Transaction(transactionIdCounter++, accountNumber, 
                    "Deposit", amount, "Cash deposit", newBalance);
            transactions.add(transaction);
            
            System.out.println("Deposit successful!");
            System.out.println("Old Balance: ₹" + currencyFormat.format(oldBalance));
            System.out.println("Amount Deposited: ₹" + currencyFormat.format(amount));
            System.out.println("New Balance: ₹" + currencyFormat.format(newBalance));
            System.out.println("Transaction ID: " + transaction.getTransactionId());
        } else {
            System.out.println("Deposit failed! Account may be inactive.");
        }
    }

    // Function 11: Withdraw Money
    public static void withdrawMoney() {
        System.out.println("\n--- WITHDRAW MONEY ---");
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Optional<Account> account = findAccountByNumber(accountNumber);
        if (account.isEmpty()) {
            System.out.println("Account not found!");
            return;
        }
        
        System.out.print("Enter withdrawal amount: ₹");
        double amount = getDoubleInput();
        
        if (amount <= 0) {
            System.out.println("Invalid amount! Amount must be positive.");
            return;
        }
        
        double oldBalance = account.get().getBalance();
        if (account.get().withdraw(amount)) {
            double newBalance = account.get().getBalance();
            Transaction transaction = new Transaction(transactionIdCounter++, accountNumber, 
                    "Withdrawal", amount, "Cash withdrawal", newBalance);
            transactions.add(transaction);
            
            System.out.println("Withdrawal successful!");
            System.out.println("Old Balance: ₹" + currencyFormat.format(oldBalance));
            System.out.println("Amount Withdrawn: ₹" + currencyFormat.format(amount));
            System.out.println("New Balance: ₹" + currencyFormat.format(newBalance));
            System.out.println("Transaction ID: " + transaction.getTransactionId());
        } else {
            System.out.println("Withdrawal failed! Insufficient funds or account inactive.");
        }
    }

    // Function 12: Transfer Funds
    public static void transferFunds() {
        System.out.println("\n--- TRANSFER FUNDS ---");
        
        System.out.print("Enter Source Account Number: ");
        String sourceAccountNumber = scanner.nextLine();
        
        Optional<Account> sourceAccount = findAccountByNumber(sourceAccountNumber);
        if (sourceAccount.isEmpty()) {
            System.out.println("Source account not found!");
            return;
        }
        
        System.out.print("Enter Target Account Number: ");
        String targetAccountNumber = scanner.nextLine();
        
        Optional<Account> targetAccount = findAccountByNumber(targetAccountNumber);
        if (targetAccount.isEmpty()) {
            System.out.println("Target account not found!");
            return;
        }
        
        if (sourceAccountNumber.equals(targetAccountNumber)) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }
        
        System.out.print("Enter transfer amount: ₹");
        double amount = getDoubleInput();
        
        if (amount <= 0) {
            System.out.println("Invalid amount! Amount must be positive.");
            return;
        }
        
        double sourceOldBalance = sourceAccount.get().getBalance();
        if (sourceAccount.get().transfer(targetAccount.get(), amount)) {
            double sourceNewBalance = sourceAccount.get().getBalance();
            double targetNewBalance = targetAccount.get().getBalance();
            
            // Create transaction for source account
            Transaction sourceTransaction = new Transaction(transactionIdCounter++, sourceAccountNumber, 
                    "Transfer", amount, "Transfer to " + targetAccountNumber, sourceNewBalance);
            sourceTransaction.setTargetAccount(targetAccountNumber);
            transactions.add(sourceTransaction);
            
            // Create transaction for target account
            Transaction targetTransaction = new Transaction(transactionIdCounter++, targetAccountNumber, 
                    "Transfer", amount, "Transfer from " + sourceAccountNumber, targetNewBalance);
            targetTransaction.setTargetAccount(sourceAccountNumber);
            transactions.add(targetTransaction);
            
            System.out.println("Transfer successful!");
            System.out.println("Amount Transferred: ₹" + currencyFormat.format(amount));
            System.out.println("From Account: " + sourceAccountNumber + " | New Balance: ₹" + currencyFormat.format(sourceNewBalance));
            System.out.println("To Account: " + targetAccountNumber + " | New Balance: ₹" + currencyFormat.format(targetNewBalance));
            System.out.println("Transaction ID: " + sourceTransaction.getTransactionId());
        } else {
            System.out.println("Transfer failed! Insufficient funds or account issues.");
        }
    }

    // Function 13: Loan Management
    public static void loanManagement() {
        System.out.println("\n--- LOAN MANAGEMENT ---");
        System.out.println("1. Apply for Loan");
        System.out.println("2. View All Loans");
        System.out.println("3. Approve Loan");
        System.out.println("4. Make Loan Payment");
        System.out.println("5. View Loan Details");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                applyForLoan();
                break;
            case 2:
                viewAllLoans();
                break;
            case 3:
                approveLoan();
                break;
            case 4:
                makeLoanPayment();
                break;
            case 5:
                viewLoanDetails();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Function 14: Apply for Loan
    public static void applyForLoan() {
        System.out.println("\n--- APPLY FOR LOAN ---");
        
        System.out.print("Enter Customer ID: ");
        int customerId = getIntInput();
        
        Optional<Customer> customer = customers.stream()
            .filter(c -> c.getCustomerId() == customerId)
            .findFirst();
            
        if (customer.isEmpty()) {
            System.out.println("Customer not found!");
            return;
        }
        
        System.out.print("Loan Type (Personal/Home/Auto/Business/Education): ");
        String loanType = scanner.nextLine();
        
        System.out.print("Loan Amount: ₹");
        double loanAmount = getDoubleInput();
        
        System.out.print("Term (months): ");
        int termMonths = getIntInput();
        
        // Determine interest rate based on loan type (Indian rates)
        double interestRate = determineInterestRate(loanType);
        
        Loan loan = new Loan(loanIdCounter++, customerId, loanType, loanAmount, interestRate, termMonths);
        loans.add(loan);
        
        System.out.println("Loan application submitted successfully!");
        System.out.println("Loan ID: " + loan.getLoanId());
        System.out.println("Estimated Monthly Payment: ₹" + currencyFormat.format(loan.calculateMonthlyPayment()));
    }

    // Function 15: Determine interest rate based on loan type (Indian rates)
    public static double determineInterestRate(String loanType) {
        switch (loanType.toLowerCase()) {
            case "personal": return 10.5;   // Personal loan rate in India
            case "home": return 6.5;        // Home loan rate
            case "auto": return 7.5;        // Auto loan rate
            case "business": return 12.0;   // Business loan rate
            case "education": return 8.0;   // Education loan rate
            default: return 11.0;
        }
    }

    // Function 16: Bank Statistics
    public static void viewBankStatistics() {
        System.out.println("\n=== BANK STATISTICS ===");
        
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("Total Loans: " + loans.size());
        System.out.println("Total Transactions: " + transactions.size());
        
        // Total deposits
        double totalDeposits = accounts.stream()
            .mapToDouble(Account::getBalance)
            .sum();
        System.out.println("Total Bank Deposits: ₹" + currencyFormat.format(totalDeposits));
        
        // Active loans
        long activeLoans = loans.stream()
            .filter(loan -> "Active".equals(loan.getStatus()))
            .count();
        System.out.println("Active Loans: " + activeLoans);
        
        // Total loan amount
        double totalLoanAmount = loans.stream()
            .mapToDouble(Loan::getLoanAmount)
            .sum();
        System.out.println("Total Loan Amount: ₹" + currencyFormat.format(totalLoanAmount));
        
        // Accounts by type
        System.out.println("\nAccounts by Type:");
        Map<String, Long> accountsByType = accounts.stream()
            .collect(Collectors.groupingBy(Account::getAccountType, Collectors.counting()));
        accountsByType.forEach((type, count) -> 
            System.out.println("  " + type + ": " + count));
        
        // Customers by type
        System.out.println("\nCustomers by Type:");
        Map<String, Long> customersByType = customers.stream()
            .collect(Collectors.groupingBy(Customer::getCustomerType, Collectors.counting()));
        customersByType.forEach((type, count) -> 
            System.out.println("  " + type + ": " + count));
        
        // Recent transactions count
        Date oneWeekAgo = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        long recentTransactions = transactions.stream()
            .filter(t -> t.getTransactionDate().after(oneWeekAgo))
            .count();
        System.out.println("Transactions (last 7 days): " + recentTransactions);
    }

    // Function 17: Search Functionality
    public static void searchFunctionality() {
        System.out.println("\n--- SEARCH ---");
        System.out.println("1. Search Customers by Name");
        System.out.println("2. Search Accounts by Customer");
        System.out.println("3. Search Transactions by Account");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                searchCustomersByName();
                break;
            case 2:
                searchAccountsByCustomer();
                break;
            case 3:
                searchTransactionsByAccount();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Function 18: Generate Reports
    public static void generateReports() {
        System.out.println("\n--- REPORTS ---");
        System.out.println("1. Customer Report");
        System.out.println("2. Account Summary Report");
        System.out.println("3. Transaction Report");
        System.out.println("4. Loan Portfolio Report");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                generateCustomerReport();
                break;
            case 2:
                generateAccountSummaryReport();
                break;
            case 3:
                generateTransactionReport();
                break;
            case 4:
                generateLoanPortfolioReport();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Function 19: Interest Calculation
    public static void interestCalculation() {
        System.out.println("\n--- INTEREST CALCULATION ---");
        System.out.println("1. Calculate Account Interest");
        System.out.println("2. Calculate Loan Interest");
        System.out.println("3. Apply Monthly Interest");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                calculateAccountInterest();
                break;
            case 2:
                calculateLoanInterest();
                break;
            case 3:
                applyMonthlyInterest();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Helper methods
    public static Optional<Account> findAccountByNumber(String accountNumber) {
        return accounts.stream()
            .filter(acc -> acc.getAccountNumber().equals(accountNumber))
            .findFirst();
    }

    public static int getIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return input;
            } catch (Exception e) {
                System.out.print("Invalid input! Please enter a number: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    public static double getDoubleInput() {
        while (true) {
            try {
                double input = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                return input;
            } catch (Exception e) {
                System.out.print("Invalid input! Please enter a number: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Initialize with sample data (Indian context)
    public static void initializeSampleData() {
        try {
            // Sample Indian customers
            customers.add(new Customer(customerIdCounter++, "Raj", "Sharma", 
                "raj.sharma@email.com", "9876543210", "123 MG Road, Mumbai", "AADHAAR1001", 
                dateFormat.parse("1985-03-15"), "Premium"));
            customers.add(new Customer(customerIdCounter++, "Priya", "Patel", 
                "priya.patel@email.com", "9876543211", "456 Connaught Place, Delhi", "AADHAAR1002", 
                dateFormat.parse("1990-07-22"), "Regular"));
            customers.add(new Customer(customerIdCounter++, "Amit", "Kumar", 
                "amit.kumar@email.com", "9876543212", "789 Brigade Road, Bangalore", "AADHAAR1003", 
                dateFormat.parse("1978-11-30"), "VIP"));

            // Sample accounts with Indian interest rates
            accounts.add(new Account("ACC1000000001", 1001, "Savings", 50000.00, 3.5));   // Savings account interest
            accounts.add(new Account("ACC1000000002", 1001, "Current", 25000.00, 0.0));   // Current account no interest
            accounts.add(new Account("ACC1000000003", 1002, "Savings", 30000.00, 3.5));
            accounts.add(new Account("ACC1000000004", 1003, "Fixed Deposit", 100000.00, 6.5)); // FD interest rate

            // Sample transactions
            transactions.add(new Transaction(transactionIdCounter++, "ACC1000000001", 
                "Deposit", 10000.00, "Initial deposit", 10000.00));
            transactions.add(new Transaction(transactionIdCounter++, "ACC1000000001", 
                "Deposit", 40000.00, "Salary credit", 50000.00));

            // Sample loans with Indian interest rates
            Loan loan1 = new Loan(loanIdCounter++, 1001, "Personal", 50000.00, 10.5, 24);
            loan1.setStatus("Active");
            loans.add(loan1);

            System.out.println("Sample banking data initialized successfully!");
            System.out.println("All amounts are in Indian Rupees (₹)");
        } catch (Exception e) {
            System.out.println("Error initializing sample data: " + e.getMessage());
        }
    }

    // Implemented methods
    public static void findCustomerById() {
        System.out.print("Enter Customer ID: ");
        int id = getIntInput();
        customers.stream()
                .filter(c -> c.getCustomerId() == id)
                .findFirst()
                .ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("Customer not found!")
                );
    }

    public static void viewAllTransactions() {
        System.out.println("\n--- ALL TRANSACTIONS ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        transactions.forEach(System.out::println);
    }

    public static void viewAllLoans() {
        System.out.println("\n--- ALL LOANS ---");
        if (loans.isEmpty()) {
            System.out.println("No loans found.");
            return;
        }
        loans.forEach(System.out::println);
    }

    public static void searchCustomersByName() {
        System.out.print("Enter customer name to search: ");
        String name = scanner.nextLine().toLowerCase();
        List<Customer> results = customers.stream()
            .filter(c -> c.getFullName().toLowerCase().contains(name))
            .collect(Collectors.toList());
        System.out.println("Found " + results.size() + " customers:");
        results.forEach(System.out::println);
    }

    // Other implemented methods (same as before but with ₹ symbol)
    public static void updateCustomerInfo() {
        System.out.print("Enter Customer ID to update: ");
        int customerId = getIntInput();
        
        Optional<Customer> customer = customers.stream()
            .filter(c -> c.getCustomerId() == customerId)
            .findFirst();
            
        if (customer.isPresent()) {
            System.out.println("Current Information:");
            System.out.println("1. First Name: " + customer.get().getFirstName());
            System.out.println("2. Last Name: " + customer.get().getLastName());
            System.out.println("3. Phone: " + customer.get().getPhone());
            System.out.println("4. Email: " + customer.get().getEmail());
            System.out.println("5. Address: " + customer.get().getAddress());
            System.out.println("6. Customer Type: " + customer.get().getCustomerType());
            
            System.out.print("Enter field number to update (1-6): ");
            int field = getIntInput();
            
            switch (field) {
                case 1:
                    System.out.print("Enter new First Name: ");
                    customer.get().setFirstName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new Last Name: ");
                    customer.get().setLastName(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new Phone: ");
                    customer.get().setPhone(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Enter new Email: ");
                    customer.get().setEmail(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Enter new Address: ");
                    customer.get().setAddress(scanner.nextLine());
                    break;
                case 6:
                    System.out.print("Enter new Customer Type: ");
                    customer.get().setCustomerType(scanner.nextLine());
                    break;
                default:
                    System.out.println("Invalid field number!");
                    return;
            }
            System.out.println("Customer information updated successfully!");
        } else {
            System.out.println("Customer not found!");
        }
    }

    public static void viewCustomerAccounts() {
        System.out.print("Enter Customer ID: ");
        int customerId = getIntInput();
        
        List<Account> customerAccounts = accounts.stream()
            .filter(acc -> acc.getCustomerId() == customerId)
            .collect(Collectors.toList());
            
        if (customerAccounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
        } else {
            System.out.println("Accounts for Customer ID " + customerId + ":");
            customerAccounts.forEach(System.out::println);
        }
    }

    public static void openNewAccount() {
        System.out.print("Enter Customer ID: ");
        int customerId = getIntInput();
        
        Optional<Customer> customer = customers.stream()
            .filter(c -> c.getCustomerId() == customerId)
            .findFirst();
            
        if (customer.isEmpty()) {
            System.out.println("Customer not found!");
            return;
        }
        
        System.out.print("Account Type (Savings/Current/Fixed Deposit/Recurring Deposit): ");
        String accountType = scanner.nextLine();
        
        System.out.print("Initial Deposit: ₹");
        double initialDeposit = getDoubleInput();
        
        double interestRate = 0.0;
        switch (accountType.toLowerCase()) {
            case "savings": interestRate = 3.5; break;
            case "current": interestRate = 0.0; break;
            case "fixed deposit": interestRate = 6.5; break;
            case "recurring deposit": interestRate = 5.5; break;
            default: interestRate = 3.0;
        }
        
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, customerId, accountType, initialDeposit, interestRate);
        accounts.add(account);
        
        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Initial Balance: ₹" + currencyFormat.format(initialDeposit));
    }

    public static void closeAccount() {
        System.out.print("Enter Account Number to close: ");
        String accountNumber = scanner.nextLine();
        
        Optional<Account> account = findAccountByNumber(accountNumber);
        if (account.isPresent()) {
            if (account.get().getBalance() == 0) {
                account.get().setStatus("Closed");
                System.out.println("Account closed successfully!");
            } else {
                System.out.println("Cannot close account! Balance must be zero.");
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    public static void viewAccountDetails() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Optional<Account> account = findAccountByNumber(accountNumber);
        if (account.isPresent()) {
            Optional<Customer> customer = customers.stream()
                .filter(c -> c.getCustomerId() == account.get().getCustomerId())
                .findFirst();
                
            System.out.println("\n--- ACCOUNT DETAILS ---");
            System.out.println("Account Number: " + account.get().getAccountNumber());
            System.out.println("Account Type: " + account.get().getAccountType());
            System.out.println("Balance: ₹" + currencyFormat.format(account.get().getBalance()));
            System.out.println("Interest Rate: " + account.get().getInterestRate() + "%");
            System.out.println("Status: " + account.get().getStatus());
            customer.ifPresent(c -> System.out.println("Customer: " + c.getFullName()));
        } else {
            System.out.println("Account not found!");
        }
    }

    public static void viewAccountStatement() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        List<Transaction> accountTransactions = transactions.stream()
            .filter(t -> t.getAccountNumber().equals(accountNumber))
            .collect(Collectors.toList());
            
        if (accountTransactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.println("Transaction History for Account: " + accountNumber);
            accountTransactions.forEach(System.out::println);
        }
    }

    public static void viewTransactionHistory() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        viewAccountStatement(); // Reuse the same functionality
    }

    public static void approveLoan() {
        System.out.print("Enter Loan ID to approve: ");
        int loanId = getIntInput();
        
        Optional<Loan> loan = loans.stream()
            .filter(l -> l.getLoanId() == loanId)
            .findFirst();
            
        if (loan.isPresent()) {
            if ("Pending".equals(loan.get().getStatus())) {
                loan.get().setStatus("Approved");
                System.out.println("Loan approved successfully!");
            } else {
                System.out.println("Loan is not in pending status.");
            }
        } else {
            System.out.println("Loan not found!");
        }
    }

    public static void makeLoanPayment() {
        System.out.print("Enter Loan ID: ");
        int loanId = getIntInput();
        
        Optional<Loan> loan = loans.stream()
            .filter(l -> l.getLoanId() == loanId)
            .findFirst();
            
        if (loan.isPresent() && "Active".equals(loan.get().getStatus())) {
            double monthlyPayment = loan.get().calculateMonthlyPayment();
            System.out.println("Monthly Payment: ₹" + currencyFormat.format(monthlyPayment));
            System.out.print("Enter payment amount: ₹");
            double payment = getDoubleInput();
            
            if (payment > 0 && payment <= loan.get().getRemainingBalance()) {
                loan.get().setRemainingBalance(loan.get().getRemainingBalance() - payment);
                System.out.println("Payment successful!");
                System.out.println("Remaining Balance: ₹" + currencyFormat.format(loan.get().getRemainingBalance()));
                
                if (loan.get().getRemainingBalance() == 0) {
                    loan.get().setStatus("Paid");
                    System.out.println("Loan fully paid!");
                }
            } else {
                System.out.println("Invalid payment amount!");
            }
        } else {
            System.out.println("Loan not found or not active!");
        }
    }

    public static void viewLoanDetails() {
        System.out.print("Enter Loan ID: ");
        int loanId = getIntInput();
        
        Optional<Loan> loan = loans.stream()
            .filter(l -> l.getLoanId() == loanId)
            .findFirst();
            
        if (loan.isPresent()) {
            Optional<Customer> customer = customers.stream()
                .filter(c -> c.getCustomerId() == loan.get().getCustomerId())
                .findFirst();
                
            System.out.println("\n--- LOAN DETAILS ---");
            System.out.println("Loan ID: " + loan.get().getLoanId());
            System.out.println("Loan Type: " + loan.get().getLoanType());
            System.out.println("Loan Amount: ₹" + currencyFormat.format(loan.get().getLoanAmount()));
            System.out.println("Remaining Balance: ₹" + currencyFormat.format(loan.get().getRemainingBalance()));
            System.out.println("Interest Rate: " + loan.get().getInterestRate() + "%");
            System.out.println("Term: " + loan.get().getTermMonths() + " months");
            System.out.println("Monthly Payment: ₹" + currencyFormat.format(loan.get().calculateMonthlyPayment()));
            System.out.println("Status: " + loan.get().getStatus());
            customer.ifPresent(c -> System.out.println("Customer: " + c.getFullName()));
        } else {
            System.out.println("Loan not found!");
        }
    }

    public static void searchAccountsByCustomer() {
        System.out.print("Enter Customer ID: ");
        int customerId = getIntInput();
        viewCustomerAccounts(); // Reuse existing functionality
    }

    public static void searchTransactionsByAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        viewAccountStatement(); // Reuse existing functionality
    }

    public static void generateCustomerReport() {
        System.out.println("\n--- CUSTOMER REPORT ---");
        System.out.printf("%-6s %-15s %-15s %-20s %-15s %-12s%n", 
            "ID", "First Name", "Last Name", "Email", "Phone", "Type");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Customer customer : customers) {
            long accountCount = accounts.stream()
                .filter(acc -> acc.getCustomerId() == customer.getCustomerId())
                .count();
                
            System.out.printf("%-6d %-15s %-15s %-20s %-15s %-12s (Accounts: %d)%n", 
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerType(),
                accountCount
            );
        }
    }

    public static void generateAccountSummaryReport() {
        System.out.println("\n--- ACCOUNT SUMMARY REPORT ---");
        viewAllAccounts(); // Reuse existing functionality
    }

    public static void generateTransactionReport() {
        System.out.println("\n--- TRANSACTION REPORT ---");
        viewAllTransactions(); // Reuse existing functionality
    }

    public static void generateLoanPortfolioReport() {
        System.out.println("\n--- LOAN PORTFOLIO REPORT ---");
        double totalLoanAmount = loans.stream()
            .mapToDouble(Loan::getLoanAmount)
            .sum();
        double totalRemaining = loans.stream()
            .mapToDouble(Loan::getRemainingBalance)
            .sum();
            
        System.out.println("Total Loans: " + loans.size());
        System.out.println("Total Loan Amount: ₹" + currencyFormat.format(totalLoanAmount));
        System.out.println("Total Remaining Balance: ₹" + currencyFormat.format(totalRemaining));
        viewAllLoans(); // Reuse existing functionality
    }

    public static void calculateAccountInterest() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Optional<Account> account = findAccountByNumber(accountNumber);
        if (account.isPresent()) {
            double annualInterest = account.get().getBalance() * account.get().getInterestRate() / 100;
            double monthlyInterest = annualInterest / 12;
            System.out.println("Annual Interest: ₹" + currencyFormat.format(annualInterest));
            System.out.println("Monthly Interest: ₹" + currencyFormat.format(monthlyInterest));
        } else {
            System.out.println("Account not found!");
        }
    }

    public static void calculateLoanInterest() {
        System.out.print("Enter Loan ID: ");
        int loanId = getIntInput();
        
        Optional<Loan> loan = loans.stream()
            .filter(l -> l.getLoanId() == loanId)
            .findFirst();
            
        if (loan.isPresent()) {
            double monthlyInterest = loan.get().getRemainingBalance() * loan.get().getInterestRate() / 12 / 100;
            double annualInterest = monthlyInterest * 12;
            System.out.println("Monthly Interest: ₹" + currencyFormat.format(monthlyInterest));
            System.out.println("Annual Interest: ₹" + currencyFormat.format(annualInterest));
        } else {
            System.out.println("Loan not found!");
        }
    }

    public static void applyMonthlyInterest() {
        System.out.println("Applying monthly interest to all savings accounts...");
        int count = 0;
        
        for (Account account : accounts) {
            if ("Savings".equals(account.getAccountType()) && "Active".equals(account.getStatus())) {
                double interest = account.getBalance() * account.getInterestRate() / 12 / 100;
                if (interest > 0) {
                    account.deposit(interest);
                    Transaction transaction = new Transaction(transactionIdCounter++, 
                        account.getAccountNumber(), "Interest", interest, 
                        "Monthly interest", account.getBalance());
                    transactions.add(transaction);
                    count++;
                }
            }
        }
        System.out.println("Monthly interest applied to " + count + " accounts.");
    }
}