/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author hp
 */
public class userchoice extends HttpServlet {

    fileupload1 f1 = new fileupload1();
    public ArrayList<String> l1 = new ArrayList<>();
    public ArrayList<Double> l2 =  new ArrayList<>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Thank You!!!</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h5>Your images are saved in C:\\Reports-Images-Folder</h5>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String choice = request.getParameter("charttype");
        int col1 = Integer.parseInt(request.getParameter("colnum1"));
        int col2 = Integer.parseInt(request.getParameter("colnum2"));
        System.out.println("heloooooooooooooooooooooooooooooooo "+col1+" "+col2);
        f1.userprocess(col1,col2);
        System.out.println(choice+"asdfgf");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //l1 = f1.fetchl1();
        //l2 = f1.fetchl2();
        System.out.println("IN userchoice size of l1 is"+l1.size());
             for(int i=0;i<f1.l1.size();i++)
             {
                 dataset.setValue(Double.parseDouble(f1.l2.get(i)),"Food", f1.l1.get(i));
             }
        JFreeChart chart =ChartFactory.createBarChart("BarChart","Food", "Quantity", dataset, 
             PlotOrientation.VERTICAL, false,true, false);
             switch(choice)
             {
                 case "Bar":{chart = ChartFactory.createBarChart("BarChart","Food", "Quantity", dataset, 
             PlotOrientation.VERTICAL, false,true, false);break;}
                 case "Pie":{
                     
                     DefaultPieDataset ds =new DefaultPieDataset();
                     for(int i=0;i<f1.l1.size();i++)
                     {
                        ds.setValue(f1.l1.get(i),Double.parseDouble(f1.l2.get(i)));
                     }
                     chart = ChartFactory.createPieChart("PieChart", ds,true,true, false);break;}
                 case "Line":{chart = ChartFactory.createLineChart("LineChart","Food", "Quantity", dataset, 
             PlotOrientation.VERTICAL, false,true, false);break;}
             }
             
             
             
            try {
                
                String name = "\\"+f1.savename+choice+".jpg";
                File theDir = new File("C:\\Reports-Images-Folder");
                if(!theDir.exists())
                {
                    try{
                        theDir.mkdir();
                    }
                    catch(Exception e){System.out.println("Windows is not allowing to create!!!!");}
                }
                name = theDir+name;
               ChartUtilities.saveChartAsJPEG(new File(name), chart,400, 300);
               } catch (IOException e) {
               System.out.println("Problem in creating chart");}
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
