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
package in.org.mpf.parser;

import java.util.regex.Pattern;

/**
 * Helper static class to Convert to & from Hex String to Binary bytes
 * @author ashish
 */
public class Hex {

    private Hex() {
    }
    private static char hexNdx[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static Pattern patHex = Pattern.compile("[0-9A-Z]*");

    /**
     * Converts a hex String to array of bytes 
     * @param hex string 
     * @return binary array, is half the length of the input string
     * @throws IllegalArgumentException 
     */
    public static byte[] hex2bin(String hex) throws IllegalArgumentException {
         byte[] ret = null;
        if (hex == null || "".equals(hex)) {
            ret = new byte[] {0};
            return ret;
        } 
        
        String uhex = hex.toUpperCase();
        if (!patHex.matcher(uhex).matches()) {
            throw new IllegalArgumentException("Expecting Hex String got [" + hex + "]");
        }

        if ((uhex.length() % 2) != 0) {
            uhex = "0" + uhex;
        }
        ret = new byte[uhex.length() / 2];
        for (int i = 0, j = 0; i < ret.length; i++, j += 2) {
            char l = uhex.charAt(j + 1);
            char m = uhex.charAt(j);
            byte lsb, msb;
            lsb = (l >= '0' && l <= '9') ? (byte) (l - '0') : (byte) ((l - 'A') + 10);
            msb = (m >= '0' && m <= '9') ? (byte) (m - '0') : (byte) ((m - 'A') + 10);
            ret[i] = 0;
            ret[i] = (byte) (lsb & (byte) 0xf);
            ret[i] |= (byte) ((msb << 4) & 0xf0);
        }
        return ret;
    }
    /**
     * Converts a binary array into a String of Hex
     * @param bin
     * @return Hex String, twice the size of input byte array
     */
    public static String bin2hex(byte[] bin) {
        return bin2hex(bin,0,bin.length);
    }
    public static String bin2hex(byte[] bin, int curOffset, int len) {
        if (bin == null || curOffset >= len || curOffset < 0 || bin.length < (len-curOffset)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bin.length * 2);
        for (int i = curOffset; i < len; i++) {
            int ndx = bin[i] & 0xff;
            sb.append(hexNdx[(ndx >>> 4)]);
            sb.append(hexNdx[(ndx & 0xf)]);
        }
        return sb.toString();
    }

 
}
