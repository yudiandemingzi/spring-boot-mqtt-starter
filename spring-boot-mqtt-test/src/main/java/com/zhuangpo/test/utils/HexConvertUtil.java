package com.zhuangpo.test.utils;

import java.math.BigInteger;

/**
 * Created by Administrator on 2019/1/5.
 */
public class HexConvertUtil {

    /**
     * 在进制表示中的字符集合
     */
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    /**
     * 字符串转为16进制数据
     *
     * @param str
     * @return
     */
    public static String convertStringToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }


    /**
     * 16进制转为字符串
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2) {

            String s = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            sb.append((char) decimal);
            sb2.append(decimal);
        }

        return sb.toString();
    }

    /**
     * 16进制字符串到字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        String replace = hexString.replace(" ", "");
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = replace.toUpperCase();
        int length = replace.length() / 2;
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = replace.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 返回匹配字符
     *
     * @param c
     * @return
     */
    private static byte charToByte(char c) {

        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 将字节数组转换为short类型，即统计字符串长度
     *
     * @param b
     * @return
     */
    public static short bytes2Short2(byte[] b) {
        short i = (short) (((b[1] & 0xff) << 8) | b[0] & 0xff);
        return i;
    }

    /**
     * 将字节数组转换为16进制字符串
     *
     * @param bytes 字节数组
     * @return
     */
    public static String BinaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }


    /**
     * 将其它进制的数字（字符串形式）转换为十进制的数字(无符号)
     *
     * @param str1 其它进制的数字（字符串形式）
     * @param base 传进的字符串原始进制数
     * @return
     */
    public static long toDecimalism(String str1, int base) {
        //去除空格
        String str = str1.replace(" ", "");
        char[] buf = new char[str.length()];
        str.getChars(0, str.length(), buf, 0);
        long num = 0;
        for (int i = 0; i < buf.length; i++) {
            for (int j = 0; j < digits.length; j++) {
                if (digits[j] == buf[i]) {
                    num += j * Math.pow(base, buf.length - i - 1);
                    break;
                }
            }
        }
        return num;
    }

    /**
     * 十六进制字符串转十进制long (有符号)
     *
     * @param str
     * @return
     */
    public static Long hexToLong(String str) {

        BigInteger integer = new BigInteger(str.replace(" ",""), 16);
        return integer.longValue();
    }

    /**
     * 将十进制的数字转换为指定进制的字符串
     *
     * @param n    十进制的数字
     * @param base 指定的进制
     * @return
     */
    public static String toOtherBaseString(long n, int base) {
        long num = 0;
        if (n < 0) {
            num = ((long) 2 * 0x7fffffff) + n + 2;
        } else {
            num = n;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((num / base) > 0) {
            buf[--charPos] = digits[(int) (num % base)];
            num /= base;
        }
        buf[--charPos] = digits[(int) (num % base)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 验证和校验
     *
     * @param str
     * @return
     */
    public static boolean examine(String str) {
        String replace = str.replace(" ", "");
        if (replace.length() < 5 || replace.length() % 2 != 0) {
            throw new ClassCastException("数据长度不合法");
        }
        long num = 0;
        for (int i = 0; i < (replace.length()-4)/ 2; i++) {
            Long aLong = hexToLong(replace.substring(2 * i, 2 * i + 2));
            num = num + aLong;
        }
        String substring = replace.substring(replace.length() - 4);
        if (num != hexToLong(substring)) {
            return false;
        }
        return true;
    }
    /**
     * 验证和校验
     *
     * @return
     */
    public static boolean examine(byte[] bytes) {

        String replace = BinaryToHexString(bytes).replace(" ", "");
        if (replace.length() < 5 || replace.length() % 2 != 0) {
            throw new ClassCastException("数据长度不合法");
        }
        long num = 0;
        for (int i = 0; i < (replace.length()-4)/ 2; i++) {
            Long aLong = hexToLong(replace.substring(2 * i, 2 * i + 2));
            num = num + aLong;
        }
        String substring = replace.substring(replace.length() - 4);
        if (num != hexToLong(substring)) {
            return false;
        }
        return true;
    }
    /**
     * 十六进制加上和校验
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String getExamine(String str) throws Exception {
        String replace = str.replace(" ", "");
        if (replace.length() % 2 != 0) {
            throw new ClassCastException("数据长度不合法");
        }
        long num = 0;
        for (int i = 0; i < replace.length() / 2; i++) {
            Long aLong = hexToLong(replace.substring(2 * i, 2 * i + 2));
            num = num + aLong;
        }
        String s = toOtherBaseString(num, 16);
        return replace + zeroize(s, 4);
    }

    /**
     * 根据长度在字符串前边补零
     *
     * @param str
     * @param size
     * @return
     * @throws Exception
     */
    public static String zeroize(String str, int size) throws Exception {
        String replace = str.replace(" ", "");
        if (size < str.length()) {
            throw new Exception("数据长度不合法");
        }
        for (int i = 0; i < size - str.length(); i++) {
            replace = "0" + replace;
        }
        return replace;
    }

    public static void main(String[] args) throws Exception {
    }
}
