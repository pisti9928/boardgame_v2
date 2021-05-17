package boardgame.model;

/**
 * Interfeszt biztosit a kovetkezo lepes mozgasahoz (hogy hova lehet lepni) .
 */
public interface Direction {

    int getRowChange();
    int getColChange();

}
