<%-- 
    Document   : Inicio
    Created on : 26-06-2016, 17:42:03
    Author     : Toshiba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../Estilo/js/script.html"></jsp:include>
        <script src="navegar.js" type="text/javascript"></script>
        <title>Alumnos</title>
    </head>
    <body>
        <div class="row">
            <div class="col-md-4 encabezado" style="padding-bottom: -5px;">
                <div class="col-sm-4" style="padding-left: 30px; padding-right: 10px">
                    <a href="http://portal.duoc.cl" target="_blank"><img src="../Estilo/imagenes/logo.png" alt="" width="100%" /></a>
                </div>
                <br>
                <br>
                <div class="navbar" style="">
                     <div class="hola text-center" id="hola">Hola: (nombre)</div>
                </div>
            </div>
            <div class="col-md-8 encabezado" >
                <div class="navbar">
                    <ul class="nav nav-pills nav-justified"> 
                        <li role="presentation" >
                            <a id="inicio">Inicio</a></li> 
                        <li role="presentation" class="active">
                            <a id="alumnos">Alumnos</a></li> 
                        <li role="presentation" >
                            <a id="documentaciones">Documentaciones</a></li> 
                        <li role="presentation">
                            <a id="notas">Notas</a></li> 
                        <li role="presentation">
                            <a id="misDatos">Mis Datos</a></li> 
                        <li role="presentation">
                            <a id="cambiarContraseña">Cambiar Contraseña</a></li> 
                        <li role="presentation" >
                            <a id="salir">Salir</a></li> 
                        <br>
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="container">
            <br><br><br><br>
            <div><h2 class="text-center" id="bienvenida">Alumnos</h2></div>
            <table class="table table-hover">
                <thead>
                    <th>Rut</th>
                    <th>Nombre</th>
                    <th>Apellido Paterno</th>
                    <th>Apellido Materno</th>
                    <th>Correo</th>
                    <th>Telefono</th>
                    <th>Direccion</th>
                </thead>
                <tr>
                    <td>12345618-5</td>
                    <td>lalalala</td>
                    <td>lalalala</td>
                    <td>lalalala</td>
                    <td>lalalala</td>
                    <td>lalalala</td>
                    <td>lalalala</td>
                </tr>
            </table>
        </div>
    </body>
</html>
