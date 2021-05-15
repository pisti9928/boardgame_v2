package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.*;
import java.util.logging.Logger;

public class BoardGameModel {

    public static int BOARD_SIZE = 7;

    private final Piece[] pieces;
    //private ReadOnlyObjectWrapper<Piece>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    private boolean elsoLepes=true;
    private int utolsoPozicioSzam;

    public BoardGameModel() {
        this.pieces = new Piece[BOARD_SIZE*BOARD_SIZE];
        //this(new Piece(PieceType.GREEN,new Position(0,0)));

        for(int i = 0;i<BOARD_SIZE;i++){

            for(int j=0; j <BOARD_SIZE;j++)
            {


                if ((i==0 || i==BOARD_SIZE-1 )&& (j!=0 && j!=BOARD_SIZE-1)){
                    this.pieces[i * BOARD_SIZE + j] =new Piece(PieceType.BLUE,new Position(i,j));
                }
                else if ((j==0 || j==BOARD_SIZE-1 )&& (i!=0 && i!=BOARD_SIZE-1)) {
                    this.pieces[i * BOARD_SIZE + j] =new Piece(PieceType.RED,new Position(i,j));
                }
                else {
                    this.pieces[i * BOARD_SIZE + j] =new Piece(PieceType.GREEN,new Position(i,j));

                }


            }
        }


    }

    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (! isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }

    public int getPieceCount() {
        return pieces.length;
    }

    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }


    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    public boolean isValidMove(int pieceNumber, PawnDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        if (! isOnBoard(newPosition)) {
            return false;
        }
        if (pieces[newPosition.row()*BOARD_SIZE+newPosition.col()].getType().equals(PieceType.TRANSPARENT)){
            return false;
        }

        return true;
    }

    public List<Position> getPiecePositions(){
        List<Position> positions = new ArrayList<>();
        if (elsoLepes){
            for (var piece : pieces) {
                if(!piece.getType().equals(PieceType.RED)&&!piece.getType().equals(PieceType.BLUE)) {
                    positions.add(piece.getPosition());
                }
            }
            elsoLepes = false;
            return positions;
        }
        else {
            for (var direction : getValidMoves(utolsoPozicioSzam)) {
                positions.add(pieces[utolsoPozicioSzam].getPosition().moveTo(direction));
            }
            return positions;
        }


    }

    public Set<PawnDirection> getValidMoves(int pieceNumber) {
        EnumSet<PawnDirection> validMoves = EnumSet.noneOf(PawnDirection.class);
        for (var direction : PawnDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }



    public void remove(int pieceNumber) {
        pieces[pieceNumber] = new Piece(PieceType.TRANSPARENT,pieces[pieceNumber].getPosition());
        System.out.println(pieceNumber);
        System.out.println(pieces[pieceNumber].getType());
        utolsoPozicioSzam = pieceNumber;
    }



    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_SIZE
                && 0 <= position.col() && position.col() < BOARD_SIZE;
    }
/*
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>(pieces.length);
        for (var piece : pieces) {
            positions.add(piece.getPosition());
        }
        return positions;
    }
*/
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();
        System.out.println(model);
    }

}
