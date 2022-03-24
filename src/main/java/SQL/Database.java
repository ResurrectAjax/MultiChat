package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import Main.Main;

/**
 * Class for handling the database
 * 
 * @author ResurrectAjax
 * */
public abstract class Database {
    private final Main plugin;
    Connection connection;
    /**
	 * Constructor<br>
	 * @param instance instance of the {@link Main.Main} class
	 * */
    public Database(Main instance){
        plugin = instance;
    }

    /**
     * Get the SQL connection
     * @return {@link Connection} to the database
     * */
    public abstract Connection getSQLConnection();

    /**
     * load database and execute table creation statements
     * */
    public abstract void load();

    /**
     * Create the connection with the database and check if the connection is stable
     * */
    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }
    
    /**
     * Load the users' current channels into {@link HashMap}
     * @return {@link HashMap} with the user's {@link UUID} as key and channel name as value
     * */
    public HashMap<UUID, String> getAllUserChannels() {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        HashMap<UUID, String> users = new HashMap<UUID, String>();
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT UUID, channel FROM Users;");
   
            rs = ps.executeQuery();
            while(rs.next()){
                users.put(UUID.fromString(rs.getString(1)), rs.getString(2));
            }
        	return users;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return users;
    }
    
    /**
     * Load the users' filter preferences into {@link HashMap}
     * @return {@link HashMap} with the user's {@link UUID} as key and filter preferences as value
     * */
    public HashMap<UUID, Boolean> getAllFilters() {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        HashMap<UUID, Boolean> users = new HashMap<UUID, Boolean>();
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT UUID, profanityFilter FROM Users;");
   
            rs = ps.executeQuery();
            while(rs.next()){
                users.put(UUID.fromString(rs.getString(1)), rs.getBoolean(2));
            }
        	return users;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return users;
    }
    
    /**
     * Insert a user into the database
     * @param uuid {@link UUID} of the user
     * @param channel channel
     * @param profanityFilter filter preference
     * */
    public void setUser(UUID uuid, String channel, boolean profanityFilter) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO Users (uuid,channel,profanityFilter) VALUES(?,?,?)");
            ps.setString(1, uuid.toString()); 
            
            
                                                                                               
            ps.setString(2, channel); 
            
            ps.setBoolean(3, profanityFilter);
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;      
    }
    
    /**
     * Update an existing user's data
     * @param uuid {@link UUID} of the user
     * @param channel channel
     * @param profanityFilter filter preference
     * */
    public void updateUser(UUID uuid, String channel, boolean profanityFilter) {
    	Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("UPDATE Users SET channel = ?, profanityFilter = ? WHERE uuid = ?");
                     
            ps.setString(1, channel); 
            
            ps.setBoolean(2, profanityFilter);
            
            ps.setString(3, uuid.toString()); 
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;  
    }
    
    /* DELETE EXAMPLE
    public String deleteValues(String table, String query, String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM " + table + " WHERE " + query + " = '"+string+"';");
   
            try {
                ps.executeUpdate();
            }
            catch(Exception e) {
            	
            }
            return "";
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return "";
    }
    */
    

    /**
     * Close database connection
     * @param ps {@link PreparedStatement}
     * @param rs {@link ResultSet}
     * */
    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Errors.close(plugin, ex);
        }
    }
}
