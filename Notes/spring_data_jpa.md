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
