/*
 * Test program to convert a binary ISO8583 file payload to XML
 */

package test;


import in.innomon.iso8583.xml.Iso8583ToXml;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author ashish
 */
public class I2X {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        String fl = null;
        if(args.length != 1) {
            System.out.println("Usage I2X <binary file>");
            System.exit(1);
        }
        fl = args[0];
        RandomAccessFile ras = new RandomAccessFile(fl,"r");
        int len = (int)ras.length();
        byte[] msg = new byte[len];
        ras.readFully(msg);
        ras.close();
        
        Iso8583ToXml ix = new Iso8583ToXml();
        ix.parse(System.out, msg);
        
        
    }
}
