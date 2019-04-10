/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import conexion.ConexionFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.DAO.EmpleadorDAO;
import modelo.DAO.UsuarioDAO;
import modelo.Entity.Empleador;
import modelo.Entity.Usuario;

/**
 *
 * @author Casa
 */
@WebServlet(name = "ServletCambiarClave", urlPatterns = {"/ServletCambiarClave"})
public class ServletCambiarClave extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter resultado = response.getWriter(); 
        String validacion = "error";
        
        String passAntigua = request.getParameter("passAntigua");
        String passNueva = request.getParameter("passNueva");
        
        HttpSession sesion = request.getSession();
        ConexionFactory con = new ConexionFactory();
        UsuarioDAO ud = new UsuarioDAO(con.getFactoryConexion());
        
        try{
            
            try {
                Usuario usuario = (Usuario) sesion.getAttribute("u");
                Usuario u = ud.findUsuario(usuario.getRut());
                if (u.getPassword().equals(passAntigua)) {

                    u.setPassword(passNueva);

                        if (u.getTipousuarioIdtipousuario().getNombretipousuario().equals("Alumno")) {
                            ud.edit(u);
                            sesion.setAttribute("pass", passNueva);
                            validacion = "true";
                        }else{
                            if (u.getTipousuarioIdtipousuario().getNombretipousuario().equals("Docente")) {
                                ud.edit(u);
                                sesion.setAttribute("pass", passNueva);
                                validacion = "true";
                            } 
                        }
                    
                }else{
                    validacion = "error1"; //No encuentra pass antigua en bbdd
                }
            } catch (Exception ex) {
                
                try{
                    EmpleadorDAO ed = new EmpleadorDAO(con.getFactoryConexion());
                    Empleador empleador = (Empleador) sesion.getAttribute("e");
                    Empleador e = ed.findEmpleador(empleador.getRut());
                        if (e.getPassword().equals(passAntigua)) {
                            e.setPassword(passNueva);
                            ed.edit(e);
                            sesion.setAttribute("pass", passNueva);
                            validacion = "true";
                        }else{
                            validacion = "error1"; //No encuentra pass antigua en bbd
                        }
                }catch(Exception exs){
                    validacion = "error";
                }
            }
            
            
            
            resultado.print(validacion);
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
