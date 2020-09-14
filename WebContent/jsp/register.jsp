<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register here</title>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="css/stylesheet.css" rel="stylesheet">
<style type="text/css">
 .error { color: red;}
.vertical-offset-100{
    padding-top:100px;
}
</style>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="script/scripts.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

<!------ Include the above in your HEAD tag ---------->

<script src="http://mymaplist.com/js/vendor/TweenLite.min.js"></script>
 <script type="text/javascript" src="script/scripts.js"></script>
<!-- This is a very simple parallax effect achieved by simple CSS 3 multiple backgrounds, made by http://twitter.com/msurguy -->

</head>
<body>
<div class="mask" id="blockUI" style="display: none;">
</div>
<div class="container">
    <div class="row vertical-offset-100">
    	<div class="col-md-4 col-md-offset-4">
    		<div class="panel panel-default">
			  	<div class="panel-heading">
			    	<h3 class="panel-title">Please register</h3>
			 	</div>
			  	<div class="panel-body">
			    	<form accept-charset="UTF-8" role="form" id = "form" method="post">
			    	
			    		<c:if test="${userNotFound}">
						<div class="alert alert-danger" role="alert">
						  <p>No user found for the Nric number, Please check with admin</p>
						  
						</div>
						</c:if>
						
						<c:if test="${emailsent}">
						<div class="alert alert-success" role="alert">
						  <p>OTP sent to your registered Email, Please enter the OTP to register</p>
						  
						</div>
						</c:if>
						
						<c:if test="${showpassword}">
						<div class="alert alert-success" role="alert">
						  <p>Please Create a password to log in</p>
						  
						</div>
						</c:if>
						
						<c:if test="${wrongotp}">
						<div class="alert alert-success" role="alert">
						  <p>The otp you entered is wrong, Please enter correct OTP or go back to login and then register to register again</p>
						  
						</div>
						</c:if>
						
						<c:if test="${passwordsetsucess}">
						<div class="alert alert-success" role="alert">
						  <p>The password has been sucessfully set, please go to login and sign in</p>
						  
						</div>
						</c:if>
						
						<c:if test="${noemailfound}">
						<div class="alert alert-danger" role="alert">
						  <p>No email is attached to your NRIC, Please Contact Admin</p>
						  
						</div>
						</c:if>
						
			    	
                    <input type="hidden" name = "btnaction">
			    	<input type="hidden" name = "formaction">
                    <fieldset>
			    	  	<div class="form-group">
			    	  	    <span id="nricLabel"></span>
			    		    <input class="form-control" value="${nric}" id="nric" placeholder="NRIC" name="nric" type="text">
			    		</div>
			    		<c:if test="${showOTP}">
			    		<div class="form-group">
			    		    <span id="otpLabel"></span>
			    			<input class="form-control" placeholder="Enter OTP" id="otp" name="otp" type="text" value="">
			    		</div>
			    		</c:if>
			    		
			    		<c:if test="${showpassword}">
			    		<div class="form-group">
			    		   <span id="passwordLabel"></span>
			    			<input class="form-control" placeholder="Enter Password"  id="password"  name="password" type="password" value="">
			    		</div>
			    		</c:if>
			    		
			    		
			    		<c:if test="${generateOTP}">
			    		<button class="btn btn-lg btn-secondary btn-block" type="button" onclick="if(validateOTP()){generate();}">Generate OTP</button>
			    		</c:if>
			    		<c:if test="${showRegister}">
			    		   <button class="btn btn-lg btn-secondary btn-block" type="button" onclick="if(validateRegister()){register();}">
			    		   Register
			    		 </button>
			    		</c:if>
			    		<button class="btn btn-lg btn-primary btn-block" type="button" onclick="gotoPage('login');">Back to Login</button>
			    	</fieldset>
			      	</form>
			    </div>
			</div>
		</div>
	</div>
</div>

</body>
</html>