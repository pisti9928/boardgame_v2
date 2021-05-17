// CHECKSTYLE:OFF
package boardgame.jdbi;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;






@RegisterBeanMapper(PlayerSet.class)
public interface BoardSetDao {

    @SqlUpdate("CREATE TABLE leaderboard (name VARCHAR PRIMARY KEY, wins INTEGER NOT NULL, loses INTEGER NOT NULL, draws INTEGER NOT NULL ) " )
    void createTable();

    @SqlUpdate("INSERT INTO leaderboard VALUES (:name, 1, 0,0)")
    void insertWinnerPlayer(@Bind("name") String  name);


    @SqlUpdate("INSERT INTO leaderboard VALUES (:name, 0, 1,0)")
    void insertLoserPlayer(@Bind("name") String  name);


    @SqlUpdate("INSERT INTO leaderboard VALUES (:name, 0, 0,1)")
    void insertDrawerPlayer(@Bind("name") String  name);


    @SqlUpdate("UPDATE leaderboard SET wins = :wins WHERE name IN (:name)")
    void updateWins(@Bind("wins") int wins, @Bind("name") String name);


    @SqlUpdate("UPDATE leaderboard SET loses = :loses WHERE name IN (:name)")
    void updateLoses(@Bind("loses") int loses, @Bind("name") String name);


    @SqlUpdate("UPDATE leaderboard SET draws = :draws where name IN (:name)")
    void updateDraws(@Bind("draws") int draws, @Bind("name") String name);


    @SqlQuery ("SELECT wins FROM leaderboard WHERE name IN (:name)")
    int numberOfWins(@Bind("name") String name);


    @SqlQuery ("SELECT loses FROM leaderboard WHERE name IN(:name)")
    int numberOfLoses(@Bind("name") String name);


    @SqlQuery("SELECT draws FROM leaderboard WHERE name IN(:name)")
    int numberOfDraws(@Bind("name") String name);


    @SqlQuery("SELECT name FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    String[] getFirstTenName();


    @SqlQuery("SELECT wins FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    int[] getFirstTenWinCount();


    @SqlQuery("SELECT loses FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    int[] getFirstTenLosesCount();


    @SqlQuery("SELECT draws FROM leaderboard ORDER BY wins DESC, draws DESC ,loses")
    int[] getFirstTenDrawsCount();



    @SqlQuery("SELECT EXISTS (SELECT * from leaderboard where name IN(:name))")
    boolean playerExists(@Bind("name")String name);

    @SqlUpdate("Drop table leaderboard")
    void deleteLeaderboard();
}