package boardgame.jdbi;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerSet {
    private String name;
    private int wins;
    private int loses;
    private int draws;

}
