package tqs.cars;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create") // note the TestPropertySource to enforce the ddl generation!
class CarControllerTest {
    // instantiate the container passing selected config
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withUsername("demo")
            .withPassword("demopass")
            .withDatabaseName("test_db");

    @LocalServerPort
    int localPortForTestServer;
    Car car1, car2;

    @Autowired
    private CarRepository repository;

    // read configuration from running db
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);

    }

    @BeforeEach
    public void setUpTestCars() {
        repository.flush();
        car1 = repository.save(new Car("porsche", "911"));
        car2 = repository.save(new Car("tesla", "model s"));
    }

    @AfterEach
    public void resetCars() {
        repository.deleteAll();
    }

    @Test
    void whenPostCar_thenCreateCar() throws IOException {
        Car car1 = new Car("Mercedes", "AMG");

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json").body(JsonUtils.toJson(car1))
                .when().post(endpoint)
                .then().statusCode(201)
                .body("maker", is("Mercedes"))
                .body("model", is("AMG"))
        ;
    }

    @Test
    void whenGetCarById_thenApiReturnsOneCar() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars", String.valueOf(car1.getCarId()))
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json").get(endpoint)
                .then().statusCode(200)
                .body("maker", is("porsche"))
                .body("model", is("911"))
        ;
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json").get(endpoint)
                .then().statusCode(200)
                .body("", hasSize(2))
                .body("[0].maker", is(car1.getMaker()))
                .body("[1].maker", is(car2.getMaker()))
                .body("[0].model", is(car1.getModel()))
                .body("[1].model", is(car2.getModel()))
        ;
    }
}
