package scxml2svg.svgcomposer;

import java.util.List;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.CDATASection;
      
import scxml2svg.scxmlmodel.State; 
import scxml2svg.scxmlmodel.Transition;

/**
 * This class composes SVG from the SCXML model
 * 
 * @author peping
 */
public class SvgComposer extends StateLayout {    
    private SvgComposer(State[] rootStates, List<Transition> transitions)
    {
        super(rootStates,transitions);
    }
    
    /**
     * Converts SCXML model to string
     * @param rootStates root states of the automaton
     * @param transitions all transitions in the automaton
     * @return string representation of the resulting SVG document
     */
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
     * Node svg representation of the layout.
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
            
            svgElement.setAttribute("width", Double.toString(width));
            svgElement.setAttribute("height", Double.toString(height));
            
            Element styleElement = document.createElement("style");
            {
                styleElement.setAttribute("type", "text/css");
                CDATASection cdataNode;
                {
                    String cssStyle= "svg {\n" +
"	font-family: Arial;\n" +
"	font-size: 14px;\n" +
"}\n" +
"\n" +
".state-rect {\n" +
"    stroke: black;\n" +
"    stroke-width: 1;\n" +
"    fill: white;\n" +
"}\n" +
"\n" +
".final-rect {\n" +
"	stroke: black;\n" +
"    stroke-width: 0.75;\n" +
"    fill: white;\n" +
"}\n" +
"\n" +
".parallel-rect {\n" +
"	stroke: black;\n" +
"	stroke-width: 1;\n" +
"	stroke-dasharray: 2,2;\n" +
"	fill: white;\n" +
"}\n" +
"\n" +
".history-rect {\n" +
"	stroke: black;\n" +
"	stroke-width: 3;\n" +
"	stroke-dasharray: 12,2;\n" +
"	fill: white;\n" +
"}\n" +
"\n" +
".id-caption {\n" +
"	font-weight: bold;\n" +
"}\n" +
"\n" +
".transition line {\n" +
"	stroke: black;\n" +
"	stroke-width: 1;\n" +
"}\n" +
"\n" +
".transition .initial circle {\n" +
"	fill: black;\n" +
"}\n"+
"\n" +
".transition .transition-event {\n" +
"	font-size: 11px; \n" +
"}";
                           
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
