# Spring Data JPA Concepts

## Composite Keys with `@EmbeddedId`
```java
@Entity
public class User {
    @EmbeddedId
    private UserId id;
    private String fullName;
}

@Embeddable
public class UserId implements Serializable {
    private String username;
    private String email;
    // equals() and hashCode() must be overridden
}

public interface UserRepository extends JpaRepository<User, UserId> {}
```

---

## Common JPA Annotations
```java
@Entity
@Table(name="User")
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE, TABLE, UUID
@Column(name = "user_id", nullable = false, unique = true, length=30)

@Embedded
@AttributeOverrides({ 
    @AttributeOverride(name = "city", column = @Column(name = "city", length = 20)),
    @AttributeOverride(name = "state", column = @Column(name = "state", length = 20)),
    @AttributeOverride(name = "country", column = @Column(name = "country", length = 20)),
    @AttributeOverride(name = "pincode", column = @Column(name = "pincode", length = 10))
})
private Address address;

@JoinColumn(name="address")
@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)

@Temporal(TemporalType.TIMESTAMP)
@CreationTimestamp
@UpdateTimestamp
@Transient
```

---

## Cascade Types in JPA
| Cascade Type        | Description                                               |
|---------------------|-----------------------------------------------------------|
| `CascadeType.ALL`   | Applies all operations (persist, merge, remove, refresh, detach) |
| `CascadeType.PERSIST` | Saves child when parent is saved                        |
| `CascadeType.REMOVE`  | Deletes child when parent is deleted                    |
| `CascadeType.MERGE`   | Updates child when parent is updated                    |
| `CascadeType.REFRESH` | Refreshes child when parent is refreshed                |
| `orphanRemoval=true`  | Deletes child if removed from parent’s collection       |

---

## SQL Trigger Type vs JPA Equivalent Annotation

| Annotation     | Triggered When...             | Use Case Example                     |
|----------------|-------------------------------|--------------------------------------|
| `@PrePersist`  | Before the entity is inserted | Validate or initialize fields        |
| `@PostPersist` | After the entity is inserted  | Logging or audit trail               |
| `@PreUpdate`   | Before the entity is updated  | Enforce business rules               |
| `@PostUpdate`  | After the entity is updated   | Sync or notify other systems         |
| `@PreRemove`   | Before the entity is deleted  | Prevent deletion based on conditions |
| `@PostRemove`  | After the entity is deleted   | Cleanup or archive                   |

---

## Example: Lifecycle Callbacks in Entity
```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private Double salary;

    @PrePersist
    public void beforeInsert() {
        if (salary == null) salary = 30000.0;
    }

    @PostPersist
    public void afterInsert() {
        System.out.println("Inserted employee: " + name);
    }

    @PreUpdate
    public void beforeUpdate() {
        if (salary > 1000000) throw new RuntimeException("Salary exceeds allowed limit.");
    }

    @PostUpdate
    public void afterUpdate() {
        System.out.println("Updated employee: " + name);
    }

    @PreRemove
    public void beforeDelete() {
        if ("Manager".equalsIgnoreCase(role)) throw new RuntimeException("Managers cannot be deleted directly.");
    }

    @PostRemove
    public void afterDelete() {
        System.out.println("Deleted employee: " + name);
    }
}
```

---

## `@EntityListeners` — Modular Lifecycle Logic
```java
@Component
public class EmployeeEntityListener {
    @PrePersist
    public void beforeInsert(Employee emp) {
        if (emp.getSalary() == null) emp.setSalary(30000.0);
    }

    @PostPersist
    public void afterInsert(Employee emp) {
        System.out.println("Inserted: " + emp.getName());
    }

    @PreUpdate
    public void beforeUpdate(Employee emp) {
        if (emp.getSalary() > 1000000) throw new RuntimeException("Salary too high!");
    }

    @PostUpdate
    public void afterUpdate(Employee emp) {
        System.out.println("Updated: " + emp.getName());
    }

    @PreRemove
    public void beforeDelete(Employee emp) {
        if ("Manager".equalsIgnoreCase(emp.getRole())) throw new RuntimeException("Managers cannot be deleted.");
    }

    @PostRemove
    public void afterDelete(Employee emp) {
        System.out.println("Deleted: " + emp.getName());
    }
}

@Entity
@EntityListeners(EmployeeEntityListener.class)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```
---
### Spring Validator
| Annotation  | Purpose                                                                                  | Example Usage                                  |
|-------------|------------------------------------------------------------------------------------------|-----------------------------------------------|
| `@NotNull`  | Ensures the field is **not null** (allows empty strings or empty collections).           | `@NotNull private String name;`                |
| `@NotEmpty` | Ensures the field is **not null** and **not empty** (non-empty string or collection).    | `@NotEmpty private List<String> items;`        |
| `@NotBlank` | Applies to **strings only**; ensures **not null**, **not empty**, and **not whitespace** | `@NotBlank private String username;`           |
| `@Min`      | Ensures a numeric field’s value is **≥ given minimum**.                                 | `@Min(18) private int age;`                     |
| `@Max`      | Ensures a numeric field’s value is **≤ given maximum**.                                 | `@Max(100) private int score;`                  |
| `@Size`     | Validates size of string, collection, array (min and/or max length).                    | `@Size(min=5, max=20) private String password;`|
| `@Email`    | Validates that the string is a **well-formed email address**.                           | `@Email private String email;`                  |
| `@Pattern`  | Validates string against a **regular expression** pattern.                              | `@Pattern(regexp="\\d{10}") private String phone;` |
> @valid to trigger the validation In case any failure happen it'll throw MethodArgumentNotValidException we can handle in @ExceptionHandler(MethodArgumentNotValidException.class)

