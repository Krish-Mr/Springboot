## @Transactional
### 🔁 What is `@Transactional`?
`@Transactional` is a declarative annotation in Spring Boot that wraps a method in a transaction boundary. It ensures that the method either fully completes or rolls back in case of failure.

Types:
Declarative transaction management - @Transactional
Programmatic transaction control -  TransactionTemplate

---

### 🔐 How `@Transactional` Ensures ACID Properties

| **ACID Property** | **Explanation in Spring Context** |
|------------------|------------------------------------|
| **Atomicity**    | All operations inside the transaction either **commit** or **rollback**. No partial updates. |
| **Consistency**  | Ensures that the system moves from one **valid state to another**. Spring integrates with JPA/Hibernate to enforce constraints. |
| **Isolation**    | Transactions are **isolated** from each other based on the configured isolation level. |
| **Durability**   | Once committed, data is **persisted** even if the system crashes. This is handled by the underlying database. |

---

### 🏗️ TransactionManager Hierarchy

Spring uses the `PlatformTransactionManager` interface to abstract transaction management. Here's the hierarchy:

```
TransactionManager
    └── PlatformTransactionManager
          ├── AbstractPlatformTransactionManager
          │     ├── DataSourceTransactionManager (JDBC)
          │     ├── JpaTransactionManager (JPA/Hibernate)
          │     ├── HibernateTransactionManager (Hibernate native)
          │     └── others...
```

Spring Boot auto-configures the appropriate transaction manager based on your dependencies.

---

### 🧰 What is `TransactionTemplate`?

`TransactionTemplate` is a programmatic way to manage transactions in Spring.

#### ✅ Use Cases:
- Fine-grained control over transaction boundaries
- Avoid annotations and manage rollback manually

#### 🔍 Example:
```java
@Autowired
private TransactionTemplate transactionTemplate;

public void executeTransaction() {
    transactionTemplate.execute(status -> {
        // business logic
        if (someConditionFails) {
            status.setRollbackOnly();
        }
        return null;
    });
}
```

---

### 🧪 Rollback Behavior

- **Runtime exceptions** trigger rollback by default.
- **Checked exceptions** do not trigger rollback unless specified:
```java
@Transactional(rollbackFor = Exception.class)
```
- You can also use:
```java
@Transactional(noRollbackFor = CustomException.class)
```

---


# @Transactional Propagation Types: Behavior, Analogy & Real-Time Scenarios

| **Propagation Type** | **Behavior**                                               | **Real-World Analogy**                                               | **Real-Time Scenario**                                                                 |
|----------------------|------------------------------------------------------------|----------------------------------------------------------------------|----------------------------------------------------------------------------------------|
| `REQUIRED` (default) | Join existing transaction or create a new one              | "If a meeting is already happening, join it; otherwise, start a new one." | Placing an order: `OrderService.placeOrder()` starts a transaction and calls other services. |
| `REQUIRES_NEW`       | Always suspend current and start a new transaction         | "Step out of the current meeting to take a private call."           | Logging payment: `PaymentService.logPayment()` should commit even if order fails.     |
| `NESTED`             | Create a nested transaction within the current one         | "Start a sub-meeting within the main meeting. If sub-meeting fails, main can still continue." | Updating inventory: `InventoryService.updateStock()` can rollback independently if stock update fails. |
| `SUPPORTS`           | Join if a transaction exists, else run non-transactionally | "If a meeting is happening, join; else just chat casually."         | Audit logging: `AuditService.logAction()` joins transaction if present, else logs without one. |
| `NOT_SUPPORTED`      | Suspend current transaction and run non-transactionally    | "Pause the meeting to have an informal discussion."                 | Sending email: `NotificationService.sendEmail()` should not be part of transaction to avoid rollback. |
| `NEVER`              | Throw exception if a transaction exists                    | "Refuse to talk if a meeting is already happening."                 | External API call: `ExternalService.call()` must not run inside a transaction (e.g., legacy system restriction). |
| `MANDATORY`          | Must run within an existing transaction                    | "Only speak if you're already in a meeting; otherwise, throw an error." | Validating order: `ValidationService.validate()` must be called within a transaction or it fails. |



## Transaction Propagation:

- Transaction propagation defines how transactions behave when a method is called within the context of an existing transaction. It determines whether the method should run in the current transaction, start a new one, or suspend the existing one.

### OrderServiceApp - Transaction Propagation Demo
#### 📁 Folder Structure
```
order-service-app/
├── controller/
│   └── OrderController.java
├── service/
│   ├── OrderService.java          // @Transactional(REQUIRED)
│   ├── PaymentService.java        // @Transactional(REQUIRES_NEW)
│   ├── InventoryService.java      // @Transactional(NESTED)
│   ├── NotificationService.java   // @Transactional(NOT_SUPPORTED)
│   └── AuditService.java          // @Transactional(SUPPORTS)
├── entity/
│   ├── Order.java
│   ├── Payment.java
│   ├── Inventory.java
│   └── AuditLog.java
├── repository/
│   ├── OrderRepository.java
│   ├── PaymentRepository.java
│   ├── InventoryRepository.java
│   └── AuditRepository.java
└── OrderServiceApp.java
```

