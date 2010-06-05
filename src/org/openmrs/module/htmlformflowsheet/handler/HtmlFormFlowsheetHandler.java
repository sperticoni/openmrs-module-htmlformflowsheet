package org.openmrs.module.htmlformflowsheet.handler;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.htmlformentry.FormEntryContext.Mode;
import org.openmrs.module.htmlformentry.handler.TagHandler;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class HtmlFormFlowsheetHandler  implements TagHandler {

    /** The logger to use with this class */
    protected final Log log = LogFactory.getLog(getClass());

    
    public boolean doStartTag(FormEntrySession session, PrintWriter out, Node parent, Node node) {
        Map<String, String> attributes = new HashMap<String, String>();        
        NamedNodeMap map = node.getAttributes();
        for (int i = 0; i < map.getLength(); ++i) {
            Node attribute = map.item(i);
            attributes.put(attribute.getNodeName(), attribute.getNodeValue());
        }
        
        
        String configuration = null;
        Patient patient = session.getPatient();
        
        try {
            configuration = attributes.get("formId");
            if (configuration == null)
                throw new RuntimeException("htmlformflowsheet tag must have a formId attribute in your htmlform xml.");
        } catch (Exception ex){
            throw new IllegalArgumentException("Cannot find formId in "
                    + attributes);
        }
            
        if (patient != null){
            StringBuilder sb = new StringBuilder("");
            
            sb.append("<iframe id='iframeFor" + configuration + "'");
            if (!session.getContext().getMode().equals(Mode.VIEW))
                sb.append("  src='/openmrs/module/htmlformflowsheet/patientWidgetChart.list?fullPage=false&patientId=" + patient.getPatientId() + "&configuration=F:BOO:" + configuration + "'  ");
            else
                sb.append("  src='/openmrs/module/htmlformflowsheet/patientWidgetChart.list?readOnly=true&fullPage=false&patientId=" + patient.getPatientId() + "&configuration=F:BOO:" + configuration + "'  ");
            sb.append(" width='100%' frameborder='0' scrolling='no'></iframe><br/>");

            out.print(sb.toString());
        } else {
            out.print("<div>You must create the patient first!</div>");
        }
        
        
        return true;
    }
    
    public void doEndTag(FormEntrySession session, PrintWriter out, Node parent, Node node) {
        // TODO Auto-generated method stub
        
    }
    
    
}