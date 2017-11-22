import com.google.gson.JsonObject;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author hp
 */
public class fileupload1 extends HttpServlet {
    
    private static final String temp = "C:\\Users\\hp\\Desktop\\Reports-Lite-Login\\TempFiles";
    private File tempdir;
    private static final String dest = "C:\\Users\\hp\\Desktop\\Reports-Lite-Login\\SavedFiles";
    private File destdir;
    public static ArrayList<String> l1 = new ArrayList<>();
    public static ArrayList<String> l2 =  new ArrayList<>();
    public static File myfile;
    public static String savename ="";
    public ArrayList<String> passl1 = new ArrayList<>();
    public ArrayList<Double> passl2 =  new ArrayList<>();
    public fileupload1()
    {
        super();
    }
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        //String realpath = getServletContext().getRealPath(temp);
        //System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+realpath);
        tempdir = new File(temp);
        if(!tempdir.isDirectory())
        {
            throw new ServletException(tempdir + "Is not a directory");
        }
        //realpath = getServletContext().getRealPath(dest);
        destdir = new File(dest);
        if(!destdir.isDirectory())
        {
            throw new ServletException(destdir + "Is not a directory");
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
            out.println("<title>Servlet fileupload</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet fileupload at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
         DiskFileItemFactory fileitemfactory = new DiskFileItemFactory();
         fileitemfactory.setSizeThreshold(1*1024*1024);
         fileitemfactory.setRepository(tempdir);
         ServletFileUpload uphandler = new ServletFileUpload(fileitemfactory);
         JsonObject myobj = new JsonObject();
         String filename = null;
         String fullname = null;
         File file = null;
         try
         {
             List items = uphandler.parseRequest(request);
             Iterator it = items.iterator();
             while(it.hasNext())
             {
                 FileItem item = (FileItem) it.next();
                 if(item.isFormField())
                 {
                     System.out.println("Field Name = "+item.getFieldName()+", Value = "+item.getString());
                      if(item.getFieldName().trim().equalsIgnoreCase("filename")){
                        filename = item.getString().trim();}
                 }
                 else
                 {
                     System.out.println("Field Name = " + item.getFieldName()+
                            ", File Name = "+ item.getName()+
                            ", Content type = "+item.getContentType()+
                            ", File Size = "+item.getSize());
                    
                    fullname = item.getName().trim();
                    //Write file to the ultimate location.
                    file = new File(destdir,item.getName());
                    item.write(file);
                 }
             }
             int count = 0;
             myfile = file;
            String extension = FilenameUtils.getExtension(fullname);
            savename = fullname.substring(0, fullname.length()-5);
            //int col1 = Integer.parseInt(request.getParameter("colnum1"));
            //int col2 = Integer.parseInt(request.getParameter("colnum2"));
            //System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii "+col1+"   "+col2);
            if(extension.trim().equalsIgnoreCase("xlsx")){
                //count = processExcelFile(file);
            }
            myobj.addProperty("success", true);
            myobj.addProperty("message", count + " item(s) were processed for file " + filename);
             DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             System.out.println("IN fileupload size of l1 is"+l1.size());
             for(int i=0;i<l1.size();i++)
             {
                 dataset.setValue(Double.parseDouble(l2.get(i)),"Food", l1.get(i));
                 passl1.add(l1.get(i));
             }
             //System.out.println("AIyyyyyyyyyyyy"+passl1.size());
             /*String choice = request.getParameter("home");
             System.out.println(choice+"hhh");*/
             JFreeChart chart =ChartFactory.createBarChart("BarChart","Food", "Quantity", dataset, 
             PlotOrientation.VERTICAL, false,true, false);
             /*switch(choice)
             {
                 case "Bar":{chart = ChartFactory.createBarChart("BarChart","Food", "Quantity", dataset, 
             PlotOrientation.VERTICAL, false,true, false);break;}
                 case "Pie":{
                     
                     DefaultPieDataset ds =new DefaultPieDataset();
                     for(int i=0;i<l1.size();i++)
                     {
                        ds.setValue(l1.get(i),l2.get(i));
                     }
                     chart = ChartFactory.createPieChart("PieChart", ds,true,true, false);break;}
                 case "Line":{chart = ChartFactory.createLineChart("LineChart","Food", "Quantity", dataset, 
             PlotOrientation.VERTICAL, false,true, false);break;}
             }*/
             
             
             
            try {
                int i=2;
                String name = "C:\\habbada\\img"+i+".jpg";
                i++;
               ChartUtilities.saveChartAsJPEG(new File(name), chart,400, 300);
               } catch (IOException e) {
               System.out.println("Problem in creating chart");}
            response.setContentType("text/html");
            String hi = "123345";
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js\"></script>");
            out.println("<script type=\"text/javascript\">");
            //out.println("<canvas id=\"myChart\" width=\"600\" height=\"600\"></canvas>");
            out.println("alert(\"File Successfully uploaded...\")");
            out.println("location = '/Reports-Lite-Login/userinp.html';");
            out.println("</script>");
            //out.println(myobj.toString());

         }
         catch(FileUploadException e)
         {
             
         } catch (Exception ex) {
            Logger.getLogger(fileupload1.class.getName()).log(Level.SEVERE, null, ex);
        }
         out.close();
    }
    public void userprocess(int col1,int col2) throws IOException
    {
        l1.clear();
        l2.clear();
        processExcelFile(myfile,col1,col2);
    }
    public ArrayList<String> fetchl1()
    {

        System.out.println("HIiiiii inside");
        //System.out.println(passl1.size());
        System.out.println(l1.size());
        return l1;
    }
    public ArrayList<String> fetchl2()
    {
        //passl2 = l2;
        return l2;
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private int processExcelFile(File file,int col1,int col2) throws FileNotFoundException, IOException {
        int count=0;
        FileInputStream minp = new FileInputStream(file);
        XSSFWorkbook mywb = new XSSFWorkbook(minp);
        XSSFSheet mysheet = mywb.getSheetAt(0);
        Iterator<Row> rowit  = mysheet.rowIterator();
        System.out.println("Inside process file");
        int rowpos=0;
        while(rowit.hasNext())
        {
            XSSFRow myrow = (XSSFRow) rowit.next();
            Iterator<org.apache.poi.ss.usermodel.Cell> cellit = myrow.cellIterator();
            int colpos=1;
            while(cellit.hasNext()){
                    
                    XSSFCell myCell = (XSSFCell) cellit.next();
                    System.out.println("Cell column index: " + myCell.getColumnIndex());
                    //System.out.println("Cell Type: " + myCell.getCellType());
                    if(myCell.getColumnIndex()+1 == col1 && rowpos!=0){
                    switch (myCell.getCellType()) {
                    case XSSFCell.CELL_TYPE_NUMERIC :
                        System.out.println("Cell Value: " + myCell.getNumericCellValue());
                        Double d = myCell.getNumericCellValue();
                        l1.add(d.toString());
                        break;
                    case XSSFCell.CELL_TYPE_STRING:   
                        System.out.println("Cell Value: " + myCell.getStringCellValue());
                        l1.add(myCell.getStringCellValue());
                        break;
                    default:   
                        System.out.println("Cell Value: " + myCell.getRawValue());
                        break;   
                    }
                    System.out.println("---");
                    }
                    else if(myCell.getColumnIndex()+1==col2 && rowpos!=0)
                    {
                    //System.out.println("Cell Type: " + myCell.getCellType());
                    switch (myCell.getCellType()) {
                    case XSSFCell.CELL_TYPE_NUMERIC :
                        System.out.println("Cell Value: " + myCell.getNumericCellValue());
                        Double d = myCell.getNumericCellValue();
                        l2.add(d.toString());
                        break;
                    case XSSFCell.CELL_TYPE_STRING:   
                        System.out.println("Cell Value: " + myCell.getStringCellValue());
                        l2.add(myCell.getStringCellValue());
                        break;
                    default:   
                        System.out.println("Cell Value: " + myCell.getRawValue());
                        break;   
                    }
                    System.out.println("---");
                    }
                    colpos++;

                }
                 rowpos++;
            
        }
        return count;
    }
}