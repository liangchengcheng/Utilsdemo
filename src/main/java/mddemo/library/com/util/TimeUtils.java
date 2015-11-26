package mddemo.library.com.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月8日22:36:41
 * Description:对时间和时间戳的操作
 */
public class TimeUtils {

    /**
     * 将时间戳格式化用户可以理解的时间
     * @param time 时间戳
     * @return 时间字符串
     */
    public static String singltime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);

    }

    /**
     * 将时间转换成字符串
     * @param user_time 时间格式的字符串
     * @return 时间戳
     */
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 将时间戳截取到10位数
     * @param time 传进来的时间戳
     * @return 10位数的时间戳
     */
    public static String singlechou(long time) {
        String timechou = time + "";
        timechou = timechou.substring(0, timechou.length() - 3);
        return timechou;
    }

    //获取当前的时间
    public static long getTime() {
        return System.currentTimeMillis();
    }
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 获取年龄值
     * @param birthday
     * @return
     */
    public static int getAge(String birthday) {
        if (StringUtils.isEmpty(birthday)) return 0;
        String yearStr = birthday.substring(0, 4);
        int integer = Integer.parseInt(yearStr);
        int newYear = new GregorianCalendar().get(Calendar.YEAR);
        return newYear - integer;
    }
}
