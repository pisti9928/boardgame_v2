package boardgame.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStateTest {

    @Test
    void setPlayername() {
        PlayerState.setPlayername(Player.PLAYERRED,"András");
        assertEquals("András",PlayerState.getRedPlayerName());
        PlayerState.setPlayername(Player.PLAYERBLUE,"Géza");
        assertEquals("Géza",PlayerState.getBluePlayerName());
    }
}