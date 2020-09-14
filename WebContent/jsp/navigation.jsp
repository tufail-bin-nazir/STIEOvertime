 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
     <script type="text/javascript" src="script/scripts.js"></script>
    
    </head>
    <body>
 <nav id="sidebar">
                <div class="sidebar-header">
                    <h3>Menu</h3>
                    <strong>OT</strong>
                </div>

                <ul class="list-unstyled components">
                <c:if test="${role=='ENGINEER'}">
                    <li  <c:if test="${page=='home'}">class="active"</c:if>>
                        <a href="#" aria-expanded="false" onclick="gotoPage('home')">
                            <i class="glyphicon glyphicon-home"></i>
                            Home
                        </a>
                       
                    </li>
                    </c:if>
                    <c:if test="${role=='ENGINEER' || role =='MANAGER'}">
                    
                    <li <c:if test="${page=='history'}">class="active"</c:if>>
                        <a href="#"  onclick="gotoPage('history')">
                            <i class="glyphicon glyphicon-briefcase"></i>
                            Over Time History
                        </a>
                       
                    </li>
                    </c:if>
                   
                    <c:if test="${role=='MANAGER' || role =='HR'}">
                    <li  <c:if test="${page=='search'}">class="active"</c:if>>
                    
                       
                        <a href="#"  onclick="gotoPage('search')">
                            <i class="glyphicon glyphicon-briefcase"></i>
                            Search
                        </a>
                    </li>
                    </c:if>
                    
                    <li><a href="#" onclick="gotoPage('login');">Logout</a></li>
                </ul>

                
            </nav>
            </body>
            
            </html>