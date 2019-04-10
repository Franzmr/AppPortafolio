function CambiarNotas()
            {
                document.getElementById("btnListo").style.visibility = "visible";
                document.getElementById("btnCambiar").style.visibility = "hidden";
                
                var not1 = document.getElementById("not1");
                var not2 = document.getElementById("not2");
                var not3 = document.getElementById("not3");
                                
                not1.value = (not1.value)*10;
                not2.value = (not2.value)*10;
                not3.value = (not3.value)*10;
                                
                not1.disabled = false;
                not2.disabled = false;
                not3.disabled = false;
                
                
            }
            
            function ListoNotas()
            {
                document.getElementById("btnListo").style.visibility = "hidden";
                document.getElementById("btnCambiar").style.visibility = "visible";
                document.getElementById("btnGuardar").style.visibility = "visible";
                
                var not1 = document.getElementById("not1");
                var not2 = document.getElementById("not2");
                var not3 = document.getElementById("not3");
                
                not1.disabled = true;
                not2.disabled = true;
                not3.disabled = true;
                
                var notF = (((parseInt(not1.value) + parseInt(not2.value) + parseInt(not3.value))/3)/10).toFixed(1);
                
                not1.value = (parseInt(not1.value)/10).toFixed(1);
                not2.value = (parseInt(not2.value)/10).toFixed(1);
                not3.value = (parseInt(not3.value)/10).toFixed(1);               
                
                document.getElementById("notF").value = notF;
            }