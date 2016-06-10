/*
  * Inputs an ISO8583 binary file and outputs Java Property formatted file.
  */
package test;

import in.innomon.iso8583.dict.MessageTokenizer;
import in.innomon.iso8583.parser.BinMessageTokenizer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;

/**
 *
 * @author ashish
 */
public class I2P {
     
     public static  Properties genProp(byte[] msg) {
         MessageTokenizer msgTnz = new BinMessageTokenizer();
         Properties prop = new Properties();
         String[] strArr = msgTnz.MessageToStringArray(msg);
         for(int i=0; i < strArr.length; i++) {
             if(strArr[i] != null)
                 prop.setProperty(msgTnz.getFieldName(i), strArr[i]);
         }
         return prop;
     }
     public static void main(String args[]) throws FileNotFoundException, IOException {
        String fl = null,pfl;
        if(args.length != 2) {
            System.out.println("Usage I2P <input binary file> <output property file>");
            System.exit(1);
        }
        fl = args[0];
        pfl = args[1];
        RandomAccessFile ras = new RandomAccessFile(fl,"r");
        int len = (int)ras.length();
        byte[] msg = new byte[len];
        ras.readFully(msg);
        ras.close();
        
        Properties prop = genProp(msg);
        FileOutputStream out = new FileOutputStream(pfl);
        prop.store(out, fl);
        out.close();
        System.out.println("generated "+pfl);
    }   
}
