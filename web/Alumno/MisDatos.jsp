<%-- 
    Document   : MisDatos
    Created on : 27-06-2016, 3:15:04
    Author     : Toshiba
--%>

<%@page import="modelo.Entity.Usuario"%>
<%@page import="modelo.Entity.Asociacion"%>
<%@page import="modelo.Entity.Tipousuario"%>
<%@page import="modelo.Entity.Comuna"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../Estilo/js/script.html"></jsp:include>
        <script src="navegar.js" type="text/javascript"></script>
        <title>Mis Datos</title>
        <%  
            //Acá creas una sesión, la cuál recibe todos lo parametros que se le asignaron a la variable del servlet, debes capturar los atributos por el nombre que se le dio y
            //hacer la conversion de tipo pertinente
            //DEBES HACER ESTO EN CADA UNA DE LAS VISTAR, SÓLO CON LOS DATOS QUE NECESITES SIPO xD
            HttpSession sesion = request.getSession();            
            Usuario u = (Usuario) session.getAttribute("usuario");
            
            if (u.getCorreo()==null) {
                    sesion.setAttribute("correo", " ");
                }
            String correo = (String) session.getAttribute("correo");
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
                    <div class="hola text-center" id="hola">Hola: <%=u.getNombres()%></div>
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
                            <a id="misNotas">Mis Notas</a></li> 
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
                      <input disabled id="rut" name="rut" type="text" placeholder="Rut..." value="<%=u.getRut()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="nombres">Nombres</label>  
                  <div class="col-md-4">
                  <input disabled id="nombres" name="nombres" type="text" placeholder="Nombres..." value="<%=u.getNombres()%>"  class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="apellidoPaterno">Apellido Paterno</label>  
                  <div class="col-md-4">
                  <input disabled id="apellidoPaterno" name="apellidoPaterno" type="text" value="<%=u.getApellidopaterno()%>"  placeholder="Apellido Paterno..." class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="apellidoMaterno">Apellido Materno</label>  
                  <div class="col-md-4">
                  <input disabled id="apellidoMaterno" name="apellidoMaterno" type="text" value="<%=u.getApellidomaterno()%>" placeholder="Apellido Materno..." class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="telefono">Telefono</label>  
                  <div class="col-md-4">
                  <input id="telefono" name="telefono" type="text" placeholder="Telefono..." value="<%=u.getTelefono()%>" class="form-control input-md">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="correo">Correo</label>  
                  <div class="col-md-4">
                  <input id="correo" name="correo" type="text" placeholder="Correo" value="<%=correo%>" class="form-control input-md">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="direccion">Direccion</label>  
                  <div class="col-md-4">
                  <input disabled id="direccion" name="direccion" type="text" value="<%=u.getDireccion()%>" placeholder="Direccion..." class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="Comuna">Comuna</label>  
                  <div class="col-md-4">
                  <input disabled id="Comuna" name="Comuna" type="text" placeholder="Comuna..." value="<%=u.getComunaIdcomuna().getNombrecomuna()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="carrera">Carrera</label>  
                  <div class="col-md-4">
                  <input disabled id="carrera" name="carrera" type="text" placeholder="Carrera..." value="<%=u.getAsociacionIdasociacion().getCarreraIdcarrera().getNombrecarrera()%>" class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Search input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="sede">Sede</label>
                  <div class="col-md-4">
                      <input disabled id="sede" name="sede" type="search" placeholder="Sede..." value="<%=u.getAsociacionIdasociacion().getSedeIdsede().getNombresede()%>" class="form-control input-md" required="">

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
