package mddemo.library.com.util;

import android.content.Context;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日23:35:46
 * Description:单位转换工具
 */

public class DensityUtil {

	//dp转换成px
	public static int dip2px(Context context, float dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	//px转换成dp
	public static int px2dip(Context context, float px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	//sp转换成px
	public static int sp2px(Context context, float sp) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (sp * scale + 0.5f);
	}

	//px转换成sp
	public static int px2sp(Context context, float px) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (px / scale + 0.5f);
	}
}
