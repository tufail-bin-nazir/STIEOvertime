package com.overtime.stie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import com.overtime.stie.connectionhelper.ConnectionHelper;
import com.overtime.stie.dto.Asset;
import com.overtime.stie.dto.User;

public class AccessabilityDao {
	
	private ResourceBundle bundle = null;
	
	
	
	public AccessabilityDao() {
		super();
		bundle = ResourceBundle.getBundle("com.overtime.stie.resources.utlities");
	}



	public boolean isUserValid(String nric, String password) throws Exception{
		
		System.out.println("===Entering isUserValid method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		ResultSet rstl = null;
		boolean userExists = false;
		try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("validateUser");
			stment = conn.prepareStatement(sql);
			stment.setString(1, nric.trim());
			stment.setString(2, password.trim());
			rstl = stment.executeQuery();
			if(rstl.next()) {
				userExists = true;
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		finally {
			ConnectionHelper.closeDatabase(conn, stment, rstl);
		}
		System.out.println("===Ending isUserValid method=====");

		return userExists;
		
		
	}
	
	public String findRole(String nric) {
		System.out.println("===Entering findRole method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		ResultSet rstl = null;
		String role = "";
		try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("retrieveRole");
			stment = conn.prepareStatement(sql);
			stment.setString(1, nric.trim());
			
			rstl = stment.executeQuery();
			if(rstl.next()) {
				role = rstl.getString("ROLE");
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		finally {
			ConnectionHelper.closeDatabase(conn, stment, rstl);
		}
		System.out.println("===Ending isUserValid method=====");

		return role;
		
		
		
	}
	
	public User findUserByNRIC(String nric) {
		
		System.out.println("===Entering findUserByNRIC method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		ResultSet rstl = null;
        User user =null;
        try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("retrieveUser");
			stment = conn.prepareStatement(sql);
			stment.setString(1, nric.trim());
			
			rstl = stment.executeQuery();
			if(rstl.next()) {
				user = new User();
				user.setEmail(rstl.getString("EMAIL"));
				user.setFin(rstl.getString("FIN"));
				user.setRole(rstl.getString("ROLE"));
				user.setPassword(rstl.getString("PASSWORD"));
				user.setOtp(rstl.getString("OTP"));
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
        finally {
			ConnectionHelper.closeDatabase(conn, stment, rstl);
		}
		System.out.println("===Ending findUserByNRIC method=====");

		return user;
		
		
		
	}



	public void updateUser(String nric, String otp) throws Exception {
		System.out.println("===Entering updateUser method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		
       
        try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("updateuser");
			stment = conn.prepareStatement(sql);
			stment.setString(1, otp.trim());
			stment.setString(2, nric.trim());
			
			int count = stment.executeUpdate();
			if(count >0) {
				conn.commit();
				System.out.println("updated sucessfully");
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
        
        finally {
			ConnectionHelper.closeDatabase(conn, stment, null);
		}
		System.out.println("===Ending updateUser method=====");

		
		
		
	}



	public void setpassword(String password, String nric) throws Exception {
		System.out.println("===Entering setpassword method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		
       
        try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("updatepassword");
			stment = conn.prepareStatement(sql);
			stment.setString(1,password);
			stment.setString(2, nric.trim());
			
			int count = stment.executeUpdate();
			if(count >0) {
				conn.commit();
				System.out.println("updated sucessfully");
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			ConnectionHelper.closeDatabase(conn, stment, null);
		}
		System.out.println("===Ending setpassword method=====");
		
	}
	
	public List<Asset> retrieveAssets(String assetname) throws Exception{
		
		System.out.println("===Entering retrieveAssets method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		ResultSet rst = null;
		List<Asset> assetList = new ArrayList<Asset>();
		
       
        try {
			conn = conhelper.initConnection();
			String sql = "select * from ";
			
			if(assetname.equalsIgnoreCase("department")) {
				sql = sql+"department";
			}else if(assetname.equalsIgnoreCase("technician")) {
				sql = sql+"technician";
			}
			stment = conn.prepareStatement(sql);
			
			rst = stment.executeQuery();
			while(rst.next()) {
				Asset ast = new Asset();
				ast.setId(rst.getInt("ID"));
				ast.setName(rst.getString("NAME"));
				assetList.add(ast);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			ConnectionHelper.closeDatabase(conn, stment, null);
		}
        
		System.out.println("===Ending retrieveAssets method=====");

        return assetList;
	}



	public List<String> retrieveManagers() throws Exception {
		System.out.println("===Entering retrieveManagers method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		ResultSet rst = null;
		List<String> managerNRICList = new ArrayList<String>();
		
       
        try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("retrievemanagers");
			stment = conn.prepareStatement(sql);
			stment.setString(1, "MANAGER");
			
			rst = stment.executeQuery();
			while(rst.next()) {
				String fin = rst.getString("FIN");
				String name = rst.getString("NAME");
				managerNRICList.add(fin+" ("+name+")");
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			ConnectionHelper.closeDatabase(conn, stment, null);
		}
        
		System.out.println("===Ending retrieveManagers method=====");

        return managerNRICList;
	}

}
