<%-- 
    Document   : newjsp
    Created on : 03-jun-2016, 19:41:50
    Author     : Francisco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="modelo.Entity.Practica"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th>id practica</th>
                    <th>rut</th>
                    <th>nombre</th>
                    <th>apellido</th>
                    <th>nota 1</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${lista}" var="lista"> 
                <tr>                    
                    <td>${lista.idpractica}</td>
                    <td>${lista.alumnoRut.rut}</td>
                    <td>${lista.alumnoRut.nombres}</td>
                    <td>${lista.alumnoRut.apellidopaterno}</td>
                    <td>${lista.notasIdnotas.nota1}</td>
                </tr>
                </c:forEach>    
            </tbody>
        </table>

    </body>
</html>
