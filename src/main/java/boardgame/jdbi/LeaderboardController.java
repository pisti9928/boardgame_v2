package boardgame.jdbi;

import org.checkerframework.checker.units.qual.A;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;

/**
 * Az adatbazis kezelesehez szukseges metodusokat tartalmazo osztaly.
 */
public class LeaderboardController {
    /**
     * jdbi peldanyositva.
     */
    private static Jdbi jdbi;

    /**
     * LeaderboardController konstruktora.
     * Letrehozza a mappat, fajlt ha nem lezezik.
     */
    public LeaderboardController() {
        this.jdbi = Jdbi.create("jdbc:h2:file:~/.boardgame-2_16/leaderboard","as","");
        this.jdbi.installPlugin(new SqlObjectPlugin());
        this.jdbi.installPlugin(new H2DatabasePlugin());

    }

    /**
     * Ha letezik a tabla akkor Exception-t elkap es logol.
     */
    public static void createTable(){
        try{
            jdbi.withExtension(BoardSetDao.class, dao -> {dao.createTable(); return true;});
        }
        catch (Exception e) {
            Logger.debug("A tábla már létezik");
        }
    }

    /**
     * Beszurja a tablaba nev alapjan a gyoztest.
     * @param playerName jatekos neve.
     */
    public static void insertWinner(String playerName){
        boolean playerExists = jdbi.withExtension(BoardSetDao.class, dao -> dao.playerExists(playerName));
        if (playerExists){
            int winsCount = jdbi.withExtension(BoardSetDao.class, dao -> dao.numberOfWins(playerName));
            jdbi.withExtension(BoardSetDao.class, dao ->{
                dao.updateWins(winsCount+1,playerName);
                return true;
            });
        }
        else {
            jdbi.withExtension(BoardSetDao.class, dao ->{
               dao.insertWinnerPlayer(playerName);
               return true;
            });
        }
    }

    /**
     * Beszurja a tablaba nev alapjan a vesztest.
     * @param playerName jatekos neve.
     */
    public static void insertLoser(String playerName){
        boolean playerExists = jdbi.withExtension(BoardSetDao.class, dao -> dao.playerExists(playerName));
        if (playerExists){
            int losesCount = jdbi.withExtension(BoardSetDao.class, dao -> dao.numberOfLoses(playerName));
            jdbi.withExtension(BoardSetDao.class, dao ->{
                dao.updateLoses(losesCount+1,playerName);
                return true;
            });
        }
        else {
            jdbi.withExtension(BoardSetDao.class, dao ->{
                dao.insertLoserPlayer(playerName);
                return true;
            });
        }
    }

    /**
     * Dontetlen eseten beszurja a tablaba a jatekost.
     * @param playerName jatekos neve.
     */
    public static void insertDrawer(String playerName){
        boolean playerExists = jdbi.withExtension(BoardSetDao.class, dao -> dao.playerExists(playerName));
        if (playerExists){
            int drawsCount = jdbi.withExtension(BoardSetDao.class, dao -> dao.numberOfDraws(playerName));
            jdbi.withExtension(BoardSetDao.class, dao ->{
                dao.updateDraws(drawsCount+1,playerName);
                return true;
            });
        }
        else {
            jdbi.withExtension(BoardSetDao.class, dao ->{
                dao.insertDrawerPlayer(playerName);
                return true;
            });
        }
    }

    /**
     * Ez a fuggveny  visszaadja az elso 10 jatekost, ha kevesebb jatekos van akkor kevesebbet.
     * @return PlayerSet tipusu lista.
     */
    public static ArrayList<PlayerSet> getFirstTenRecord(){
        if (jdbi == null){
            ArrayList<PlayerSet> topTen = new ArrayList<PlayerSet>();
            Logger.debug("nincs jdbi");
            return topTen;
        }
        else {
            String[] playerNames = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenName());
            int[] winnerScores = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenWinCount());
            int[] losesScores = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenLosesCount());
            int[] drawsScores = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenDrawsCount());
            int topNameCount = 10;
            ArrayList<PlayerSet> topTen = new ArrayList<PlayerSet>();
            if (playerNames.length < 10) {
                topNameCount = playerNames.length;
            }
            for (int i = 0; i < topNameCount; i++) {
                topTen.add(new PlayerSet(playerNames[i], winnerScores[i], losesScores[i], drawsScores[i]));
            }
            return topTen;
        }
    }

    /**
     * Az adatbazisbol kitorli a rekordokat.
     * Ez a tabla eldobasaval tortenik.
     */
    public static void deleteLeaderboard(){
        jdbi.withExtension(BoardSetDao.class, dao -> {dao.deleteLeaderboard();return true;});
        Logger.debug("Az adatbázis tartalma törölve");
    }

}
