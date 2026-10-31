
# Assignment 02 — Refactoring Legacy Code

**Course:** Software Construction & Development  
**Assignment:** 02 — Refactoring Legacy Code  
**Language:** Java 21  
**Build Tool:** Apache Maven  
**Testing Framework:** JUnit 5  
**Package:** `com.uet.legacy`

## 1. Project Overview

This project focuses on refactoring a legacy order billing system to improve code readability, maintainability, modularity, and testability while preserving its original business behavior.

The original `OrderBillingEngine` contained multiple responsibilities, including order validation, discount calculation, tax calculation, payment processing, file logging, and customer notifications.

The refactoring process separates these responsibilities into dedicated classes and introduces design patterns to reduce coupling.

## 2. Project Objectives

- Identify code smells in legacy code.
- Apply SOLID design principles.
- Extract responsibilities into separate classes.
- Implement the Strategy Pattern for payment processing.
- Decouple notification processing using an interface.
- Implement constructor dependency injection.
- Preserve existing business logic.
- Verify functionality through automated unit tests.

## 3. Refactored Architecture

| Class | Responsibility |
|---|---|
| `OrderBillingEngine` | Coordinates the order processing workflow |
| `OrderValidator` | Validates order information |
| `OrderCalculator` | Calculates raw order totals |
| `DiscountCalculator` | Calculates customer discounts |
| `TaxCalculator` | Determines tax rates and calculates tax |
| `PaymentStrategy` | Interface for payment strategies |
| `CreditCardPayment` | Processes credit card payments |
| `EasyPaisaPayment` | Processes EasyPaisa payments |
| `CashOnDeliveryPayment` | Processes cash-on-delivery payments |
| `PaymentProcessor` | Selects and executes a payment strategy |
| `NotificationService` | Interface for notification services |
| `EmailNotification` | Handles email notification output |
| `SmsNotification` | Handles SMS notification output |
| `NotificationProcessor` | Selects and processes notification channels |
| `OrderRepository` | Handles order audit logging |

## 4. Design Patterns and Principles

### Strategy Pattern

The Strategy Pattern is implemented for payment processing. Each payment method has its own implementation of `PaymentStrategy`.

Supported strategies:
- Credit Card
- EasyPaisa
- Cash on Delivery

### Dependency Inversion

The notification processor depends on the `NotificationService` interface rather than directly depending on concrete notification implementations.

### Constructor Dependency Injection

Constructor injection is used in `OrderBillingEngine` and `NotificationProcessor` to provide their dependencies.

### Single Responsibility Principle

Responsibilities such as validation, discount calculation, tax calculation, payment processing, notification handling, and logging are separated into dedicated classes.

### Open/Closed Principle

New payment strategies can be introduced through the `PaymentStrategy` abstraction without placing all payment validation logic inside the original billing engine.

## 5. Unit Testing

JUnit 5 is used to verify the behavior of the refactored components.

| Test Class | Test Cases |
|---|---:|
| `OrderBillingEngineTest` | 21 |
| `DiscountCalculatorTest` | 7 |
| `TaxCalculatorTest` | 8 |
| `PaymentProcessorTest` | 8 |
| `NotificationProcessorTest` | 5 |
| **Total** | **49** |

### Test Execution

Run all tests using Maven:

```bash
mvn clean test
```

### Latest Test Result

**49 tests passed successfully.**

The original 21 order billing tests remain unchanged. Additional tests independently verify discount calculation, tax calculation, payment strategies, and notification processing.

## 6. How to Run the Project

1. Open the project in NetBeans or another Java IDE.
2. Ensure JDK 21 and Maven are configured.
3. Build the Maven project.
4. Run the automated tests:

```bash
mvn clean test
```

## 7. Git Version Control

The project was developed through incremental Git commits documenting the refactoring and testing process.

View the commit history:

```bash
git log --oneline
```

## 8. Conclusion

The legacy order billing system was refactored into smaller, focused components. The Strategy Pattern was introduced for payment processing, and notification handling was decoupled through an interface.

Constructor dependency injection improved testability, while automated testing verified that the existing order processing behavior was preserved.

The final test suite contains 49 passing tests, including the original 21 tests and additional component-level tests.

## 9. Repository

**GitHub Repository:** Add your Assignment 02 repository URL here.