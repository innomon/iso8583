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


import in.innomon.iso8583.dict.MessageTokenizer;
import in.innomon.iso8583.parser.BinMessageTokenizer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * TODO: Ensure Thread safety.
 * @author Ashish Banerjee, 9-March-2011
 */
public class Iso8583ToXml   {
    protected MessageTokenizer msgTnz = new BinMessageTokenizer();
    protected String nameSpace = MessageTokenizer.NS;
    protected String pfix = "ns0";
    protected String rootElement = MessageTokenizer.ROOT_ELEMENT;

    public Iso8583ToXml() {
    }

    public Iso8583ToXml(String namespace) {
        nameSpace = namespace;
    }

    public Iso8583ToXml(String namespace, String preFix) {
        nameSpace = namespace;
        pfix = preFix;
    }
    public Iso8583ToXml(String namespace, String preFix, MessageTokenizer tok ) {
        nameSpace = namespace;
        pfix = preFix;
        msgTnz = tok;
    }

    public void setMsgTnz(MessageTokenizer msgTnz) {
        this.msgTnz = msgTnz;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public void setPfix(String pfix) {
        this.pfix = pfix;
    }

    public void setRootElement(String rootElement) {
        this.rootElement = rootElement;
    }

    public byte[] parse(byte[] binMsgIn) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream prn = new PrintStream(bos);
        parse(prn, binMsgIn);
        prn.close();
        bos.close();
        return bos.toByteArray();
    }

    public void parse(java.io.PrintStream out, byte[] binMsgIn) {
        String[] outMsg = msgTnz.MessageToStringArray(binMsgIn);
        header(out);
        for(int i=0; i < outMsg.length;i++) {
            if(outMsg[i] != null) 
                body(out, msgTnz.getFieldName(i), outMsg[i]);
        }
        footer(out);

    }

    protected void header(PrintStream out) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        out.println("<" + pfix + ":" +rootElement+" xmlns:"+pfix+"='"+nameSpace+"' >");
    
    }
    protected void body(PrintStream out, String eleName, String eleVal) {
        out.println("<" + pfix + ":" +eleName+">"+eleVal+"</"+ pfix + ":" +eleName+">");
    }
    protected void footer(PrintStream out) {
       out.println("</" + pfix + ":" +rootElement+">"); 
    }
}
