package mddemo.library.com.util;

import android.util.Base64;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日23:24:21
 * Description:DES加密类
 */
public class DESUtils {

    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static String encode(String key, String data) throws Exception {
        return encode(key, data.getBytes());
    }

    private static String encode(String key, byte[] data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            return Base64.encodeToString(bytes, 0);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private static byte[] decode(String key, byte[] data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static String decode(String key, String data) {
        byte[] datas;
        String value = null;
        try {
            if (System.getProperty("os.name") != null
                    && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System
                    .getProperty("os.name").equalsIgnoreCase("linux"))) {
                datas = decode(key, Base64.decode(data, 0));
            } else {
                datas = decode(key, Base64.decode(data, 0));
            }
            value = new String(datas);
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

}
