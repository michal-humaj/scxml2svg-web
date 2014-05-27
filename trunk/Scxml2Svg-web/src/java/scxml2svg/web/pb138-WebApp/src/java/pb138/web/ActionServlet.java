/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pb138.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import scxml2svg.web.scxmlmodel.State;
import scxml2svg.web.scxmlparser.SCXMLParser;
import scxml2svg.web.svgcomposer.SvgComposer;

/**
 *
 * @author Dobroslav Bernáth
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/upload", "/downloadSVG", "/index", "/downloadXML"})
@MultipartConfig
public class ActionServlet extends HttpServlet {

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
            throws ServletException, IOException, URISyntaxException {
        response.setContentType("text/html;charset=UTF-8");
        
         if (request.getServletPath().equals("/upload")) {
             uploadFile(request, response);
         }   
         
         if (request.getServletPath().equals("/index")) {
             request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
         } 
         
         if (request.getServletPath().equals("/downloadSVG")) {
              downloadSVG(request, response);
         }
         
         if (request.getServletPath().equals("/downloadXML")) {
              downloadXML(request, response);
         }
    }
    
    private void uploadFile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, URISyntaxException {
        // Uploaded file destination
        String destination = System.getProperty("user.dir");
        //String destination = ActionServlet.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        //String destination = "/WEB-INF";
        
         // Create path components to save the file
        final String path = destination;
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            writer.println("New file " + fileName + " created at " + path);
            
            createSvg(path,fileName);
        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
        
        
    }
    
    private void downloadSVG(HttpServletRequest request, HttpServletResponse response) 
            throws FileNotFoundException, IOException {
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
        "attachment;filename=result.svg");
        
        // File name
        String fileName;
        fileName = "";
        
        // Setting Streams
        File file = new File(fileName);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[4096];
        //copy binary contect to output stream
        while(fileIn.read(outputByte, 0, 4096) != -1)
        {
                out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
    
    }
    
     private void downloadXML(HttpServletRequest request, HttpServletResponse response) 
            throws FileNotFoundException, IOException {
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
        "attachment;filename=new.scxml");
        
        // File name
        String fileName;
        fileName = "D:\\Test\\hovno.txt";
        
        // Setting Streams
        File file = new File(fileName);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[4096];
        //copy binary contect to output stream
        while(fileIn.read(outputByte, 0, 4096) != -1)
        {
                out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
    
    }
    
    // tuto bude metoda na menenie css stylu
    // 
    /*
    colors:
    red = FF3300
    blue = 3366CC
    green = 339933
    black = 000000
    yellow = FFCC00
    
    
    
    
    */
     
    private void createSvg(String path, String fileName) {
        
        if (path == null) {
            throw new NullPointerException("Null path");
        }
        
        if (fileName == null) {
            throw new NullPointerException("Null file name");
        }
        
        try {
            SCXMLParser parser = new SCXMLParser(path + File.separator + fileName);
             String string = SvgComposer.composeFromRootStates(parser.getRootStates().toArray(new State[0]), parser.getAllTransitions());
             
              File file = new File(path + File.separator + "result.svg");
              if (file.createNewFile()){
	        System.out.println("File is created!");
	      }else{
	        System.out.println("File already exists.");
	      }
              
              FileOutputStream s = new FileOutputStream(file);
              OutputStreamWriter osw = new OutputStreamWriter(s);
              osw.write(string, 0, string.length());
              osw.close();

        } catch (IOException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
          
        } catch (SAXException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    private String getFileName(final Part part) {
    final String partHeader = part.getHeader("content-disposition");
    for (String content : part.getHeader("content-disposition").split(";")) {
        if (content.trim().startsWith("filename")) {
            return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
        }
    }
    return null;
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
        } catch (URISyntaxException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (URISyntaxException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
