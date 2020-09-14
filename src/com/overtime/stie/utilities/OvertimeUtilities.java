package com.overtime.stie.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.overtime.stie.dto.Overtime;

public class OvertimeUtilities {
	
	public static String retrieveParam(HttpServletRequest request, String parametername) {
		
		return  request.getParameter(parametername)!=null?request.getParameter(parametername).trim():"";

	}
	
	public static String cleanString(String param) {
		String returnval = null;
		if(param== null) {
			returnval = "";
		}else {
			returnval = param.trim();
		}
		
		return returnval;
	}
	
	public static int calculateDaysBetween(String date1, String date2) {
		
		if(date1== null || date2== null) {
			return -1;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		 int numberOfdays = 0;
		 Calendar cal1 = new GregorianCalendar();
	     Calendar cal2 = new GregorianCalendar();
		try {
			Date d1 = sdf.parse(date1);
			cal1.setTime(d1);
			Date d2 = sdf.parse(date2);
			cal2.setTime(d2);
		
			numberOfdays = daysBetween(cal1.getTime(),cal2.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return numberOfdays;
	}
	
	public static String addDays(String date , int days) {
		
		    String retDate = "";
		
			if(date== null || date.isEmpty()) {
				return "-1";
			}
		
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		  Calendar cal1 = new GregorianCalendar();
		 
		 try {
				Date d1 = sdf.parse(date);
				cal1.setTime(d1);
				cal1.add(Calendar.DAY_OF_MONTH, days);
				retDate = sdf.format(cal1.getTime());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 return retDate;
	}
	
	
	
	
	
	
	 public static int daysBetween(Date d1, Date d2){
         return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
 }
	 
	 
	 public static void exportExcel(List<Overtime> overtimeList, HttpServletResponse response) throws IOException {

		    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		    Workbook workbook = new XSSFWorkbook();
		    
		    Sheet sheet = workbook.createSheet("Overtime");
		    sheet.setColumnWidth(0, 4000);
		    sheet.setColumnWidth(1, 4000);
		    sheet.setColumnWidth(2, 4000);
		    sheet.setColumnWidth(3, 4000);
		    sheet.setColumnWidth(4, 4000);
		    sheet.setColumnWidth(5, 4000);
		    sheet.setColumnWidth(6, 4000);
		    sheet.setColumnWidth(7, 4000);
		    sheet.setColumnWidth(8, 4000);
		    sheet.setColumnWidth(9, 4000);
		     
		    Row header = sheet.createRow(0);
		     
		    CellStyle headerStyle = workbook.createCellStyle();
		    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		     
		    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		    font.setFontName("Arial");
		    font.setFontHeightInPoints((short) 8);
		    font.setBold(true);
		    headerStyle.setFont(font);
		     
		    Cell headerCell = header.createCell(0);
		    headerCell.setCellValue("S.NO");
		    headerCell.setCellStyle(headerStyle);
		     
		    headerCell = header.createCell(1);
		    headerCell.setCellValue("Technician Name");
		    headerCell.setCellStyle(headerStyle);
		    
		    headerCell = header.createCell(2);
		    headerCell.setCellValue("Start Date");
		    headerCell.setCellStyle(headerStyle);
		    
		    
		    headerCell = header.createCell(3);
		    headerCell.setCellValue("Start Time");
		    headerCell.setCellStyle(headerStyle);
		    
		    
		    
		    headerCell = header.createCell(4);
		    headerCell.setCellValue("End Date");
		    headerCell.setCellStyle(headerStyle);
		    
		    
		    headerCell = header.createCell(5);
		    headerCell.setCellValue("End Time");
		    headerCell.setCellStyle(headerStyle);
		    
		    headerCell = header.createCell(6);
		    headerCell.setCellValue("BreakDown");
		    headerCell.setCellStyle(headerStyle);
		    
		    headerCell = header.createCell(7);
		    headerCell.setCellValue("Department");
		    headerCell.setCellStyle(headerStyle);
		    
		    headerCell = header.createCell(8);
		    headerCell.setCellValue("Work type");
		    headerCell.setCellStyle(headerStyle);
		    
		    headerCell = header.createCell(9);
		    headerCell.setCellValue("Site");
		    headerCell.setCellStyle(headerStyle);
		    
		    headerCell = header.createCell(10);
		    headerCell.setCellValue("Workorder Number");
		    headerCell.setCellStyle(headerStyle);
		    
		    


		    
		    CellStyle style = workbook.createCellStyle();
		    style.setWrapText(true);
		     
		    
		  for (int i = 0; i < overtimeList.size(); i++) {
			 
			      Row row = sheet.createRow(i+1);

			      int j = 0;
			      
			      Cell cell = row.createCell(j++);
				  cell.setCellValue(i+1);
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getTechnicianName());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getStartDate());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getStartTime());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getEndDate());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getEndTime());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getBreakDown());
				  cell.setCellStyle(style);
				  
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getDepartment());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getBreakdowntype());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getSite());
				  cell.setCellStyle(style);
				  
				  cell = row.createCell(j++);
				  cell.setCellValue(overtimeList.get(i).getWorkorderNumber());
				  cell.setCellStyle(style);
				  
				 
			
		   }
		  
		    ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		    workbook.write(outByteStream);
		    byte[] outArray = outByteStream.toByteArray();
		    String fileOut = "Overtime-"+timeStamp+ ".xlsx";
		  
		    response.setContentType("application/vnd.ms-excel");
		    response.setContentLength(outArray.length);
		    response.setHeader("Content-Disposition", "attachment; filename=\""
		            + fileOut + "\"");

		    OutputStream outStream = response.getOutputStream();
		    outStream.write(outArray);

		    outStream.flush();
		    outStream.close();
		  
	

}
	 
	public static void sendMail(String emailTo, String OTP) throws NoSuchProviderException {
		
		System.out.println("sending email start to "+ emailTo);
		
		// Recipient's email ID needs to be mentioned.
	      String to = emailTo;

	      // Sender's email ID needs to be mentioned
	      String from = "info@stie.com.sg";

	      
	      String host = "mail.stie.com.sg";

	      // Get system properties
	      Properties properties = System.getProperties();
	      
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.host", host); 
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.port", "587");
        
	      
	      Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("info@stie.com.sg", "I5PaS3123457!(");
				}
			};

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties, auth);
	     

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("One Time Password For Overtime Application");

	         // Send the actual HTML message, as big as you like
	         message.setContent("<p>Please enter the below&nbsp; <b>one time password</b>&nbsp; for the registration </p> <br>"+OTP, "text/html");

	         // Send message
	         
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	} 
	
	public static String hashPassowrd(String password) {
		
		String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
       return generatedPassword;
    }
		
}
	
	

