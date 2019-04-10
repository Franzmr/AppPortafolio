<%-- 
    Document   : index
    Created on : 12-06-2016, 21:01:41
    Author     : Toshiba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Inicio</title>
        <script src="Estilo/js/jquery-2.2.4.min.js" type="text/javascript"></script>
        <script src="Estilo/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="Estilo/js/jquery.validationEngine.js" type="text/javascript"></script>
        
        <link href="Estilo/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        
        <script>
            $(document).ready(function (){
                $('#error').hide();
                
                $('#rut').focus(function (){
                    $('#error').hide();
                });
                
                $('#password').focus(function (){
                   var rut = $('#rut').val();
                   $.ajax({
                      type: 'POST',
                      data: {rut: rut},
                      url: 'validarRut',
                      success: function (resultado){
                          if (resultado == "false") {
                              $('#error').html('<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>\
                                <span class="sr-only">Error:</span> Rut invalido');
                              $('#error').show();
                          }
                      }
                   });
                });
                
                $('#entrar').click(function(event) {
                        var rut =  $('#rut').val();
                        var password = $('#password').val();
                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.ajax({
                      type: 'POST',
                      data: {rut: rut, password: password},
                      url: 'ServletValidarUsuario',
                      success: function (resultado){
                          if (resultado == "alumno") {
                              window.location.href = 'Alumno/Inicio.jsp';
                          }
                          if (resultado == "docente") {
                              window.location.href = 'Docente/Inicio.jsp';
                          }
                          if (resultado == "empleador") {
                              window.location.href = 'Empleador/Inicio.jsp';
                          }
                          if (resultado == "error") {
                              $('#error').html('<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>\
                                <span class="sr-only">Error:</span> Rut y/o password erroneos');
                              $('#error').show();
                          }
                      }
                   });
                });
                
            });
            
               
            
            
            
           /* 
            $('#entrar').click(function(event) {
                        var rut =  $('#rut').val();
                        var pass = $('#password').val();
                        alert("Bien2");
                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.post('ServletValidarUsuario', {
                                rut : rut,
                                password: pass
                        }, function(resultado) {
                                alert("Bien");
                        });
                        alert("Bien3");
                });
            */
            
            /*
            $(document).ready(function (){
                $("#mensaje").hide();
                
                $('#agregar').on('hide.bs.modal', function (){
                   limpiarFormularioGuardar();
                   $("#mensaje").hide();
                });
                
                $("#FormularioGuardar").validationEngine();
                
                $("#guardar").click(
                    function (){
                        $("#mensaje").hide();
                        var res=$("#FormularioGuardar").validationEngine("Validate");
                        if(res){
                            var datos=$("#FormularioGuardar").serialize();

                            $.ajax({
                                type: "POST",
                                data: datos,
                                url: "../envios/guardarCliente.php",
                                success: function (data) {
                                    if (data == "Guardado") {
                                        limpiarFormularioGuardar();
                                        $('#agregar').modal('toggle');
                                    }else{
                                        $("#mensaje").show();
                                        $("#mensaje").html("El Cliente ya existe!!!");
                                        $("#rut").select();
                                        $("#rut").focus();
                                    }
                                }
                            });
                        }
                    }      
                );
        
                function limpiarFormularioGuardar(){
                    var $inputs = $('#FormularioGuardar :input[type!=button]');
                    $inputs.each(function (){
                       $(this).val(""); 
                    });
                }
            });
            */
        </script>
    </head>
    <body>
        <div class="container" ><div style="height: 70px"></div></div>
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-md-4 col-md-offset-4">
                    <h1 class="text-center login-title">Inicio de Sesion</h1>
                    <div class="account-wall">
                        <div align="center">
                            <img class="profile-img" src="Estilo/imagenes/Isotipo.jpg" alt="" height="120px" />
                        </div><br>
                        <form class="form-signin" >
                            <input type="text" id="rut" class="form-control" placeholder="Rut..." required autofocus>
                            <input type="password" id="password" class="form-control" placeholder="Password..." required><br>
                            <button class="btn btn-lg btn-primary btn-block" id="entrar" type="button">Iniciar Sesion</button>
                            <a id="ayudar" href="" class="pull-right need-help" data-toggle="modal" data-target="#ayuda" style="color: #003865">¿Necesitas Ayuda?</a>
                        <span class="clearfix"></span>
                        </form>
                    </div>
                    <div id="error" class="alert alert-danger" role="alert">
                        
                    </div>
                </div>
            </div>
            <br><br><br><br><br>
            <div align="center" class="col-sm-6 col-md-4 col-md-offset-4">
                <a href="http://portal.duoc.cl" target="_blank"><img src="Estilo/imagenes/logotipo.jpg" alt="" style="width: 50%"/></a>
            </div>
            <br><br><br>
        </div>
        
        
        <div id="ayuda" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Ayuda</h4>
                    </div>
                    <div class="modal-body">
                        <div> 
                            <div style="height: 70%" align="center"><img src="Estilo/imagenes/ayuda.png" alt="" /><br></div>
                            <div>Recuerda siempre colocar tu rut no importa si esta con punto y/o guion.<br>
                                 Ingresar tu password o clave secreta.<br>
                                 <div style="color: red">Recuerda que el password es secreto, nunca se lo entregus a nadie</div><br>
                                 <div style="color: #ff9900">Si llegas a olvidar tu contraseña o te han robado tu usuario, ve a donde tu coordinador inmediatamente!</div>
                            </div>
                            <div class="form-group" align="right">
                                <input type="button" class="btn btn-primary" id="cerrar" data-dismiss="modal" value="Cerrar">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

