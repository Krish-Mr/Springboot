
# 📌 Spring AOP (Aspect-Oriented Programming)

Aspect-Oriented Programming (AOP) is used to encapsulate cross-cutting concerns—behavior that affects multiple modules—such as logging, security, transactions, etc.

---

## 🔧 Key Components of AOP

| **Component**                     | **Meaning**                                                                |
|----------------------------------|-----------------------------------------------------------------------------|
| `execution(...)`                 | Specifies that this is a method execution pointcut                          |
| `*`                              | Matches any return type                                                     |
| `com.bookmaster.aop.run.*`       | Matches any class in the `com.bookmaster.aop.run` package                   |
| `.*`                             | Matches any method name                                                     |
| `(..)`                           | Matches any number and type of method arguments                             |

---

## 🧩 AOP Annotations

### `@Aspect`
- Declares a class as an aspect.
- Should be used at the class level.

---

### `@Before("execution(* com.example.service.*.*(..))")`
- Executes **before** the matched method.
- Pointcut: All methods in classes under `com.example.service`.

```java
public void methodA(JoinPoint p) {
    System.out.println(p.getSignature());
}
```

---

### `@After("pointcut expression")`
- Executes **after** the method finishes (either normally or by throwing an exception).

---

### `@Around("pointcut expression")`
- Wraps the method execution.
- Can control whether the method executes or not.

```java
public Object m1(ProceedingJoinPoint pjp) throws Throwable {
    return pjp.proceed();
}
```

---

### `@AfterThrowing(pointcut = "...", throwing = "ex")`
- Executes **only if the method throws an exception**.

```java
public void m2(Exception ex) {
    // Handle exception
}
```

---

### `@AfterReturning(pointcut = "...", returning = "res")`
- Executes **after the method returns successfully**.

```java
public void m3(Object res) {
    // Handle return value
}
```
