// CHECKSTYLE:OFF
package boardgame.jdbi;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * H2-t definial az interfesz az SQL adatbazishoz.
 */
@RegisterBeanMapper(PlayerSet.class)
public interface BoardSetDao {

    /**
     * Tabla letrehozasa
     */
    @SqlUpdate("CREATE TABLE leaderboard (name VARCHAR PRIMARY KEY, wins INTEGER NOT NULL, loses INTEGER NOT NULL, draws INTEGER NOT NULL ) " )
    void createTable();

    /**
     * gyoztes jatekos beszurasa a tablaba.
     * @param name jatekos neve.
     */
    @SqlUpdate("INSERT INTO leaderboard VALUES (:name, 1, 0,0)")
    void insertWinnerPlayer(@Bind("name") String  name);


    /**
     * vesztes jatekos beszurasa a tablaba.
     * @param name jatekos neve.
     */
    @SqlUpdate("INSERT INTO leaderboard VALUES (:name, 0, 1,0)")
    void insertLoserPlayer(@Bind("name") String  name);

    /**
     * Dontetlen eseten jatekos beszureasa a tablaba.
     * @param name jatekos neve.
     */
    @SqlUpdate("INSERT INTO leaderboard VALUES (:name, 0, 0,1)")
    void insertDrawerPlayer(@Bind("name") String  name);

    /**
     * Ha mar letezik a jatekos es nyer akkor frissiti az eredmenyeit.
     * @param wins uj gyozelmek szama.
     * @param name jatekos neve.
     */
    @SqlUpdate("UPDATE leaderboard SET wins = :wins WHERE name IN (:name)")
    void updateWins(@Bind("wins") int wins, @Bind("name") String name);

    /**
     * Ha mar letezik a jatekos es veszit akkor frissiti az eredmenyeit.
     * @param loses uj vesztett jatszma szama
     * @param name jatekos neve
     */
    @SqlUpdate("UPDATE leaderboard SET loses = :loses WHERE name IN (:name)")
    void updateLoses(@Bind("loses") int loses, @Bind("name") String name);

    /**
     * Ha mar letezik a jatekos es dontetlen a merkozes, akkor frissiti az eredmenyeit.
     * @param draws uj dontetlen jatszma szama.
     * @param name jatekos neve.
     */
    @SqlUpdate("UPDATE leaderboard SET draws = :draws where name IN (:name)")
    void updateDraws(@Bind("draws") int draws, @Bind("name") String name);

    /**
     * nyert jatszmak szama.
     * @param name jatekos neve.
     * @return nyert jatszmak szamaval ter vissza.
     */
    @SqlQuery ("SELECT wins FROM leaderboard WHERE name IN (:name)")
    int numberOfWins(@Bind("name") String name);

    /**
     * vesztett jatszmak szama.
     * @param name jatekos neve.
     * @return vesztett jatszmak szamaval ter vissza.
     */
    @SqlQuery ("SELECT loses FROM leaderboard WHERE name IN(:name)")
    int numberOfLoses(@Bind("name") String name);

    /**
     * dontetlen jatszmak szama.
     * @param name jatekos neve.
     * @return dontetlen jatszmak szamaval ter vissza.
     */
    @SqlQuery("SELECT draws FROM leaderboard WHERE name IN(:name)")
    int numberOfDraws(@Bind("name") String name);

    /**
     * jatekosok nevevel ter vissza eredmeny szerint rendezve.
     * @return jatekos nevekkel ter vissza tombkent.
     */
    @SqlQuery("SELECT name FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    String[] getFirstTenName();

    /**
     * gyozelmek szamaval ter vissza eredmeny szerint rendezve.
     * @return gyozelmek szama tombkent.
     */
    @SqlQuery("SELECT wins FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    int[] getFirstTenWinCount();

    /**
     * veresegek szamaval ter vissza eredmeny szerint rendezve.
     * @return veresegek szama tombkent.
     */
    @SqlQuery("SELECT loses FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    int[] getFirstTenLosesCount();

    /**
     * dontetlenek szamaval ter vissza eredmeny szerint rendezve.
     * @return dontetlenek szama tombkent.
     */
    @SqlQuery("SELECT draws FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    int[] getFirstTenDrawsCount();


    /**
     * letezik e az adott jatekos.
     * @param name jatekos neve.
     * @return boolean, igaz vagy hamis.
     */
    @SqlQuery("SELECT EXISTS (SELECT * FROM leaderboard WHERE name IN(:name))")
    boolean playerExists(@Bind("name")String name);

    /**
     * tabla kiuritese, torlese.
     */
    @SqlUpdate("DROP TABLE leaderboard")
    void deleteLeaderboard();
}