package boardgame.model;

public record Position(int row, int col) {
    /**
     * Adott poziciobol parameterkent megkapott irannyal eltolja a poziciot.
     * @param direction mozgatas iranya.
     * @return uj pozicioval ter vissza.
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    /**
     * oszlopot meg a sort (sor,oszlop) formaban adja vissza Sztringkent.
     * @return "(sor,oszlop)" .
     */
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}