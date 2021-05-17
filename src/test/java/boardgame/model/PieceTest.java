package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    Position position = new Position(3,3);
    Piece piece1 = new Piece(PieceType.BLUE,position);
    Piece piece2 = new Piece(PieceType.LIME,position);
    Piece piece3 = new Piece(PieceType.RED,position);
    Piece piece4 = new Piece(PieceType.TRANSPARENT,position);
    @Test
    void getType() {
        assertEquals(PieceType.BLUE,piece1.getType());
        assertEquals(PieceType.LIME,piece2.getType());
        assertEquals(PieceType.RED,piece3.getType());
        assertEquals(PieceType.TRANSPARENT,piece4.getType());
    }

    @Test
    void getPosition() {
        Position p = new Position(3,3);
        assertEquals(p,piece1.getPosition());
    }

    @Test
    void testToString() {
        assertEquals("BLUE",piece1.getType().toString());
        assertEquals("(3,3)",piece1.getPosition().toString());

    }
}