/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import conexion.ConexionFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.DAO.DocumentacionDAO;
import modelo.DAO.PracticaDAO;
import modelo.Entity.Documentacion;
import modelo.Entity.Practica;


/**
 *
 * @author Francisco
 */
@WebServlet(name = "ServletSubirArchivo", urlPatterns = {"/ServletSubirArchivo"})
public class ServletSubirArchivo extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        
        InputStream inputStream = null;
        
        String ID = request.getParameter("id");
        String IDPRACTICA = request.getParameter("idpractica");
        java.math.BigDecimal id = new java.math.BigDecimal(String.valueOf(ID));
        java.math.BigDecimal idpractica = new java.math.BigDecimal(String.valueOf(IDPRACTICA));
        
        String nombre = request.getParameter("nombre");
        String fecha = request.getParameter("fecha");
        double peso = Double.parseDouble(request.getParameter("peso"));
        
        
        
        ConexionFactory con = new ConexionFactory();
        
//        DocumentacionDAO dd = new DocumentacionDAO(con.getFactoryConexion());
//        Documentacion d = new Documentacion();
        
//        PracticaDAO pd = new PracticaDAO(con.getFactoryConexion());
//        Practica p = pd.findPractica(new java.math.BigDecimal(String.valueOf(1)));
//        
//        d.setIddocumentacion(id);
//        d.setNombredocumentacion(nombre);
//        d.setDocumentacion((Serializable) filePart);
//        d.setFecha(fecha);
//        d.setPeso(peso);
//        d.setPracticaIdpractica(p);
//        
//        dd.create(d);
//        RequestDispatcher rs = request.getRequestDispatcher("subirArchivo.jsp");
//        rs.forward(request, response);
        Part filePart = request.getPart("archivo");
        
        if(filePart!= null){
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            
            inputStream = filePart.getInputStream();
        }
        
        // JDBC driver name and database URL
            
        
        
        try{
           Connection conn = (Connection)con.getFactoryConexion();
           
           String sql ="insert into documentacion (iddocumentacion, nombredocumentacion, documentacion, fecha, peso, practica_idpractica) values(?,?,?,?,?,?)";
           
           PreparedStatement statement =  (PreparedStatement) conn.prepareStatement(sql);
           
           statement.setBigDecimal(1, id);
           statement.setString(2, nombre);
//           statement.setBlob(3, inputStream);
           statement.setString(4, fecha);
           statement.setDouble(5, peso);
           statement.setBigDecimal(6, idpractica);
           
           if(inputStream !=null){
               statement.setBinaryStream(3,inputStream, (int)filePart.getSize());
           }
           
           int row = statement.executeUpdate();
           
           if(row>0){
               out.println("Archivo subido");
               
               conn.close();
               RequestDispatcher rs = request.getRequestDispatcher("subirArchivo.jsp");
               rs.include(request, response);
           }else{
               out.print("No se pudo subir el archivo");
               conn.close();
               
               RequestDispatcher rs = request.getRequestDispatcher("subirArchivo.jsp");
               rs.forward(request, response);
           }
            
            
        }catch(Exception ex){
           String msj = "error";
           request.setAttribute("msj", msj);
           RequestDispatcher rs = request.getRequestDispatcher("subirArchivo.jsp");
               rs.forward(request, response);
        }
        
        
//        archivo = new File(ar);
        
//        d.setDocumentacion(archivo);
        
        
        
        
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletSubirArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletSubirArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
