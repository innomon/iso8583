/*
 * Generates XML output containing fields diectionary.
 */
package test;

import in.innomon.iso8583.parser.BinMessageTokenizer;



/**
 *
 * @author ashish
 */
public class GenXmlDef extends BinMessageTokenizer {

    public static java.io.PrintStream prn = System.out;

    public static void main(String[] args) {
        genDef();
    }

    public static void genDef() {
        header();
        body();
        footer();

    }

    public static void header() {
        prn.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        prn.println("<ab:ParseIso8583   xmlns:ab='http://www.ashishbanerjee.com/parse/iso8583' >");
    }

    public static void body() {
        for (int i = 0; i < fieldInfoArr.length; i++) {
            if (fieldInfoArr[i] == null) {
                continue;
            }
            String fmt = fieldInfoArr[i].getFormat().name();
            String attr = fieldInfoArr[i].getAttribute().name();
            int len = fieldInfoArr[i].getLength();
            prn.println("    <ab:field name=\"" + fieldInfoArr[i].getPropertyName() + "\" offset=\"" + i + "\" format=\"" + fmt + "\" attr=\"" + attr + "\" len=\"" + len + "\" />");
        }

    }

    public static void footer() {
        prn.println("</ab:ParseIso8583>");
    }
}

class GenEmpty extends BinMessageTokenizer {

    public static java.io.PrintStream prn = System.out;

    public static void gen() {
        header();
        body();
        footer();
    }

    public static void header() {
        prn.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        prn.println("<ab:InIso8583   xmlns:ab='http://www.ashishbanerjee.com/in/iso8583' >");
    }
    public static void body() {
        for (int i = 0; i < fieldInfoArr.length; i++) {
            if (fieldInfoArr[i] == null) {
                continue;
            }
            String fmt = fieldInfoArr[i].getFormat().name();
            String attr = fieldInfoArr[i].getAttribute().name();
            int len = fieldInfoArr[i].getLength();
            prn.println("    <ab:"+fieldInfoArr[i].getPropertyName() + "> </ab:" +fieldInfoArr[i].getPropertyName() + ">");
        }

    }
    public static void footer() {
        prn.println("</ab:InIso8583>");
    }
}
