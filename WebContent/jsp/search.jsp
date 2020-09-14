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
       
        <style type="text/css" >
           .error { color: red;}
        
        </style>
        
        
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
             
                   <input type="hidden" name = "btnaction">
			    	<input type="hidden" name = "formaction">
			    	<input type="hidden" name="id">
			    	
			    	
			       <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="name">Technician Name</label>
				      
				      
				      <select  name="name" id="name"  class="form-control">
				       <option value="">Select Technician Name</option>
				       <c:forEach items="${technicianList}" var="tech">
				        <option <c:if test="${ot.technicianName == tech.name}">selected</c:if> value="${tech.name}">${tech.name}</option>
				       </c:forEach>
				      </select>
				   </div>
				    <div class="form-group col-md-6">
				      <label for="department">Department</label>
				    
				     <select  name="department" id="department"  class="form-control">
				       <option value="">Select Department</option>
				       <c:forEach items="${depList}" var="dep">
				        <option <c:if test="${ot.department == dep.name}">selected</c:if> value="${dep.name}">${dep.name}</option>
				       </c:forEach>
				      </select>
				    
				    
				    
				    </div>
				  </div>
				  
				  
				   <div class="form-row">
					  <div class="form-group  col-md-4">
					    <label for="startdate" id="startDateLabel">Start Date/Time<span style="color: red;">*</span></label>
					    <input type="date"  name = "startdate" required="required" value="${ot.startDate}" class="form-control" id="startdate" placeholder="Start Date">
					    
					  </div>
					   <div class="form-group  col-md-4">
					    <label for="enddate" id="endDateLabel">End Date/Time<span style="color: red;">*</span></label>
					    <input type="date" name ="enddate" required="required" value="${ot.endDate}"  class="form-control" id="enddate" placeholder="End Date">
					  </div>
					  
					  <c:if test="${role=='MANAGER' }">
					  <div class="form-group  col-md-4">
					    <label for="enddate">Status</label>
					   <select  class="form-control" name="status">
					     <option value="">Please Select One</option>
					     <option value="01">Approved</option>
					     <option value="02">Rejected</option>
					     <option value="03">Pending</option>
					     <option value="04">Cancelled</option>
					     <option value="05">Pending Cancellation</option>
					     
					     
					   </select>
					   </div>
					   </c:if>
					  
				  
				  </div>
				  
				     <div class="form-row">
				    
				     <div class="form-group  col-md-4">
				     	<label for="site">Site Assigned</label>
				         <select  name ="site" id="site" class="form-control">
				         <option value="">Select Site</option>
				        <c:forEach items="${depList}" var="site">
				        <option <c:if test="${ot.site == site.name}">selected</c:if> value="${site.name}">${site.name}</option>
				       </c:forEach>
				       </select>
				     
				      </div>
				     </div>
				  <div class="clearfix"></div>
				  
				  <div class="form-row">
					   <div class="form-group  col-md-4">
						 <button type="button" class="btn btn-primary"  onclick="if (validateSearchForm()){search()}">Search</button>
					   </div>
				  </div>
                
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
				     
				    </tr>
				  </thead>
				  <tbody>
				  <c:forEach items="${overtimesearch}" var="ot" varStatus="i">
				  <tr>
				      <th scope="row">${i.count}</th>
				      <td>${ot.technicianName}</td>
				      <td>${ot.startDate}</td>
				      <td>${ot.startTime}</td>
				      <td>${ot.endDate}</td>
				      <td>${ot.endTime}</td>
				      <td>${ot.status}</td>
				    
				    </tr>
				  </c:forEach>
				 </tbody>
				</table>
				
				<button type="button" class="btn btn-primary" <c:if test="${empty overtimesearch}">disabled</c:if>  onclick="exportToExcel()"> Export</button>
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
