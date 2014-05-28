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
                        handles: 's'
                    });
                });
            </script>
    </head>

    <body>
        <div class="container">
            <div class="uploadBox">
                <h1><span class="hoverH1">SCXML</span> &#8594; SVG<span style="color: #8F938F;font-weight: normal;"> Web Converter</span></h1>
                <div class="uploadForm">
                    <p style="font-size: 15px;">Please select SCXML file from your computer:</p>
                    <form method="POST" action="upload" enctype="multipart/form-data" >
                        File: <input class="fileInput" type="file" name="file" id="file" alt="Select file to upload"/>
                        <input class="uploadButton" type="submit" value="Upload" name="upload" id="upload" alt="Upload file" />
                    </form>
                </div>
            </div>
            <hr />
                <div class="codeBox">    
                    <div class="scxmlBox">
                        <form method="GET" action="downloadXML" >
                            <input class="downloadButton" type="submit" value="Download SCXML file" name="DownloadXML" alt="Download SCXML file" />
                        </form>
                        <div class="resizable ui-widget-content">
                            <textarea id="scxmlArea" class="CodeMirror"><c:out value="${scxmlCode}"/></textarea>                                                                    
                        </div>
                    </div>
                    <div class="svgBox">
                        <div>
                            <form method="GET" action="downloadSVG" >
                                <input class="downloadButton" type="submit" value="Download SVG file" name="downloadSVG" alt="Download SVG file" />
                            </form>
                            <form method="GET" action="showSVG" >
                                <input class="downloadButton" type="submit" value="Show SVG file" name="showSVG" alt="Show SVG file" />
                            </form>
                        </div>
                        <div class="resizable ui-widget-content">
                            <textarea id="svgArea" class="CodeMirror"><c:out value="${svgCode}" /></textarea>                            
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
                <footer>
                    <hr />
                    <p style="text-align: left;">
                        <strong>Project homepage:</strong> <a href="https://code.google.com/p/scxml2svg-web">code.google.com/scxml2svg-web</a><br />
                        <strong>Wiki:</strong> <a href="https://code.google.com/p/scxml2svg-web/wiki/Home">code.google.com/scxml2svg-web/wiki</a><br />
                        <strong>Developers:</strong><em> (alphabetically)</em>
                            <ul style="margin-top: -10px;margin-left:20px;">
                                <li>Dobroslav Bernáth</li>
                                <li>Adam Brenkous</li>
                                <li>Josef Hornych</li>
                                <li>Ivo Hrádek</li>
                            </ul>
                    </p>
                </footer>
        </div>
    </body>
</html>