### 🧩 Service Classes and Propagation Types

#### 1. `OrderService.java` – `REQUIRED`
- Default behavior.
- Joins the current transaction if one exists; otherwise, creates a new one.
- Coordinates the entire transaction. Rolls back if any part fails.

```java
@Service
public class OrderService {
    @Autowired private PaymentService paymentService;
    @Autowired private InventoryService inventoryService;
    @Autowired private NotificationService notificationService;
    @Autowired private AuditService auditService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void placeOrder(Order order) {
        orderRepository.save(order);
        paymentService.processPayment(order);
        inventoryService.updateInventory(order);
        notificationService.sendConfirmation(order);
        auditService.logOrder(order);
    }
}
```


#### 2. `PaymentService.java` – `REQUIRES_NEW`
- Runs in a separate transaction. Rolls back independently.
- Suspends any existing transaction.
- After this operation it will continue the existing transaction.

```java
@Service
public class PaymentService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processPayment(Order order) {
        // Save payment info
        // Simulate failure to test rollback isolation
    }
}
```


#### 3. `InventoryService.java` – `NESTED`
- Creates a savepoint. Rolls back partially without affecting the main transaction.
- Executes within a nested transaction if a current transaction exists.

```java
@Service
public class InventoryService {
    @Transactional(propagation = Propagation.NESTED)
    public void updateInventory(Order order) {
        // Update stock
        // Simulate failure to test savepoint rollback
    }
}
```

#### 4. `AuditService.java` – `SUPPORTS`
- Joins the current transaction if one exists.
- Otherwise, runs non-transactionally.

```java
@Service
public class AuditService {
    @Transactional(propagation = Propagation.SUPPORTS)
    public void logOrder(Order order) {
        // Save audit log
    }
}
```

#### 5. `NotificationService.java` – `NOT_SUPPORTED`
 - Executes non-transactionally and  Suspends any existing transaction.
 - Failure does not affect the main transaction.

```java
@Service
public class NotificationService {
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendConfirmation(Order order) {
        // Send email/SMS
    }
}
```


#### 6. NEVER
 - Throws an exception if a transaction exists.
 - Ensures the method is never run within a transaction.

```java
@Transactional(propagation = Propagation.NEVER)
public void doNever() {
    // business logic
}

```


#### 7. MANDATORY
 - Must be called within an existing transaction.
 - Throws an exception if no transaction exists.

```java
@Transactional(propagation = Propagation.MANDATORY)
public void doMandatory() {
    // business logic
}
```

 >It will throw ```IllegalTransactionPropagationException.```


### =======================================================================================


## Transaction Isolation
|Scenario               |Isolation Level   | Description | 
|-----------------------|------------------|-------------|
|Logging or analytics	| READ_UNCOMMITTED |Allows reading uncommitted changes from other transactions. Example: User A can see the updated value from User B even before User B commits. Risk of dirty reads.|
|General business apps	|READ_COMMITTED (default)   |Only committed data is visible. Example: User A reads a value, User B updates and commits, then User A sees the new value. Prevents dirty reads, but non-repeatable reads are possible.|
|Financial transactions	|REPEATABLE_READ   |Ensures repeatable reads within the same transaction. Example: User A reads value ₹100, User B updates to ₹200 and commits, but User A still sees ₹100 until the transaction ends. Prevents dirty and non-repeatable reads, but phantom reads may occur.|
|Regulatory or audit systems| SERIALIZABLE | Provides full isolation by serializing access. Example: If User A starts a transaction, User B is blocked until A completes. Prevents dirty, non-repeatable, and phantom reads. Highest consistency, lowest performance.|


## Transaction readOnly

 - No write operations (insert/update/delete) are expected.
 - The transaction can be optimized for performance.
>Note:  If you try to modify data in a readOnly = true transaction, Spring won't block it, but Hibernate may skip flushing changes.


## Timeout
 - maximum timout to execute the transaction, default = -1 (no timeout)

## rollbackFor	
 - Class<?>[]	Specifies which checked exceptions should trigger rollback.
## rollbackForClassName	
 - String[]	Same as rollbackFor, but uses class names as strings.
## noRollbackFor	
 - Class<?>[]	Exceptions that should not trigger rollback.
## noRollbackForClassName	
 - String[]	Same as noRollbackFor, but uses class names as strings.
## value / transactionManager	
 - String	Specifies the name of the transaction manager bean to use (useful when multiple managers exist).
