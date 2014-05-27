<%-- 
    Document   : index
    Created on : 27.5.2014, 11:59:00
    Author     : Doboss
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SCXML to SVG Web Converter</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="<c:url value="/layout.css"/>" type="text/css">      
        
              
    </head>
    <body>
        <div>
          
            <hr>
            <div class="container">
            <div class="uploadBox">
                <h1>Welcome to SCXML to SVG Web Converter.</h1>
                <p>
                    Please select SCXML file from your computer:
                </p>
                <form method="POST" action="upload" enctype="multipart/form-data" >
                    File:<input type="file" name="file" id="file" /><br/></br>
                    <input type="submit" value="Upload" name="upload" id="upload" />
                </form>
            </div>
            <hr>
            <div class="codeBox">    
                <div class="scxmlBox">
                    <form method="GET" action="downloadSVG" >
                        <input type="submit" value="Download SVG file" name="downloadSVG" style="text-align: left"/>
                    </form>
                    <textarea class="codeBox"></textarea>
                </div>
                <div class="svgBox">
                    <form method="GET" action="downloadXML" >
                        <input type="submit" value="Download SCXML file" name="DownloadXML" style="text-align: left"/>
                    </form>
                    <textarea class="codeBox"></textarea>
                </div>
            </div>
        </div>
            <footer>
                <hr>
                <p>hovno</p>
            </footer>
        </div>
        
        
       
    </body>
</html>
