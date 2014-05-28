/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.svgcomposer;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import scxml2svg.web.scxmlmodel.*;
        
/**
 * This class composes SVG from the SCXML model
 * 
 * @author pepin_000
 */
public class SvgComposer extends StateLayout {    
    private SvgComposer(State[] rootStates, List<Transition> transitions)
    {
        super(rootStates,transitions);
    }
    
    public static String composeFromRootStates( State[] rootStates, List<Transition> transitions)
    {
        SvgComposer compo = new SvgComposer(rootStates, transitions);
        compo.layout();
        return compo.toString();
    }
    
    /**
     * String svg representation of the layout
     * 
     * @return SVG string
     */
    @Override public String toString()
    {
        Node node = toNode(null);
        try
        {
            DOMSource domSource = new DOMSource(node);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch(TransformerException ex)
        {
           ex.printStackTrace();
           return null;
        }
    }
    
    /**
     * Node svg representation of the layout
     * Loads and copies res/style.css into output
     * 
     * @param doc Document to create elements in. If null, a new document is created
     * @return SVG node
     */
    @Override public Node toNode(Document doc)
    {
        Document document = doc;
        try
        {
            if (doc==null)
            {
                DocumentBuilder factory = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                document = factory.newDocument();
                document.setXmlVersion("1.0");
            }
        } catch (ParserConfigurationException e)
        {
            return null;
        }
        
        Element layoutElement = document.createElement("g");
        layoutElement.setAttribute("transform", "translate("+(getWidth()/2)+","+(getHeight()/2)+")");
        for (StateContainer c : stateContainers)
        {
            layoutElement.appendChild(c.toNode(document));
        }

        for (TransitionArrow c : transitionArrows)
        {
            layoutElement.appendChild(c.toNode(document));
        }

        if (doc==null)
        {
            Element svgElement = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            
            Element styleElement = document.createElement("style");
            {
                styleElement.setAttribute("type", "text/css");
                CDATASection cdataNode;
                {
                    String cssStyle;
                    try {cssStyle = new String(Files.readAllBytes(Paths.get("res/style.css")), StandardCharsets.UTF_8);}
                    catch (IOException e) {cssStyle = "";}
                    cdataNode = document.createCDATASection(cssStyle);
                }
                styleElement.appendChild(cdataNode);
            }
            svgElement.appendChild(styleElement);
            
            svgElement.appendChild(layoutElement);
            
            document.appendChild(svgElement);
            return document;
        }
        else return layoutElement;
    }
}
