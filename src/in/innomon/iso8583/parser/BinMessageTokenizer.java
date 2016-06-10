
/*
 * MessageParser.java
 * 
 * Created on Oct 1, 2003, 11:49:43 AM
 * Refactored 14-jun-2011
 */
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

import in.innomon.iso8583.dict.FieldInfo;
import in.innomon.iso8583.dict.GlobalFieldInfoDict;
import in.innomon.iso8583.dict.MessageTokenizer;
import java.util.BitSet;

/**
 *
 * @author ASHISH BANERJEE, Modified 17-Feb-11 (changed the names to camel case)
 * 17-may-11 : minor fix in method arrayToMessage() line 2, and fixed registerGlobalFieldInfoRange()
 * 12-Jun-11 : major fixes, introduced BitSet
 * 14-Jun-11 refactored to new package
 */
/*
 * BinMessageTokenizer converts an ISO8583 bin message to an string array (bacically unpacks it) and viceversa.
 * 
 */
public class BinMessageTokenizer extends GlobalFieldInfoDict implements MessageTokenizer {

    private FieldInfo fldArr[] = null;
    private ArrayToMessage arr2Msg = null;

    /**
     * Default constructor. Will use the Global default Field info dictionary @see GlobalFieldInfoDict
     */
    public BinMessageTokenizer() {
    }
    /**
     * Use this constructor if you want to use a custom field dictionary
     * @param fldArr : Custom field dictionary
     */
    public BinMessageTokenizer(FieldInfo fldArr[]) {
        this.fldArr = fldArr;
    }
    /**
     * Register a field info to override default field info.
     * @param finfo 
     */
    @Override
    public void registerFieldInfo(FieldInfo finfo) {
        if (fldArr == null) {
            fldArr = fieldInfoArr.clone();
        }
        fldArr[finfo.getBitMapIndex() % fldArr.length] = finfo;

    }
    /**
     * Gets the field name corresponding to an index
     * @param index
     * @return The field name corresponding to the field index
     */
    @Override
    public String getFieldName(int index) {
        if (fldArr == null) {
            fldArr = fieldInfoArr.clone();
        }
        String ret = "Unknown";
        FieldInfo fld = fldArr[index % fldArr.length];
        if (fld != null) {
            ret = fld.getPropertyName();
        }

        return ret;
    }
    /**
     * Parses a binary message and returns a String Array
     * @param msg the binary message
     * @return Array of Strings values, parsed from the binary message
     * @throws IllegalArgumentException 
     */
    @Override
    public String[] MessageToStringArray(byte[] msg) throws IllegalArgumentException {
        FieldInfo arr[] = fldArr;
        if (arr == null) {
            arr = fieldInfoArr;
        }
        BitSet bset = ISOBitSetFactory.createBitSet(msg);
        String ret[] = new String[bset.get(1) ? 129 : 65];
        ret[0] = (new BinMessageTypeIndicator(msg, 0)).toString();
        ret[1] = bset.toString();

        FieldExtrator extr = new FieldExtrator(msg, (4 + (bset.get(1) ? 16 : 8)));

        for (int ndx = bset.nextSetBit(2); ndx > 0; ndx = bset.nextSetBit(ndx + 1)) {
            FieldInfo finfo = arr[ndx];
            if (finfo == null) {
                throw new IllegalArgumentException("E001: field def for" + ndx + " not found message[" + msg + "]");
            }
            ret[ndx] = extr.messageToString(finfo);
        }

        return ret;
    }

    /**
     *   Converts an Array of Objects into a binary ISO8583 message
     *   index 0 = MTI, index 1 = bit array (this is ignored), if no element is present set the 
     *       element at the index to null 
     * @param arr array of Object String (ALPHA, ALNUM), Date, byte[] for binary, Number for num  
     * @return byte array containing ISO8583 message
     * @throws java.lang.IllegalArgumentException 
     */
    @Override
    public byte[] arrayToMessage(Object arr[]) throws IllegalArgumentException {
        if (fldArr == null) {
            fldArr = fieldInfoArr.clone();
        }

        if (arr2Msg == null) {
            arr2Msg = new ArrayToMessage(fldArr);
        }
        return arr2Msg.parse(arr);
    }

    /**
     * Returns the Field Dictionary
     * @return 
     */
    @Override
    public FieldInfo[] getFieldInfoArray() {
        if (fldArr == null) {
            fldArr = fieldInfoArr.clone();
        }
        return fldArr;
    }
}

/**
 * Internal helper class to extract fields
 * @author ashish
 */
class FieldExtrator {

    byte[] msg;
    int curOff;

    FieldExtrator(byte[] msg, int curOff) {
        this.msg = msg;
        this.curOff = curOff;
    }

    public int getCurOffset() {
        return curOff;
    }

