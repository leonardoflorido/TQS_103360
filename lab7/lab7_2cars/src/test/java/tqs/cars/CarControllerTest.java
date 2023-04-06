package tqs.cars;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.cars.boundary.CarController;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

/**
 * WebMvcTest loads a simplified web environment for the tests. Note that the normal
 * auto-discovery of beans (and dependency injection) is limited
 * This strategy deploys the required components to a test-friendly web framework, that can be accessed
 * by injecting a MockMvc reference
 */
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mvc;    //entry point to the web framework

    // inject required beans as "mockeable" objects
    // note that @AutoWire would result in NoSuchBeanDefinitionException
    @MockBean
    private CarManagerService service;


    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car car1 = new Car("maker1", "model1");

        when(service.save(Mockito.any())).thenReturn(car1);

        RestAssuredMockMvc.given().auth().none().contentType("application/json").body(JsonUtils.toJson(car1))
                .when().post("/api/cars").then()
                .statusCode(201)
                .body("maker", is("maker1"))
                .body("model", is("model1"));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() {
        Car car1 = new Car("maker1", "model1");
        Car car2 = new Car("maker2", "model2");
        Car car3 = new Car("maker3", "model3");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc.given().auth().none().contentType("application/json").get("/api/cars")
                .then().statusCode(200)
                .body("", hasSize(3))
                .body("[0].maker", is(car1.getMaker()))
                .body("[1].maker", is(car2.getMaker()))
                .body("[2].maker", is(car3.getMaker()))
                .body("[0].model", is(car1.getModel()))
                .body("[1].model", is(car2.getModel()))
                .body("[2].model", is(car3.getModel()));

        verify(service, times(1)).getAllCars();
    }
}
