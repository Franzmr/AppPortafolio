/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.google.gson.Gson;
import conexion.ConexionFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.DAO.EmpleadorDAO;
import modelo.DAO.NotasDAO;
import modelo.DAO.PracticaDAO;
import modelo.DAO.UsuarioDAO;
import modelo.Entity.Empleador;
import modelo.Entity.Practica;
import modelo.Entity.Usuario;

/**
 *
 * @author Toshiba
 */
@WebServlet(name = "ServletGuardarNotas", urlPatterns = {"/ServletGuardarNotas"})
public class ServletGuardarNotas extends HttpServlet {
    
    public class Notas{
        private BigDecimal idNota;
        private double nota1;
        private double nota2;
        private double nota3;
        private double notaFinal;

        public Notas(BigDecimal idNota, double nota1, double nota2, double nota3, double notaFinal) {
            this.idNota = idNota;
            this.nota1 = nota1;
            this.nota2 = nota2;
            this.nota3 = nota3;
            this.notaFinal = notaFinal;
        }

        public Notas() {
        }

        public BigDecimal getIdNota() {
            return idNota;
        }

        public void setIdNota(BigDecimal idNota) {
            this.idNota = idNota;
        }

        public double getNota1() {
            return nota1;
        }

        public void setNota1(double nota1) {
            this.nota1 = nota1;
        }

        public double getNota2() {
            return nota2;
        }

        public void setNota2(double nota2) {
            this.nota2 = nota2;
        }

        public double getNota3() {
            return nota3;
        }

        public void setNota3(double nota3) {
            this.nota3 = nota3;
        }

        public double getNotaFinal() {
            return notaFinal;
        }

        public void setNotaFinal(double notaFinal) {
            this.notaFinal = notaFinal;
        }
    }

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
        
        
        HttpSession sesion = request.getSession();
        ConexionFactory con = new ConexionFactory();        
        NotasDAO notas = new NotasDAO(con.getFactoryConexion());
        PracticaDAO pract = new PracticaDAO(con.getFactoryConexion());
        Practica p = new Practica();
        
        try{
            
            try {
                UsuarioDAO ud = new UsuarioDAO(con.getFactoryConexion());
                Usuario usuario = (Usuario) sesion.getAttribute("usuario");
                Usuario u = ud.findUsuario("18741377k");
                
                

                if (u.getTipousuarioIdtipousuario().getNombretipousuario().equals("Docente")) {
                    
                    List<Practica> listaPractica = pract.findPracticaEntities();
                    
                    BigDecimal idNotas = BigDecimal.valueOf(Double.parseDouble(request.getParameter("idNotas")));
                    double nota1 = Double.parseDouble(request.getParameter("nota1"));
                    double nota2 = Double.parseDouble(request.getParameter("nota2"));
                    double nota3 = Double.parseDouble(request.getParameter("nota3"));
                    double notaFinal = Double.parseDouble(request.getParameter("notaFinal"));


                    modelo.Entity.Notas nota = new modelo.Entity.Notas(idNotas);
                    nota.setNota1(nota1);
                    nota.setNota2(nota2);
                    nota.setNota3(nota3);
                    nota.setNotafinal(notaFinal);
                    nota.setPracticaList(listaPractica);
                    
                    notas.edit(nota);
                    
                    resultado.print("true");
                    resultado.close();
                } 
                
                resultado.close();
            } catch (Exception ex) {
                
                try{
                    EmpleadorDAO em = new EmpleadorDAO(con.getFactoryConexion());
                    Empleador empleador = (Empleador) sesion.getAttribute("e");
                    Empleador e = em.findEmpleador("18741377k");
                    
                    
                    if (e.getTipousuarioIdtipousuario().getNombretipousuario().equals("Empleador")) {
                        
                        List<Practica> listaPractica = pract.findPracticaEntities();
                    
                        BigDecimal idNotas = BigDecimal.valueOf(Double.parseDouble(request.getParameter("idNotas")));
                        double nota1 = Double.parseDouble(request.getParameter("nota1"));
                        double nota2 = Double.parseDouble(request.getParameter("nota2"));
                        double nota3 = Double.parseDouble(request.getParameter("nota3"));
                        double notaFinal = Double.parseDouble(request.getParameter("notaFinal"));


                        modelo.Entity.Notas nota = new modelo.Entity.Notas(idNotas);
                        nota.setNota1(nota1);
                        nota.setNota2(nota2);
                        nota.setNota3(nota3);
                        nota.setNotafinal(notaFinal);
                        nota.setPracticaList(listaPractica);

                        notas.edit(nota);

                        resultado.print("true");
                        resultado.close();
                    } 
                    
                }catch(Exception exs){
                    resultado.print("error1"); // genera erro al guardar o no tiene una sesion asignada
                    resultado.close();
                }
            }
            
            
            
            //resultado.print(validacion);
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
