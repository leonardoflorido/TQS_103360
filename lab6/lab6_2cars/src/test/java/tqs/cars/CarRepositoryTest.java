package tqs.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void whenFindById_thenReturnCar() {
        // given
        Car car = new Car("porsche", "911");
        entityManager.persist(car);
        entityManager.flush();

        // when
        Car found = carRepository.findById(car.getCarId()).orElse(null);

        // then
        assertThat(found.getCarId()).isEqualTo(car.getCarId());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindAll_thenReturnAllCars() {
        Car porsche = new Car("porsche", "911");
        Car ferrari = new Car("ferrari", "f40");
        Car tesla = new Car("tesla", "model x");

        entityManager.persist(porsche);
        entityManager.persist(ferrari);
        entityManager.persist(tesla);
        entityManager.flush();

        Iterable<Car> found = carRepository.findAll();

        assertThat(found).hasSize(3).extracting(Car::getMaker).containsOnly(porsche.getMaker(), ferrari.getMaker(), tesla.getMaker());
    }
}
