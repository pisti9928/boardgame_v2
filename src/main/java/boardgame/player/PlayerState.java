package boardgame.player;

public class PlayerState {
    private static Player nextPlayer;

    public static void setStartingPlayer(int szam){
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
