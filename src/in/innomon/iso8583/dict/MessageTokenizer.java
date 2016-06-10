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


package in.innomon.iso8583.dict;

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
