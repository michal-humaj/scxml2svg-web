package pb138.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URISyntaxException;

import javax.servlet.http.Part;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import scxml2svg.scxmlmodel.State;
import scxml2svg.scxmlparser.SCXMLParser;
import scxml2svg.svgcomposer.SvgComposer;

/**
 *
 * @author Dobroslav Bernáth
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/upload", "/downloadSVG", "/index", "/downloadXML", "/showSVG", "/tryScxml"})
@MultipartConfig
public class ActionServlet extends HttpServlet {

    
    private String path,fileName;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.net.URISyntaxException
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
         
         if (request.getServletPath().equals("/showSVG")) {
              showSVG(request, response);
         }
         
         if (request.getServletPath().equals("/tryScxml")) {
              tryScxml(request, response);
         }
    }
    
    private void uploadFile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, URISyntaxException {
        // Uploaded file destination
        
        String destination = System.getProperty("user.dir");
        //String destination = ActionServlet.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        //String destination = "/WEB-INF";
        
         // Create path components to save the file
        path = destination;
        final Part filePart = request.getPart("file");
        fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            int read;
            File file = new File(path + File.separator + fileName);
            
            final byte[] bytes = new byte[(int)filePart.getSize()];
            out = new FileOutputStream(file);
            filecontent = filePart.getInputStream();            
            
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            //writer.println("New file " + fileName + " created at " + path);            
            createSvg(path,fileName);                       
            String scxmlCode = getCode(path + File.separator + fileName);
            String svgCode = getCode(path + File.separator + "result.svg");
            request.setAttribute("scxmlCode", scxmlCode); 
            request.setAttribute("svgCode", svgCode); 
            request.setAttribute("path", path + File.separator + "result.svg"); 
            request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
            
        } catch (FileNotFoundException fne) {
            writer.println("<script>alert(\"You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.\\nERROR: " + fne.getMessage() + "\");"
                    + "window.location.href = '/index'</script>");            
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
        fileName = path + File.separator + "result.svg";
        
        // Setting Streams
        File file = new File(fileName);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
      
        byte[] outputByte = new byte[(int)file.length()];
        //copy binary contect to output stream
        while(fileIn.read(outputByte, 0, (int)file.length()) != -1) {
                out.write(outputByte, 0, (int)file.length());
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
        
        String name = path + File.separator + fileName;
        
        // Setting Streams
        File file = new File(name);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[(int)file.length()];
        //copy binary contect to output stream
        while(fileIn.read(outputByte, 0, (int)file.length()) != -1) {
                out.write(outputByte, 0, (int)file.length());
        }
        fileIn.close();
        out.flush();
        out.close();   
    }
    
    private String getCode(String path) {
        byte[] bytes;
        FileInputStream fs;
        try {
            fs = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: `" + path + "`");            
            return null;
        }
        
        try {
            bytes = new byte[fs.available()];
            fs.read(bytes);
            fs.close();
        } catch (IOException e) {
            System.out.println("IO Exception while getting contents of `"
                    + path + "`");    
            return null;
        }
        return new String(bytes);
    }
    
    private void tryScxml(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        FileOutputStream s = null;
        OutputStreamWriter osw = null;
        try {
            String textAreaValue = request.getParameter("scxmlText");
            if(textAreaValue.length() > 0) {
                File file = new File(path + File.separator + fileName);
                s = new FileOutputStream(file);
                osw = new OutputStreamWriter(s);
                osw.write(textAreaValue, 0, textAreaValue.length());
                osw.close();
                createSvg(path, fileName);
                String scxmlCode = getCode(path + File.separator + fileName);
                String svgCode = getCode(path + File.separator + "result.svg");
                request.setAttribute("scxmlCode", scxmlCode); 
                request.setAttribute("svgCode", svgCode); 
                request.setAttribute("path", path + File.separator + "result.svg"); 
                request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
    }
      
    
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
              
              
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);          
        }
    }
    
    private void showSVG(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, URISyntaxException {    
        final PrintWriter writer = response.getWriter();
        String svgCode = getCode(path + File.separator + "result.svg");
        String svg = svgCode.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println(svg);
        writer.println("</html>");
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
