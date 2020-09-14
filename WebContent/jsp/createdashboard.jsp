<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
        
        
        
        
<!--         <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
 -->        <script type="text/javascript" src="script/scripts.js"></script>
        
        <style type="text/css" >
           .error { color: red;}
          .hide { display: none; }
          .show { display: block; }
        </style>
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

                <h2>Create New Over Time</h2>

				<form id = "form" method="post">
				
				
				<c:if test="${not empty engineerremark}">
				<div class="alert alert-success" role="alert">
				  <p> Over Time Updated and Routed To Manager successfully</p>
				  
				</div>
				</c:if>
				
				<c:if test="${not empty msg}">
				<div class="alert alert-success" role="alert">
				  <p>Created <c:out value="${count}"></c:out> Over Time Cases and Routed To Manager successfully</p>
				  
				</div>
				</c:if>
				
				
				<c:if test="${not empty updatemsg}">
				<div class="alert alert-success" role="alert">
				  <p>Updated and Routed The OT to Manager successfully</p>
				  
				</div>
				</c:if>
				
				<c:if test="${not empty updatemsgfail}">
				<div class="alert alert-success" role="alert">
				  <p>Sorry , You cannot update the days</p>
				  
				</div>
				</c:if>
				
				
				<c:if test="${not empty updatemsgcancel}">
				 <div class="alert alert-success" role="alert">
				  <p>OT cancelled and routed to Manager successfully</p>
				  
				</div>
				</c:if>
				
				<c:if test="${not empty updatemsgcancelformaanger}">
				 <div class="alert alert-success" role="alert">
				  <p>OT cancelled and routed to Engineer</p>
				  
				</div>
				</c:if>
				
				
				
				<c:if test="${not empty updatemsgapprovalcancel}">
				 <div class="alert alert-success" role="alert">
				  <p>OT cancelled successfully</p>
				  
				</div>
				</c:if>
				
				
				
				<c:if test="${not empty createfail}">
				 <div class="alert alert-danger" role="alert">
				  <p>${createfail }</p>
				  
				</div>
				</c:if>
				
				
				
				
				
				
				<c:if test="${not empty updatemsgaccept}">
					<div class="alert alert-success" role="alert">
					  <p>Approved OT successfully</p>
					  
					</div>
				</c:if>
				
				<c:if test="${not empty updatemsgreject}">
					<div class="alert alert-success" role="alert">
					  <p>Rejected OT successfully </p>
					  
					</div>
				</c:if>
				
			
				
				    <input type="hidden" name = "btnaction">
			    	<input type="hidden" name = "formaction">
			    	<input type="hidden" name = "id" value="${overtime.id}">
			    	
			    	
				   <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="name" id="nameLabel">Technician Name<span style="color: red;">*</span></label>
				      
				      <select  name="name" id="name" <c:if test="${role=='MANAGER'}">disabled</c:if> class="form-control">
				       <option value="">Select Technician Name</option>
				       <c:forEach items="${technicianList}" var="tech">
				        <option <c:if test="${overtime.technicianName == tech.name}">selected</c:if> value="${tech.name}">${tech.name}</option>
				       </c:forEach>
				      </select>
				   </div>
				    <div class="form-group col-md-6">
				      <label for="department" id="departmentLabel">Department<span style="color: red;">*</span></label>
				      
				        <select  name="department" id="department" <c:if test="${role=='MANAGER'}">disabled</c:if> class="form-control">
				       <option value="">Select Department</option>
				       <c:forEach items="${depList}" var="dep">
				        <option <c:if test="${overtime.department == dep.name}">selected</c:if> value="${dep.name}">${dep.name}</option>
				       </c:forEach>
				      </select>
				      
				    </div>
				  </div>
				  
				   <div class="form-row">
					  <div class="form-group  col-md-6">
					    <label for="startdate" id="startLabel">Start Date/Time<span style="color: red;">*</span></label>
					    
					    <input type="date" id="startdate"  name = "startdate"  <c:if test="${role=='MANAGER' || overtime.status == '02'}">readonly</c:if> value="${overtime.startDate}" class="form-control" id="startdate" placeholder="Start Date">
					    <input type="time" id="starttime" name = "starttime"  <c:if test="${role=='MANAGER'}">readonly</c:if>  value="${overtime.startTime}" class="form-control" id="startdate" placeholder="Start Time">
					    
					  </div>
					   <div class="form-group  col-md-6">
					    <label for="enddate" id="endLabel">End Date/Time<span style="color: red;">*</span></label>
					    <input type="date" id="enddate" name ="enddate"  <c:if test="${role=='MANAGER' || overtime.status == '02'}">readonly</c:if>  value="${overtime.endDate}"  class="form-control" id="enddate" placeholder="End Date">
					    <input type="time"  id="endtime" name = "endtime"  <c:if test="${role=='MANAGER'}">readonly</c:if>  value="${overtime.endTime}"  class="form-control" id="startdate" placeholder="End Time">
					  </div>
				  
				  </div>
				 <div class="form-row">
					 <div class="form-group  col-md-12">
					    <label for="inputAddress" id="purposeLabel">Purpose<span style="color: red;">*</span></label>
					    
					
					  </div>
					   <div class="form-group  col-md-12">
					   		<textarea style="margin-top: -18px;
                             width: 50%;" name="purpose" id="purpose" rows="2"  <c:if test="${role=='MANAGER'}">disabled</c:if> cols="12">${overtime.purpose}</textarea>
					   
					   </div>
				 </div>
				  
				  
				   <div class="form-row">
				    <div class="form-group  col-md-12">
				    	 <label for="breakdown" id="breakdownLabel">Break Down<span style="color: red;">*</span></label>
				    </div>
				    <div class="form-group col-md-12" style="margin-top: -20px;">
				         <input type="radio" name="breakdown" value="Yes" <c:if test="${role=='MANAGER'}">disabled</c:if> <c:if test="${overtime.breakDown == 'Yes'}">checked</c:if>  onclick="toggleBreakDown(this.value)">Yes&nbsp;&nbsp;&nbsp;&nbsp;
				    	 <input type="radio" name="breakdown" value="No" <c:if test="${role=='MANAGER'}">disabled</c:if> <c:if test="${overtime.breakDown == 'No'}">checked</c:if> onclick="toggleBreakDown(this.value)">No
				    </div>
				   </div>
				   
				   
				   
				    <div class="form-row ${hideBreakDownType ? 'hide' : 'show'}"  id="breakdowntype">
				    <div class="form-group col-md-12" id="breakdowntypeLabel">
				         <input type="radio" name="breakdowntype" value="WorksOrder" <c:if test="${role=='MANAGER'}">disabled</c:if> <c:if test="${overtime.breakdowntype == 'WorksOrder'}">checked</c:if>  onclick="toggleBreakDownType(this.value)">Works order&nbsp;&nbsp;&nbsp;&nbsp;
				    	 <input type="radio" name="breakdowntype" value="PreventiveMaintenance" <c:if test="${role=='MANAGER'}">disabled</c:if> <c:if test="${overtime.breakdowntype == 'PreventiveMaintenance'}">checked</c:if>  onclick="toggleBreakDownType(this.value)">Preventive maintenance&nbsp;&nbsp;&nbsp;&nbsp;
				         <input type="radio" name="breakdowntype" value="Others" <c:if test="${role=='MANAGER'}">disabled</c:if> <c:if test="${overtime.breakdowntype == 'Others'}">checked</c:if> onclick="toggleBreakDownType(this.value)">Others(Office)
				         
				    </div>
				   </div>
				   
				   
				   
				   
				   
				   <c:if test="${overtime.breakDown == 'Yes' || role =='ENGINEER'}">
				   
				   <div class="form-row ${hideSiteDiv ? 'hide' : 'show'}" id="siteDiv" >
				    <div class="form-group  col-md-6">
				        <label for="site" id="siteLabel">Site<span style="color: red;">*</span></label>
				        
				       <select  name ="site" id="site" <c:if test="${role=='MANAGER'}">disabled</c:if> class="form-control">
				         <option value="">Select Site</option>
				        <c:forEach items="${depList}" var="site">
				        <option <c:if test="${overtime.site == site.name}">selected</c:if> value="${site.name}">${site.name}</option>
				       </c:forEach>
				       </select>
				      </div>
				   </div>
				   
				   </c:if>
				   
				   
				    <c:if test="${overtime.breakDown == 'No' || role =='ENGINEER'}">
				     <div class="form-row  ${hideWoDiv ? 'hide' : 'show'}" id = "woDiv">
				    <div class="form-group  col-md-6">
				        <label for="worder" id="worderLabel">Works Order<span style="color: red;">*</span></label>
					    <input type="text" id="worder" name ="worder"  value="${overtime.workorderNumber}" <c:if test="${role=='MANAGER'}">readonly</c:if>  class="form-control"  placeholder="Works Order">
					    
				    </div>
				   </div>
				    </c:if>
				  
				   
				   <c:if test="${role=='ENGINEER'}">
				    <div class="clearfix"></div>
				   <div class="form-row">
				    <div class="form-group  col-md-6">
				        <label for="manager" id="managerLabel">Route To<span style="color: red;">*</span></label>
				        
				        
				         <select  name ="manager" id="manager"  class="form-control">
					        <option value="">Select Manager to Route</option>
					        <c:forEach items="${manangerList}" var="mananger">
					        <c:set var = "manager" value = "${fn:trim(fn:split(mananger, '(')[0])}" />
					        <option <c:if test="${overtime.userto == manager}">selected</c:if> value="${manager}">${mananger}</option>
					       </c:forEach>
				        </select>
					    
				    </div>
				   </div>
				   </c:if>
			       <div class="clearfix"></div>
				   
				   <c:if test="${overtime.remarks.size() > 0 }">
				        <div class="form-row">
						<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="questionTwo">
						<h5 class="panel-title">
						<a class="collapsed" data-toggle="collapse" data-parent="#faq" href="#answerTwo" aria-expanded="false" aria-controls="answerTwo">
						 Remarks
						</a>
						</h5>
						</div>
						<div id="answerTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="questionTwo">
						<div class="panel-body">
						<table class="table table-striped">
					      <thead>
					      <tr>
					      <th scope="col">User</th>
					      <th scope="col">Comment</th>
					      </tr>
					     </thead>
					     <tbody>
					     </tbody>
					     
					     <c:forEach  items="${overtime.remarks}" var = "remark" >
					      <tr>
					       <td>${remark.user}</td>
					       <td>${remark.remarks}</td>
					      </tr>
					     
					     </c:forEach>
					      
					      
					      </table>
						</div>
						</div>
						</div>
						</div>
				   
				   
				   </c:if>
				 <div class="clearfix"></div>
				 
				  <c:if test="${role=='ENGINEER'}">
				 <div class="form-row">
				 	<c:if test="${empty overtime.status}">
				 
						 <div class="form-group  col-md-4">
						    <button type="button" class="btn btn-primary"  onclick="if (validateForm()){gotoPage('create')}">Create</button>
						  </div>
					  </c:if>
					  
				
					  
					  <c:if test="${overtime.status == '02'}">
					  
					  <div class="form-group  col-md-4">
					  	<button type="button" class="btn btn-primary"  onclick="if(validateForm()){gotoPage('update')}">Update</button>
					    <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#remarkModal">Add Remark</button>
					  </div>
					  </c:if>
					  
				  </div>
				  </c:if>
				  
				  
				
				  
				  <c:if test="${role=='MANAGER'}">
				 <div class="form-row">
					 <div class="form-group  col-md-4">
					  <button type="submit" class="btn btn-primary" <c:if test="${overtime.status == '01' || overtime.status == '05'|| overtime.status == '02' || overtime.status == '04' }">disabled</c:if>  onclick="accept(${overtime.id});">Accept</button>
					  <button type="button" class="btn btn-danger"  <c:if test="${overtime.status == '01' || overtime.status == '05'|| overtime.status == '02' || overtime.status == '04' }">disabled</c:if> data-toggle="modal" data-target="#exampleModal">
					   Reject
					  </button>
					  </div>
					 
				  </div>
				  </c:if>
				  
				  <c:if test="${role=='MANAGER' && overtime.status == '05'}">
				   <div class="form-row">
				  		<div class="form-group  col-md-4">
				  			<button type="button" class="btn btn-primary" onclick="approvecancel(${overtime.id});" >Approve Cancel</button>
				  			
				  		</div>
				    </div>
				  </c:if>
				  
				  
				    <c:if test="${(overtime.status == '03' && role=='ENGINEER') || (role=='MANAGER' && overtime.status == '01')}">
				  				 <div class="form-row">
				  				 	 <div class="form-group  col-md-4">
				  				        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#cancelModal">Cancel</button>
				  				     </div>
				                </div>
				    </c:if>
				  
				  
				  
				  
				  
				  
				  
				  
				  <!-- Modal -->
					<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLabel">Reason For Rejection<span style="color: red;">*</span></h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					      <textarea rows="4" cols="" id="remarkbyman" name="remarkbyman" style="width: 100%;"></textarea>
					       
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					        <button type="button" class="btn btn-primary" onclick="if(validateRejectModal()){reject(${overtime.id});}">Submit</button>
					      </div>
					    </div>
					  </div>
					</div>
					
					
					
					  
				  <!--Remark Modal -->
					<div class="modal fade" id="remarkModal" tabindex="-1" role="dialog" aria-labelledby="remarkModalLabel" aria-hidden="true">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="remarkModalLabel">Add Remark<span style="color: red;">*</span></h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					      <textarea rows="4" cols="" id="remarkbyeng" name="remarkbyeng" style="width:100%">
					      
					      </textarea>
					       
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					        <button type="button" class="btn btn-primary" onclick="if(validateRemarkModal()){addremark(${overtime.id});}">Submit</button>
					      </div>
					    </div>
					  </div>
					</div>
					
					
					
					
					
					
					
					  <!-- Cancel Modal -->
					<div class="modal fade" id="cancelModal" tabindex="-1" role="dialog" aria-labelledby="cancelModalLabel" aria-hidden="true">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="cancelModalLabel">Reason For Cancel<span style="color: red;">*</span></h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					      <textarea rows="4"  cols="" id="cancel" name="cancel" style="width: 100%"></textarea>
					       
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					        <c:if test="${role=='MANAGER'}">
					        	<button type="button" class="btn btn-primary" onclick="if(reasonforcancelModal()){cncl(${overtime.id})}">Submit</button>
					        
					        </c:if>
					         <c:if test="${role=='ENGINEER'}">
					        	<button type="button" class="btn btn-primary" onclick="if(reasonforcancelModal()){pendingcancel(${overtime.id})}">Submit</button>
					        
					        </c:if>
					      </div>
					    </div>
					  </div>
					</div>
					
					
				  
				  
				</form>


            </div>
        </div>
        
        <!-- jQuery CDN -->
         <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
       
         
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
