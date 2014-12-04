package com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	/**
	 * yyyy/MM/dd
	 */
	public static final String dateFormatStr = "yyyy/MM/dd";
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
	/**
	 * yyyy年MM月dd日
	 */
	public static final String DATE_FORMAT_2 = "yyyy年MM月dd日";
	/**
	 * yyyy.MM.dd
	 */
	public static final String DATE_FORMAT_3 = "yyyy.MM.dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME_FORMAT_1_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String DATETIME_FORMAT_1_HH_MM = "yyyy-MM-dd HH:mm";
	/**
	 * yyyy年MM月dd日 HH:mm:ss
	 */
	public static final String DATETIME_FORMAT_2_HH_MM_SS = "yyyy年MM月dd日 HH:mm:ss";
	/**
	 * yyyy年MM月dd日 HH:mm
	 */
	public static final String DATETIME_FORMAT_2_HH_MM = "yyyy年MM月dd日 HH:mm";
	/**
	 * yyyy.MM.dd HH:mm:ss
	 */
	public static final String DATETIME_FORMAT_3_HH_MM_SS = "yyyy.MM.dd HH:mm:ss";
	/**
	 * yyyy.MM.dd HH:mm
	 */
	public static final String DATETIME_FORMAT_3_HH_MM = "yyyy.MM.dd HH:mm";

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String DATETIME_FORMAT_yyyyMMddhhmmss = "yyyyMMddHHmmss";

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
	// timeFormatter

	public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat(DATE_FORMAT_1);

	public static final SimpleDateFormat yyyy_MM_dd_hh_mm_ss = new SimpleDateFormat(DATETIME_FORMAT_1_HH_MM_SS);

	public static final SimpleDateFormat yyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss");

	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public static <T> List<T> toList(T[] bsels) {
		if (Utils.nullOrEmpty(bsels)) {
			return Collections.emptyList();
		}
		List<T> l = new ArrayList<T>();
		for (T t : bsels) {
			l.add(t);
		}
		return l;
	}

	public static boolean nullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean nullOrEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean nullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static boolean nullOrEmpty(Integer str) {
		return str == null;
	}

	public static boolean nullOrEmpty(Long str) {
		return str == null;
	}

	public static boolean nullOrEmpty(Double str) {
		return str == null;
	}

	public static boolean nullOrEmpty(String str, boolean trimSpace) {
		if (str == null) {
			return true;
		}
		return trimSpace ? str.trim().isEmpty() : str.isEmpty();
	}

	public static boolean nullOrEmpty(Object[] array) {
		return array == null || array.length <= 0;
	}

	public static boolean nullOrEmpty(byte[] array) {
		return array == null || array.length <= 0;
	}

	public static boolean nullOrEmpty(int[] array) {
		return array == null || array.length <= 0;
	}

	public static boolean nullOrEmpty(long[] array) {
		return array == null || array.length <= 0;
	}

	public static boolean equals(String src, String dest) {
		if (src == null && dest == null) {
			return true;
		}
		return src != null && src.equals(dest);
	}

	public static boolean equals(Integer src, Integer dest) {
		if (src == null && dest == null) {
			return true;
		}
		return src != null && src.equals(dest);
	}

	public static boolean equals(Boolean src, Boolean dest) {
		if (src == null && dest == null) {
			return true;
		}
		return src != null && src.equals(dest);
	}

	public static boolean equals(Float src, Float dest) {
		if (src == null && dest == null) {
			return true;
		}
		return src != null && src.equals(dest);
	}

	public static boolean equals(Date src, Date dest) {
		if (src == null && dest == null) {
			return true;
		}
		return src != null && src.equals(dest);
	}

	public static Double nullToZero(Double number) {
		if (number == null) {
			return 0D;
		} else {
			return number;
		}

	}

	public static Float nullToZero(Float number) {
		if (number == null) {
			return 0F;
		} else {
			return number;
		}

	}

	// /********
	// * MD5加密
	// *
	// * @param str
	// * 要加密的字符串
	// * @param slat
	// * 加密盐
	// * @return 加密后的MD5字符串
	// */
	// public static String encodeMd5(String str, String slat) {
	// Md5PasswordEncoder md5 = new Md5PasswordEncoder();
	// return md5.encodePassword(str, slat);
	// }
	//
	// /********
	// * SHA加密
	// *
	// * @param str
	// * 要加密的字符串
	// * @param slat
	// * 加密盐
	// * @return 加密后的SHA字符串s
	// */
	//
	// public static String encodeSHA(String str, String slat) {
	// ShaPasswordEncoder sha = new ShaPasswordEncoder();
	// return sha.encodePassword(str, slat);
	// }

	/***********
	 * 文件大小格式化显示
	 */
	public static final long KB = 1024;

	public static final long MB = KB * KB;

	public static final long GB = KB * MB;

	public static String fileSize(long fileSize) {
		if (fileSize <= 0) {
			return "";
		} else if (fileSize < MB) {
			BigDecimal b = new BigDecimal((double) fileSize / KB);
			return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "K";
		} else if (fileSize < GB) {
			BigDecimal b = new BigDecimal((double) fileSize / MB);
			return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "M";
		} else {
			BigDecimal b = new BigDecimal((double) fileSize / GB);
			return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "G";
		}
	}

	/**
	 * 两个时间之间相差距离多少天
	 * 
	 * @param one
	 *            时间参数 1：
	 * @param two
	 *            时间参数 2：
	 * @return 相差天数
	 */
	public static long getDistanceDays(Date one, Date two) {
		long days = 0;
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		days = diff / (1000 * 60 * 60 * 24);
		return days;
	}

	/**
	 * 两个时间之间相差距离多少月
	 * 
	 * @param one
	 *            时间参数 1：
	 * @param two
	 *            时间参数 2：
	 * @return 相差月数
	 */
	public static long getDistanceMonths(Date one, Date two) {
		long monthday;
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(one);

		int sYear = starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH);
		int sDay = starCal.get(Calendar.DATE);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(two);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH);
		int eDay = endCal.get(Calendar.DATE);

		monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
		if (sDay < eDay) {
			monthday = monthday + 1;
		}
		return monthday;
	}

	/**
	 * 两个时间之间相差距离多少年
	 * 
	 * @param one
	 *            时间参数 1：
	 * @param two
	 *            时间参数 2：
	 * @return 相差年数
	 */
	public static long getDistanceYears(Date one, Date two) {
		long year;
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(one);

		int sYear = starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(two);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH);

		year = eYear - sYear;
		if (sMonth > eMonth) {
			year = year - 1;
		}
		return year;
	}

	/*********
	 * 日期向后移动
	 * 
	 * @param date
	 *            日期基点
	 * @param field
	 *            要移运的日期 部份，如:Calendar.YEAR,Calendar.MONTH
	 * @param amount
	 *            移动的值
	 * @return
	 */
	public static Date dateAdd(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static Date addYears(Date date, int amount) {
		return dateAdd(date, Calendar.YEAR, amount);
	}

	public static Date addMonths(Date date, int amount) {
		return dateAdd(date, Calendar.MONTH, amount);
	}

	public static Date addDays(Date date, int amount) {
		return dateAdd(date, Calendar.DATE, amount);
	}

	public static Date addHours(Date date, int amount) {
		return dateAdd(date, Calendar.HOUR_OF_DAY, amount);
	}

	public static Date addMinutes(Date date, int amount) {
		return dateAdd(date, Calendar.MINUTE, amount);
	}

	public static Date addSecond(Date date, int amount) {
		return dateAdd(date, Calendar.SECOND, amount);
	}

	public static Date addMilliseconds(Date date, int amount) {
		return dateAdd(date, Calendar.MILLISECOND, amount);
	}

	public static Date onlyDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取日期年月日在一起的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getYear(date));
		buffer.append(getMonth(date));
		buffer.append(getDay(date));
		return buffer.toString();
	}

	/**
	 * 把日期转成格式华后的字符串日期,
	 * 
	 * @param date
	 * @param formart
	 * @return
	 */
	public static String formartDate(Date date, final String formart) {
		SimpleDateFormat df = new SimpleDateFormat(formart);
		return df.format(date);
	}

	/**
	 * 把服务器时间转为字符窜日期
	 * 
	 * @param formart
	 * @return
	 */
	public static String formartDate(final String formart) {
		SimpleDateFormat df = new SimpleDateFormat(formart);
		return df.format(new Date());
	}

	/**
	 * 把字符型的日期转换成日期型
	 * 
	 * @param strDate
	 * @param formart
	 * @return
	 */
	public static Date parseDate(String strDate, final String formart) {
		SimpleDateFormat df = new SimpleDateFormat(formart);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getYear(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static String getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		if (month < 10) {
			return "0" + (month + 1);
		} else {
			return String.valueOf(month);
		}

	}

	public static String getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		if (day < 10) {
			return "0" + day;
		} else {
			return String.valueOf(day);
		}
	}

	public static Date today() {
		return onlyDate(new Date());
	}

	public static Date tomorrow() {
		return addDays(today(), 1);
	}

	public static Date yesterday() {
		return addDays(today(), -1);
	}

	public static boolean isToday(Date date) {
		return onlyDate(date).equals(today());
	}

	/**
	 * 根据出生日期判断是否是儿童
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isChild(Date date) {
		Date today = Utils.today();
		Date birthday = Utils.addYears(date, 14);
		if (birthday.after(today)) {
			return true;
		}
		return false;
	}

	public static final long DayMilliseconds = 86400000;// 24 * 60 * 60 * 1000

	public static int days(Date startDate, Date endDate) {
		return (int) ((endDate.getTime() - startDate.getTime()) / DayMilliseconds) + 1;
	}

	public static int dayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/******
	 * 判断指定日期是否是周六或周日
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		int dayOfWeek = Utils.dayOfWeek(date);
		return dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
	}

	public static int weekends(Date startDate, Date endDate) {
		int days = days(startDate, endDate);
		int m = days % 7;
		if (m == 0) {
			return 2 * days / 7;
		} else {
			int w = 2 * (days - m) / 7;
			int d = dayOfWeek(endDate);
			if (m >= d + 1) {
				w += 2;
			} else if (m == d || d == Calendar.SATURDAY) {
				w += 1;
			}
			return w;
		}
	}

	/***************************************************************************
	 * 二进制流转化成byte[]
	 */
	public static byte[] InputStreamToByte(InputStream iStrm) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int ch;
		while ((ch = iStrm.read()) != -1) {
			stream.write(ch);
		}
		byte data[] = stream.toByteArray();
		stream.flush();
		stream.close();
		return data;
	}

	/***************************************************************************
	 * 汉字url编码转为UTF8
	 * 
	 * @param str
	 * @return
	 */
	public static String urlToUTF8(String str) {
		String strnew = null;
		if (str != null) {
			try {
				return URLEncoder.encode(URLDecoder.decode(str, "UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return strnew;
	}

	/****
	 * 汉字转为UTF8编码
	 * 
	 * @param str
	 * @return
	 */
	public static String strToUTF8(String str) {
		if (str != null) {
			String utr8Str = null;
			try {
				utr8Str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return utr8Str;
		}
		return null;
	}

	/*******
	 * 得到适用于Sql语名like条件符合的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getSqlLikeString(String str) {
		if (str != null) {
			return "%" + str + "%";
		}
		return null;
	}

	/********
	 * 将指定数字格式化为指定长度，不够时前面加0
	 * 
	 * @param number
	 *            数字
	 * @param length
	 *            长度
	 * @return
	 */
	public static String numberToFormatLenth(Long number, int length) {
		String str = String.valueOf(number);
		String format = "";
		for (int i = 0; i < length - str.length(); i++) {
			format = format + "0";
		}
		return format + str;
	}

	/**
	 * 将Object数组转换为String数组
	 * 
	 * @author robin
	 * @param obj
	 *            []
	 * @return
	 */
	public static String[] convertObjArrToStrArr(Object obj[]) {
		if (Utils.nullOrEmpty(obj)) {
			return null;
		}
		String[] str = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			str[i] = (String) obj[i];
		}
		return str;
	}

	/**
	 * ******* 获得当前周的第一天
	 * 
	 * @return
	 */
	public static Date getWeekFirstDay() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String week_strat = df.format(c.getTime());
		Date date = null;
		try {
			date = df.parse(week_strat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * * 获得指定日期的周
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getThisWeeks(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		return c1.get(Calendar.WEEK_OF_YEAR);
	}

	/** 获得当前年*********** */
	public static Integer getThisYear() {
		Calendar c1 = Calendar.getInstance();
		return c1.get(Calendar.YEAR);
	}

	/**
	 * *******获得当前周的最后一天
	 * 
	 * @return
	 */
	public static Date getWeekEndDay() {
		Calendar c_end = Calendar.getInstance();
		c_end.setFirstDayOfWeek(Calendar.MONDAY);
		c_end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String week_end = df.format(c_end.getTime());
		Date date = null;
		try {
			date = df.parse(week_end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeek(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	/** *******获得当前月的第一天***************** */
	public static Date getMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		String month_first = df.format(c.getTime());
		Date date = null;
		try {
			date = df.parse(month_first);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/** *******获得当前月的最后一天***************** */
	public static Date getMonthEndDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);
		String month_end = df.format(cal.getTime());
		Date date = null;
		try {
			date = df.parse(month_end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/***
	 * 取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	/***
	 * 取文件扩展名之前的名称
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileNameNotExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	public static String htmlEncode(int i) {
		if (i == 13 || i == 10) {
			return "";
		}
		if (i == '&')
			return "&amp;";

		else if (i == '<')
			return "&lt;";

		else if (i == '>')
			return "&gt;";

		else if (i == '"')
			return "&quot;";

		else
			return "" + (char) i;

	}

	/**
	 * HTML特殊字符解码处理
	 * 
	 * @param st
	 * @return
	 */
	public static String htmlDecode(String st) {
		if (!Utils.nullOrEmpty(st)) {
			st = st.replaceAll("&amp;", "&");
			st = st.replaceAll("&lt;", "<");
			st = st.replaceAll("&gt;", ">");
			st = st.replaceAll("&quot;", "\"");
			return st;
		}
		return null;
	}

	/**
	 * HTML特殊字符编码处理
	 * 
	 * @param st
	 * @return
	 */
	public static String htmlEncode(String st) {
		if (st != null) {
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < st.length(); i++) {
				buf.append(htmlEncode(st.charAt(i)));
			}
			return buf.toString();
		}
		return null;

	}

	/***
	 * 判断一个字符串是不是纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;

	}

	/**
	 * 判读是否是一个整数或是小数，
	 * 
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isNumeric(str) || isDecimal(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成数字随机组合的数字字符
	 * 
	 * @return
	 */
	public static String randCode() {
		Random random = new Random(System.currentTimeMillis());
		String randCode = "";
		for (int i = 0; i < 6; i++) {
			randCode += String.valueOf(random.nextInt(10));
		}
		return randCode;
	}

	/**
	 * 把字符数组转换成字符的Set对象
	 * 
	 * @param str
	 * @return
	 */
	public static Set<String> coverSet(String[] str) {
		if (str.length > 0) {
			List<String> list = Arrays.asList(str);
			Set<String> set = new LinkedHashSet<>(list);
			set.remove("");
			set.remove(null);
			return set;
		} else {
			return null;
		}
	}

	/**
	 * 把字符数组转换成字符的Set对象
	 * 
	 * @param str
	 * @return
	 */
	public static Set<Long> coverLongSet(String[] str) {
		if (str.length > 0) {
			List<String> list = Arrays.asList(str);
			Set<String> set = new LinkedHashSet<String>(list);
			set.remove("");
			set.remove(null);
			Set<Long> selected = new LinkedHashSet<Long>(set.size());
			for (String id : set) {
				selected.add(Long.valueOf(id));
			}
			return selected;
		} else {
			return null;
		}
	}

	/**
	 * 拷贝对象的字段值到目标对象
	 * 
	 * @param target
	 *            目标对象
	 * @param object
	 *            源对象
	 * @return
	 */
	public static Object copy(Object target, Object object) {
		// 获得对象的类型
		Class<? extends Object> classType = object.getClass();
		Class<? extends Object> targetType = target.getClass();
		// 通过默认构造方法创建一个新的对象
		try {
			// 获得对象的所有属性
			Field fields[] = classType.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set" + firstLetter + fieldName.substring(1);
				// 获得和属性对应的getXXX()方法
				try {
					Method getMethod = classType.getMethod(getMethodName, new Class[] {});
					// 获得和属性对应的setXXX()方法
					Method setMethod = targetType.getMethod(setMethodName, new Class[] { field.getType() });
					// 调用原对象的getXXX()方法
					Object value = getMethod.invoke(object, new Object[] {});
					System.out.println(fieldName + ":" + value);
					// 调用复制对象的setXXX()方法
					setMethod.invoke(target, new Object[] { value });
				} catch (NoSuchMethodException e) {
					System.out.println("没有此方法 : " + e.getMessage());
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {

			e.printStackTrace();
		}

		return target;
	}
	public static String trim(String str){
		return str.replaceAll(" ", "");
	}
	public static void main(String[] str) {
		String text = "1,2，3.4。5;6！7;8";
		// text = text.replaceAll("[\\p{Punct}\\p{Space}]+", "");
		text = text.replaceAll("[\\pP]", "");
		System.out.println(text);
	}

}
