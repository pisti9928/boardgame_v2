package boardgame.model;

/**
 * Enum osztaly {@code PawnDirection} ennek az interfesze a Direction.
 * 8 szomszedos irany.
 */
public enum PawnDirection implements Direction {

    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    RIGHT(0, 1),
    DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DOWN_LEFT(1, -1),
    LEFT(0, -1);

    /**
     * {@code rowChange} sorvaltozas erteke.
     */
    private final int rowChange;
    /**
     * {@code volChange} oszlopvaltozas erteke.
     */
    private final int colChange;


    PawnDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * privat rowChange gettere.
     * @return {@code rowChange}.
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * privat colChange gettere.
     * @return {@code colChange}.
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * iranyt rendel a koordinatahoz.
     * @param rowChange sor eltolas erteke.
     * @param colChange oszlop eltolas erteke.
     * @return {@code direction}  rowChange, colChange paros.
     * @throws IllegalArgumentException ha nincs olyan pozicio amelyhez iranyt lehet rendelni.
     */
    public static PawnDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