    public String messageToString(FieldInfo finfo) throws IllegalArgumentException {
        String ret = null;
// switch/case is really not OO design, delegating to field info was a better option. But we 
// chose this for performance, as finer grain means more overheads.
        switch (finfo.getFormat()) {
            case MTI:
                ret = getMTI();
            case LLVAR:
                ret = getVarLen(finfo, 2);
                break;
            case LLLVAR:
                ret = getVarLen(finfo, 3);
                break;
            case FIXED:
            case DDMMYY:
            case MMDDhhmmss:
            case YYMMDDhhmmss:
                ret = getFixedLen(finfo);
                break;
        }
        return ret;
    }

    private String getMTI() {
        BinMessageTypeIndicator mti = new BinMessageTypeIndicator(new String(msg, curOff, BinMessageTypeIndicator.MTI_LEN));
        curOff += BinMessageTypeIndicator.MTI_LEN;

        return mti.toString();
    }

    private String getVarLen(FieldInfo finfo, int pfix) throws IllegalArgumentException {
        String slen = new String(msg, curOff, pfix);
        int len = Short.parseShort(slen);
        if (len < 0 || len > finfo.getLength() || ((len + curOff) >= msg.length)) {
            throw new IllegalArgumentException("E002: Field len error in message[" + msg + "], offset =" + curOff + " got len=" + len);
        }
        curOff += pfix;
        // TODO: format validation based on filed attributes
        String ret;
        if (finfo.getAttribute() == FieldInfo.Attribute.BIN) {
            ret = Hex.bin2hex(msg, curOff, len);
        } else {
            ret = new String(msg, curOff, len);
        }
        curOff += len;

        return ret;
    }

    private String getFixedLen(FieldInfo finfo) throws IllegalArgumentException {
        int len = finfo.getLength();
        if (len < 0 || ((len + curOff) > msg.length)) {
            throw new IllegalArgumentException("E003: Field len error in Field Ndx = " + finfo.getBitMapIndex() + " name [" + finfo.getPropertyName() + "] message len =" + msg.length + ", offset =" + curOff + " got len=" + len);
        }

        // TODO: format validation based on field attributes
        String ret;
        if (finfo.getAttribute() == FieldInfo.Attribute.BIN) {
            ret = Hex.bin2hex(msg, curOff, (len + curOff));
        } else {
            ret = new String(msg, curOff, (len + curOff));
        }

        curOff += len;

        return ret;

    }
}
/**
 * Internal helper class to parse the bitmap fields
 * @author ashish
 */
class ISOBitSetFactory {

    public static final int FIRST_BIT = 0x01;
    public static final int PRIMARY_START = 4;

    private ISOBitSetFactory() {
    }

    public static BitSet createBitSet(byte[] msg) {
        BitSet bset = new BitSet(((msg[PRIMARY_START] & FIRST_BIT) == FIRST_BIT) ? 65 : 129);
        bset.clear();
        bset.set(0); // MTI is always present
        int bitmapLen = 8;
        if ((msg[PRIMARY_START] & FIRST_BIT) == FIRST_BIT) {
            bset.set(1); // secondry bitmap present
            bitmapLen = 16;
        }
        int ndx = 1;
        for (int i = 0; i < bitmapLen; i++) {
            for (int j = 0; j < 8; j++, ndx++) {
                int mask = (FIRST_BIT << j);
                if (i == 0 && j == 0) {
                    continue; // we have already taken care of the secondary bitmap
                }
                if ((msg[PRIMARY_START + i] & mask) == mask) {
                    bset.set(ndx);
                }
            }
        }
        return bset;
    }
}

/**
 * Internal helper class to convert String Array into binary Message
 * @author ashish
 */
class ArrayToMessage {

    private FieldInfo fldArr[] = null;
    int curOff = 0;
    StringBuilder sb = new StringBuilder();
    byte[] bitmap = null;
    public static final int FIRST_BIT = 0x01;

    ArrayToMessage(FieldInfo fldArr[]) {
        this.fldArr = fldArr;
    }

    public byte[] parse(Object oar[]) throws IllegalArgumentException {
        int i, cnt = 0;
        // pass 1
        boolean isbig = oar.length > 65;
        if (isbig) {
            boolean isAllNull = true;
            for (i = 66; i < oar.length; i++) {
                if (oar[i] != null) {
                    isAllNull = false;
                    break;
                }
            }
            if (isAllNull) {
                isbig = false;
            }
        }
        //
        bitmap = new byte[((isbig) ? 16 : 8)];

        int ndx = 1;
        for (i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < 8; j++, ndx++) {
                int mask = (FIRST_BIT << j);
                if (i == 0 && j == 0) {
                    if (isbig) {
                        bitmap[i] |= mask;
                    } else {
                        bitmap[i] &= (~mask);
                    }
                    continue;
                }

                if ((ndx >= oar.length) || (oar[ndx] == null)) {
                    bitmap[i] &= (~mask); // reset bit
                } else {
                    bitmap[i] |= mask; // set the bit
                    if (ndx >= fldArr.length) {
                        throw new IllegalArgumentException("E003: FieldInfo index exceeded ndx=" + ndx);
                    }
                    FieldInfo finfo = fldArr[ndx];
                    if (finfo == null) {
                        throw new IllegalArgumentException("E004: FieldInfo null at ndx=" + ndx);
                    }
                    parseField(oar[ndx], finfo);
                } //else    
            }// for j
        } // for i
        byte body[] = sb.toString().getBytes();
        int msgLen = 4 + bitmap.length + body.length;
        byte msg[] = new byte[msgLen];
        byte mti[];

