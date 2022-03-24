package SQL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import Main.Main;

/**
 * Class for creating tables and creating the database connection
 * 
 * @author ResurrectAjax
 * */
public class MysqlMain extends Database{
	
	private Main plugin = Main.getPlugin(Main.class);
	
	private String dbname;
	/**
	 * Constructor<br>
	 * @param instance instance of the {@link Main.Main} class
	 * */
    public MysqlMain(Main instance){
        super(instance);
        dbname = plugin.getConfig().getString("SQLite.MultiChat", "MultiChat"); // Set the database name here e.g player_kills
    }
	
    /* EXAMPLES
    private String SQLiteCreateSpawnZonesTable = "CREATE TABLE IF NOT EXISTS SpawnZones (" + // make sure to put your table name in here too.
    		"`uuid` varchar(32)," +
    		"`pos1` varchar(6) NOT NULL," + // This creates the different colums you will save data too. varchar(32) Is a string, int = integer
            "`pos2` varchar(6) NOT NULL," +
            "`spawnpos` varchar(6) NOT NULL," +
            "`world` varchar(32) NOT NULL" +
            ");"; // we can search by player, and get kills and total. If you some how were searching kills it would provide total and player.
    
    private String SQLiteCreateStructuresTable = "CREATE TABLE IF NOT EXISTS Structures (" + // make sure to put your table name in here too.
    		"`name` varchar(32)," +
    		"`pos1` varchar(6) NOT NULL," + // This creates the different colums you will save data too. varchar(32) Is a string, int = integer
            "`pos2` varchar(6) NOT NULL" +
            ");"; // we can search by player, and get kills and total. If you some how were searching kills it would provide total and player.
    
    private String SQLiteCreateIslandsTable = "CREATE TABLE IF NOT EXISTS Islands (" + // make sure to put your table name in here too.
    		"`uuid` varchar(32)," +
    		"`time` bigint(20) NOT NULL," +
    		"`raidsense` float(24) NOT NULL" + // This creates the different colums you will save data too. varchar(32) Is a string, int = integer
            ");"; // we can search by player, and get kills and total. If you some how were searching kills it would provide total and player.
    
    private String SQLiteCreateRaidsTable = 
    		"create table if not exists Raids(" + 
			"raidID INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		"partyID int not null, " +
			"dateTimeStart datetime not null, " +
			"islandUUID varchar(32) not null " +
			");";
    
    private String SQLiteCreatePartiesTable = 
    		"create table if not exists Parties(" + 
			"partyID int not null, " +
			"playerUUID varchar(32) not null, " +
			"leaderUUID varchar(32) not null, " +
			"primary key(partyID, playerUUID)" +
			");";
    
    private String SQLiteCreateBlocksTable = 
    		"create table if not exists Blocks("
    		+ "blockID INTEGER PRIMARY KEY AUTOINCREMENT, "
    		+ "raidID int not null, "
    		+ "type varchar(32) not null, "
    		+ "amount int not null, "
    		+ "isContainer boolean not null check(isContainer in (0, 1)), "
    		+ "foreign key(raidID) references Raids(raidID) on delete cascade"
    		+ ");";
	*/
    
    private String SQLiteCreateItemsTable = 
    		"create table if not exists Users("
    		+ "UUID varchar(32) PRIMARY KEY, "
    		+ "channel varchar(32) not null,"
    		+ "profanityFilter boolean not null"
    		+ ");";
    
    
	@Override
	public Connection getSQLConnection() {
		File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
	}
	@Override
	public void load() {
		connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateItemsTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
		
}
