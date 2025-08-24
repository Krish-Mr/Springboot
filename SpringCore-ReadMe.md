 # Spring Container (IoC Container) Overview

The **Spring Container** is the core of the Spring Framework and an implementation of the **Inversion of Control (IoC)** principle.

---

## 🌱 What is IoC (Inversion of Control)?

In traditional programming, your application code is responsible for creating and managing dependencies (objects). 

With **Inversion of Control**, this responsibility is shifted to the **Spring Container**. The container manages the complete lifecycle and configuration of application objects.
It can have sub frameworks such as AOP, ORM, Event Handling, Listerners, Starter Web,...

---

## 📦 What Does the Spring Container Do?

The container performs several key tasks:

1. **Reads Configuration**
   - Sources: `@Configuration`, `@Component`, `applicationContext.xml`, etc.

2. **Scans for Components**
   - Scans annotated classes and registers them as **Bean Definitions**.

3. **Instantiates Beans**
   - Creates instances of beans as defined by the configuration and annotations.

4. **Injects Dependencies**
   - Supports multiple injection types:
     - Constructor Injection
     - Setter Injection
     - Field Injection

5. **Manages Bean Lifecycle**
   - Handles:
     - Initialization (`@PostConstruct`, `InitializingBean`)
     - Destruction (`@PreDestroy`, `DisposableBean`)
     - Bean post-processing (`BeanPostProcessor`, etc.)

6. **Provides Beans on Request**
   - Through methods like:
     - `ApplicationContext.getBean(Class/Name)`
     - Autowiring (`@Autowired`, `@Inject`)

---

## 💡 Summary

The Spring Container abstracts and automates the complexities of object creation, wiring, and lifecycle management, letting you focus on business logic while adhering to the **IoC** design pattern.

---

✅ Use the container to build scalable, maintainable, and loosely-coupled applications in Spring Boot!









# 🌱 Spring Bean Scopes & Lifecycle Cheatsheet

This document provides a quick reference to how Spring manages different bean scopes and their lifecycle callbacks.

---

## 🧬 Bean Scopes Overview

| Scope         | Description                                      | Lifecycle Managed by Spring | `@PostConstruct` | `@PreDestroy` |
|---------------|--------------------------------------------------|------------------------------|------------------|---------------|
| `singleton`   | One instance per Spring container                | ✅ Yes                       | ✅ Yes           | ✅ Yes        |
| `prototype`   | New instance every time it's requested           | ❌ No                        | ✅ Yes           | ❌ No         |
| `request`     | One instance per HTTP request (Web only)         | ✅ Yes                       | ✅ Yes           | ✅ Yes        |
| `session`     | One instance per HTTP session (Web only)         | ✅ Yes                       | ✅ Yes           | ✅ Yes        |
| `application` | One instance per ServletContext (Web only)       | ✅ Yes                       | ✅ Yes           | ✅ Yes        |

---

## 🔁 Prototype Scope Details

- Spring creates a **new bean instance** every time it's requested.
- Only `@PostConstruct` is called.
- **No `@PreDestroy`** — you must handle cleanup manually.
- Beans are **not stored** in the Spring container after creation.

---

## 🧠 Managed Scopes (Singleton, Request, Session, Application)

- Spring **fully manages** the lifecycle.
- Both `@PostConstruct` and `@PreDestroy` are invoked automatically.
- Ideal for shared state or consistent behavior across requests/sessions.

---







Spring Security
UserDetailsService - Authendication

Spring core - https://www.geeksforgeeks.org/advance-java/spring-boot/







































