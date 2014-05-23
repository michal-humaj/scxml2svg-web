<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="application/xhtml+xml;charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles.css"/>" />
        <script type="text/javascript" src="<c:url value="/scripts.js" />" />
        <title>scxml2svg</title>
    </head>
    <body>        
        <div class="container">
            <div class="containerUpload">
                <!-- <img src="<c:url value="/images/logo.png" />" alt="Web logo" /><br /> -->
                <input id="uploadFile" type="file" onchange="checkFileType();"/>
                <div class="help">
                    <p></p>
                </div>
            </div>
            <div class="containerSvg">
            </div>
        </div>
    </body>
</html>
