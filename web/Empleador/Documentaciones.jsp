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
        <title>Documentaciones</title>
        <link href="../Estilo/especial/chosen.css" rel="stylesheet" type="text/css"/>
        <script src="../Estilo/js/jquery-2.2.4.min.js" type="text/javascript"></script>
        <script src="../Estilo/especial/chosen.jquery.js" type="text/javascript"></script>
        
        
        <script>
        jQuery(document).ready(function(){
            
            $('#documentacion').hide();
            
            $('#cerrar').click(function (){
                $('#documentacion').hide();
            });
                
            $('#buscar').click(function (){
                $('#documentacion').show();
            });
  
            jQuery.getScript( "//harvesthq.github.io/chosen/chosen.jquery.js" )
                .done(function( script, textStatus ) {
                    jQuery("#alumno").chosen();
                })
                .fail(function( jqxhr, settings, exception ) {
                     alert("Error");
                });

        });
        </script>
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
                            <a id="alumnos">Alumnos</a></li> 
                        <li role="presentation" class="active">
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
                <br><br><br><br><br>
            <div><h2 class="text-center" id="bienvenida">Documentacion </h2></div>
            <br><br>
            <form class="form-horizontal">
                <fieldset>

                <!-- Form Name -->
                <legend>Documentacion</legend>

                <!-- Select Basic -->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="selectbasic">Seleccione un alumno</label>
                    <div class="col-md-4">
                        <select id="alumno" name="selectbasic" class="form-control">
                            <option>Seleccione un alumno...</option>
                            <option>alumno 1</option>
                            <option>alumno 2</option>
                            <option>alumno 3</option>
                            <option>alumno 4</option>
                            <option>alumno 5</option>
                            <option>alumno 6</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <input class="btn-primary " type="button" id="buscar" value="Buscar">
                    </div>
                </div>

                </fieldset>
        </form>
        </div>
        
        <div id="documentacion" tabindex="-1" class="" role="dialog" class="container">
                    <div class="modal-header container">
                        <h4 class="modal-title">Nombre Alumno y rut</h4>
                    </div>
                    <div class="modal-body container">
                        <table class="table table-hover">
                            <thead>
                                <th>1</th>
                                <th>2</th>
                            </thead>
                            <tr>
                                <td>3</td>
                                <td>4</td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-header container">
                        <h4 class="modal-title">Subir Archivo</h4>
                    </div>
                    <div class="modal-body container">
                        <input type="file" multiple class="filestyle" data-buttonName="btn-primary" data-buttonBefore="true">
                        <div class="modal-footer">   
                            <button id="subir" type="button" class="btn btn-primary bfd-ok">Subir</button> 
                            <button id="cerrar" type="button" class="btn btn-default bfd-cancel" data-dismiss="modal">Cerrar</button> 
                        </div>  
                    </div>
        </div>
        
    </body>
</html>
