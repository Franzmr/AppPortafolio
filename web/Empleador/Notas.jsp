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
            
            $('#cerrar').click(function (){
                var Evaluacion = $('#evaluacionNumero').val();
                var nota = ($('#nota').val());
                
                if(Evaluacion == "Evaluacion Nota 1"){
                    $('#not1').val(nota);
                    alert(nota);
                }
                if(Evaluacion == "Evaluacion Nota 2"){
                    $('#not2').val(nota);
                }
                if(Evaluacion == "Evaluacion Nota 3"){
                    $('#not3').val(nota);
                }
            });
            
            
            function obtenerPracticas(){
                   $.ajax({
                       type: 'POST',
                       url: "../ServletTraerAlumnos",
                       async: true,
                       cache: false,
                       success: function (resultado) {
                          $.each(resultado, function (index, valor){
                                var opcion = "Nombre: " + valor.NombreAlumno + " Rut:" + valor.rutAlumno;
                                $('#alumno').append($("<option>",{value:valor.idNotas,text:opcion}));
                           });
                       }
                   });
            }
            
            function obtenerNotas(){
                var idNotas = $('#alumno').val();
                if(idNotas != "0"){
                    $('#documentacion').hide();
                    $.ajax({
                        type: 'POST',
                        data: {idNotas: idNotas},
                        url: "../ServletTraerNotas",
                        async: false,
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
            };
            
            
            $(document).mousemove(function (){
                $('#puntaje').val(puntajeFinal()); 
            });
            
            function puntajeItem1(){
                var ip1 = $('input[name=ip1]:checked').val();
                var ip2 = $('input[name=ip2]:checked').val();
                var ip3 = $('input[name=ip3]:checked').val();
                
                if(isNaN(parseInt(ip1)))
                    ip1 = "0";
                
                if(isNaN(parseInt(ip2)))
                    ip2 = "0";
                
                if(isNaN(parseInt(ip3)))
                    ip3 = "0";
                
                var resultado = (parseInt(ip1) + parseInt(ip2) + parseInt(ip3))*1.2;
                return resultado;
            };
            
            function puntajeItem2(){
                var ip1 = $('input[name=iip1]:checked').val();
                var ip2 = $('input[name=iip2]:checked').val();
                var ip3 = $('input[name=iip3]:checked').val();
                
                if(isNaN(parseInt(ip1)))
                    ip1 = "0";
                
                if(isNaN(parseInt(ip2)))
                    ip2 = "0";
                
                if(isNaN(parseInt(ip3)))
                    ip3 = "0";
                
                var resultado = (parseInt(ip1) + parseInt(ip2) + parseInt(ip3))*1.5;
                return resultado;
            };
            
            function puntajeItem3(){
                var ip1 = $('input[name=iiip1]:checked').val();
                var ip2 = $('input[name=iiip2]:checked').val();
                var ip3 = $('input[name=iiip3]:checked').val();
                
                if(isNaN(parseInt(ip1)))
                    ip1 = "0";
                
                if(isNaN(parseInt(ip2)))
                    ip2 = "0";
                
                if(isNaN(parseInt(ip3)))
                    ip3 = "0";
                
                var resultado = (parseInt(ip1) + parseInt(ip2) + parseInt(ip3))*1.8;
                return resultado;
            };
            
            function puntajeFinal(){
                var item1 = puntajeItem1();
                var item2 = puntajeItem2();
                var item3 = puntajeItem3();
                
                if(isNaN(parseInt(item1)))
                    item1 = "0";
                
                if(isNaN(parseInt(item2)))
                    item2 = "0";
                
                if(isNaN(parseInt(item3)))
                    item3 = "0";
                
                var resultado = parseInt(item1) + parseInt(item2) + parseInt(item3);
                return resultado;
            };
            
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
                
                
            $('#calcularNota').click(function (){
               var puntaje = $('#puntaje').val();
                   $.ajax({
                        type: 'POST',
                        data: {puntaje: puntaje},
                        url: '../evaluar',
                        success: function (resultado){
                            $('#nota').val(resultado);
                        }
                   }); 
                document.getElementById("btnGuardar").style.visibility = "visible";
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
                
            $('#buscar').click(function (){
                obtenerNotas();
                $('#documentacion').show();
                
                if($("#not1").val() != "0"){
                    if($("#not2").val() != "0"){
                        if($("#not3").val() != "0"){
                            $("#evaluar").hide();
                        }else{
                            $('#notaEvaluar').html("");
                            $('#notaEvaluar').html(" nota 3");
                            $('#evaluacionN').html("");
                            $('#evaluacionNumero').val("Evaluacion Nota 3");
                            $('#rutNombre').html("");
                            $('#rutNombre').html("RUT: 0-0 NOMBRE: Lalalalala");
                            $("#evaluar").show();
                        }
                    }else{
                        $('#notaEvaluar').html("");
                        $('#notaEvaluar').html(" nota 2");
                        $('#evaluacionN').html("");
                        $('#evaluacionNumero').val("Evaluacion Nota 2");
                        $('#rutNombre').html("");
                        $('#rutNombre').html("RUT: 0-0 NOMBRE: Lalalalala");
                        $("#evaluar").show();
                    }
                }else{
                    $('#notaEvaluar').html("");
                    $('#notaEvaluar').html(" nota 1");
                    $('#evaluacionN').html("");
                    $('#evaluacionNumero').val("Evaluacion Nota 1");
                    $('#rutNombre').html("");
                    $('#rutNombre').html("RUT: 0-0 NOMBRE: Lalalalala");
                    $("#evaluar").show();
                }
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
        
        <div id="documentacion" tabindex="-1" class="container">
            <a href="" data-toggle="modal" id="evaluar" data-target="#evaluando" style="color: #003865;">Evaluar<span id="notaEvaluar"></span></a>
            <div class="modal-header container">
                <h4 class="modal-title">Nombre Alumno rut </h4>
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
                        <td><input id="not1" disabled type="text" value="0.0" min="1" max="70"/></td>
                        <td><input id="not2" disabled type="text" value="0.0" min="1" max="70"/></td>
                        <td><input id="not3" disabled type="text" value="0.0" min="1" max="70"/></td>
                        <td><input id="notF" disabled type="text" value="0.0" min="1" max="70"/></td>
                    </tr>
                </table>
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
        
            
        <div id="evaluando" class="modal fade" role="dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="evaluacionN" >Evaluacion</h4>
                    <input type="text" id="evaluacionNumero" disabled>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal container" id="evaluacion">
                        <fieldset>

                        <!-- Form Name -->
                        <legend id="rutNombre">Evaluacion</legend>

                        <!-- Multiple Radios (inline) -->
                        <div class="form-group">

                            <table class="table table-hover">
                                <thead>
                                    <th>N°</th>
                                    <th>Pregunta</th>
                                    <th>Siempre</th>
                                    <th>Comunmente</th>
                                    <th>A veces</th>
                                    <th>Nunca</th>
                                </thead>
                                <tr>
                                    <td colspan="6" style="font-weight: bold">Item I: Evaluacion Generales</td>
                                </tr>
                                <tr>
                                    <td style="font-weight: bold">1</td>
                                    <td style="font-style: italic">¿El practicante siempre ha sido puntual en todos sus compromisos?</td>
                                    <td><input type="radio" name="ip1" value="3"></td>
                                    <td><input type="radio" name="ip1" value="2"></td>
                                    <td><input type="radio" name="ip1" value="1"></td>
                                    <td><input type="radio" name="ip1" value="0"></td>
                                </tr>
                                <tr>
                                    <td style="font-weight: bold">2</td>
                                    <td style="font-style: italic">¿El practicante siempre ha sido responsable con todo lo que se ha propuesto?</td>
                                    <td><input type="radio" name="ip2" value="3"></td>
                                    <td><input type="radio" name="ip2" value="2"></td>
                                    <td><input type="radio" name="ip2" value="1"></td>
                                    <td><input type="radio" name="ip2" value="0"></td>
                                </tr>
                                <tr>
                                    <td style="font-weight: bold">3</td>
                                    <td style="font-style: italic">¿El practicante siempre a mostrado respeto y tolerancia hacia lo demas?</td>
                                    <td><input type="radio" name="ip3" value="3"></td>
                                    <td><input type="radio" name="ip3" value="2"></td>
                                    <td><input type="radio" name="ip3" value="1"></td>
                                    <td><input type="radio" name="ip3" value="0"></td>
                                </tr>
                                <tr>
                                    <td colspan="6" style="font-weight: bold">Item II: Evaluacion en Equipo</td>
                                </tr>                    
                                <tr>
                                    <td style="font-weight: bold">1</td>
                                    <td style="font-style: italic">¿El practicante comunmente busca la instancia de comunicacion con su equipo de trabajo?</td>
                                    <td><input type="radio" name="iip1" value="3"></td>
                                    <td><input type="radio" name="iip1" value="2"></td>
                                    <td><input type="radio" name="iip1" value="1"></td>
                                    <td><input type="radio" name="iip1" value="0"></td>
                                </tr>
                                <tr>
                                    <td style="font-weight: bold">2</td>
                                    <td style="font-style: italic">¿El practicante es cumplidor con lo que se compromete o asignaron?</td>
                                    <td><input type="radio" name="iip2" value="3"></td>
                                    <td><input type="radio" name="iip2" value="2"></td>
                                    <td><input type="radio" name="iip2" value="1"></td>
                                    <td><input type="radio" name="iip2" value="0"></td>
                                </tr>
                                <tr>
                                    <td style="font-weight: bold">3</td>
                                    <td style="font-style: italic">¿El practicante busca informacion o ayuda cuando no logra desarrollar un problema o desafio?</td>
                                    <td><input type="radio" name="iip3" value="3"></td>
                                    <td><input type="radio" name="iip3" value="2"></td>
                                    <td><input type="radio" name="iip3" value="1"></td>
                                    <td><input type="radio" name="iip3" value="0"></td>
                                </tr>
                                <tr>
                                    <td colspan="6" style="font-weight: bold">Item III: Evaluacion Individual</td>
                                </tr>  
                                <tr>
                                    <td style="font-weight: bold">1</td>
                                    <td style="font-style: italic">¿El practicante es proactivo?</td>
                                    <td><input type="radio" name="iiip1" value="3"></td>
                                    <td><input type="radio" name="iiip1" value="2"></td>
                                    <td><input type="radio" name="iiip1" value="1"></td>
                                    <td><input type="radio" name="iiip1" value="0"></td>
                                </tr>
                                <tr>
                                    <td style="font-weight: bold">2</td>
                                    <td style="font-style: italic">¿El practicante asume y aprende de sus errores?</td>
                                    <td><input type="radio" name="iiip2" value="3"></td>
                                    <td><input type="radio" name="iiip2" value="2"></td>
                                    <td><input type="radio" name="iiip2" value="1"></td>
                                    <td><input type="radio" name="iiip2" value="0"></td>
                                </tr>
                                <tr>
                                    <td style="font-weight: bold">3</td>
                                    <td style="font-style: italic">¿El practicante rinde bajo presion o pruebas complejas?</td>
                                    <td><input type="radio" name="iiip3" value="3"></td>
                                    <td><input type="radio" name="iiip3" value="2"></td>
                                    <td><input type="radio" name="iiip3" value="1"></td>
                                    <td><input type="radio" name="iiip3" value="0"></td>
                                </tr>
                                <tr>
                                    <td colspan="5" style="font-weight: bold" class="text-right">Puntuacion</td>
                                    <td><input type="text" id="puntaje" disabled></td>
                                </tr> 
                                <tr>
                                    <td  colspan="5" style="font-weight: bold" class="text-right">Nota</td>
                                    <td><input type="text" id="nota" disabled></td>
                                </tr> 
                            </table>
                    </fieldset>
                        <div class="form-group">
                            <input type="button" class="btn btn-primary" id="calcularNota" value="Calcular Nota">
                            <input type="button" class="btn btn-primary" id="cerrar" data-dismiss="modal" value="Cerrar">
                        </div>
                        <div class="form-group">
                            <div class="center-block alert alert-warning" id="mensaje"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
