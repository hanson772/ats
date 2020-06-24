package com.milu.ats.util;

import com.milu.ats.bean.enums.EProperty;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Date;

/**
 * @author max.chen
 * @class
 */
public class Tools {
    private static Logger log = LoggerFactory.getLogger(Tools.class);

    public static String Date_Format = "yyyy-MM-dd HH:mm:ss";
    /**
     * id解密
     *
     * @param idStr
     * @return
     */
    public static int idDecode(String idStr) {
        String idEncodeKey = EProperty.PROJECT_ID_ENCODE.value("sadwer23");
        String idPre = EProperty.PROJECT_ID_PRE.value("sad235b");

        String decode = decode(idStr, idEncodeKey);
        if (StringUtils.hasText(decode) && decode.startsWith(idPre)) {
            decode = decode.replaceFirst(idPre, "");
            return Integer.valueOf(decode);
        }
        return 0;
    }

    /**
     * 解密
     * @param object  解密对象
     * @param missKey 解密key
     * @return
     */
    public static String decode(String object, String missKey) {
        String decode = "";
        if (StringUtils.hasText(object)) {
            try {
                DESKeySpec desKeySpec = new DESKeySpec(missKey.getBytes());
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey generateKey = secretKeyFactory.generateSecret(desKeySpec);

                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, generateKey);
                byte[] result = Hex.decodeHex(object.toCharArray());
                decode = new String(cipher.doFinal(result));
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("解密-转码异常");
            }
        }
        return decode;
    }

    /**
     * ID加密
     *
     * @param i
     * @return
     */
    public static String idEncode(Integer i) {
        if (i != null) {
            String idEncodeKey = EProperty.PROJECT_ID_ENCODE.value("sadwer23");
            String idPre = EProperty.PROJECT_ID_PRE.value("sad235b");

            String str = idPre + i;
            return encode(str, idEncodeKey);
        }

        return "";
    }

    /**
     * 加密
     * @param object  加密对象
     * @param missKey 加密key
     * @return
     */
    public static String encode(String object, String missKey) {
        String encode = "";
        if (StringUtils.hasText(object)) {
            try {
                DESKeySpec desKeySpec = new DESKeySpec(missKey.getBytes());
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey generateKey = secretKeyFactory.generateSecret(desKeySpec);

                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, generateKey);
                byte[] resultBytes = cipher.doFinal(object.getBytes());

                encode = Hex.encodeHexString(resultBytes);
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("加密-转码异常");
            }
        }
        return encode;
    }

    public static long dateToTime(Date dt){
        return dt == null ? 0L : dt.getTime();
    }
    public static Date timeToDate(long time){
        return new Date(time < 10000000000L ? time * 1000L : time);
    }
}
