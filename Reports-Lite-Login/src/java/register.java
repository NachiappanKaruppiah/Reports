/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author hp
 */
public class register extends HttpServlet {

   public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
       response.setContentType("text/html");  
       PrintWriter out = response.getWriter();
       String user = request.getParameter("username");
       String pwd = request.getParameter("password");
       Connection connection = null;
       try{
            Class.forName("org.postgresql.Driver");
            connection = null;
            connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/register", "postgres",
					"samanyu");
            PreparedStatement ps=connection.prepareStatement(  "insert into users values(?,?)"); 
            ps.setString(1,user);  
            ps.setString(2,pwd);
            int i = ps.executeUpdate();
            if(i>0)
            {
                //response.getOutputStream().println("<script> alert(Successfully Registered) </script>");
                PrintWriter out1 = response.getWriter();  
                response.setContentType("text/html");  
                out1.println("<script type=\"text/javascript\">");  
                out1.println("alert('Successfully Registered!!! Login to continue');");  
                out1.println("location = '/Reports-Lite-Login/login.html';");
                out1.println("</script>");
                //response.sendRedirect("/Reports-Lite-Login/login.html");
            }
       }
       catch(Exception e)
       {
           
       }
       out.close();
       if (connection != null) {
			System.out.println("Successfully connected!!!");
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
            out.println("<title>Servlet register</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet register at " + request.getContextPath() + "</h1>");
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
