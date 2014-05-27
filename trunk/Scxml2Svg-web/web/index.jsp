<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="styles.css" />                
        <title>SCXML to SVG</title>                
    </head>
    <body>
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
            <div class="codeBox">                    
                <div class="scxmlBox">
                    <textarea class="codeBox"></textarea>
                </div>
                <div class="svgBox">
                    <textarea class="codeBox"></textarea>
                </div>
            </div>
        </div>
    </body>
</html>