<%-- 
    Document   : svgSettings
    Created on : 27.5.2014, 13:20:37
    Author     : Doboss
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <title>JSP Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="<c:url value="/default.css"/>" type="text/css"> 
    </head>
    <body>
        <h1>Svg Settings</h1>
        
        <p>Color:</p>
        <form method="POST" action="setStateColor">
            <select name="stateColor">
            <option value="red">Volvo</option>
            <option value="blue">Saab</option>
            <option value="green">Fiat</option>
            <option value="yellow">Audi</option>
            </select>
        </form>
        
        
        
    </body>
</html>
