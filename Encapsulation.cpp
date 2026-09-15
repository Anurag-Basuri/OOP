#include<bits/stdc++.h>
using namespace std;

class BankAccount {
private:
    string accountNumber;
    double balance;
    bool  isActive;

    void checkAccountStatus() const {
        if (!isActive) {
            throw runtime_error("Account is inactive.");
        }
    }

public:
    BankAccount(const string& accNum, double initialBalance)
        : accountNumber(accNum), balance(initialBalance), isActive(true) {
            if (initialBalance < 0) {
                throw invalid_argument("Initial balance cannot be negative.");
            }

            if (accNum.empty()) {
                throw invalid_argument("Account number cannot be empty.");
            }
        }

    void deposit(double amount) {
        checkAccountStatus();
        if (amount <= 0) {
            throw invalid_argument("Deposit amount must be positive.");
        }
        balance += amount;
    }

    void withdraw(double amount) {
        checkAccountStatus();
        if (amount <= 0) {
            throw invalid_argument("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw runtime_error("Insufficient funds.");
        }
        balance -= amount;
    }

    double getBalance() const {
        checkAccountStatus();
        return balance;
    }

    bool getAccountStatus() const{
        return isActive;
    }
};

int main() {
    try {
        BankAccount account("123456789", 1000.0);
        cout << "Initial Balance: $" << account.getBalance() << endl;

        account.deposit(500.0);
        cout << "Balance after deposit: $" << account.getBalance() << endl;

        account.withdraw(200.0);
        cout << "Balance after withdrawal: $" << account.getBalance() << endl;

        // Uncommenting the following line will throw an exception
        // account.withdraw(2000.0);

    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
    }

    return 0;
}