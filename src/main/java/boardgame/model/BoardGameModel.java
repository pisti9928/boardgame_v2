package boardgame.model;

import boardgame.jdbi.LeaderboardController;
import boardgame.player.PlayerState;
import javafx.beans.property.ObjectProperty;
import org.tinylog.Logger;

import java.util.*;

/**
 * Egy modelt biztosit a jatekszabalyok leirasahoz.
 */
public class BoardGameModel {
    /**
     * jatektabla merete {@code BOARD_SIZE}.
     */
    public static int BOARD_SIZE = 7;

    /**
     * A korongokat ebben reprezentaljuk.
     */
    private final Piece[] pieces;

    /**
     * Jatekallapot reprezentalasa.
     */
    public GameStateType state;

    /**
     * Az elso lepesnel csak zoldre lephetunk ezert kell hozza egy boolean valtozo {@code elsolepes}, hogy tudjuk azt hogy az elso lepesnel tartunk-e.
     */
    public static boolean elsoLepes=true;
    /**
     * eltaroljuk az utolso levett korong poziciojanak indexet, pieces tomb indexe.
     */
    private int utolsoPozicioSzam;

    /**
     * Kiinduloallapot, korongok helyenek meghatarozasa.
     */
    public BoardGameModel() {
        this.pieces = new Piece[BOARD_SIZE*BOARD_SIZE];
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
                    this.pieces[i * BOARD_SIZE + j] =new Piece(PieceType.LIME,new Position(i,j));
                }
            }
        }
    }

    /**
     * Adott feltetelek alapjan megallapitja, hogy vegetert-e mar a jatek.
     * Ha vegetert akkor visszaadja a state -et amelynek a tipusa GameStateType.
     * @return GameStateType {@code state} .
     */
    public  GameStateType setstate(){
        boolean blueWin = true;
        boolean redWin = true;
        int blueCount = 0;
        int redCount = 0;
        if (getPiecePositions().isEmpty()){
            LeaderboardController leaderboardController = new LeaderboardController();
            leaderboardController.createTable();
            for (var piece : pieces){
                if (piece.getType().equals(PieceType.BLUE)) {
                    blueCount += 1;
                }
                if (piece.getType().equals(PieceType.RED)){
                    redCount += 1;
                }
            }
            if (blueCount<redCount){
                state = GameStateType.PLAYERBLUEWIN;
                GameState.blueWin();


            }
            else if (blueCount>redCount){
                state = GameStateType.PLAYERREDWIN;
                GameState.redWin();
            }
            else{
                state = GameStateType.DRAW;
                GameState.draw();
            }
        }
        else
        {
            for (var piece : pieces){
                if (piece.getType().equals(PieceType.BLUE)) {
                    blueWin = false;
                }
                if (piece.getType().equals(PieceType.RED)){
                    redWin = false;
                }
            }
            if (blueWin){
                state =  GameStateType.PLAYERBLUEWIN;
                GameState.blueWin();
            }
            else if (redWin){
                state =  GameStateType.PLAYERREDWIN;
                GameState.redWin();
            }
            else{
                state =  GameStateType.PLAYING;
            }
        }
        return state;
    }

    /**
     * Korongok szama, mivel nem toroljuk azt amelyiket eltavolitjuk, ezert ez a tabla meretet adja vissza.
     * @return {@code pieces.length} korongok szama.
     */
    public int getPieceCount() {
        return pieces.length;
    }


    /**
     * Teszteleshez van a szukseg, hogy beallitsuk a korongok erteket, valamilyen szinrol transparentre.
     * @param type (BLUE/RED/GREEN/TRANSPARENT).
     * @param position Position tipusu sor, oszlop koordinata.
     * @param pieceNumber pieces tomb indexe.
     */
    public void setPieces(PieceType type, Position position, int pieceNumber){
        pieces[pieceNumber] = new Piece(type,position);
    }

    /**
     * Egy piece tipusat adja vissza, ehhez a pieces tomb indexere van szuksege.
     * @param pieceNumber index.
     * @return PieceType -al tér vissza.
     */
    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }

    /**
     * Egy piece position-jet adja vissza,  ehhez a pieces tomb indexere van szuksege.
     * @param pieceNumber index.
     * @return Position -nel tér vissza.
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    /**
     * Position Property-t lehet lekerni egy piece-rol .
     * @param pieceNumber index.
     * @return positionProperty.
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    /**
     * Szabalyos e a lepes.
     * @param pieceNumber pieces tomb indexe.
     * @param direction irany amerre nezzuk, hogy szabalyos-e.
     * @return boolean.
     */
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

    /**
     * Position tipusu listaban visszaadja a position-oket.
     * @return Position lista.
     */
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

    /**
     * Visszaadja a szabalyos lepeseket egy halmazban.
     * @param pieceNumber index.
     * @return PawnDirection EnumSetet ad vissza.
     */
    public Set<PawnDirection> getValidMoves(int pieceNumber) {
        EnumSet<PawnDirection> validMoves = EnumSet.noneOf(PawnDirection.class);
        for (var direction : PawnDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * Atallitja a az aktualis lepes PieceType -at TRANSPARENTRE.
     * @param pieceNumber index.
     */
    public void remove(int pieceNumber) {
        pieces[pieceNumber] = new Piece(PieceType.TRANSPARENT,pieces[pieceNumber].getPosition());
        Logger.debug(pieceNumber);
        Logger.debug(pieces[pieceNumber].getType());
        utolsoPozicioSzam = pieceNumber;
    }

    /**
     * A pozicio a tablan belul van e.
     * @param position pozicio.
     * @return true / false.
     */
    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_SIZE
                && 0 <= position.col() && position.col() < BOARD_SIZE;
    }

    /**
     * pieces ben megkeresi melyiknek a Position-je egyezik meg a parameterrel.
     * @param position amelyik piecest keressuk.
     * @return pieces index.
     */
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /**
     * Egy stringge osszefuzzuk a pieceket.
     * @return string.
     */
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }
}
