## Inversion of Control (IoC) in Spring

IoC is the **core principle** of the Spring Framework. It refers to the **inversion of control** from the application code to the Spring container, which manages the creation and lifecycle of objects (beans).

### Types of IoC Containers

### 1. **BeanFactory**
- The **basic IoC container**.
- Responsible for **creating and managing bean lifecycle** (initialization, destruction).
- Lightweight and used in simple scenarios.

### 2. **ApplicationContext**
- **Extends BeanFactory** and is its more advanced child.
- Provides all features of BeanFactory **plus**:
  - Event publishing (`ApplicationEvent` and `@EventListener`)
  - AOP integration
  - Internationalization (i18n)
  - Dependency Injection
  - Environment Abstraction (`context.getEnvironment().getProperty("spring.datasource.url")`)
  - Resource Loading (`context.getResource("classpath:data.json")`)
  - Profile Management
  - Parent-Child Context Hierarchy

---

### ApplicationContext Types in Spring

| Type | Configuration Style | Use Case |
|------|---------------------|----------|
| `AnnotationConfigApplicationContext` | Java-based (`@Configuration`) | Modern Spring apps |
| `ClassPathXmlApplicationContext` | XML from classpath | Legacy apps |
| `FileSystemXmlApplicationContext` | XML from file system | External config |
| `WebApplicationContext` | Java/XML + ServletContext | Web apps |
| `GenericApplicationContext` | Programmatic | Dynamic or modular apps |
| `StaticApplicationContext` | Manual | Testing or simple apps |

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

>✅ Use the container to build scalable, maintainable, and loosely-coupled applications in Spring Boot!
---

## 🌱 Spring Bean Scopes & Lifecycle Cheatsheet

### 🧬 Bean Scopes Overview

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
