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


import in.org.mpf.iso8583.dict.MessageTokenizer;
import in.org.mpf.parser.BinMessageTokenizer;
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
