/*
 * Checks if Field Dictionary has any duplicate field names. 
 */
package test;

import in.innomon.iso8583.dict.FieldInfo;
import in.innomon.iso8583.dict.GlobalFieldInfoDict;
import java.util.ArrayList;

/**
 *
 * @author ashish
 */
public class ChkDup extends GlobalFieldInfoDict {
    
    public static void main(String[] args) {
       ArrayList<String> strArr = new  ArrayList<String>();
       for(int i=0; i < fieldInfoArr.length ; i++) {
           FieldInfo fld = fieldInfoArr[i];
           String fname = fld.getPropertyName().toUpperCase();
           if(strArr.contains(fname)) {
               System.out.println("Duplicate Field Name ["+fld.getPropertyName()+"]");
           } else {
               strArr.add(fname);
           }
       } 
       System.out.println("Processed "+fieldInfoArr.length+ " Fields");
    }
}
