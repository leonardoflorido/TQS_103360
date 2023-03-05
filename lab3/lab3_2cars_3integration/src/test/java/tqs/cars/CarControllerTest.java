package tqs.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.cars.boundary.CarController;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car car = new Car("Porsche", "911");

        when(service.save(Mockito.any())).thenReturn(car);

        mvc.perform(
                        post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is(car.getMaker())));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car porche = new Car("Porsche", "911");
        Car ferrari = new Car("Ferrari", "F40");
        Car jeep = new Car("Jeep", "Wrangler");

        List<Car> allCars = List.of(porche, ferrari, jeep);

        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                        get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(porche.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(ferrari.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(jeep.getMaker())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void givenCar_whenGetCarById_thenReturnJson() throws Exception {
        Car car = new Car("Porsche", "911");

        when(service.getCarDetails(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));

        mvc.perform(
                        get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(car.getMaker())));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }
}
