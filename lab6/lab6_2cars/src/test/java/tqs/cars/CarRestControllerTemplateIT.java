package tqs.cars;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class CarRestControllerTemplateIT {
    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateEmployee() {
        Car tesla = new Car("tesla", "model s");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", tesla, Car.class);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("tesla");
    }

    @Test
    void givenEmployees_whenGetEmployees_thenStatus200() {
        createTestCar("porsche", "911");
        createTestCar("mercedes", "amg");


        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("porsche", "mercedes");
    }

    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        carRepository.saveAndFlush(car);
    }
}