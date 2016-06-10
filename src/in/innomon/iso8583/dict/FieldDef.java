/*
 * FieldDef.java
 * 
 * Created on Oct 1, 2003, 11:17:57 AM
 * 
 */
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
 * @author ASHISH BANERJEE
 */
public class FieldDef implements FieldInfo{
    private    Format fmt;
    private    Attribute attr;
    private    int len;
    private    String label;
    private    int bitmapNdx;

    public FieldDef(Format fmt, Attribute attr, int len, String label, int bitmapNdx ) {
        this.fmt = fmt;
        this.attr = attr;
        this.len = len;
        this.label = label;
        this.bitmapNdx = bitmapNdx;
    }
    public FieldDef(FieldInfo cpy) {
        this.fmt = cpy.getFormat();
        this.attr = cpy.getAttribute();
        this.len = cpy.getLength();
        this.label = cpy.getPropertyName();
        this.bitmapNdx = cpy.getBitMapIndex();
        
    }
    @Override
    public Format getFormat() {
        return fmt;
    }

    @Override
    public Attribute getAttribute() {
        return attr;
    }

    @Override
    public int getLength() {
        return len;
    }

    @Override
    public String getPropertyName() {
       return label;
    }

    @Override
    public int getBitMapIndex() {
        return bitmapNdx;
    }

    public String format2String() {
        return fmt.toString();
    }



}
