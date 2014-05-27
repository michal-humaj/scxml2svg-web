/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scxml2svg.web.scxmlparser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author xbrenkus
 */
public class DocumentErrorHandler implements ErrorHandler {
    @Override
    public void warning(SAXParseException exception)
            throws SAXException{        
        throw exception;
    }
    @Override
    public void error(SAXParseException exception)
           throws SAXException{
        throw exception;
    }
    @Override
    public void fatalError(SAXParseException exception)
                throws SAXException{
        throw exception;
    }
}
