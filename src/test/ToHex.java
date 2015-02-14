/*
 * Inputs a binary file and outputs hex dump
 */
package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author ashish banerjee
 */
public class ToHex {
    private ToHex() {}
    private static char hexNdx[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    
    public static void byte2hex(byte b, StringBuilder sbld) {
        int ndx = b & 0xff;
        sbld.append(hexNdx[(ndx >>>4)]);
        sbld.append(hexNdx[(ndx &0xf)]);
    }
     public static void main(String args[]) throws FileNotFoundException, IOException {
         String fl = null; 
         if(args.length ==1) {
             fl = args[0];
         } else {
             System.out.println("usage: ToHex <input binary file>");
             System.exit(1);
         }
         System.out.println("Reading ["+fl+"]");
         RandomAccessFile ras = new RandomAccessFile(fl,"r");
        int len = (int)ras.length();
        byte[] msg = new byte[len];
        ras.readFully(msg);
        ras.close();
        int i;
        System.out.println("file lenght ="+len);
        System.out.println("-----Bin Dump Start ----");
        char c;
        for(i=0; i < len; i++) {
            c = (msg[i] >= '0' && msg[i] <= 'z' )? (char)msg[i]: '.';
                
            System.out.print(c);
        }
        System.out.println("\n-----Bin Dump End ----");
        StringBuilder sb = new StringBuilder();
        for(i=0; i < len;i++) {
            if((i%10) == 0) {
                sb.append("\n ndx[").append(i).append("]\n");
            }
            byte2hex(msg[i],sb);
            sb.append(" ");
        }
        System.out.println(sb);
     }
}
