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
 *
 * @author ashish
 */
public interface MessageTypeIndicator {
    int MSG_CLASS_ANMINISTRATIVE = 6;
    int MSG_CLASS_AUTHORIZATION = 1;
    int MSG_CLASS_FEE_COLLECTION = 7;
    int MSG_CLASS_FILE_ACTION = 3;
    int MSG_CLASS_FINANCIAL = 2;
    int MSG_CLASS_NETWORK_MANAGEMENT = 8;
    int MSG_CLASS_RECONCILIATION = 5;
    int MSG_CLASS_RESERVED_0 = 0;
    int MSG_CLASS_RESERVED_9 = 9;
    int MSG_CLASS_REVERSAL_CHARGEBACK = 4;
    int MSG_FUNC_ADVICE = 2;
    int MSG_FUNC_ADVICE_RESPONSE = 3;
    int MSG_FUNC_NOTIFICATION = 4;
    int MSG_FUNC_REQUEST = 0;
    int MSG_FUNC_REQUEST_RESPONSE = 1;
    int MTI_LEN = 4;
    int NDX_MSG_CLASS = 1;
    int NDX_MSG_FUNC = 2;
    int NDX_TXN_ORIG = 3;
    int NDX_VER_NO = 0;
    int TXN_ORIG_ACQUIRER = 0;
    int TXN_ORIG_ACQUIRER_REPEAT = 1;
    int TXN_ORIG_CARD_ISSUER = 2;
    int TXN_ORIG_CARD_ISSUER_REPEAT = 3;
    int TXN_ORIG_OTHER = 4;
    int TXN_ORIG_OTHER_REPEAT = 5;
    int VER_1987 = 0;
    int VER_1993 = 1;
    int VER_2003 = 2;

    int getMessageClass();

    int getMessageFunction();

    int getTransactionOriginator();

    int getVersionNumer();

    void setMessageClass(int i);

    void setMessageFunction(int i);

    void setTransactionOriginator(int i);

    void setVersionNumer(int i);
    
}
