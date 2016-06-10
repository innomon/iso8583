/*
 * Converts an XML file to ISO8583 Binary file
 */

package test;


import in.innomon.iso8583.xml.XmlToIso8583;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author ashish
 */
public class X2I {
    public static void main(String args[]) throws FileNotFoundException, XMLStreamException, IOException {
        String flIn = null, flOut = null;
        if(args.length != 2) {
            System.out.println("Usage X2I <input xml file> <output binary file>");
            System.exit(1);
        }
        flIn = args[0];  
        flOut = args[1];
        XmlToIso8583 xi = new XmlToIso8583();
        byte[] out = xi.parse(new java.io.FileInputStream(flIn));
        FileOutputStream fos = new FileOutputStream(flOut, false);
        fos.write(out);
        fos.close();
        System.out.println("Generated "+flOut);
    }
}
