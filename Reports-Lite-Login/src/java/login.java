/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
public class login extends HttpServlet {

    
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html");  
       PrintWriter out = response.getWriter();
       String user = request.getParameter("username");
       String pwd = request.getParameter("password");
       Connection connection;
       try{
           Class.forName("org.postgresql.Driver");
            connection = null;
            connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/register", "postgres",
					"samanyu");
            PreparedStatement ps=connection.prepareStatement(  "select * from users where name = ? and pwd = ? "); 
            ps.setString(1,user);  
            ps.setString(2,pwd);
            ResultSet rs=ps.executeQuery();  
            if(rs.next())
            {
                    PrintWriter out1 = response.getWriter();  
                response.setContentType("text/html");  
                out1.println("<script type=\"text/javascript\">");  
                //out1.println("alert('Successfully Registered!!! Login to continue');");  
                out1.println("location = '/Reports-Lite-Login/welcome.html';");
                out1.println("</script>");
                
            }
            else
            {
                PrintWriter out1 = response.getWriter();  
                response.setContentType("text/html");  
                out1.println("<script type=\"text/javascript\">");  
                out1.println("alert('Invalid Credentials!!! Try again!');");  
                out1.println("location = '/Reports-Lite-Login/login.html';");
                out1.println("</script>");
            }
            
                  }
       catch(Exception e)
       {
           
       }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
   
  
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
