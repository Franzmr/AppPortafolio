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
 * @author Francisco
 */
@WebServlet(name = "ServletValidarUsuario", urlPatterns = {"/ServletValidarUsuario"})
public class ServletValidarUsuario extends HttpServlet {

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
        
        ConexionFactory con = new ConexionFactory();
        UsuarioDAO ud = new UsuarioDAO(con.getFactoryConexion());
        HttpSession sesion = request.getSession();
        String rut = request.getParameter("rut");
        String pass = request.getParameter("password");
        try {
            Usuario u = ud.findUsuario(rut);
            if (u.getRut().equals(rut) && u.getPassword().equals(pass)) {

                String nombre = u.getNombres();
                String ApellidoP = u.getApellidopaterno();
                String rutU = u.getRut();
                
                request.setAttribute("nombre", nombre);
                request.setAttribute("ApellidoP", ApellidoP);
                request.setAttribute("rutU", rutU);
                if (u.getTipousuarioIdtipousuario().getNombretipousuario().equals("Alumno")) {
//                        sesion.setAttribute("rut", u.getRut());
//                        sesion.setAttribute("nombre", u.getNombres());
//                        sesion.setAttribute("app", u.getApellidopaterno());
//                        sesion.setAttribute("apm", u.getApellidomaterno());
//                        sesion.setAttribute("dir", u.getDireccion());
//                        sesion.setAttribute("pass", u.getPassword());
//                        sesion.setAttribute("correo", u.getCorreo());
//                        sesion.setAttribute("telefono", u.getTelefono());
//                        sesion.setAttribute("estado", u.getEstado());
//                        sesion.setAttribute("comuna", u.getComunaIdcomuna());
//                        sesion.setAttribute("tipoUsu", u.getTipousuarioIdtipousuario());
//                        sesion.setAttribute("carrera", u.getAsociacionIdasociacion());
//                        sesion.setAttribute("sede", u.getAsociacionIdasociacion());

                        sesion.setAttribute("usuario", u);
                    
                        validacion = "alumno";
                        resultado.print(validacion);
                        resultado.close();
                } else {
                    if (u.getTipousuarioIdtipousuario().getNombretipousuario().equals("Docente")) {
//                        sesion.setAttribute("rut", u.getRut());
//                        sesion.setAttribute("nombre", u.getNombres());
//                        sesion.setAttribute("app", u.getApellidopaterno());
//                        sesion.setAttribute("apm", u.getApellidomaterno());
//                        sesion.setAttribute("dir", u.getDireccion());
//                        sesion.setAttribute("pass", u.getPassword());
//                        sesion.setAttribute("correo", u.getCorreo());
//                        sesion.setAttribute("telefono", u.getTelefono());
//                        sesion.setAttribute("estado", u.getEstado());
//                        sesion.setAttribute("comuna", u.getComunaIdcomuna());
//                        sesion.setAttribute("tipoUsu", u.getTipousuarioIdtipousuario());
//                        sesion.setAttribute("asociacion", u.getAsociacionIdasociacion());
                        sesion.setAttribute("usuario", u);

                        validacion = "docente";
                        resultado.print(validacion);
                        resultado.close();
                    } 
                }

            }else{
                validacion = "error1";
                resultado.print(validacion);
                resultado.close();
            }
        } catch (Exception ex) {
            
            try{

                EmpleadorDAO ed = new EmpleadorDAO(con.getFactoryConexion());
                Empleador e = ed.findEmpleador(rut);
                if (e.getRut().equals(rut) && e.getPassword().equals(pass)) {
                    
//                    sesion.setAttribute("rut", e.getRut());
//                    sesion.setAttribute("nombre", e.getNombrejefe());
//                    sesion.setAttribute("razon", e.getRazonsocial());
//                    sesion.setAttribute("nomfa", e.getNombrefantasia());
//                    sesion.setAttribute("direccion", e.getDireccion());
//                    sesion.setAttribute("correo", e.getCorreo());
//                    sesion.setAttribute("telefono", e.getTelefono());
//                    sesion.setAttribute("pass", e.getPassword());
//                    sesion.setAttribute("estado", e.getEstado());
//                    sesion.setAttribute("comuna", e.getComunaIdcomuna());
//                    sesion.setAttribute("tipoUsu", e.getTipousuarioIdtipousuario());

                    sesion.setAttribute("e", e);
                    
                    validacion = "empleador";
                    resultado.print(validacion);
                    resultado.close();
                }else{
                    validacion = "error1";
                    resultado.print(validacion);
                    resultado.close();
                }

                } catch (Exception exs) {
                        validacion = "error";
                        resultado.print(validacion);
                        resultado.close();
                }
        }
        resultado.close();
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
