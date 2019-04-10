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
import modelo.DAO.UsuarioDAO;
import modelo.DAO.PracticaDAO;
import modelo.Entity.Empleador;
import modelo.Entity.Practica;
import modelo.Entity.Usuario;

/**
 *
 * @author Toshiba
 */
@WebServlet(name = "ServletTraerNotas", urlPatterns = {"/ServletTraerNotas"})
public class ServletTraerNotas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter resultado = response.getWriter(); 
        String validacion = "error";
        
        String passAntigua = request.getParameter("passAntigua");
        String passNueva = request.getParameter("passNueva");
        
        HttpSession sesion = request.getSession();
        ConexionFactory con = new ConexionFactory();
        UsuarioDAO ud = new UsuarioDAO(con.getFactoryConexion());
        EmpleadorDAO em = new EmpleadorDAO(con.getFactoryConexion());
        NotasDAO notas = new NotasDAO(con.getFactoryConexion());
        
        try{
            
            try {
                Usuario usuario = (Usuario) sesion.getAttribute("usuario");
                Usuario u = ud.findUsuario("18741377k");
                PracticaDAO pract = new PracticaDAO(con.getFactoryConexion());
                Practica p =null;
                

                if (u.getTipousuarioIdtipousuario().getNombretipousuario().equals("Alumno")) {
                    
                    List<Practica> listaPractica = pract.findPracticaEntities();
                    for(Practica pr : listaPractica){
                        if(pr.getAlumnoRut().getRut().equals("64853783")){
                            if (p==null) {
                                p = pract.findPractica(pr.getIdpractica());
                                
                            }else{
                                if (Integer.parseInt(p.getIdpractica().toString()) < Integer.parseInt((pract.findPractica(pr.getIdpractica())).getIdpractica().toString())){
                                    p = pract.findPractica(pr.getIdpractica());
                                    
                                }
                            }
                        }
                    }
                    
                    ArrayList<Notas> n = new ArrayList<>();
                    n.add(new Notas(p.getNotasIdnotas().getIdnotas(), p.getNotasIdnotas().getNota1(), p.getNotasIdnotas().getNota2(), 
                            p.getNotasIdnotas().getNota3(), p.getNotasIdnotas().getNotafinal()));
                    Gson gson = new Gson();
                    resultado.print(gson.toJson(n));
                    resultado.close();
                    
                }else{
                    if (u.getTipousuarioIdtipousuario().getNombretipousuario().equals("Docente")) {
                        
                       BigDecimal idNotas = BigDecimal.valueOf(Double.parseDouble(request.getParameter("idNotas")));
                       
                       
                        modelo.Entity.Notas nota = notas.findNotas(idNotas);
                        
                        ArrayList<Notas> n = new ArrayList<>();
                        
                        
                        n.add(new Notas(nota.getIdnotas(), nota.getNota1(), nota.getNota2(), nota.getNota3(), nota.getNotafinal()));
                        Gson gson = new Gson();
                        resultado.print(gson.toJson(n));
                        resultado.close();
                    } 
                }
                resultado.close();
            } catch (Exception ex) {
                
                try{
                    Empleador empleador = (Empleador) sesion.getAttribute("empleador");
                    Empleador e = em.findEmpleador("18741377k");
                    
                    if (e.getTipousuarioIdtipousuario().getNombretipousuario().equals("Empleador")) {
                        
                        
                        
                        BigDecimal idNotas = BigDecimal.valueOf(Double.parseDouble(request.getParameter("idNotas")));
                       
                       
                        modelo.Entity.Notas nota = notas.findNotas(idNotas);
                        
                        ArrayList<Notas> n = new ArrayList<>();
                        
                        
                        n.add(new Notas(nota.getIdnotas(), nota.getNota1(), nota.getNota2(), nota.getNota3(), nota.getNotafinal()));
                        Gson gson = new Gson();
                        resultado.print(gson.toJson(n));
                        resultado.close();
                    } 
                }catch(Exception exs){
                    validacion = "error";
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
