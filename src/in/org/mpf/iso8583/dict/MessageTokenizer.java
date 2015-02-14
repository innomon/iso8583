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


package in.org.mpf.iso8583.dict;

/**
 * This Interface abstracts the implementation of a Message parser.
 * Multiple implementations may be required as some ATMs and Switches 
 * Interpret the ISO8583 standard differently. Also, some use ASCII, BCD formats.
 * Assumptions :
 * 0 th element is MTI, 1st element is bitmap (including secondary bitmap)
 * The Array thus has max of 129 elements.
 * @author ashish
 */
public interface MessageTokenizer {
    public static final String NS = "http://in.org.mpf/iso8583/v01";
    public static final String ROOT_ELEMENT = "Iso8583";

    public static final int FIRST_BIT = 128;
    public static final String MTI_ELEMENT = "MTI";
    public static final int PRIMARY_BITMAP_START = 4;
    public static final int MAX_ARRAY_ELEMENTS = 129;

    /**
     * 
     * @param msg  Binary Message input
     * @return Array of strings corresponding to the field in the message, Those 
     *          elements not present in the bitmap are nulled. Array length must always be 129. 
     * @throws IllegalArgumentException 
     */
    public String[] MessageToStringArray(byte[] msg) throws IllegalArgumentException;

    /**
     * Converts a String array into a binary message. 
     * index 0 = MTI, index 1 = bit array (this is ignored), if no element is present set the
     * element at the index to null, max elements in array is 129, mti + primary bitmap (not present)+ 128 elements including secondary bitmap
     * @param arr array of Object String (ALPHA, ALNUM), Date, byte[] for binary, Number for num
     * @return byte array containing ISO8583 message
     * @throws java.lang.IllegalArgumentException
     */
    public byte[] arrayToMessage(Object[] arr) throws IllegalArgumentException;

    /**
     * 
     * @param index
     * @return Field name corresponding to the field index
     */
    public String getFieldName(int index);

    public void registerFieldInfo(FieldInfo finfo);
    
    public FieldInfo[] getFieldInfoArray();
    
}
