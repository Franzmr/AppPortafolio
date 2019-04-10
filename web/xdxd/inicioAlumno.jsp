<%-- 
    Document   : inicio
    Created on : 15-jun-2016, 21:21:34
    Author     : Francisco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <img src="icono/logo-fondoazul.jpg" width="320" height="122" alt="logo-fondoazul" align="left"/>
            
            <br>
        </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
        <a href="">Mis Datos</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
        <a href="">Subir informes</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
        <a href="">Ver informes</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
        <a href="">Ver mis notas</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
        <br><br>
        <br><br>
        <br>
        <h3>${requestScope.rutU}</h3>
        <h3>${requestScope.nombre}</h3>
        <h3>${requestScope.ApellidoP}</h3>
        
    </body>
</html>
