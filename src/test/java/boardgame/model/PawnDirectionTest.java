package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnDirectionTest {

    @Test
    void getRowChange() {
        assertEquals(-1,PawnDirection.UP_LEFT.getRowChange());
        assertEquals(-1,PawnDirection.UP.getRowChange());
        assertEquals(-1,PawnDirection.UP_RIGHT.getRowChange());
        assertEquals(0,PawnDirection.RIGHT.getRowChange());
        assertEquals(1,PawnDirection.DOWN_RIGHT.getRowChange());
        assertEquals(1,PawnDirection.DOWN.getRowChange());
        assertEquals(1,PawnDirection.DOWN_LEFT.getRowChange());
        assertEquals(0,PawnDirection.LEFT.getRowChange());
    }

    @Test
    void getColChange() {
        assertEquals(-1,PawnDirection.UP_LEFT.getColChange());
        assertEquals(0,PawnDirection.UP.getColChange());
        assertEquals(1,PawnDirection.UP_RIGHT.getColChange());
        assertEquals(1,PawnDirection.RIGHT.getColChange());
        assertEquals(1,PawnDirection.DOWN_RIGHT.getColChange());
        assertEquals(0,PawnDirection.DOWN.getColChange());
        assertEquals(-1,PawnDirection.DOWN_LEFT.getColChange());
        assertEquals(-1,PawnDirection.LEFT.getColChange());
    }

    @Test
    void of() {
        assertEquals(PawnDirection.UP_LEFT, PawnDirection.of(-1,-1));
        assertEquals(PawnDirection.UP, PawnDirection.of(-1,0));
        assertEquals(PawnDirection.UP_RIGHT, PawnDirection.of(-1,1));
        assertEquals(PawnDirection.RIGHT, PawnDirection.of(0,1));
        assertEquals(PawnDirection.DOWN_RIGHT, PawnDirection.of(1,1));
        assertEquals(PawnDirection.DOWN, PawnDirection.of(1,0));
        assertEquals(PawnDirection.DOWN_LEFT, PawnDirection.of(1,-1));
        assertEquals(PawnDirection.LEFT, PawnDirection.of(0,-1));
    }

}