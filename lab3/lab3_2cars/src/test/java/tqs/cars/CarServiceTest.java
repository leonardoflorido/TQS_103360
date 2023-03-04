package tqs.cars;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {
        Car porsche = new Car("Porsche", "911");
        porsche.setCarId(111L);

        Car ferrari = new Car("Ferrari", "F40");
        Car jeep = new Car("Jeep", "Wrangler");

        List<Car> allCars = List.of(porsche, ferrari, jeep);

        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findByCarId(porsche.getCarId())).thenReturn(porsche);
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    void whenSearchValidMaker_thenCarShouldBeFound() {
        // TODO: implement this test
        /*
        String maker = "Porsche";
        List<Car> found = carManagerService.getAllCarsByMaker(maker);
        assertThat(found).extracting(Car::getMaker).containsOnly(maker);
         */
    }
}
