<%-- 
    Document   : subirArchivo
    Created on : 25-jun-2016, 14:44:41
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
        <form action="ServletSubirArchivo" enctype="multipart/form-data" method="post" >
            <label>id
                <input type="text" name="id" value="" />
            </label>
            <br>
            <label>archivo
                <input type="file" name="archivo" />
            <input type="submit" value="subir" /> 
            </label>
            <br>
            <label>nombre
                <input type="text" name="nombre" value="" />
            </label>
            <br>
            <label>fecha
               <input type="text" name="fecha" value="" /> 
            </label>
            <br>
            <label>peso
               <input type="text" name="peso" value="" /> 
            </label>
            <br>
            <label>id practica
               <input type="text" name="idpractica" value="" /> 
            </label>
            <br>
            <input type="submit" value="eniar" />
        </form>
        <h1>${requestScope.msj}</h1>
    </body>
</html>
