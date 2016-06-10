/*
Copyright (c) 2016, BON BIZ IT Services Pvt Ltd

The Universal Permissive License (UPL), Version 1.0

Subject to the condition set forth below, permission is hereby granted to any person obtaining a copy of this software, associated documentation and/or data (collectively the "Software"), free of charge and under any and all copyright rights in the Software, and any and all patent rights owned or freely licensable by each licensor hereunder covering either (i) the unmodified Software as contributed to or provided by such licensor, or (ii) the Larger Works (as defined below), to deal in both
(a) the Software, and
(b) any piece of software and/or hardware listed in the lrgrwrks.txt file if one is included with the Software (each a “Larger Work” to which the Software is contributed by such licensors),

without restriction, including without limitation the rights to copy, create derivative works of, display, perform, and distribute the Software and make, use, sell, offer for sale, import, export, have made, and have sold the Software and the Larger Work(s), and to sublicense the foregoing rights on either these or other terms.

This license is subject to the following condition:
The above copyright notice and either this complete permission notice or at a minimum a reference to the UPL must be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package in.innomon.iso8583.xml;



import in.innomon.iso8583.dict.FieldInfo;
import in.innomon.iso8583.dict.FieldName2Index;
import in.innomon.iso8583.dict.MessageTokenizer;
import in.innomon.iso8583.parser.BinMessageTokenizer;
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
