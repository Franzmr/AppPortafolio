<%-- 
    Document   : MisDatos
    Created on : 27-06-2016, 3:15:04
    Author     : Toshiba
--%>

<%@page import="modelo.Entity.Empleador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../Estilo/js/script.html"></jsp:include>
        <script src="navegar.js" type="text/javascript"></script>
        <title>Mis Datos</title>
        <%  
            HttpSession sesion = request.getSession();
             Empleador e = (Empleador) sesion.getAttribute("e");
        %>
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
                        <li role="presentation" >
                            <a id="documentacion">Documentacion</a></li> 
                        <li role="presentation" >
                            <a id="notas">Notas</a></li> 
                        <li role="presentation" class="active">
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
            <br><br><br><br><br>
            <form class="form-horizontal">
                <fieldset>

                <!-- Form Name -->
                <legend class="text-center">Mis Datos</legend>
                <br>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="rut">Rut</label>  
                  <div class="col-md-4">
                  <input disabled id="rut" name="rut" type="text" placeholder="Rut..." value="<%=e.getRut()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="razonSocial">Razon Social</label>  
                  <div class="col-md-4">
                  <input disabled id="razonSocial" type="text" placeholder="Razon Social..." value="<%=e.getRazonsocial()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="nombreFantasia">Nombre Fantasia</label>  
                  <div class="col-md-4">
                  <input disabled id="nombreFantasia" name="nombreFantasia" type="text" placeholder="Nombre Fantasia..." value="<%=e.getNombrefantasia()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="apellidoMaterno">Apellido Materno</label>  
                  <div class="col-md-4">
                  <input disabled id="apellidoMaterno" name="apellidoMaterno" type="text" placeholder="Apellido Materno..." class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="telefono">Telefono</label>  
                  <div class="col-md-4">
                  <input id="telefono" name="telefono" type="text" placeholder="Telefono..." value="<%=e.getTelefono()%>" class="form-control input-md">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="correo">Correo</label>  
                  <div class="col-md-4">
                  <input id="correo" name="correo" type="text" placeholder="Correo" value="<%=e.getCorreo()%>" class="form-control input-md">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="direccion">Direccion</label>  
                  <div class="col-md-4">
                  <input disabled id="direccion" name="direccion" type="text" placeholder="Direccion..." value="<%=e.getDireccion()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="Comuna">Comuna</label>  
                  <div class="col-md-4">
                  <input disabled id="Comuna" name="Comuna" type="text" placeholder="Comuna..." value="<%=e.getComunaIdcomuna().getNombrecomuna()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="nombreJefe">Nombre Jefe</label>  
                  <div class="col-md-4">
                  <input disabled id="carrera" name="nombreJefe" type="text" placeholder="Nombre Jefe..." value="<%=e.getNombrejefe()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Button -->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="guardar"></label>
                  <div class="col-md-4">
                    <button id="guardar" name="guardar" class="btn btn-primary">Guardar</button>
                  </div>
                </div>

                </fieldset>
            </form>
        </div>

    </body>
</html>
