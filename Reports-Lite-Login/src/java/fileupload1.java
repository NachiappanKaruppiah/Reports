import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author hp
 */
public class fileupload1 extends HttpServlet {
    
    private static final String temp = "C:\\Users\\hp\\Desktop\\Reports-Lite-Login\\TempFiles";
    private File tempdir;
    private static final String dest = "C:\\Users\\hp\\Desktop\\Reports-Lite-Login\\SavedFiles";
    private File destdir;
    
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
            String extension = FilenameUtils.getExtension(fullname);
            if(extension.trim().equalsIgnoreCase("xlsx")){
                count = processExcelFile(file);
            }
            myobj.addProperty("success", true);
            myobj.addProperty("message", count + " item(s) were processed for file " + filename);
            response.setContentType("text/html");
            String hi = "123345";
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js\"></script>");
            out.println("<canvas id=\"myChart\" width=\"600\" height=\"600\"></canvas>");
            out.println("<script type=\"text/javascript\">\n" +"    var test = hello " +"</script>");
            out.println("<script type = \"text/javascript\" src=\"\\Reports-Lite-Login\\Scripts\\plot.js\">");
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

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private int processExcelFile(File file) throws FileNotFoundException, IOException {
        int count=0;
        FileInputStream minp = new FileInputStream(file);
        XSSFWorkbook mywb = new XSSFWorkbook(minp);
        XSSFSheet mysheet = mywb.getSheetAt(0);
        Iterator<Row> rowit  = mysheet.rowIterator();
        while(rowit.hasNext())
        {
            XSSFRow myrow = (XSSFRow) rowit.next();
            Iterator<org.apache.poi.ss.usermodel.Cell> cellit = myrow.cellIterator();
            while(cellit.hasNext()){

                    XSSFCell myCell = (XSSFCell) cellit.next();
                    System.out.println("Cell column index: " + myCell.getColumnIndex());
                    //System.out.println("Cell Type: " + myCell.getCellType());
                    switch (myCell.getCellType()) {
                    case XSSFCell.CELL_TYPE_NUMERIC :
                        System.out.println("Cell Value: " + myCell.getNumericCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_STRING:   
                        System.out.println("Cell Value: " + myCell.getStringCellValue());
                        break;
                    default:   
                        System.out.println("Cell Value: " + myCell.getRawValue());
                        break;   
                    }
                    System.out.println("---");

                    

                }

            
        }
        return count;
    }
}