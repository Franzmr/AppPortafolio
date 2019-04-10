<%-- 
    Document   : Documentacion
    Created on : 26-06-2016, 20:04:02
    Author     : Toshiba
--%>

<%@page import="modelo.Entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%  
            HttpSession sesion = request.getSession();
            Usuario u = (Usuario) sesion.getAttribute("usuario");
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentacion</title>
        <jsp:include page="../Estilo/js/script.html"></jsp:include>
        <script src="navegar.js" type="text/javascript"></script>
        <script>
            $(document).ready(function (){
                $(":file").filestyle({buttonBefore: true});
                $(":file").filestyle({buttonName: "btn-primary"});
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
                     <div class="hola text-center" id="hola">Hola: <%=u.getNombres()%></div>
                </div>
            </div>
            <div class="col-md-8 encabezado" >
                <div class="navbar">
                    <ul class="nav nav-pills nav-justified"> 
                        <li role="presentation" >
                            <a id="inicio">Inicio</a></li> 
                        <li role="presentation" class="active">
                            <a id="documentacion">Documentacion</a></li> 
                        <li role="presentation">
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
            <div><h2 class="text-center" id="bienvenida">Documentacion </h2></div>
            <br><br><br><br>
            <div class="row">
                <a href="" data-toggle="modal" data-target="#subirDocumentacion" style="color: #003865;">Subir documentacion</a>
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
        </div>
        
        <div id="subirDocumentacion" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Subir documentacion</h4>
                    </div>
                    <div class="modal-body">
                        <input type="file" multiple class="filestyle" data-buttonName="btn-primary" data-buttonBefore="true">
                        <div class="modal-footer">   
                            <button type="button" class="btn btn-primary bfd-ok">Subir</button> 
                            <button type="button" class="btn btn-default bfd-cancel" data-dismiss="modal">Close</button> 
                        </div>      
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
