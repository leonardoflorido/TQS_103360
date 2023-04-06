package tqs.music;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.music.model.Music;
import tqs.music.repository.MusicRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MusicApplicationTests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withUsername("demo")
            .withPassword("password")
            .withDatabaseName("test");

    @Autowired
    private MusicRepository musicRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    void contextLoads() {
        String[] names = {"Born in Winter", "Post American World", "Walk"};
        String[] bands = {"Gojira", "Megadeth", "Pantera"};
        int[] years = {2012, 2016, 1992};

        for (int i = 0; i < 3; i++) {
            Music music = new Music();
            music.setName(names[i]);
            music.setBand(bands[i]);
            music.setReleaseYear(years[i]);
            musicRepository.save(music);
        }
    }

    @Test
    @Order(1)
    public void testCreateMusic() {
        Music music = new Music();
        music.setName("The Beautiful People");
        music.setBand("Marilyn Manson");
        music.setReleaseYear(1990);
        musicRepository.save(music);

        Optional<Music> optionalMusic = musicRepository.findById(music.getId());
        assertThat(optionalMusic).isPresent().hasValueSatisfying(musicInDb -> {
            assertThat(musicInDb.getName()).isEqualTo(music.getName());
            assertThat(musicInDb.getBand()).isEqualTo(music.getBand());
            assertThat(musicInDb.getReleaseYear()).isEqualTo(music.getReleaseYear());
        });
    }

    @Test
    @Order(2)
    public void testUpdateMusic() {
        Optional<Music> optionalMusic = musicRepository.findByName("The Beautiful People");

        assertThat(optionalMusic).isPresent();
        Music music = optionalMusic.get();
        music.setReleaseYear(1996);
        musicRepository.save(music);

        Optional<Music> optionalMusicUpdated = musicRepository.findById(music.getId());
        assertThat(optionalMusicUpdated).isPresent().hasValueSatisfying(musicInDb -> assertThat(musicInDb.getReleaseYear()).isEqualTo(1996));
    }

    @Test
    @Order(3)
    public void testDeleteMusic() {
        Optional<Music> optionalMusic = musicRepository.findByName("The Beautiful People");

        assertThat(optionalMusic).isPresent();
        Music music = optionalMusic.get();
        musicRepository.delete(music);

        Optional<Music> optionalMusicDeleted = musicRepository.findById(music.getId());
        assertThat(optionalMusicDeleted).isNotPresent();
    }
}