        if (oar[0] == null) {
            mti = new BinMessageTypeIndicator().getBytes();
        } else if (oar[0] instanceof byte[]) {
            mti = (byte[]) oar[0];
        } else if (oar[0] instanceof BinMessageTypeIndicator) {
            mti = ((BinMessageTypeIndicator) oar[0]).getBytes();
        } else {
            mti = new BinMessageTypeIndicator(oar[0].toString()).getBytes();
        }
        if (mti.length != 4) {
            throw new IllegalArgumentException("E014: MTI lenght should be 4 but got" + mti.length);
        }
        System.arraycopy(mti, 0, msg, 0, 4);
        System.arraycopy(bitmap, 0, msg, 4, bitmap.length);
        System.arraycopy(body, 0, msg, 4 + bitmap.length, body.length);

        return msg;
    }

    private void parseField(Object obj, FieldInfo finfo) {
        String ele = obj.toString();
        String pad = "                ";
        String zero = "000000000000000";
        
        // TODO: Validate the strings for ALPHA, ALNUM, NUM, Date etc.
        if (FieldInfo.Attribute.BIN == finfo.getAttribute()) {
            if(finfo.getFormat() == FieldInfo.Format.FIXED) {
                int dictLen = finfo.getLength() * 2; // 2 Hex digits == 1 binary byte 
                if(dictLen != ele.length()) {
                    if(dictLen > ele.length()) {
                        StringBuilder fpad = new StringBuilder(dictLen);
                        for(int i=ele.length(); i < dictLen; i++)
                            fpad.append('0');
                        fpad.append(ele);
                        ele = fpad.toString();
                    } else {
                        // element length is bigger, trim it
                        ele = ele.substring(0, dictLen);
                    }
                }
            }
            byte[] bin = Hex.hex2bin(ele);            
            ele = new String(bin);
        }
        int len = ele.length();
        String slen;
        switch (finfo.getFormat()) {
            case FIXED:
                if (len > finfo.getLength()) {
                    throw new IllegalArgumentException("E005: field len =" + len + " expected =" + finfo.getLength() + " at [" + finfo.getPropertyName());
                }
                if ((finfo.getAttribute() == FieldInfo.Attribute.NUM) && len < finfo.getLength()) {
                    sb.append(zero.substring(0, finfo.getLength() - len));
                }
                sb.append(ele);
                if ((finfo.getAttribute() != FieldInfo.Attribute.NUM) && len < finfo.getLength()) {
                    sb.append(pad.substring(0, finfo.getLength() - len));
                }

                break;
            case LLLVAR:
                if (len > finfo.getLength() || len > 999) {
                    throw new IllegalArgumentException("E006: field len =" + len + " expected =" + finfo.getLength() + " at [" + finfo.getPropertyName());
                }
                slen = String.valueOf(len);
                if (slen.length() == 1) {
                    sb.append("00");
                } else if (slen.length() == 2) {
                    sb.append("0");
                }
                sb.append(slen);
                sb.append(ele);
                break;
            case LLVAR:
                if (len > finfo.getLength() || len > 99) {
                    throw new IllegalArgumentException("E007: field len =" + len + " expected =" + finfo.getLength() + " at [" + finfo.getPropertyName());
                }
                slen = String.valueOf(len);
                if (slen.length() == 1) {
                    sb.append("0");
                }
                sb.append(slen);
                sb.append(ele);
                break;
            case YYMMDDhhmmss:
                if (obj instanceof java.util.Date) {
                    java.util.Date dt = (java.util.Date) obj;
                    java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyMMddHHmmss");
                    ele = fmt.format(dt);
                }
                break;
            case MMDDhhmmss:
                if (obj instanceof java.util.Date) {
                    java.util.Date dt = (java.util.Date) obj;
                    java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("MMddHHmmss");
                    ele = fmt.format(dt);
                }
                break;
            case DDMMYY:
                if (obj instanceof java.util.Date) {
                    java.util.Date dt = (java.util.Date) obj;
                    java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyMMdd");
                    ele = fmt.format(dt);
                }
                break;
        } //switch
    }
}
