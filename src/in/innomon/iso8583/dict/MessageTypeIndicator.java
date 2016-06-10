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
