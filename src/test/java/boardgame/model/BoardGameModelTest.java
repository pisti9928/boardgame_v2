package boardgame.model;

import boardgame.player.Player;
import boardgame.player.PlayerState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardGameModelTest {
    static BoardGameModel model;
    @BeforeAll
    static void init() {
        model = new BoardGameModel();
    }
    @Test
    void getPieceCount() {
         assertEquals(49,model.getPieceCount());
    }

    @Test
    void getPieceType() {
        assertEquals(PieceType.LIME,model.getPieceType(0));
        assertEquals(PieceType.BLUE,model.getPieceType(1));
        assertEquals(PieceType.RED,model.getPieceType(7));
    }

    @Test
    void getPiecePosition() {
        assertEquals(new Position(0,0),model.getPiecePosition(0));
        assertEquals(new Position(0,1),model.getPiecePosition(1));
        assertEquals(new Position(1,0),model.getPiecePosition(7));
    }

    @Test
    void isValidMove() {
        assertEquals(true,model.isValidMove(0,PawnDirection.DOWN));
        assertEquals(false,model.isValidMove(0,PawnDirection.UP));
    }

    @Test
    void isOnBoard() {
        assertEquals(true,BoardGameModel.isOnBoard(new Position(0,0)));
        assertEquals(false,BoardGameModel.isOnBoard(new Position(-1,0)));
        assertEquals(false,BoardGameModel.isOnBoard(new Position(0,9)));
    }

    @Test
    void testToString() {
        assertEquals("[LIME(0,0),BLUE(0,1),BLUE(0,2),BLUE(0,3),BLUE(0,4),BLUE(0,5),LIME(0,6),RED(1,0),LIME(1,1),LIME(1,2),LIME(1,3),LIME(1,4),LIME(1,5),RED(1,6),RED(2,0),LIME(2,1),LIME(2,2),LIME(2,3),LIME(2,4),LIME(2,5),RED(2,6),RED(3,0),LIME(3,1),LIME(3,2),LIME(3,3),LIME(3,4),LIME(3,5),RED(3,6),RED(4,0),LIME(4,1),LIME(4,2),LIME(4,3),LIME(4,4),LIME(4,5),RED(4,6),RED(5,0),LIME(5,1),LIME(5,2),LIME(5,3),LIME(5,4),LIME(5,5),RED(5,6),LIME(6,0),BLUE(6,1),BLUE(6,2),BLUE(6,3),BLUE(6,4),BLUE(6,5),LIME(6,6)]",model.toString());
    }

    @Test
    void setstate() {
        PlayerState.setPlayername(Player.PLAYERBLUE,"blue");
        PlayerState.setPlayername(Player.PLAYERRED,"red");
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(1),1);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(2),2);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(3),3);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(4),4);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(5),5);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(43),43);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(44),44);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(45),45);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(46),46);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(47),47);
        assertEquals(GameStateType.PLAYERBLUEWIN,model.setstate());

        model = new BoardGameModel();
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(7),7);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(13),13);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(14),14);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(20),20);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(21),21);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(27),27);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(28),28);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(34),34);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(35),35);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(41),41);
        assertEquals(GameStateType.PLAYERREDWIN,model.setstate());

        model = new BoardGameModel();
        assertEquals(GameStateType.PLAYING,model.setstate());

        model = new BoardGameModel();
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(8),8);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(1),1);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(7),7);
        model.setPieces(PieceType.TRANSPARENT,model.getPiecePosition(0),0);
        assertEquals(GameStateType.DRAW,model.setstate());

    }
}