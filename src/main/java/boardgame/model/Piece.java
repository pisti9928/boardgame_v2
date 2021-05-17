package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Ez az osztaly informaciot tarol egy {@code Piece} -ről.
 * */
public class Piece {
    /**
     * Ez tárolja, hogy milyen {@code PieceType} -al rendelkezik a {@code Piece}.
     * */
    private final PieceType type;

    /**
     * Ez tárolja, hogy mi a {@code Position} -je a {@code Piece} -nak.
     * */
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     * Ez egy függveny amely visszadja a Piece -nek a tipusat.
     * @return {@code Piece} -nek a {@code PieceType} -at adja vissza.
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Ez egy függveny amely visszadja a Piece -nek a Position-jet.
     * @return {@code Piece} -nek a {@code Position}-jet adja vissza.
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * Adott objektumhoz tartozo poziciot adja vissza.
     * @return {@code Position}.
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * Ez a függveny osszefuzi a tipust meg a poziciot es String-kent adja vissza.
     * @return type + position.
     */
    public String toString() {
        return type.toString() + position.get().toString();
    }

}
