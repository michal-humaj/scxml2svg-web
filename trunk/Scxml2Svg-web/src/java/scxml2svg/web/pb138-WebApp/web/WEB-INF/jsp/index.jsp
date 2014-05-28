<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SCXML to SVG Web Converter</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">       
        <link type="text/css" rel="stylesheet" href="<c:url value="/layout.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/codemirror/lib/codemirror.css" />" />                
        <link rel="stylesheet" type="text/css" href="<c:url value="/codemirror/theme/pastel-on-dark.css" />" />        
        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
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
                    <div class="resizable ui-widget-content">
                        <textarea id="svgArea" class="CodeMirror">${svgCode}</textarea>                    
                    </div>
                </div>
                <div class="svgBox">
                    <form method="GET" action="downloadXML" >
                        <input type="submit" value="Download SCXML file" name="DownloadXML" style="text-align: left"/>
                    </form>                      
                    <div class="resizable ui-widget-content">
                        <textarea id="scxmlArea" class="CodeMirror"><c:out value="${scxmlCode}"/></textarea>                    
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
        </div>
    </div>
    <footer>
        <hr>
        <p>hovno</p>
    </footer>
</div>
</body>
</html>