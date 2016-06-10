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
package in.innomon.iso8583.parser;

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
