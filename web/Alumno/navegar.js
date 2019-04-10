
$(document).ready(
        function(){
            $('#inicio').click(
                        function()
                        {
                            window.location.href = 'Inicio.jsp';
                        }
                    );
            
            $('#documentacion').click(
                        function()
                        {
                            window.location.href = 'Documentacion.jsp';
                        }
                    );
            
            $('#misNotas').click(
                        function()
                        {
                            window.location.href = 'MisNotas.jsp';
                        }
                    );
            
            $('#misDatos').click(
                        function()
                        {
                            window.location.href = 'MisDatos.jsp';
                        }
                    );
            
             $('#cambiarContraseña').click(
                        function()
                        {
                            window.location.href = 'CambiarContraseña.jsp';
                        }
                    );
            
            $('#salir').click(
                        function()
                        {
                            window.location.href = '../Inicio.jsp';
                        }
                    );
        });

