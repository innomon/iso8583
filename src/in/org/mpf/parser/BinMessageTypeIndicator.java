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

import in.org.mpf.iso8583.dict.MessageTypeIndicator;
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
