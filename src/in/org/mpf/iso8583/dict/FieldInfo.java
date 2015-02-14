/*
 * FieldParser.java
 * 
 * Created on Oct 1, 2003, 10:19:02 AM
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
public interface FieldInfo extends Cloneable {

    /**
    we have used enum rather than a fine grain classes so as to reduce number of new class ctors
    per message parsing. Creating new classes adds to overheads.
    Also MTI (sec 4.1.1 of ISO8583:87) and Bit map are handled separately. FieldInfo corresponds to the Data Dict
    section of ISO8583:87
     */
    public enum Format {

        LLVAR, LLLVAR, FIXED, YYMM, YYMMDD, DDMMYY, MMDDhhmmss, YYMMDDhhmmss, MTI, BITMAP
    };

    public enum Attribute {

        ALPHA, ALNUM, ALNUMPAD, ALPHA_OR_NUM, NUM, SIGN_NUM, ALNUMSPECIAL, SPECIAL, BIN
    };

    public Format getFormat();

    public Attribute getAttribute();

    public int getLength();

    public String getPropertyName();

    public int getBitMapIndex(); // offset in the bitmap
}
