<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.ResourceBundle" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Create Over time</title>

         <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!-- Our Custom CSS -->
        <link rel="stylesheet" href="css/style4.css">
        <link href="css/stylesheet.css" rel="stylesheet">
        
        
        
        
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="script/scripts.js"></script>
    </head>
    <body>
    
   
    <div class="mask" id="blockUI" style="display: none;">
   </div>

     <div class="content">
      <div class="alert alert-warning" role="alert">
		  Oops , Something went wrong  <a href="/STIEOverTime" class="alert-link">Please log in here</a>. Give it a click if you like.
		</div>
		</div>
       
      <!-- jQuery CDN -->
         <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
         <!-- Bootstrap Js CDN -->
         <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

         <script type="text/javascript">
             $(document).ready(function () {
                 $('#sidebarCollapse').on('click', function () {
                     $('#sidebar').toggleClass('active');
                 });
             });
         </script>
    </body>
</html>
