<%-- 
    Document   : ver
    Created on : 03-jun-2016, 18:30:45
    Author     : Francisco
--%>
<%@page import="modelo.Entity.Practica"%>
<%@page import="java.util.List"%>
<%@page import="modelo.DAO.PracticaDAO"%>
<%@page import="conexion.ConexionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1></h1>
        
        

        <form action="VerNotas">
            <input type="text" placeholder="rut" name="rut">
            <input type="submit" value="ver">
        </form>
        
        
    </body>
</html>
