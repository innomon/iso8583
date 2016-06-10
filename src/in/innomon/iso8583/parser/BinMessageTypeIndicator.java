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

import in.innomon.iso8583.dict.MessageTypeIndicator;
import java.util.regex.Pattern;

/**
 *
 * @author ashish
 *  section 4.1.1 of ISO8583:87
 */
public class BinMessageTypeIndicator implements MessageTypeIndicator {
     
    private Pattern patMTI = Pattern.compile("[0-9]{4,4}");

    private byte mti[] = new byte[4];

    public BinMessageTypeIndicator() {
        mti[NDX_VER_NO] = VER_1987;
        mti[NDX_MSG_CLASS] = MSG_CLASS_NETWORK_MANAGEMENT;
        mti[NDX_MSG_FUNC] = MSG_FUNC_NOTIFICATION;
        mti[NDX_TXN_ORIG] = TXN_ORIG_OTHER;
    }

    public BinMessageTypeIndicator(String msg) {
        if (!patMTI.matcher(msg).matches()) {
            throw new IllegalArgumentException("Expecting 4 digit numbers got[" + msg + "]");
        }
        for (int i = 0; i < 4; i++) {
            mti[i] = (byte) (msg.charAt(i) - '0');
        }
    }
 
    public BinMessageTypeIndicator(byte[] bin) {
       this(bin,0);
    }
     public BinMessageTypeIndicator(byte[] bin, int startOffset) {
          if (bin == null) {
            throw new IllegalArgumentException(" Null MTI not valid, use default constructor");
        }
         if ((bin.length - startOffset ) < 4) {
            throw new IllegalArgumentException("Expecting 4 bytes, got len=" + bin.length+" start="+ startOffset);
        }         
       for (int i = 0; i < 4; i++) {
            if (bin[i+startOffset] < 0 || bin[i+startOffset] > 9) {
                throw new IllegalArgumentException(" Format error at position " + i + " value should be between 0 & 9");
            }
            mti[i] = bin[i+startOffset];
        }         
    }

    @Override
    public void setVersionNumer(int i) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException(" Expecting values between 0 & 9");
        }
        mti[NDX_VER_NO] = (byte) i;
    }

    @Override
    public void setMessageClass(int i) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException(" Expecting values between 0 & 9");
        }
        mti[NDX_MSG_CLASS] = (byte) i;
    }

    @Override
    public void setMessageFunction(int i) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException(" Expecting values between 0 & 9");
        }
        mti[NDX_MSG_FUNC] = (byte) i;
    }

    @Override
    public void setTransactionOriginator(int i) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException(" Expecting values between 0 & 9");
        }
        mti[NDX_TXN_ORIG] = (byte) i;
    }

    @Override
    public int getVersionNumer() {
        return mti[NDX_VER_NO];
    }

    @Override
    public int getMessageClass() {
        return mti[NDX_MSG_CLASS];
    }

    @Override
    public int getMessageFunction() {
        return mti[NDX_MSG_FUNC];
    }

    @Override
    public int getTransactionOriginator() {
        return mti[NDX_TXN_ORIG];
    }
    
    public byte[] getBytes() {
        return mti;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append((char)('0' + (char)mti[i]));
        }
        return sb.toString();
    }
   
}
