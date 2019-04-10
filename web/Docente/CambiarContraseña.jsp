<%-- 
    Document   : CambiarContraseña
    Created on : 27-06-2016, 3:44:37
    Author     : Toshiba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../Estilo/js/script.html"></jsp:include>
        <script src="navegar.js" type="text/javascript"></script>
        <title>Cambiar Contraseña</title>
        <%  
            HttpSession sesion = request.getSession();
            String nombre = (String) sesion.getAttribute("nombre");
        %>
        
        
        <script>
            $(document).ready(function (){
                $('#error').hide();
                $('#sinProblemas').hide();
                
                $(".close").click(function (){
                    window.location.href = 'Inicio.jsp';
                });
                
                $("#cerrar").click(function (){
                    window.location.href = 'Inicio.jsp';
                });
                
                $("#guardar").click(function (){
                    if(validarCambio()){
                        var passAntigua = $('#passAntigua').val();
                        var passNueva = $('#passNueva').val();
                        $.ajax({
                            type: 'POST',
                            data: {passNueva: passNueva, passAntigua: passAntigua},
                            url: '../ServletCambiarClave',
                            success: function (resultado){
                                if (resultado == "error1") {
                                    $('#error').html('<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>\
                                                        <span class="sr-only">Error:</span> Password antigua no coincide con la password actual existente.');
                                    $('#error').show();
                                }
                                if (resultado == "true") {
                                    $('#sinProblemas').html('<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>\
                                                        <span class="sr-only">Exito!!!</span> La password se ha cambiado correctamente!!!');
                                    $('#sinProblemas').show();
                                            $ ( '#listo' ). modal ( 'show' ); 
                                }
                                if (resultado == "error") {
                                    $('#error').html('<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>\
                                                        <span class="sr-only">Error:</span> Se produjo un error. Cierre el navegador y vuelva a abrirlo, \n\
                                                         si sigue teniendo problemas, contactese con servicio tecnico.');
                                    $('#error').show();
                                }
                            }
                        });
                    }
                });

                function validarCambio(){
                    var validado = true;
                    if($("#passNueva").val() != $("#passNueva2").val()){
                            $('#error').html('<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>\
                                    <span class="sr-only">Error:</span> Nueva contraseña y repeticion de esta no es la misma');
                                  $('#error').show();
                                  validado = false;
                        }
                        if($("#passAntigua").val() == "" || $("#passNueva").val() == "" || $("#passNueva2").val() == ""){
                            $('#error').html('<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>\
                                    <span class="sr-only">Error:</span> Llenar todos los cambios');
                                  $('#error').show();
                                  validado = false;
                        }
                        return validado;
                }

                $("#guardar").focusout(function (){
                    $('#error').hide();
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
                            <a id="documentacion">Documentacion</a></li> 
                        <li role="presentation" >
                            <a id="misNotas">Mis Notas</a></li> 
                        <li role="presentation" >
                            <a id="misDatos">Mis Datos</a></li> 
                        <li role="presentation" class="active">
                            <a id="cambiarContraseña">Cambiar Contraseña</a></li> 
                        <li role="presentation" >
                            <a id="salir">Salir</a></li> 
                        <br>
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="container">
            <br><br><br>
            <form class="form-horizontal" action="../ServletCambiarClave">
                <fieldset>

                <!-- Form Name -->
                <legend>Cambiar Contraseña</legend>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="passAntigua">Password Antigua</label>  
                  <div class="col-md-4">
                  <input id="passAntigua" name="passAntigua" type="text" placeholder="Password Antigua..." class="form-control input-md" required="">

                  </div>
                </div>
                <br>
                <div class="text-info text-center">Coloca tu contraseña actual y la nueva contraseña donde se te indica. Procura que sea la misma</div>
                <br>
                <br>

                <!-- Text input-->
                <div class="form-group" action="ServletCambiarClave">
                  <label class="col-md-4 control-label" for="passNueva">Password Nueva</label>  
                  <div class="col-md-4">
                  <input id="passNueva" name="passNueva" type="text" placeholder="Password Nueva..." class="form-control input-md" required="">

                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-md-4 control-label" for="passNueva2">Repetir Password Nueva</label>  
                  <div class="col-md-4">
                  <input id="passNueva2" name="passNueva2" type="text" placeholder="Repetir Password Nueva..." class="form-control input-md" required="">

                  </div>
                </div>
                </fieldset>
                
            </form>
                <br>
                
                <div >
                  <label class="col-md-4 control-label" for="guardar"></label>
                  <div class="col-md-4">
                    <button id="guardar" class="btn btn-primary">Guardar</button>
                  </div>
                </div>
                
                <br>
                <br>
                
                <div id="error" class="alert alert-danger" role="alert">
                </div>
                
               
                
                
                
            <div id="listo" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content alert alert-success">
                        <div class="alert alert-success" role="alert">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Exito</h4>
                                <div id="sinProblemas" >
                                </div>
                            <div class="form-group" align="right">
                                <input type="button" align="right" id="cerrar" class="form-group btn btn-primary" id="cerrar" data-dismiss="modal" value="Cerrar">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
