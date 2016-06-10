/*
 *  Converts a Properties file to ISO8583 Binary file
 */
package test;

import in.innomon.iso8583.dict.FieldInfo;
import in.innomon.iso8583.dict.FieldName2Index;
import in.innomon.iso8583.dict.GlobalFieldInfoDict;
import in.innomon.iso8583.dict.MessageTokenizer;
import in.innomon.iso8583.parser.BinMessageTokenizer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 *
 * @author ashish
 */
public class P2I {
   public static byte[] parse(Properties prop) {
       FieldInfo fieldInfoArr[] = GlobalFieldInfoDict.getGlobalFieldInfoArray();
       byte ret[] = null;
       FieldName2Index f2i = new FieldName2Index(fieldInfoArr);
       Iterator<String> flds = prop.stringPropertyNames().iterator(); 
       
       String valArr[] = new String[fieldInfoArr.length];
       
       while(flds.hasNext()) {
           String fld = flds.next();
           int ndx = f2i.getIndex(fld);
           if(ndx < 0) {
               System.out.println("Field not in Dictionary ["+fld+"]");
           } else {
               valArr[ndx] = prop.getProperty(fld);
           }
       }
       MessageTokenizer mtok = new BinMessageTokenizer(fieldInfoArr);
       ret = mtok.arrayToMessage(valArr);
       
       return ret;
   } 
   public static void main(String args[]) throws FileNotFoundException,  IOException {
        String flIn = null, flOut = null;
        if(args.length != 2) {
            System.out.println("Usage P2I <input Properties file> <output binary file>");
            System.exit(1);
        }
        flIn = args[0];  
        flOut = args[1];
        
        Properties prop = new Properties();
        FileInputStream fin = new java.io.FileInputStream(flIn);
        prop.load(fin);
        fin.close();
        
        byte[] out = parse(prop);
        FileOutputStream fos = new FileOutputStream(flOut, false);
        fos.write(out);
        fos.close();
        System.out.println("Generated "+flOut);
    }    
}
