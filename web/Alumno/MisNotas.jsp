<%-- 
    Document   : misNotas
    Created on : 27-06-2016, 3:08:06
    Author     : Toshiba
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="modelo.Entity.Usuario"%>
<%@page import="modelo.Entity.Notas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%  
            HttpSession sesion = request.getSession();      
            Usuario u = (Usuario) sesion.getAttribute("usuario");
            %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../Estilo/js/script.html"></jsp:include>
        <script src="navegar.js" type="text/javascript"></script>
        <title>Mis Notas</title>
        <script>
            $(document).ready(function (){
                cargarNotas();
                
                function cargarNotas(){
                    alert('hola');
                    $.ajax({
                    url: '../ServletTraerNotas',
                    async: true,
                    cache: false,
                        success: function (resultado){
                            $.each(resultado, function (index, notas){
                                $('#nota1').html(notas.nota1);
                                $('#nota2').html(notas.nota2);
                                $('#nota3').html(notas.nota3);
                                $('#notaFinal').html(notas.notaFinal);
                            });
                        }
                    });
                }
                
               
            });
        </script>
              <% 
                  
            
            
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
                     <div class="hola text-center" id="hola">Hola: </div>
                </div>
            </div>
            <div class="col-md-8 encabezado" >
                <div class="navbar">
                    <ul class="nav nav-pills nav-justified"> 
                        <li role="presentation" >
                            <a id="inicio">Inicio</a></li> 
                        <li role="presentation" >
                            <a id="documentacion">Documentacion</a></li> 
                        <li role="presentation" class="active">
                            <a id="misNotas">Mis Notas</a></li> 
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
            <div><h2 class="text-center" id="bienvenida">Mis Notas </h2></div>
            <br><br><br><br>
            <a href="" data-toggle="modal" data-target="#reclamar" style="color: #003865;">¿Enviar reclamo por correo?</a>
            <div class="row">
                <table class="table table-hover">
                    <thead>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                        <th>Nota 3</th>
                        <th>Nota Final</th>
                    </thead>
                    <tr>
                        <td id="nota1"></td>
                        <td id="nota2"></td>
                        <td id="nota3"></td>
                        <td id="notaFinal"></td>
                    </tr>
                </table>
            </div>
        </div>
        <input type="button" value="hola" id="a">
        
        <div id="reclamar" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Medios de envio</h4>
                    </div>
                    <div class="modal-body text-center">
                        <a href="http://www.duoc.cl/simplesaml1/module.php/core/loginuserpass.php?AuthState=_ceb4ca87154bdbc067f495b80e4f5de929cb81d825%3Ahttp%3A%2F%2Fwww.duoc.cl%2Fsimplesaml1%2Fsaml2%2Fidp%2FSSOService.php%3Fspentityid%3Dgoogle.com%26cookieTime%3D1467159896%26RelayState%3Dhttps%253A%252F%252Fwww.google.com%252Fa%252Falumnos.duoc.cl%252FServiceLogin%253Fservice%253Dmail%2526passive%253Dtrue%2526rm%253Dfalse%2526continue%253Dhttps%25253A%25252F%25252Fmail.google.com%25252Fmail%25252F%2526ss%253D1%2526ltmpl%253Ddefault%2526ltmplcache%253D2%2526emr%253D1%2526osid%253D1" target="_blank">
                            <img src="../Estilo/imagenes/logotipo.jpg" alt="" style="width: 50%"/>
                        </a>
                        <a href="http://gmail.com" target="_blank"><img src="../Estilo/imagenes/gmail.png" style="width: 24%" alt=""/></a>
                        <br>
                        <a href="http://hotmail.cl" target="_blank"><img src="../Estilo/imagenes/hotmail.png" alt="" style="width: 40%"/></a>
                        
                        <a href="https://login.yahoo.com" target="_blank"><img src="../Estilo/imagenes/yahoo.png" alt="" style="width: 50%"/></a>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default bfd-cancel" data-dismiss="modal">Close</button> 
                        </div>      
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
