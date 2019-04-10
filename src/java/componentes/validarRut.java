/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Toshiba
 */
@WebServlet(name = "validarRut", urlPatterns = {"/validarRut"})
public class validarRut extends HttpServlet {

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
        String validacion = "false";
        try {
            String rut = request.getParameter("rut");
            
                try {
                    rut = rut.toUpperCase();
                    rut = rut.replace(".", "");
                    rut = rut.replace("-", "");

                    int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

                    char dv = rut.charAt(rut.length() - 1);

                    int m = 0, s = 1;
                    for (; rutAux != 0; rutAux /= 10) {
                        s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
                    }
                    if (dv == (char) (s != 0 ? s + 47 : 75)) {
                        validacion = "";
                        validacion = "true";
                    }else{
                        validacion = "";
                        validacion = "false";
                    }
                } catch (java.lang.NumberFormatException e) {
                    } catch (Exception e) {
                }
            
            resultado.print(validacion);
        } finally {
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
