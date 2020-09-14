package com.overtime.stie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.overtime.stie.connectionhelper.ConnectionHelper;
import com.overtime.stie.dto.OverTimeRemarks;
import com.overtime.stie.dto.Overtime;
import com.overtime.stie.utilities.OvertimeUtilities;

public class OverTimeDao {
	
	private ResourceBundle bundle = null;
	
	private AccessabilityDao userdao = null;

	public OverTimeDao() {
		super();
		userdao = new AccessabilityDao();
		bundle = ResourceBundle.getBundle("com.overtime.stie.resources.utlities");
	}
	
	
public boolean save(Overtime ot) throws Exception{
		
		System.out.println("===Entering save OT method=====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		boolean saveSucessfull = false;
		try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("saveot");
			System.out.println("sql is: "+ sql);
			stment = conn.prepareStatement(sql);
			stment.setString(1, OvertimeUtilities.cleanString(ot.getTechnicianName()));
			stment.setString(2, OvertimeUtilities.cleanString(ot.getDepartment()));
			stment.setString(3, OvertimeUtilities.cleanString(ot.getStartDate()));
			stment.setString(4, OvertimeUtilities.cleanString(ot.getStartTime()));
			stment.setString(5, OvertimeUtilities.cleanString(ot.getEndDate()));
			stment.setString(6, OvertimeUtilities.cleanString(ot.getEndTime()));
			stment.setString(7, OvertimeUtilities.cleanString(ot.getPurpose()));
			stment.setString(8, OvertimeUtilities.cleanString(ot.getBreakDown()));
			stment.setString(9, OvertimeUtilities.cleanString(ot.getSite()));
			stment.setString(10, OvertimeUtilities.cleanString(ot.getWorkorderNumber()));
			stment.setString(11, OvertimeUtilities.cleanString(ot.getUserfrom()));
			stment.setString(12, OvertimeUtilities.cleanString(ot.getUserto()));
			stment.setString(13, OvertimeUtilities.cleanString(ot.getStatus()));
			stment.setString(14, OvertimeUtilities.cleanString(ot.getBreakdowntype()));
			java.util.Date date = new Date();

			stment.setTimestamp(15, new java.sql.Timestamp(date.getTime()));
			stment.setTimestamp(16, new java.sql.Timestamp(date.getTime()));

			int i = stment.executeUpdate();
			if(i == 1) {
				conn.commit();
				saveSucessfull = true;
				System.out.println("==OT created Sucessfully===");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally {
			ConnectionHelper.closeDatabase(conn, stment, null);
		}
		System.out.println("===Ending saveOT method=====");

		return saveSucessfull;
		
		
	}

public List<Overtime> retrieveOTList(String nric, String role) throws Exception{
	
	System.out.println("====retrieveOTList start=====");
	
	ConnectionHelper conhelper = new ConnectionHelper();
	Connection conn = null;
	PreparedStatement stment = null;
	ResultSet rstl = null;
	List<Overtime> overtimeList = new ArrayList<Overtime>();
	try {
		conn = conhelper.initConnection();
		String sql = bundle.getString("retrieveOTList");
		if(role.equalsIgnoreCase("ENGINEER")) {
			sql = sql + " where USR_FROM = ?";
		}else if(role.equalsIgnoreCase("MANAGER")) {
			sql = sql + " where USR_TO= ?";
		}
		sql = sql+" Order by TME_UPDATE DESC";
		stment = conn.prepareStatement(sql);
		stment.setString(1, OvertimeUtilities.cleanString(nric));
		
		rstl = stment.executeQuery();
		
		while(rstl.next()) {
			Overtime ot = new Overtime();
			ot.setId(rstl.getInt("ID"));
			ot.setTechnicianName(rstl.getString("TECHNICIAN_NAME"));
			ot.setStartDate(rstl.getString("START_DTE"));
			ot.setStartTime(rstl.getString("START_TME"));
			ot.setEndDate(rstl.getString("END_DTE"));
			ot.setEndTime(rstl.getString("END_TME"));
			ot.setNumStatus(rstl.getString("STATUS"));
			ot.setRemarks(findOverTime(String.valueOf(ot.getId())).getRemarks());
			
			if((rstl.getString("STATUS").equalsIgnoreCase("04"))) {
				String cancelledBy = ot.getRemarks().get(ot.getRemarks().size()-1).getUser();
				if(role.equalsIgnoreCase("ENGINEER")) {
					if(userdao.findRole(cancelledBy).equalsIgnoreCase("ENGINEER")) {
						 ot.setStatus("Cancelled");
					}else {
						 ot.setStatus("Cancelled By Manager");
					}
				}else if(role.equalsIgnoreCase("MANAGER")) {
					if(userdao.findRole(cancelledBy).equalsIgnoreCase("MANAGER")) {
						 ot.setStatus("Cancelled");
					}else {
						 ot.setStatus("Cancelled By Engineer");
					}
				}
			}
			else {
				if(role.equalsIgnoreCase("ENGINEER")) {
					  ot.setStatus(bundle.getString(rstl.getString("STATUS")+"-ENGINEER"));

				}
				else if(role.equalsIgnoreCase("MANAGER")) {
					  ot.setStatus(bundle.getString(rstl.getString("STATUS")+"-MANAGER"));

				}
			}
			
		    overtimeList.add(ot);
		}
	}catch (Exception e) {
		e.printStackTrace();
		throw new Exception(e.getMessage());
	}finally {
		ConnectionHelper.closeDatabase(conn, stment, rstl);
	}
	
    System.out.println("=====retrieveOTList end====");
	return overtimeList;
	
}


public Overtime findOverTime(String id) throws Exception {
	
	System.out.println("====starting findOverTime method======");
	

	ConnectionHelper conhelper = new ConnectionHelper();
	Connection conn = null;
	PreparedStatement stment = null;
	ResultSet rstl = null;
	Overtime ot = new Overtime();
	List<OverTimeRemarks> remarksList = new ArrayList<OverTimeRemarks>();
	
	try {
		conn = conhelper.initConnection();
        String sql = bundle.getString("retrieveOTByid");
        
       stment= conn.prepareStatement(sql);
       stment.setString(1, id);
       
       rstl = stment.executeQuery();
       
       if(rstl.next()) {
    	   ot.setId(rstl.getInt("ID"));
    	   ot.setTechnicianName(OvertimeUtilities.cleanString(rstl.getString("TECHNICIAN_NAME")));
    	   ot.setDepartment(OvertimeUtilities.cleanString(rstl.getString("DEPARTMENT")));
    	   ot.setStartDate(OvertimeUtilities.cleanString(rstl.getString("START_DTE")));
    	   ot.setStartTime(OvertimeUtilities.cleanString(rstl.getString("START_TME")));
    	   ot.setEndDate(OvertimeUtilities.cleanString(rstl.getString("END_DTE")));
    	   ot.setEndTime(OvertimeUtilities.cleanString(rstl.getString("END_TME")));
    	   ot.setPurpose(OvertimeUtilities.cleanString(rstl.getString("PURPOSE")));
    	   ot.setBreakDown(OvertimeUtilities.cleanString(rstl.getString("BREAKDOWN")));
    	   ot.setSite(OvertimeUtilities.cleanString(rstl.getString("SITE")));
    	   ot.setWorkorderNumber(OvertimeUtilities.cleanString(rstl.getString("WORKS_ORDER")));
    	   ot.setStatus(OvertimeUtilities.cleanString(rstl.getString("STATUS")));
    	   ot.setUserfrom(OvertimeUtilities.cleanString(rstl.getString("USR_FROM")));
    	   ot.setUserto(OvertimeUtilities.cleanString(rstl.getString("USR_TO")));
    	   ot.setBreakdowntype(OvertimeUtilities.cleanString(rstl.getString("BREAKDOWN_TYPE")));
    	   
       }
       
       stment.clearParameters();
       stment.close();
       rstl.close();
       String retrieveRemarksById = bundle.getString("retrieveRemarksById");
       stment  = conn.prepareStatement(retrieveRemarksById);
       stment.setString(1, id);
       
       rstl = stment.executeQuery();
  
       while(rstl.next()) {
    	   OverTimeRemarks remarks = new OverTimeRemarks();
    	   remarks.setRemarks(rstl.getString("TEXT"));
    	   remarks.setUser(rstl.getString("USER"));
    	   remarksList.add(remarks);
       }
       
       ot.setRemarks(remarksList);
       

       
       
   
	}catch (Exception e) {
		throw new Exception(e.getMessage());
	}finally {
		ConnectionHelper.closeDatabase(conn, stment, rstl);
	}
	
	System.out.println("====ending findOverTime method======");

	return ot;
}


public boolean update(String id, String flag) throws Exception {
	System.out.println("====starting update method======");
	

	ConnectionHelper conhelper = new ConnectionHelper();
	Connection conn = null;
	PreparedStatement stment = null;
	boolean isUpdateSucess = false;
	try {
		conn = conhelper.initConnection();
        String sql = bundle.getString("updateOT");
        
       stment= conn.prepareStatement(sql);
       
       if(flag.equalsIgnoreCase("accept") || flag.equalsIgnoreCase("approve")  ) {
           stment.setString(1, "01"); //Accepted by Manager

       }
       else if(flag.equalsIgnoreCase("reject")) {
      	 stment.setString(1, "02"); //Rejected by Manager 
       }else if(flag.equalsIgnoreCase("remarkByEngineer")){
           stment.setString(1, "03"); //Rejected by Manager 

       }else if(flag.equalsIgnoreCase("cancel")) {
    	   
           stment.setString(1, "04"); //Cancelled

       }else if(flag.equalsIgnoreCase("pendingcancel")) {
    	   stment.setString(1, "05"); //Pending Cancellation
       }
       
       java.util.Date date = new Date();

	   stment.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
       stment.setString(3, id);
       
       int count = stment.executeUpdate();
       
       if(count ==1) {
    	   conn.commit();
    	   isUpdateSucess = true;
    	   System.out.println("record updated sucessfully");
       }
	}catch(Exception ex) {
		throw new Exception(ex.getMessage());
		
	}finally {
		ConnectionHelper.closeDatabase(conn, stment, null);
	}
	
	System.out.println("====ending update method======");

	return isUpdateSucess;

	
}


   public boolean insertRemarks(Overtime overtime, String role) throws Exception {
	   
	   System.out.println("===start insertRemarks =====");
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		boolean isInserted = false;
		
		try {
			conn = conhelper.initConnection();
	        String sql = bundle.getString("insertremarks");
	        
	        stment= conn.prepareStatement(sql);
	        stment.setInt(1, overtime.getId());
	        stment.setString(2, overtime.getRemarks().get(overtime.getRemarks().size()-1).getRemarks());
	        
	        if(role.equalsIgnoreCase("MANAGER")) {
	            stment.setString(3, overtime.getUserto());

	        }else if(role.equalsIgnoreCase("ENGINEER")) {
	        	 stment.setString(3, overtime.getUserfrom());
	        }

	       
	        int i = stment.executeUpdate();
	        
	        if(i==1) {
	        	conn.commit();
	        	isInserted = true;
	        	System.out.println("==remarks inserted successfully==");
	        }
		}catch(Exception ex) {
			
			throw new Exception(ex.getMessage());
		}finally {
			ConnectionHelper.closeDatabase(conn, stment, null);
		}
		
		System.out.println("===end insertRemarks =====");

		return isInserted;
	   
   }
   
   
	
public boolean updateOT(Overtime ot) throws Exception{
		
		System.out.println("===Entering update OT method=====");
		
		
		ConnectionHelper conhelper = new ConnectionHelper();
		Connection conn = null;
		PreparedStatement stment = null;
		boolean updateSucessfull = false;
		try {
			conn = conhelper.initConnection();
			String sql = bundle.getString("updateot");
			System.out.println("sql is: "+ sql);
			stment = conn.prepareStatement(sql);
			stment.setString(1, OvertimeUtilities.cleanString(ot.getTechnicianName()));
			stment.setString(2, OvertimeUtilities.cleanString(ot.getDepartment()));
			stment.setString(3, OvertimeUtilities.cleanString(ot.getStartDate()));
			stment.setString(4, OvertimeUtilities.cleanString(ot.getStartTime()));
			stment.setString(5, OvertimeUtilities.cleanString(ot.getEndDate()));
			stment.setString(6, OvertimeUtilities.cleanString(ot.getEndTime()));
			stment.setString(7, OvertimeUtilities.cleanString(ot.getPurpose()));
			stment.setString(8, OvertimeUtilities.cleanString(ot.getBreakDown()));
			stment.setString(9, OvertimeUtilities.cleanString(ot.getSite()));
			stment.setString(10, OvertimeUtilities.cleanString(ot.getWorkorderNumber()));
			stment.setString(11, OvertimeUtilities.cleanString(ot.getUserfrom()));
			stment.setString(12, OvertimeUtilities.cleanString(ot.getUserto()));
			stment.setString(13, OvertimeUtilities.cleanString(ot.getStatus()));
			stment.setString(14, OvertimeUtilities.cleanString(ot.getBreakdowntype()));
			java.util.Date date = new Date();

			stment.setTimestamp(15, new java.sql.Timestamp(date.getTime()));
			stment.setInt(16,  ot.getId());
			int i = stment.executeUpdate();
			
			System.out.println("value of i" + i);
			if(i == 1) {
				conn.commit();
				updateSucessfull = true;
				System.out.println("==OT updated Sucessfully===");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally {
			ConnectionHelper.closeDatabase(conn, stment, null);
		}
		System.out.println("===Ending updateOT method=====");

		return updateSucessfull;
		
		
	}
	
public List<Overtime> searchOvertime(Overtime ot, String role) throws Exception{
	System.out.println("===Entering searchOvertime  method=====");
	
	ConnectionHelper conhelper = new ConnectionHelper();
	Connection conn = null;
	PreparedStatement stment = null;
	ResultSet rstl = null;
	
	List<Overtime> overtimeList = new ArrayList<Overtime>();
	try {
		conn = conhelper.initConnection();
        String sql = "SELECT * FROM overtime WHERE ";
        
        boolean isTechnicianset = false;
        boolean isDepartmentset = false;
        boolean isSiteset=  false;
        
        int parametercount = 0;
        
        if(ot.getTechnicianName()!= null && !ot.getTechnicianName().isEmpty()) {
        	parametercount++;
        	sql = sql + " TECHNICIAN_NAME=?";
        	isTechnicianset = true;
        }
        
      if(ot.getDepartment()!= null && !ot.getDepartment().isEmpty()) {
        	if(isTechnicianset) {
        		sql = sql + " AND ";
        	}
        	parametercount++;
        	sql = sql + " DEPARTMENT=?";
        	isDepartmentset = true;
        }
      
      if(ot.getSite()!= null && !ot.getSite().isEmpty()) {
    	  if(isDepartmentset) {
      		sql = sql + " AND ";
      	}
      	parametercount++;
      	sql = sql + " SITE=?";
      	isSiteset = true;
      }
        if(ot.getStartDate()!= null && !ot.getStartDate().isEmpty()) {
        	if(isSiteset || isDepartmentset || isTechnicianset) {
        		sql = sql + " AND ";
        	}
        	parametercount++;
        	sql = sql + " DATE(START_DTE)>=? AND";
        }
        
        if(ot.getEndDate()!= null && !ot.getEndDate().isEmpty()) {
        	sql = sql + " DATE(END_DTE)<=?";
        	parametercount++;
        }
        if(role.equalsIgnoreCase("HR")) {
        	ot.setUserto("");
        	sql = sql + " AND STATUS = '01'";
        }else {
            if(ot.getStatus()!= null && !ot.getStatus().isEmpty()) {

            	sql = sql + " AND STATUS = ?";
            	parametercount++;
            }
            
            sql = sql + " AND USR_TO= ?";
            parametercount++;


        }
        
        System.out.println("Search sql: " + sql);
        System.out.println("startDate: "+ ot.getStartDate() + "End date " + ot.getEndDate());
        System.out.println("paarmeter count: " + parametercount);
        stment = conn.prepareStatement(sql);
        for (int i = 1; i <= parametercount; i++) {
        	System.err.println("value of counter: " + i);
        	 if(ot.getTechnicianName()!= null && !ot.getTechnicianName().isEmpty()) { 
                 stment.setString(i, ot.getTechnicianName());
                 ot.setTechnicianName("");

        	 }
        	 else if(ot.getDepartment()!= null && !ot.getDepartment().isEmpty()) {
                 stment.setString(i, ot.getDepartment());
                 ot.setDepartment("");

        	 }
        	 else if(ot.getSite()!= null && !ot.getSite().isEmpty()) {
                 stment.setString(i, ot.getSite());
                 ot.setSite("");

        	 }
        	 else if(ot.getStartDate()!= null && !ot.getStartDate().isEmpty()) {
                 stment.setString(i, ot.getStartDate());
                 ot.setStartDate("");

             }
        	 else if(ot.getEndDate()!= null && !ot.getEndDate().isEmpty()) {
                 stment.setString(i, ot.getEndDate());
                 ot.setEndDate("");

             }
        	 else if(ot.getStatus()!= null && !ot.getStatus().isEmpty()) {
        		 stment.setString(i, ot.getStatus());
                 ot.setStatus("");
        	 }
        	 
        	 else if(ot.getUserto()!= null && !ot.getUserto().isEmpty()) {
        		 stment.setString(i, ot.getUserto());
                 ot.setUserto("");
        	 }

        	

		}
       
        rstl = stment.executeQuery();
        
      
        
        while(rstl.next()) {
           Overtime ovt = new Overtime();
		   ovt.setId(rstl.getInt("ID"));
           ovt.setTechnicianName(OvertimeUtilities.cleanString(rstl.getString("TECHNICIAN_NAME")));
           ovt.setDepartment(OvertimeUtilities.cleanString(rstl.getString("DEPARTMENT")));
           ovt.setStartDate(OvertimeUtilities.cleanString(rstl.getString("START_DTE")));
           ovt.setStartTime(OvertimeUtilities.cleanString(rstl.getString("START_TME")));
           ovt.setEndDate(OvertimeUtilities.cleanString(rstl.getString("END_DTE")));
           ovt.setEndTime(OvertimeUtilities.cleanString(rstl.getString("END_TME")));
           ovt.setPurpose(OvertimeUtilities.cleanString(rstl.getString("PURPOSE")));
           ovt.setBreakDown(OvertimeUtilities.cleanString(rstl.getString("BREAKDOWN")));
           ovt.setSite(OvertimeUtilities.cleanString(rstl.getString("SITE")));
           ovt.setWorkorderNumber(OvertimeUtilities.cleanString(rstl.getString("WORKS_ORDER")));
           ovt.setStatus(OvertimeUtilities.cleanString(rstl.getString("STATUS")));
		   ovt.setRemarks(findOverTime(String.valueOf(ovt.getId())).getRemarks());

           if(role.equalsIgnoreCase("HR")) {
        	   ovt.setStatus("Approved");  

           }else {
        	   if((rstl.getString("STATUS").equalsIgnoreCase("04"))) {
   				String cancelledBy = ovt.getRemarks().get(ovt.getRemarks().size()-1).getUser();
   				
   					if(userdao.findRole(cancelledBy).equalsIgnoreCase("MANAGER")) {
   						 ovt.setStatus("Cancelled");
   					}else {
   						 ovt.setStatus("Cancelled By Engineer");
   					}
   				
   			}
   			else {
   				  ovt.setStatus(bundle.getString(rstl.getString("STATUS")+"-MANAGER"));

   				
   			}
        	   
           }
          
           ovt.setUserfrom(OvertimeUtilities.cleanString(rstl.getString("USR_FROM")));
           ovt.setUserto(OvertimeUtilities.cleanString(rstl.getString("USR_TO")));
           ovt.setBreakdowntype(OvertimeUtilities.cleanString(rstl.getString("BREAKDOWN_TYPE")));
           overtimeList.add(ovt);
        	
        	
        }
        
        System.out.println("Result list: " + overtimeList.size());
		
	}catch (Exception e) {
		e.printStackTrace();
		throw new Exception(e.getLocalizedMessage());
	}finally {
		ConnectionHelper.closeDatabase(conn, stment, rstl);
	}

	System.out.println("===Ending searchOvertime  method=====");
	
	return overtimeList;

}
	

}
