## Spring Event Listeners: `@EventListener` vs `@TransactionalEventListener`

Spring provides powerful event-driven programming capabilities through annotations like `@EventListener` and `@TransactionalEventListener`. 

---

### `@EventListener`
- Used to **separate logic** triggered by application events.
- Executes **immediately** when an event is published.
- **Not transaction-aware** — runs even if the transaction fails.

#### ✅ Use Case:
- Send notifications or log events **regardless of transaction success**.

#### 📦 Example:
```java
@EventListener
public void handleOrderCreated(OrderCreatedEvent event) {
    emailService.sendOrderConfirmation(event.getOrder());
    hotelService.notifyKitchen(event.getOrder());
    deliveryService.assignDelivery(event.getOrder());
}
```

---

### `@TransactionalEventListener`
- Listens to events **within a transactional context**.
- Executes **only if the transaction reaches a specific phase**.
- Default phase is `AFTER_COMMIT`.

#### ✅ Use Case:
- Send notifications **only after successful payment or order creation**.

#### 📦 Example:
```java
@Service
public class OrderService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    public void placeOrder(Order order) {
        orderRepository.save(order);
        paymentService.process(order.getPaymentDetails());
        publisher.publishEvent(new OrderPlacedEvent(order));
    }
}

@Component
public class OrderEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderPlaced(OrderPlacedEvent event) {
        emailService.sendConfirmation(event.getOrder());
        hotelService.notifyKitchen(event.getOrder());
        deliveryService.assignDelivery(event.getOrder());
    }
}
```

---

### 🔄 Transaction Phases

| TransactionPhase | Description |
|------------------|-------------|
| `BEFORE_COMMIT` | Fires **just before** the transaction is committed |
| `AFTER_COMMIT` *(default)* | Fires **after successful commit** |
| `AFTER_ROLLBACK` | Fires **if the transaction rolls back** |
| `AFTER_COMPLETION` | Fires **after transaction ends**, whether commit or rollback |

---
## Conditional Event Listener
```java
@EventListener(condition = "#event.status == 'FAILED' and #event.retryCount < 3")
public void retryFailedEvent(Event event) {
    System.out.println("Retrying event: " + event.getId());
}

```
>🧠 Notes:   The condition uses SpEL, so you can access event fields using #event.
You can also use beans in the condition: @EventListener(condition = "@securityService.isAllowed(#event.user)")
Conditions are evaluated before the method is invoked — if false, the method is skipped.

---
