package com.lz.android_bluetooth_sample;

/**
 * @author lizhen
 * @date 21-1-29
 */
public class ByteUtil {
    /**
     * Converts byte array to hexidecimal useful for logging and fault finding
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     * @param hexStr
     * @return
     */
    public  static  String hexStr2Str(String hexStr) {
        String str =  "0123456789ABCDEF" ;
        char [] hexs = hexStr.toCharArray();
        byte [] bytes =  new  byte [hexStr.length() /  2 ];
        int  n;
        for  ( int  i =  0 ; i < bytes.length; i++) {
            n = str.indexOf(hexs[ 2  * i]) *  16 ;
            n += str.indexOf(hexs[ 2  * i +  1 ]);
            bytes[i] = ( byte ) (n &  0xff );
        }
        return  new  String(bytes);
    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public  static  String str2HexStr(String str) {
        char [] chars =  "0123456789ABCDEF" .toCharArray();
        StringBuilder sb =  new  StringBuilder( "" );
        byte [] bs = str.getBytes();
        int  bit;
        for  ( int  i =  0 ; i < bs.length; i++) {
            bit = (bs[i] &  0x0f0 ) >>  4 ;
            sb.append(chars[bit]);
            bit = bs[i] &  0x0f ;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return  sb.toString().trim();
    }


    public static String fromHex(String hex) {
        /*兼容带有\x的十六进制串*/
        hex = hex.replace("\\x","");
        char[] data = hex.toCharArray();
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("字符个数应该为偶数");
        }
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f |= toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return new String(out);
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

}
