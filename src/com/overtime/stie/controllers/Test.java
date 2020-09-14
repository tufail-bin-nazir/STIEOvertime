/*
 * package com.overtime.stie.controllers;
 * 
 * import java.io.FileInputStream; import java.io.FileNotFoundException; import
 * java.io.IOException; import java.io.InputStream; import java.nio.file.Path;
 * import java.nio.file.Paths; import java.security.MessageDigest; import
 * java.security.NoSuchAlgorithmException; import java.sql.Connection; import
 * java.sql.PreparedStatement; import java.sql.ResultSet; import
 * java.util.Iterator;
 * 
 * import org.apache.poi.hssf.usermodel.HSSFCell; import
 * org.apache.poi.hssf.usermodel.HSSFRow; import
 * org.apache.poi.hssf.usermodel.HSSFSheet; import
 * org.apache.poi.hssf.usermodel.HSSFWorkbook; import
 * org.apache.poi.xssf.usermodel.XSSFCell; import
 * org.apache.poi.xssf.usermodel.XSSFRow; import
 * org.apache.poi.xssf.usermodel.XSSFSheet; import
 * org.apache.poi.xssf.usermodel.XSSFWorkbook;
 * 
 * import com.overtime.stie.connectionhelper.ConnectionHelper;
 * 
 * 
 * 
 * public class Test {
 * 
 * public static void main(String[] main) throws IOException {
 * 
 * Path path = Paths.get("C:", "Users", "tufai", "Documents","email.xls");
 * 
 * InputStream ExcelFileToRead = new FileInputStream(path.toFile());
 * HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
 * 
 * 
 * HSSFSheet sheet = wb.getSheetAt(0);HSSFRow row; HSSFCell cell;
 * 
 * Iterator rows = sheet.rowIterator();
 * 
 * while (rows.hasNext()) { String nric = ""; String email = ""; String role="";
 * String name=""; row=(HSSFRow) rows.next(); Iterator cells =
 * row.cellIterator(); while (cells.hasNext()) { cell=(HSSFCell) cells.next();
 * 
 * 
 * if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING && (
 * cell.getColumnIndex()==1 ||cell.getColumnIndex()==6)) {
 * if(cell.getColumnIndex()==1) { name= cell.getStringCellValue().trim(); }
 * 
 * else if(cell.getColumnIndex()==6) { email = cell.getStringCellValue().trim();
 * }else if(cell.getColumnIndex()==2) { email =
 * cell.getStringCellValue().trim(); }
 * 
 * else if(cell.getColumnIndex()==6) {
 * 
 * if(cell.getStringCellValue().trim().contains("ENGINEER")) { role= "ENGINEER";
 * }else if(cell.getStringCellValue().trim().contains("MANAGER")) { role=
 * "MANAGER"; } }
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 * 
 * 
 * }
 * 
 * System.out.println("email: "+ email + " name: "+ name +" role" + role);
 * ConnectionHelper conhelper = new ConnectionHelper(); Connection conn = null;
 * PreparedStatement stment = null; ResultSet rstl = null; try {
 * 
 * conn = conhelper.initConnection();
 * 
 * String sql = "update user set EMAIL= ? where NAME= ?"; stment =
 * conn.prepareStatement(sql); //stment.setString(1, stment.setString(1,
 * email.trim()); stment.setString(2, name.trim());
 * 
 * 
 * int count = stment.executeUpdate(); if(count >0 ) {
 * 
 * conhelper.commitConnection();
 * 
 * 
 * System.out.println("technician updated sucessfully"); } }
 * 
 * catch (Exception e) { System.out.println(e.getMessage()); }
 * 
 * finally { ConnectionHelper.closeDatabase(conn, stment, rstl); }
 * System.out.println("===Ending isUserValid method=====");
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * } }
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */