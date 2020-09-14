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
        
         <!-- DataTable -->
       <link rel="stylesheet" href="//cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"> 
        
        
        <script type="text/javascript" src="script/scripts.js"></script>
    </head>
    <body>
    
   
    <div class="mask" id="blockUI" style="display: none;">
</div>


        <div class="wrapper">
            <!-- Sidebar Holder -->
            <jsp:include page="navigation.jsp"></jsp:include>

            <!-- Page Content Holder -->
            <div id="content"  style="width: 100%;">

                <nav class="navbar navbar-default">
                    <div class="container-fluid">

                        <div class="navbar-header">
                            <button type="button" id="sidebarCollapse" class="btn btn-info navbar-btn">
                                <i class="glyphicon glyphicon-align-left"></i>
                                <span>Toggle Menu</span>
                            </button>
                        </div>

                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav navbar-right">
                                <li><a href="#" onclick="gotoPage('login');">Logout</a></li>
                               
                            </ul>
                        </div>
                    </div>
                </nav>

                <h2>Over time History</h2>
                
             <form id = "form" method="post">
             
                <c:if test="${not empty updatemsgaccept}">
					<div class="alert alert-success" role="alert">
					  <p>Approved OT successfully</p>
					  
					</div>
				</c:if>
             
                    <input type="hidden" name = "btnaction">
			    	<input type="hidden" name = "formaction">
			    	<input type="hidden" name="id">
                
                 <table class="table table-striped" id="myTable">
				  <thead>
				    <tr>
				      <th scope="col">S.NO</th>
				      <th scope="col">Technician Name</th>
				      <th scope="col">Start Date</th>
				      <th scope="col">Start Time</th>
				      <th scope="col">End Date</th>
				      <th scope="col">End Time</th>
				      <th scope="col">Status</th>
				      <c:if test="${role=='MANAGER' }">
				       <th scope="col">Approve</th>
				      </c:if>
				      <th scope="col">View</th>
				    </tr>
				  </thead>
				  <tbody>
				  <c:forEach items="${overtimeList}" var="ot" varStatus="i">
				  <tr>
				      <th scope="row">${i.count}</th>
				      <td>${ot.technicianName}</td>
				      <td>${ot.startDate}</td>
				      <td>${ot.startTime}</td>
				      <td>${ot.endDate}</td>
				      <td>${ot.endTime}</td>
				      <td>${ot.status}</td>
				      
				      <c:if test="${role=='MANAGER'}">
				        <td>
				        <c:if  test="${ot.numStatus == '03'}">
				         <button onclick="approve(${ot.id})">Approve</button>
				        </c:if>
				        </td>
				      </c:if>
				      
				     
				      <td><a href="#" onclick="viewOvertime(${ot.id})">view</a></td>
				      
				    </tr>
				  </c:forEach>
				    
				  
				  </tbody>
				</table>
				</form>
								


            </div>
        </div>
        
        
            





        <!-- jQuery CDN -->
         <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
         <!-- Bootstrap Js CDN -->
         <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
         
         <script type="text/javascript" src="//cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
         

         <script type="text/javascript">
             $(document).ready(function () {
            	 
                 $('#myTable').DataTable();

                 $('#sidebarCollapse').on('click', function () {
                     $('#sidebar').toggleClass('active');
                 });
             });
         </script>
    </body>
</html>
