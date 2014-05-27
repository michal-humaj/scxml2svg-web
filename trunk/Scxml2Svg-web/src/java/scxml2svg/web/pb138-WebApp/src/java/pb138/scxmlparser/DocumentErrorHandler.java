package scxml2svg.web.scxmlparser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *  Error handler for xml schema validation
 */
public class DocumentErrorHandler implements ErrorHandler {
    /**
     *  Catches a warning from document validation
     * 
     * @param   exception   exception with warning
     */
    @Override
    public void warning(SAXParseException exception)
            throws SAXException{        
        throw exception;
    }
    /**
     *  Catches an error from document validation
     * 
     * @param   exception   exception with warning
     */
    @Override
    public void error(SAXParseException exception)
           throws SAXException{
        throw exception;
    }
    /**
     *  Catches a fatal error from document validation
     * 
     * @param   exception   exception with warning
     */
    @Override
    public void fatalError(SAXParseException exception)
                throws SAXException{
        throw exception;
    }
}
