package mddemo.library.com.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日23:59:32
 * Description:资源的操作累
 */
public class ResouceFileUtil {

	//从assets中读取资源
	public static String readStringFromAssert(Context context, String fileName) {
		String result = null;
		byte[] buffer = readBytesFromAssert(context, fileName);
		try {
			result = new String(buffer, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	//从raw中读取资源
	public static String readStringFromRaw(Context context, int rawId) {
		String result = null;
		byte[] buffer = readBytesFromRaw(context, rawId);
		try {
			result = new String(buffer, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	//assets中的资源到字节数组
	public static byte[] readBytesFromAssert(Context context, String fileName) {
		InputStream is = null;
		byte[] buffer = null;
		try {
			is = context.getAssets().open(fileName);
			int size = is.available();
			buffer = new byte[size];
			is.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return buffer;
	}

	//从raw文件夹下读取文件到字节数组
	public static byte[] readBytesFromRaw(Context context, int rawId) {
		InputStream is = null;
		byte[] buffer = null;
		try {
			is = context.getResources().openRawResource(rawId);
			int size = is.available();
			buffer = new byte[size];
			is.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer;
	}
}
