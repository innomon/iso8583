/*
 * FieldDef.java
 * 
 * Created on Oct 1, 2003, 11:17:57 AM
 * 
 */
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
 * Portions Copyrighted 2003-2011 Ashish Banerjee
 * 
 */

package in.org.mpf.iso8583.dict;



/**
 *
 * @author ASHISH BANERJEE
 */
public class FieldDef implements FieldInfo{
    private    Format fmt;
    private    Attribute attr;
    private    int len;
    private    String label;
    private    int bitmapNdx;

    public FieldDef(Format fmt, Attribute attr, int len, String label, int bitmapNdx ) {
        this.fmt = fmt;
        this.attr = attr;
        this.len = len;
        this.label = label;
        this.bitmapNdx = bitmapNdx;
    }
    public FieldDef(FieldInfo cpy) {
        this.fmt = cpy.getFormat();
        this.attr = cpy.getAttribute();
        this.len = cpy.getLength();
        this.label = cpy.getPropertyName();
        this.bitmapNdx = cpy.getBitMapIndex();
        
    }
    @Override
    public Format getFormat() {
        return fmt;
    }

    @Override
    public Attribute getAttribute() {
        return attr;
    }

    @Override
    public int getLength() {
        return len;
    }

    @Override
    public String getPropertyName() {
       return label;
    }

    @Override
    public int getBitMapIndex() {
        return bitmapNdx;
    }

    public String format2String() {
        return fmt.toString();
    }



}
