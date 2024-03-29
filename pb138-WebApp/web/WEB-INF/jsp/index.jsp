<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">      
    <head>
        <title>SCXML to SVG Web Converter</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />        
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>" />           
        <link rel="stylesheet" type="text/css" href="<c:url value="/codemirror/lib/codemirror.css" />" />                
        <link rel="stylesheet" type="text/css" href="<c:url value="/codemirror/theme/pastel-on-dark.css" />" />                

        <link href="<c:url value="/jquery/css/smoothness/jquery-ui-1.10.4.custom.css"/>" rel="stylesheet">
            <script src="<c:url value="/jquery/js/jquery-1.10.2.js"/>"></script>
            <script src="<c:url value="/jquery/js/jquery-ui-1.10.4.custom.js"/>"></script>

            <script type="text/javascript" src="<c:url value="/codemirror/lib/codemirror.js"/>"></script>
            <script type="text/javascript" src="<c:url value="/codemirror/mode/javascript/javascript.js"/>"></script>
            <script>
                $(function() {
                    $(".resizable").resizable({
                        handles: 's, n'
                    });
                });
            </script>
    </head>

    <body>                   
        <div class="container">
            <div class="uploadBox">
                <h1>Welcome to SCXML to SVG Web Converter</h1>
                <p>
                    Please select SCXML file from your computer:
                </p>
                <form method="POST" action="upload" enctype="multipart/form-data" >
                    File:<input class="fileInput" type="file" name="file" id="file" />
                    <input class="uploadButton" type="submit" value="Upload" name="upload" id="upload" />                    
                </form>
            </div>
            <hr>
                <div class="codeBox">    
                    <div class="scxmlBox">
                        <form method="GET" action="tryScxml" >
                            <div class="resizable ui-widget-content">
                                <textarea id="scxmlArea" class="CodeMirror" name="scxmlText"><c:out value="${scxmlCode}"/></textarea>                                                                    
                            </div>
                            <input class="downloadButton" type="submit" value="Try SCXML" name="tryScxml"/>
                        </form>
                        <form method="GET" action="downloadXML" >
                            <input class="downloadButton" type="submit" value="Download SCXML file" name="DownloadXML" style="text-align: left"/>
                        </form>                                                     
                    </div>
                    <div class="svgBox">

                        <div class="resizable ui-widget-content">
                            <textarea id="svgArea" class="CodeMirror"><c:out value="${svgCode}" /></textarea>
                            <div>                                
                                <form method="GET" action="showSVG" target="_blank">
                                    <input class="downloadButton" type="submit" value="Show SVG file" name="showSVG" style="text-align: right"/>
                                </form>
                                <form method="GET" action="downloadSVG" >
                                    <input class="downloadButton" type="submit" value="Download SVG file" name="downloadSVG" style="text-align: left"/>
                                </form>                                
                            </div>
                            <script type="text/javascript">
                                var config, editor;
                                config = {
                                    lineNumbers: true,
                                    mode: "xml",
                                    theme: "pastel-on-dark",
                                    readOnly: false
                                };
                                editor = CodeMirror.fromTextArea(document.getElementById("scxmlArea"), config);
                                editor = CodeMirror.fromTextArea(document.getElementById("svgArea"), config);
                            </script>
                        </div>
                    </div>        
                </div>    


                <footer style="color: silver;">
                    <hr>
                        <p style="text-align: left;">Project homepage: <a href="https://code.google.com/p/scxml2svg-web/">code.google.com/scxml2svg-web</a><br />
                            Wiki: <a href="https://code.google.com/p/scxml2svg-web/wiki/Home">code.google.com/scxml2svg-web/wiki</a><br /><br />
                            <b>Developers:</b><ul><li>Dobroslav Bernáth</li><li>Ivo Hrádek</li><li>Josef Hornych</li><li>Adam Brenkous</li></ul></p>
                </footer>
        </div>
    </body>
</h