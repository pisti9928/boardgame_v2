package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position testPos;

    @BeforeEach
    void init(){
        testPos=new Position(0,0);
    }

    @Test
    void moveTo() {
        assertEquals(new Position(-1,-1),testPos.moveTo(PawnDirection.UP_LEFT));
        assertEquals(new Position(-1,0),testPos.moveTo(PawnDirection.UP));
        assertEquals(new Position(-1,1),testPos.moveTo(PawnDirection.UP_RIGHT));
        assertEquals(new Position(0,1),testPos.moveTo(PawnDirection.RIGHT));
        assertEquals(new Position(1,1),testPos.moveTo(PawnDirection.DOWN_RIGHT));
        assertEquals(new Position(1,0),testPos.moveTo(PawnDirection.DOWN));
        assertEquals(new Position(1,-1),testPos.moveTo(PawnDirection.DOWN_LEFT));
        assertEquals(new Position(0,-1),testPos.moveTo(PawnDirection.LEFT));
    }

    @Test
    void testToString() {
        assertEquals("(-1,-1)",testPos.moveTo(PawnDirection.UP_LEFT).toString());
        assertEquals("(-1,0)",testPos.moveTo(PawnDirection.UP).toString());
        assertEquals("(-1,1)",testPos.moveTo(PawnDirection.UP_RIGHT).toString());
        assertEquals("(0,1)",testPos.moveTo(PawnDirection.RIGHT).toString());
        assertEquals("(1,1)",testPos.moveTo(PawnDirection.DOWN_RIGHT).toString());
        assertEquals("(1,0)",testPos.moveTo(PawnDirection.DOWN).toString());
        assertEquals("(1,-1)",testPos.moveTo(PawnDirection.DOWN_LEFT).toString());
        assertEquals("(0,-1)",testPos.moveTo(PawnDirection.LEFT).toString());
        assertEquals("(0,0)",testPos.toString());
    }


}