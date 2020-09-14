<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log In to Overtime</title>
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
<!-- This is a very simple parallax effect achieved by simple CSS 3 multiple backgrounds, made by http://twitter.com/msurguy -->

</head>
<body>
<div class="mask" id="blockUI" style="display: none;">
</div>

<c:if test="${ USEREXIST == 'false'}">
<div class="alert alert-danger" role="alert">
  <strong>Oh snap!</strong> Your user name and email seems incorrect.
</div>
</c:if>
<div class="container">
    <div class="row vertical-offset-100">
    	<div class="col-md-4 col-md-offset-4">
    		<div class="panel panel-default">
			  	<div class="panel-heading">
			    	<h3 class="panel-title">Please sign in</h3>
			 	</div>
			  	<div class="panel-body">
			    	<form accept-charset="UTF-8" role="form" id = "form" action="/STIEOverTime/FrontServlet" method="post">
			    	
			    	<input type="hidden" name = "btnaction">
			    	<input type="hidden" name = "formaction">
                    <fieldset>
			    	  	<div class="form-group">
			    	  	    <span id="nricLabel"></span>
			    		    <input class="form-control" id="nric" placeholder="NRIC" name="nric" type="text">
			    		</div>
			    		<div class="form-group">
			    		    <span id="passwordLabel"></span>
			    			<input class="form-control" id="password" placeholder="Password" name="password" type="password" value="">
			    		</div>
			    		
			    		<button class="btn btn-lg btn-success btn-block" type="button"  onclick="if(validateLogin()){gotoPage('dashboard');}">Login</button>
			    		<button class="btn btn-lg btn-primary btn-block" type="button" onclick="gotoPage('registeration');" >Register</button>
			    	</fieldset>
			      	</form>
			    </div>
			</div>
		</div>
	</div>
</div>

</body>
</html>