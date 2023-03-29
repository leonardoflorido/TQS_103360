package tqs.cars;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
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
    void whenSearchValidID_thenCarShouldBeFound() {
        long id = 111L;

        Car car = carManagerService.getCarDetails(id).get();

        assertThat(car.getCarId()).isEqualTo(id);
    }

    @Test
    void whenSearchInvalidID_thenCarShouldNotBeFound() {
        long id = -99L;

        Optional<Car> car = carManagerService.getCarDetails(id);

        assertThat(car).isEmpty();

        verifyFindByIDIsCalledOnce(id);
    }

    @Test
    void given3Cars_whenGetAll_thenReturn3Records() {
        Car porsche = new Car("Porsche", "911");
        Car ferrari = new Car("Ferrari", "F40");
        Car jeep = new Car("Jeep", "Wrangler");

        List<Car> allCars = carManagerService.getAllCars();

        verifyFindAllCarsIsCalledOnce();

        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(porsche.getMaker(), ferrari.getMaker(), jeep.getMaker());
    }

    private void verifyFindByIDIsCalledOnce(long id) {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(id);
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
