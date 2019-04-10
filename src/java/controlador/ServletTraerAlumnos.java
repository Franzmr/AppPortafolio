/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import conexion.ConexionFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DAO.PracticaDAO;
import modelo.DAO.UsuarioDAO;
import modelo.Entity.Practica;
import modelo.Entity.Usuario;

/**
 *
 * @author Toshiba
 */
public class ServletTraerAlumnos extends HttpServlet {
   
    public class AsociacionPractica {
        private String idPractica;
        private String rutAlumno;
        private String NombreAlumno;
        private String rutEmpleador;
        private String rutProfesor;
        private String idNotas;

        public AsociacionPractica() {
        }

        public AsociacionPractica(String idPractica, String rutAlumno, String NombreAlumno, String rutEmpleador, String rutProfesor, String idNotas) {
            this.idPractica = idPractica;
            this.rutAlumno = rutAlumno;
            this.NombreAlumno = NombreAlumno;
            this.rutEmpleador = rutEmpleador;
            this.rutProfesor = rutProfesor;
            this.idNotas = idNotas;
        }

        public String getIdPractica() {
            return idPractica;
        }

        public void setIdPractica(String idPractica) {
            this.idPractica = idPractica;
        }

        public String getRutAlumno() {
            return rutAlumno;
        }

        public void setRutAlumno(String rutAlumno) {
            this.rutAlumno = rutAlumno;
        }

        public String getNombreAlumno() {
            return NombreAlumno;
        }

        public void setNombreAlumno(String NombreAlumno) {
            this.NombreAlumno = NombreAlumno;
        }

        public String getRutEmpleador() {
            return rutEmpleador;
        }

        public void setRutEmpleador(String rutEmpleador) {
            this.rutEmpleador = rutEmpleador;
        }

        public String getRutProfesor() {
            return rutProfesor;
        }

        public void setRutProfesor(String rutProfesor) {
            this.rutProfesor = rutProfesor;
        }

        public String getIdNotas() {
            return idNotas;
        }

        public void setIdNotas(String idNotas) {
            this.idNotas = idNotas;
        }

    }

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter resultado = response.getWriter(); 
        ConexionFactory con = new ConexionFactory();
        PracticaDAO pract = new PracticaDAO(con.getFactoryConexion());
        List<Practica> listaPractica = pract.findPracticaEntities();
        ArrayList<AsociacionPractica> practicas = new ArrayList<>();
        UsuarioDAO ud = new UsuarioDAO(con.getFactoryConexion());
        try{
            
            try {
                
                Usuario u = ud.findUsuario("18741377k");
                String rut = u.getRut();
                for(Practica pr : listaPractica){
                    if(pr.getProfesorRut().getRut().equals(rut)){
                        
                        practicas.add(new AsociacionPractica(pr.getIdpractica().toString(), pr.getAlumnoRut().getRut(), 
                                pr.getAlumnoRut().getApellidopaterno()+ " " + pr.getAlumnoRut().getApellidomaterno() + " " + pr.getAlumnoRut().getNombres(),
                                pr.getEmpleadorRut().getRut(), pr.getProfesorRut().getRut(), pr.getNotasIdnotas().getIdnotas().toString()));

                    }
                }
                
                Gson gson = new Gson();
                //resultado.print(practicas.get(0).NombreAlumno);
                resultado.print(gson.toJson(practicas));
            } catch (Exception ex) {
               
               for(Practica pr : listaPractica){
                    if(pr.getEmpleadorRut().getRut().equals("18741377k")){
                        
                        practicas.add(new AsociacionPractica(pr.getIdpractica().toString(), pr.getAlumnoRut().getRut(), 
                                pr.getAlumnoRut().getApellidopaterno()+ " " + pr.getAlumnoRut().getApellidomaterno() + " " + pr.getAlumnoRut().getNombres(),
                                pr.getEmpleadorRut().getRut(), pr.getProfesorRut().getRut(), pr.getNotasIdnotas().getIdnotas().toString()));

                    }
                }
               
                Gson gson = new Gson();
                resultado.print(gson.toJson(practicas));
            }
            
        }finally{
            resultado.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
