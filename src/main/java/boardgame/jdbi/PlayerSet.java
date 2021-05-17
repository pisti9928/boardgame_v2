package boardgame.jdbi;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PlayerSet osztaly. Ilyen tipusuak lesznek a playerek akik jatszanak, szukseg van erre a tabla Bind -olasahoz.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerSet {
    private String name;
    private int wins;
    private int loses;
    private int draws;

}
