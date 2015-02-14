/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 * 
 * Portions Copyrighted 2011 Ashish Banerjee
 * 
 */

package in.org.mpf.iso8583.xml;



import in.org.mpf.iso8583.dict.FieldInfo;
import in.org.mpf.iso8583.dict.FieldName2Index;
import in.org.mpf.iso8583.dict.MessageTokenizer;
import in.org.mpf.parser.BinMessageTokenizer;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

/**
 *  TODO: Verify thread safety
 * @author Ashish Banerjee , 6-Mar-2011 
 */
public class XmlToIso8583   {
    private MessageTokenizer msgTnz = new BinMessageTokenizer();
    private FieldName2Index f2i = null; // expensive class to construct, being lazy may payoff
    private String nameSpace = MessageTokenizer.NS;

    public XmlToIso8583() {
    }

    public XmlToIso8583(String namespace) {
        nameSpace = namespace;
    }
    public XmlToIso8583(String namespace, MessageTokenizer tok ) {
        nameSpace = namespace;
        msgTnz = tok;
    }
    /**
     * 
     * @param xmlMsg : XML message as Bytes 
     * @return binary ISO8583 message
     * @throws XMLStreamException 
     */    
    public byte[] parse(byte[] xmlMsgAsBytes) throws XMLStreamException {
        ByteArrayInputStream bai = new ByteArrayInputStream(xmlMsgAsBytes);
        return parse(bai);
    }   
    /**
     * 
     * @param input stream containing the XML 
     * @return binary ISO8583 message
     * @throws XMLStreamException 
     */
    public byte[] parse(InputStream xmlInStream) throws XMLStreamException {
        byte[] ret = null;
        HashMap<FieldInfo,String> fvmap = new HashMap<FieldInfo,String>();
        
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader =
                inputFactory.createXMLEventReader(xmlInStream);
        int maxndx = 0;
        FieldInfo[] farr = msgTnz.getFieldInfoArray();
        if (f2i == null) {
            f2i = new FieldName2Index(farr);
        }
        String fldVal = null;
        while (reader.hasNext()) {
            XMLEvent e = reader.nextEvent();
            // insert your processing here
            int evtNo = e.getEventType();
            String  fldName, ns;
            switch (evtNo) {
                case XMLStreamConstants.CHARACTERS:
                    fldVal = ((Characters) e).getData();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    ns = ((EndElement) e).getName().getNamespaceURI();
                    fldName = ((EndElement) e).getName().getLocalPart();
                    
                    if (nameSpace.equals(ns) && fldVal != null && fldName != null) {
                        int ndx = f2i.getIndex(fldName);
                        if(ndx < 0) {
                            System.out.println("ignoring "+fldName);
                            continue; //throw new XMLStreamException("ERROR: field name["+fldName+"] not found in ISO8583 def.");
                        }
                         fvmap.put(farr[ndx], fldVal);
                        if(ndx > maxndx)
                            maxndx = ndx;
                    } 
                    break;
            }

        } // while
        // got the xml field/value into prop
        String valArr[] = new String[((maxndx > 64)? 129:65)]; // 64 + 2 or 128 +2 
        Set<FieldInfo> set = fvmap.keySet();
        for(FieldInfo fi: set) {
            valArr[fi.getBitMapIndex()] = fvmap.get(fi);
        } 
        ret = msgTnz.arrayToMessage(valArr);    
        return ret;
    }
}
