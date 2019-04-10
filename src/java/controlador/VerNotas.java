/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import conexion.ConexionFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.DAO.PracticaDAO;
import modelo.DAO.UsuarioDAO;
import modelo.Entity.Practica;
import modelo.Entity.Usuario;

/**
 *
 * @author Francisco
 */
@WebServlet(name = "VerNotas", urlPatterns = {"/VerNotas"})
public class VerNotas extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
            ConexionFactory con = new ConexionFactory();
            PracticaDAO pd = new PracticaDAO(con.getFactoryConexion());
            UsuarioDAO ud = new UsuarioDAO(con.getFactoryConexion());
            
//            EntityManager em = Persistence.createEntityManagerFactory("AppWebPU").createEntityManager();
            
//            List<Practica> lista = em.createNamedQuery("Practica.findAll").getResultList();

            String rut = request.getParameter("rut");
            ud.findUsuario(rut);
            
            String idPractica;
            int nota1;
            int nota2;
            int nota3;
            int NotaFinal;
                   
            
            HttpSession sesion = request.getSession(true);    
            String id=sesion.getAttribute("idPractica").toString();            
            List<Practica> lista = pd.findPracticaEntities();
            
            
            
//            for(Practica pa : lista){
//                if(pa.getIdpractica().equals(id)){
//                    idPractica = pa.getIdpractica().toString();
//                    nota1 = java.math.BigDecimal( );
//                }
//            }
            
            
            
                
            
            RequestDispatcher rd = request.getRequestDispatcher("verNotas.jsp");
            rd.forward(request, response);
            
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
