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
public class GlobalFieldInfoDict {

    
    public static void registerGlobalFieldInfo(FieldInfo finfo) {
        fieldInfoArr[finfo.getBitMapIndex() % fieldInfoArr.length] = finfo;
    }
    // FieldDef will have start Index

    public static void registerGlobalFieldInfoRange(FieldInfo finfo, int endIndex) {
        for (int i = finfo.getBitMapIndex(); i <= endIndex; i++) {
            FieldInfo fndx = new FieldDef(finfo.getFormat(), finfo.getAttribute(), finfo.getLength(),
                    (finfo.getPropertyName() + "_" + i), i);
            fieldInfoArr[i % fieldInfoArr.length] = fndx;
        }
    }
    public static FieldInfo[] getGlobalFieldInfoArray() {
        return fieldInfoArr;
    }
    protected static FieldInfo fieldInfoArr[] = new FieldInfo[128+1];

    static {
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.MTI, FieldInfo.Attribute.NUM, 4, "MTI", 0));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.MTI, FieldInfo.Attribute.NUM, 16, "bitMap", 1));
        // ndx 1 bitmap handled specially
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 19, "primaryAccountNumber", 2));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 12, "processingCode", 3));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 12, "transactionAmount", 4));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 12, "reconciliationAmount", 5));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 12, "cardHolderBillingAmount", 6));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.MMDDhhmmss, FieldInfo.Attribute.NUM, 10, "transmissionDateTime", 7));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 8, "cardholderBillingFeeAmount", 8));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 8, "conversionRateReconciliation", 9));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 8, "conversionRateCradholerBilling", 10));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 6, "systemTraceAuditNumber", 11));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMMDDhhmmss, FieldInfo.Attribute.NUM, 12, "dateTimeLocalTransaction", 12));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMM, FieldInfo.Attribute.NUM, 4, "dateEffective", 13));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMM, FieldInfo.Attribute.NUM, 4, "dateExpiration", 14));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMM, FieldInfo.Attribute.NUM, 4, "dateSettlement", 15));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMM, FieldInfo.Attribute.NUM, 4, "dateConversion", 16));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMM, FieldInfo.Attribute.NUM, 4, "dateCapture", 17));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 4, "merchantType", 18)); //TODO: what the index values need validation?
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "countryCode", 19)); //TODO: validate countrycode
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "countryCodePrimaryAccountNumber", 20));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "countryCodeForwardingInstitution", 21));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUM, 12, "pointOfServiceDataCode", 22));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "cardSequenceNumber", 23));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "functionCode", 24));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 4, "messageReasonCode", 25));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 4, "cardAcceptorBusinessCode", 26));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 1, "approvalCodeLength", 27));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMMDD, FieldInfo.Attribute.NUM, 6, "dateReconciliation", 28));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "reconciliationIndicator", 29));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 24, "accountsOriginal", 30));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 99, "acquirerReferenceData", 31));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 11, "acquirerInstitutionIdentificationCode", 32));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 11, "forwardingInstitutionIdentificationCode", 33));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 28, "primaryAccountNumberExended", 34));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.BIN, 20, "track2Data", 35));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.BIN, 104, "track3Data", 36));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUMPAD, 12, "retrievalReferenceNumber", 37));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUMPAD, 6, "approvalCode", 38));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "actionCode", 39));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "serviceCode", 40));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUMSPECIAL, 8, "cardAcceptorTerminalIdentification", 41));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUMSPECIAL, 15, "cardAcceptorIdentificationCode", 42));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 99, "cardAcceptorNameLocation", 43));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 99, "additionalResponseData", 44));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 76, "track1Data", 45));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 204, "amountsFees", 46));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "nationalAdditionalData", 47));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "privateAdditionalData", 48));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALPHA_OR_NUM, 3, "transactionCurrencyCode", 49));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALPHA_OR_NUM, 3, "reconciliationCurrencyCode", 50));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALPHA_OR_NUM, 3, "cardHolderBillinCurrencyCode", 51));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.BIN, 8, "pinData", 52));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.BIN, 48, "securityRelatedControlInformation", 53));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 120, "additionalAmounts", 54));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.BIN, 255, "integratedCircuitCardSysRelatedData", 55));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 35, "originalDataElements", 56));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.NUM, 3, "authLifecycleCode", 57));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 11, "authAgentInstitutionIdentificationCode", 58));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "transportData", 59));
// 60...63 reserved
        registerGlobalFieldInfoRange(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "reserved", 60), 63);
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.BIN, 8, "messageAuthCodeField", 64));
// 65 reserved
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.BIN, 8, "reserved65", 65));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 204, "originalFeesAmounts", 66));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 2, "extendedPaymentData", 67));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "receivingInstitutionCountryCode", 68));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "settlementInstitutionCountryCode", 69));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "authAgentInstitutionCountryCode", 70));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 8, "messageNumber", 71));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "dataRecord", 72));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.YYMMDD, FieldInfo.Attribute.NUM, 6, "actionDate", 73));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "creditsNumber", 74));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "creditsReversalNumber", 75));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "debitsNumber", 76));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "debitsReversalNumber", 77));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "transferNumber", 78));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "transferReversalNumber", 79));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "inquiriesNumber", 80));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "authsNumber", 81));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "inquiriesReversalNumber", 82));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "paymentsNumber", 83));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "paymentsReversalNumber", 84));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "feesCollectionNumber", 85));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 16, "creditsAmount", 86));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 16, "creditsReversalAmount", 87));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 16, "debitsAmount", 88));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 16, "debitsReversalAmount", 89));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "authorizationsReversalNumber", 90));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "transactionDestinationInstitutionCountryCode", 91));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 3, "transactionOriginatorInstitutionCountryCode", 92));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 11, "transactionOriginatorInstitutionIdentificationCode", 93));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 11, "transactionDestinationInstitutionIdentificationCode", 94));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 99, "cardIssuerReferenceData", 95));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.BIN, 999, "KeyManagementData", 96));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.SIGN_NUM, 17, "amountReconciliation", 97));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUMSPECIAL, 25, "payee", 98));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUM, 11, "settlementInstitutionIdentificationCode", 99));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.NUM, 11, "receivingInstitutionIdentificationCode", 100));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 17, "fileName", 101));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALPHA, 28, "accountIdentification1", 102));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALPHA, 28, "accountIdentification2", 103));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALPHA, 100, "transactionDescription", 104));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 16, "creditsChargebackAmount", 105));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 16, "debitsChargebackAmount", 106));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "creditsChargebackNumber", 107));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.NUM, 10, "debitsChargebackNumber", 108));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.LLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 84, "creditsFeeAmounts", 109));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUMSPECIAL, 84, "debitsFeeAmounts", 109));
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.ALNUMSPECIAL, 84, "messageAuthCode", 110));
// 111-115 reserved for ISO use LLLVAR ans...999
        registerGlobalFieldInfoRange(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "reservedForISOUse", 111), 115);
// 116-122 reserved for national use LLLVAR ans...999
        registerGlobalFieldInfoRange(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "reservedForNationalUse", 116), 122);
// 123-127 reserved for private use LLLVAR ans...999
        registerGlobalFieldInfoRange(new FieldDef(FieldInfo.Format.LLLVAR, FieldInfo.Attribute.ALNUMSPECIAL, 999, "reservedForPrivateUse", 123), 127);
        registerGlobalFieldInfo(new FieldDef(FieldInfo.Format.FIXED, FieldInfo.Attribute.BIN, 8, "messageAuthCodeField2", 128));

    }
}
    

