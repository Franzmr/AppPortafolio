/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Francisco
 */
public class ConexionFactory {
    private static ConexionFactory con;
    private EntityManagerFactory factoryCon;

    public ConexionFactory() {
        factoryCon = Persistence.createEntityManagerFactory("AppWebPU");
    }

    public EntityManagerFactory getFactoryConexion() {
        return factoryCon;
    }

    public static ConexionFactory getConexion() {
        if(con == null)
        {
            con = new ConexionFactory();
        }
        return con;
    }
}
