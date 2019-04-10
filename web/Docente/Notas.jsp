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
        <title>Notas</title>
        <link href="../Estilo/especial/chosen.css" rel="stylesheet" type="text/css"/>
        <script src="../Estilo/js/jquery-2.2.4.min.js" type="text/javascript"></script>
        <script src="../Estilo/especial/chosen.jquery.js" type="text/javascript"></script>
        
        
        <script>
        jQuery(document).ready(function(){
            
            vaciarAlumnos();
            obtenerPracticas();
            
            
            function vaciarAlumnos(){
                $('#alumno').empty();
                $('#alumno').append($("<option>",{value:"0",text:"Seleccione un alumno..."}));
            }
            
            
            function obtenerPracticas(){
                   $.ajax({
                       type: 'POST',
                       url: "../ServletTraerAlumnos",
                       async: true,
                       cache: false,
                       success: function (resultado) {
                          $.each(resultado, function (index, valor){
                                var opcion = "Nombre: " + valor.NombreAlumno + " Rut:" + valor.rutAlumno + " RutEmpresa: " + valor.rutEmpleador;
                                $('#alumno').append($("<option>",{value:valor.idNotas,text:opcion}));
                           });
                       }
                   });
            }
            
            $("#alumno").change(function(){
                alert($('select[id=alumno]').val());
                //$('#valor2').val($(this).val());
            });
            
            $('#buscar').click(function (){
                var idNotas = $('#alumno').val();
                if(idNotas != "0"){
                    $('#documentacion').hide();
                    $.ajax({
                        type: 'POST',
                        data: {idNotas: idNotas},
                        url: "../ServletTraerNotas",
                        async: true,
                        cache: false,
                        success: function (resultado) {
                           $.each(resultado, function (index, notas){
                                 $('#not1').val(notas.nota1);
                                 $('#not2').val(notas.nota2);
                                 $('#not3').val(notas.nota3);
                                 $('#notF').val(notas.notaFinal);
                                 //alert(notas.nota1);
                            });
                        }
                    });
                    $('#informacion').html($('#alumno option:selected').text());
                    $('#msn').hide();
                    $('#documentacion').show();
                }else{
                    $('#informacion').html("");
                    $('#documentacion').hide();
                }
            });
            
            
            $('#btnGuardar').click(function (){
                var idNotas = $('#alumno').val();
                var nota1 = $('#not1').val();
                var nota2 = $('#not2').val();
                var nota3 = $('#not3').val();
                var notaFinal = $('#notF').val();
                
                if(idNotas != "0"){
                    $('#documentacion').hide();
                    $.ajax({
                        type: 'POST',
                        data: {idNotas: idNotas, nota1: nota1, nota2: nota2, nota3: nota3, notaFinal: notaFinal},
                        url: "../ServletGuardarNotas",
                        async: false,
                        cache: false,
                        success: function (resultado) {
                           if (resultado == "true") {
                               $('#msn').html('Notas Guardadas');
                               $('#msn').show();
                            }
                            if(resultado == "error1"){
                                $('#msn').html('Se produjo un error, contactese con servicio tecnico.');
                                $('#msn').show();
                            }
                        }
                    });
                    $('#informacion').html($('#alumno option:selected').text());
                    $('#documentacion').show();
                }else{
                    $('#informacion').html("");
                    $('#documentacion').hide();
                }
            });
            
            
            $('#btnCancelar').click(function (){
                $('#documentacion').hide();
                document.getElementById("btnGuardar").style.visibility = "hidden";
            });
            
            $('#documentacion').hide();
            
            $('#btnCerrar').click(function (){
                $('#documentacion').hide();
                document.getElementById("btnGuardar").style.visibility = "hidden";
            });
                
            jQuery.getScript( "../Estilo/especial/choosen.js" )
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
                        <li role="presentation" >
                            <a id="documentaciones">Documentaciones</a></li> 
                        <li role="presentation" class="active">
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
            <div><h2 class="text-center" id="bienvenida">Notas </h2></div>
            <br><br>
            <form class="form-horizontal">
                <fieldset>

                <!-- Form Name -->
                <legend>Alumno</legend>

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
                        <h4 class="modal-title" id="informacion">Nombre Alumno rut y empresa</h4>
                    </div>
                    <div class="modal-body container">
                        <table class="table table-hover">
                            <thead>
                                <th>Nota 1</th>
                                <th>Nota 2</th>
                                <th>Nota 3</th>
                                <th>Nota Final</th>
                            </thead>
                            <tr>
                                <td><input id="not1" disabled type="number" value="0.0" min="1" max="70"/></td>
                                <td><input id="not2" disabled type="number" value="0.0" min="1" max="70"/></td>
                                <td><input id="not3" disabled type="number" value="0.0" min="1" max="70"/></td>
                                <td><input id="notF" disabled type="number" value="0.0" min="1" max="70"/></td>
                            </tr>
                        </table>
                        <div class="text-center">
                            <input id="btnCambiar" style="visibility: visible" type="button" class="btn btn-primary bfd-ok" value="Cambiar" onclick="CambiarNotas();"/>
                            <input id="btnListo" style="visibility: hidden" type="button" class="btn btn-primary bfd-ok" value="Listo" onclick="ListoNotas();"/>
                            <button id="btnCancelar" type="button" class="btn btn-danger bfd-ok">Cancelar</button> 
                        </div>
                    </div>
                    <div class="modal-body container">
                        <div id="msn" class="modal-footer alert-info">
                        </div>
                        <div class="modal-footer">   
                            <button id="btnGuardar" type="button" class="btn btn-primary bfd-ok" style="visibility: hidden">Guardar Cambios</button> 
                            <button id="btnCerrar" type="button" class="btn btn-default bfd-cancel" data-dismiss="modal">Cerrar</button> 
                        </div>  
                    </div>
        </div>
        
        
                               
                            
        
    </body>
</html>
