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
    

