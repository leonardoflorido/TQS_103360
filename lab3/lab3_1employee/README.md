# Employee Manager

## Review Questions

### A) Identify a couple of examples that use AssertJ expressive methods chaining.

- Within the file "A_EmployeeRepositoryTest.java":

```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(),ron.getName(),bob.getName());
```

- Within the file "B_EmployeeService_UnitTest.java":

```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(),john.getName(),bob.getName());
```

### B) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

- Within the file "B_EmployeeService_UnitTest.java":

```java
@Mock(lenient = true)
private EmployeeRepository employeeRepository;
```

### C) What is the difference between standard `@Mock` and `@MockBean`?

`@Mock` is used in unit tests to mock dependencies within a single class, while `@MockBean` is used in integration tests
to mock dependencies across the entire application context.

### D) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

The "application-integrationtest.properties" file is used to configure a Spring Boot application for integration
testing. It contains configuration properties that are different from the default "application.properties" file, and it
is used when the application requires additional configuration for integration testing purposes, such as an external
database or API.

### E) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

The C tests prioritize speed and efficiency, and they don't require any interactions with databases or repositories.
Meanwhile, the D and E tests are the slowest ones, as they load the complete Spring Boot context by using the
@SpringBootTest annotation. Unlike D, the E test concentrates on evaluating data serialization and deserialization.
Moreover, the D test employs a mocked servlet, while the E test uses an authentic REST client to perform its testing
tasks.