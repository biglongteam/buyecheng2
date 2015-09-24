package com.hangzhou.tonight.module.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 返回unix时间戳 (1970年至今的秒数)
	 * 
	 * @return
	 */
	public static long getUnixStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 得到昨天的日期
	 * 
	 * @return
	 */
	public static String getYestoryDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yestoday = sdf.format(calendar.getTime());
		return yestoday;
	}

	/**
	 * 得到今天的日期
	 * 
	 * @return
	 */
	public static String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}

	/**
	 * 时间戳转化为时间格式
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String timeStampToStr(long timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(timeStamp * 1000);
		return date;
	}

	/**
	 * 得到日期 yyyy-MM-dd
	 * 
	 * @param timeStamp
	 *            时间戳
	 * @return
	 */
	public static String formatDate(long timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(timeStamp * 1000);
		return date;
	}

	/**
	 * 得到时间 HH:mm:ss
	 * 
	 * @param timeStamp
	 *            时间戳
	 * @return
	 */
	public static String getTime(long timeStamp) {
		String time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(timeStamp * 1000);
		String[] split = date.split("\\s");
		if (split.length > 1) {
			time = split[1];
		}
		return time;
	}

	/**
	 * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String convertTimeToFormat(long timeStamp) {
		long curTime = System.currentTimeMillis() / (long) 1000;
		long time = curTime - timeStamp;

		if (time < 60 && time >= 0) {
			return "刚刚";
		} else if (time >= 60 && time < 3600) {
			return time / 60 + "分钟前";
		} else if (time >= 3600 && time < 3600 * 24) {
			return time / 3600 + "小时前";
		} else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
			return time / 3600 / 24 + "天前";
		} else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
			return time / 3600 / 24 / 30 + "个月前";
		} else if (time >= 3600 * 24 * 30 * 12) {
			return time / 3600 / 24 / 30 / 12 + "年前";
		} else {
			return "刚刚";
		}
	}

	/**
	 * 将一个时间戳转换成提示性时间字符串，(多少分钟)
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String timeStampToFormat(long timeStamp) {
		long curTime = System.currentTimeMillis() / (long) 1000;
		long time = curTime - timeStamp;
		return time / 60 + "";
	}
	
	
	public static String getFormatDate(int year,int month,int day,String pattern){
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		return sdf.format(c.getTime());
	}
	
	public static void setDate(Calendar c,String date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			d = null;
		}
		if(null != d && c != null){
			c.setTime(d);
		}
	}
	
	/**
	  * @author jerry.chen
	  * @param brithday
	  * @return
	  * @throws ParseException
	  *             根据生日获取年龄;
	  */
	 public static int getCurrentAgeByBirthdate(String brithday) {
		int age;
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			Date brithDay = formatDate.parse(brithday);
			Calendar cal = Calendar.getInstance();

			if (cal.before(brithDay)) {
				throw new IllegalArgumentException(
						"The brithDay is before Now.It's unbelievable!");
			}

			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(brithDay);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
			age = yearNow - yearBirth;
			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) { // monthNow==monthBirth
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					}
				} else {// monthNow>monthBirth
					age--;
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return age;
	 }
	 
	 static String[][] cons = new String[][]{
		 {"水瓶座","0120","0218"}
		,{"双鱼座","0219","0320"}
		,{"白羊座","0321","0419"}
		,{"金牛座","0420","0520"}
		,{"双子座","0521","0621"}
		,{"巨蟹座","0622","0722"}
		,{"狮子座","0723","0822"}
		,{"处女座","0823","0922"}
		,{"天秤座","0923","1023"}
		,{"天蝎座","1024","1122"}
		,{"射手座","1123","1221"}
		,{"摩羯座","1221","0119"}
 };
	 
	 
	 
 /**
  * 获取星座
  * @param date
  * @param pattern
 * @return 
  */
 public static String getConstellation(String date,String pattern){
	Date d = null;
	try {
		d = new SimpleDateFormat(pattern).parse(date);
		String s = sdf_MM_dd.format(d);
		for (int i = 0, len = cons.length; i < len; i++) {
			String[] strs = cons[i];
			if (s.compareTo(strs[1]) >= 0 && s.compareTo(strs[2]) <= 0) {
				return strs[0];
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
 }
 
 public static final String pattern_df = "yyyy-MM-dd";
 public static final SimpleDateFormat sdf_yyyy_MM_dd_h = new SimpleDateFormat(pattern_df);
 public static final SimpleDateFormat sdf_MM_dd = new SimpleDateFormat("MMdd");
}
