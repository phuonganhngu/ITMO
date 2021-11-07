/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;


import connection.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.imageio.spi.ServiceRegistry;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import myPack.Humandb;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistryBuilder;

public class HumanORM {
    private static Session session;
    private static Transaction ts;
    
    public static void create(Humandb h){
        try  
        { 
        session = HibernateUtil.getSessionFactory().openSession();
        ts = session.beginTransaction();
        session.save(h);
        ts.commit();
        System.out.println("NewUser saved " +  
                h.toString());
        session.close();
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
            session.getTransaction().rollback();  
        } 
    }
    
   
    public static void delete(Humandb h)  
    {  
        Session session = HibernateUtil.getSessionFactory().openSession();  
        try  
        {  
            session.beginTransaction();  
            session.delete(h);  
            session.getTransaction().commit();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
            session.getTransaction().rollback();  
        }  
        session.close();  
    }  
    public static void update(Humandb h)  
    {  
        Session session = HibernateUtil.getSessionFactory().openSession();  
        try  
        {  
            session.beginTransaction();  
            session.update(h);  
            session.flush();  
            session.getTransaction().commit();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
            session.getTransaction().rollback();  
        }  
        session.close();  
    }  
    
    public static Integer getNextId() 
    {  
        Session session = HibernateUtil.getSessionFactory().openSession();  
        String hql = "select max(humanid) from Humandb";  
        Query query = session.createQuery(hql);  
        List < Integer > results = query.list();  
        Integer userId = 1;  
        if (results.get(0) != null)  
        {  
            userId = results.get(0) + 1;  
        }  
        session.flush();  
        session.close(); 
        //System.out.println(userId);
        return userId;  
    }
    
     public static List < Humandb > read() 
    {  
        Session session = HibernateUtil.getSessionFactory().openSession();  
        String hql = "from Humandb";  
        Query query = session.createQuery(hql);  
        List < Humandb > results = (List < Humandb > ) query.list();  

        session.flush();  
        session.close(); 
        //System.out.println( results.get(0).getName());
        return results;  
    }
   
//    public static void main(String arg[]){
//        Date in = new Date();
//        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
//        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//	LocalDate localDate = LocalDate.now();
//        Humandb h = new Humandb(getNextId(), "Lola","black", 50, 89,150, localDate.toString(), LocalDateTime.now());
//        create(h);
//        //delete(h);
//        //read();   
//    }
    
}
