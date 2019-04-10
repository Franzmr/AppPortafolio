
$(document).ready(
        function(){
            $('#inicio').click(
                        function()
                        {
                            window.location.href = 'Inicio.jsp';
                        }
                    );
            
            $('#alumnos').click(
                        function()
                        {
                            window.location.href = 'Alumnos.jsp';
                        }
                    );
            
            $('#documentaciones').click(
                        function()
                        {
                            window.location.href = 'Documentaciones.jsp';
                        }
                    );
            
            $('#notas').click(
                        function()
                        {
                            window.location.href = 'Notas.jsp';
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