---
### JPA Query Types:
```java
public interface UserRepository extends JpaRepository<User, Long> {

    // 1. JPQL Query
    @Query("SELECT u FROM User u WHERE u.age > ?1")
    List<User> findUsersOlderThan(int age);

    // 2. Named Parameter
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findByName(@Param("name") String name);

    // 3. SPEL Expression Parameter
    @Query("SELECT u FROM User u WHERE u.email = :#{#email}")
    List<User> findByEmailUsingSpEL(@Param("email") String email);

    // 4. Native SQL Query
    @Query(value = "SELECT * FROM user WHERE age < :age", nativeQuery = true)
    List<User> findUsersYoungerThan(@Param("age") int age);
}
```

### 📘 Spring Data JPA Query Method Reference

Spring Data JPA allows defining queries by method names in repository interfaces. This guide outlines the naming conventions and keywords used to construct expressive queries.

######  <a>Refer: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html</a>
 
---

### 🔍 Query Prefix Keywords

| Keyword     | Description                  |
|-------------|------------------------------|
| `findBy`    | Retrieve entities            |
| `existsBy`  | Check existence              |
| `countBy`   | Count entities               |
| `deleteBy`  | Delete entities              |
| `top3By`    | Limit results to top 3       |
| `first5By`  | Limit results to first 5     |

---

### 🧩 Field Naming Rules

- Use **CamelCase** matching **entity field names** (not column names)
- **Case-sensitive**
- For nested properties: use `_` (e.g., `address_city` for `address.city`)

---

### 🎯 Condition Keywords

| Keyword             | Description                          |
|---------------------|--------------------------------------|
| `Equals`            | Exact match (can be omitted)         |
| `NotEquals`         | Not equal                            |
| `LessThan`          | `<` comparison                       |
| `LessThanEqual`     | `<=` comparison                      |
| `GreaterThan`       | `>` comparison                       |
| `GreaterThanEqual`  | `>=` comparison                      |
| `Before`, `After`   | Date/time comparisons                |
| `Containing`        | LIKE %value%                         |
| `Like`, `NotLike`   | SQL LIKE / NOT LIKE                  |
| `StartingWith`      | LIKE value%                          |
| `EndingWith`        | LIKE %value                          |
| `In`, `NotIn`       | Collection membership                |
| `IgnoreCase`        | Case-insensitive match               |
| `True`, `False`     | Boolean match                        |
| `IsNull`, `IsNotNull` | Null checks                        |

---

### 🔗 Combining Conditions

| Keyword | Description               |
|---------|---------------------------|
| `And`   | Combine multiple conditions |
| `Or`    | Alternative conditions      |
| `Not`   | Negate condition            |

---

### 📐 Ordering Results

Use `OrderBy` followed by field name and direction:
- `OrderByAgeAsc`
- `OrderByCreatedDateDesc`

---

### 🧠 Syntax Template

```plaintext
<Prefix><Distinct?>By<Prop1><Condition?><And/Or?><Prop2><Condition?>...<OrderBy?><Field><Asc/Desc?>
findTop5DistinctByLastNameIgnoreCaseAndAgeGreaterThanOrderByAgeDesc();
```
Example: 
```java
List<User> findTop3DistinctByFirstNameStartingWithIgnoreCaseAndLastNameEndingWithIgnoreCaseOrEmailContainingIgnoreCaseAndAgeGreaterThanEqualAndIsActiveTrueAndCreatedDateBeforeAndUpdatedDateAfterAndRoleInAndDepartmentNotInAndManagerIsNullAndLocationIsNotNullOrderByAgeDesc(
    String firstNamePrefix,
    String lastNameSuffix,
    String emailFragment,
    Integer ageThreshold,
    LocalDate createdBefore,
    LocalDate updatedAfter,
    List<Role> roles,
    List<Department> excludedDepartments
);	
```


### JPA Specification

must have to implements a JPASpecification interface which provide the toPridicate(root, query, criteriaBuilder) method, using with that we can create a query formation.


Repository Query Method
@QueryRewriter
@Modifying Query
Example Matcher
QueryDSL / QueryFactory / JPAQuery

