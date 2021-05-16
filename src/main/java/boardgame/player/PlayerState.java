package boardgame.player;

import boardgame.jdbi.PlayerSet;
import boardgame.model.BoardGameModel;
import java.util.Random;

public class PlayerState {
    private static Player nextPlayer;

    private static PlayerSet playerNameBlue =new PlayerSet();
    private static PlayerSet playerNameRed=new PlayerSet();


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
    public static String getRedPlayerName(){
        return playerNameRed.getName();
    }
    public static String getBluePlayerName(){
        return playerNameBlue.getName();
    }

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

    public static void setNextPlayer() {
        if (nextPlayer.equals(Player.PLAYERBLUE)){
            nextPlayer = Player.PLAYERRED;
        }
        else {
            nextPlayer = Player.PLAYERBLUE;
        }
    }

    public static Player getNextPlayer(){
        return nextPlayer;
    }
}
