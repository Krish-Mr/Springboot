
# 🧠 Spring Boot Conditional Bean Loading

Spring Boot provides powerful annotations to conditionally load beans based on runtime conditions.
---

## 🔧 Custom Conditional Implementation

```java
public class CustomClass implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return true; // Custom logic which return Boolean
    }
}

@Configuration
@Conditional(CustomClass.class)
public class CustomConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

---

## 📦 Conditional Annotations

### `@ConditionalOnProperty`
Loads bean if a specific property is set to a given value.
```java
@ConditionalOnProperty(name = "feature.advanced.enabled", havingValue = "true", matchIfMissing = false)
```

### `@ConditionalOnClass`
Loads bean if the specified class is present in the classpath.
```java
@ConditionalOnClass(name = "com.example.conditionaldemo.service.AdvancedService")
```

### `@ConditionalOnMissingClass`
Loads bean if the specified class is **not** present in the classpath.
```java
@ConditionalOnMissingClass(name = "com.example.conditionaldemo.service.MissingService")
```

### `@ConditionalOnMissingBean`
Loads bean if no bean of the specified type is already registered.
```java
@ConditionalOnMissingBean(AdvancedService.class)
```

### `@ConditionalOnBean`
Loads bean only if another bean of the specified type is present.
```java
@ConditionalOnBean(Service.class)
```

### `@ConditionalOnResource`
Loads bean only if specified resource files are present.
```java
@ConditionalOnResource(resources = {
    "classpath:config/myconfig/application-dev.properties",
    "classpath:config/myconfig/feature-toggle.properties"
})
```

### `@ConditionalOnExpression`
Loads bean based on a SpEL (Spring Expression Language) expression.
```java
@ConditionalOnExpression("${app.version} > 5.1")
```

### `@ConditionalOnJava`
Loads bean based on the Java version.
```java
@ConditionalOnJava(JavaVersion.EIGHT)
```

### `@ConditionalOnWebApplication`
Loads bean only if the application is a web application.
```java
@ConditionalOnWebApplication
```

### `@ConditionalOnNotWebApplication`
Loads bean only if the application is **not** a web application.
```java
@ConditionalOnNotWebApplication
```

---

## 🧪 Example Configuration

```java
@Configuration
public class ServiceConfig {

    @Bean
    @ConditionalOnProperty(name = "feature.advanced.enabled", havingValue = "true", matchIfMissing = false)
    @ConditionalOnClass(name = "com.example.conditionaldemo.service.AdvancedService")
    @ConditionalOnMissingClass(name = "com.example.conditionaldemo.service.MissingService")
    @ConditionalOnMissingBean(AdvancedService.class)
    @ConditionalOnBean(Service.class)
    @ConditionalOnResource(resources = {
        "classpath:config/myconfig/application-dev.properties",
        "classpath:config/myconfig/feature-toggle.properties"
    })
    @ConditionalOnExpression("${app.version} > 5.1")
    public AdvancedService advancedService() {
        return new AdvancedService();
    }
}
```
