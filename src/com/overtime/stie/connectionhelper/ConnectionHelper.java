package com.overtime.stie.connectionhelper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;




public class ConnectionHelper implements Serializable{
	
	
    private static final long serialVersionUID = 9204275723046653468L;
    private String db_server   = "";
    private String db_user     = "";
    private String db_password = "";
    private String db_driver   = "";
    private ResourceBundle bundle = null;

    public Connection connection = null;
    
    

   

  
    public ConnectionHelper() {
    	super();
    	bundle = ResourceBundle.getBundle("com.overtime.stie.resources.utlities");
    	db_server = bundle.getString("DB-SERVER");
    	db_driver = bundle.getString("JDBC-DRIVER");
    	db_user = bundle.getString("DB-USER");
    	db_password = bundle.getString("DB-PASSWORD");
    	
	}

	public Connection initConnection() throws Exception{
    	
		Class.forName(db_driver);

        if( this.connection == null ){
            this.connection = DriverManager.getConnection(db_server, db_user, db_password);
            this.connection.setAutoCommit(false);
        }else if( this.connection.isClosed() ){
            this.connection = null;
            this.connection = DriverManager.getConnection(db_server, db_user, db_password);
            this.connection.setAutoCommit(false);
        }
        return this.connection;
    }

    public void closeConnection(){
        try {
            if( this.connection != null ){
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commitConnection(){
        try {
            if( this.connection != null && !this.connection.isClosed() ){
                this.connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollbackConnection(){
        try {
            if( this.connection != null && !this.connection.isClosed() ){
                this.connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeDatabase(Connection conn, PreparedStatement stment, ResultSet rstl) {
    	if(conn!=null) {
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	if(stment!=null) {
    		try {
				stment.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	if(rstl != null) {
    		
    		try {
				rstl.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

	
}