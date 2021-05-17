package boardgame.player;

import boardgame.jdbi.PlayerSet;
import boardgame.model.BoardGameModel;
import java.util.Random;

public class PlayerState {
    /**
     * kovetkezo jatekos
     */
    private static Player nextPlayer;
    /**
     * kek jatekos peldanyositasa.
     */
    private static PlayerSet playerNameBlue =new PlayerSet();
    /**
     * piros jatekos peldanyositasa.
     */
    private static PlayerSet playerNameRed=new PlayerSet();

    /**
     * jatekos nevenek elmentese, beallitasa.
     * @param playerType melyik szinu jatekos, blue/red.
     * @param name jatekos neve.
     */
    public static void setPlayername(Player playerType, String name){
        switch (playerType){
            case PLAYERBLUE:
            {
                playerNameBlue.setName(name);
                break;
            }
            case PLAYERRED:
            {
                playerNameRed.setName(name);
                break;
            }
        }
    }

    /**
     * piros jatekos nevenek a getter metodusa.
     * @return piros jatekos neve.
     */
    public static String getRedPlayerName(){
        return playerNameRed.getName();
    }

    /**
     * kek jatekos nevenek a getter metodusa.
     * @return kek jatekos neve.
     */
    public static String getBluePlayerName(){
        return playerNameBlue.getName();
    }

    /**
     * jatekot kezdo jatekos random beallitasa, igy beallitjuk a nextPlayert.
     */
    public static void setStartingPlayer(){
        int szam=0;
        if(BoardGameModel.elsoLepes)
        {
            Random rand = new Random();
            int n = rand.nextInt(2);
            n = n + 1;
            szam = n;
        }
        if(szam == 1) {
            nextPlayer = Player.PLAYERBLUE;
        }
        else {
            nextPlayer = Player.PLAYERRED;
        }
    }

    /**
     * kovetkezo jatekost beallito metodus.
     * ha a kek az aktualis jatekos a kovetkezo a piros lesz.
     * ha a piros az aktualis jatekos a kovetkezo a kek lesz.
     */
    public static void setNextPlayer() {
        if (nextPlayer.equals(Player.PLAYERBLUE)){
            nextPlayer = Player.PLAYERRED;
        }
        else {
            nextPlayer = Player.PLAYERBLUE;
        }
    }

    /**
     * kovetkezo jatekos getter metodusa
     * @return Player tipuso nexPlayer -rel ter vissza.
     */
    public static Player getNextPlayer(){
        return nextPlayer;
    }
}
