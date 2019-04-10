<%-- 
    Document   : index
    Created on : 03-jun-2016, 18:03:15
    Author     : Francisco
--%>
<%@page import="modelo.DAO.PracticaDAO"%>
<%@page import="conexion.ConexionFactory"%>
<%@page import="modelo.Entity.Practica"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="ServletValidarUsuario" method="post">
            <div>
            <img src="icono/logo-fondoazul.jpg" width="320" height="122" alt="logo-fondoazul" align="left"/>
            <br>
            </div>
            
            <div>
                <h1 align="left">Bienvenido al sistema de practicas DUOC UC</h1>
            </div>
            <br>
            <br>
            <br>
            <br>
            <div align="center">
            <label>Rut: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;           
                <input type="text" name="rut" value="" align="center"/><br>
            </label>
            <br>
            <label>Password:
                <input type="password" name="pass" value="" align="center"/><br>
            </label>
            <br>
            <input type="submit" value="Iniciar sesion" align="center"/>    
            </div>
        </form>
        
    </body>
</html>
