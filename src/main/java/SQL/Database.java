package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import Main.Main;


public abstract class Database {
    private final Main plugin;
    Connection connection;
    public int tokens = 0;
    public Database(Main instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM SpawnZones");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }
    
    /* SELECT EXAMPLE
    public List<List<String>> getStructures() {
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<List<String>> zones = new ArrayList<List<String>>();
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM Structures;");
   
            rs = ps.executeQuery();
            while(rs.next()){
                List<String> zone = new ArrayList<String>();
                
                zone.add(rs.getString("name"));
                zone.add(rs.getString("pos1"));
                zone.add(rs.getString("pos2"));
                
                zones.add(zone);
            }
        	return zones;
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
        return zones;
    }
    */
    
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

    /* INSERT EXAMPLE
    public void setSpawnZones(String uuid, String pos1, String pos2, String spawnpos, String world) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO SpawnZones (uuid,pos1,pos2,spawnpos,world) VALUES(?,?,?,?,?)");
            ps.setString(1, uuid); 
            
            
                                                                                               
            ps.setString(2, pos1); 
            
            
            
            ps.setString(3, pos2);

            
            
            ps.setString(4, spawnpos);
            
            
            
            ps.setString(5, world);
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
    */
    

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}
