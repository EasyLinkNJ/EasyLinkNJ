package com.easylink.library.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author yhb
 *
 */
public class TextUtil {
	
	public static final String TEXT_EMPTY = "";
	public static final String TEXT_ZERO = "0";
	
	public static String filterNull(String str){
		
		return str == null ? TEXT_EMPTY : str;
	}

    public static String filterEmpty(String str, String def){

        return isEmpty(str) ? def : str;
    }

	public static boolean isEmpty(CharSequence str) {
		
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	public static boolean isNotEmpty(CharSequence charSequ){

		return charSequ != null && charSequ.length() > 0;
	}
	
	public static boolean isEmptyTrim(String str) {
		
		if (str == null || str.trim().length() == 0)
			return true;
		else
			return false;
	}
	/**
	 * 判断str是否是数字组成的
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		
		if(str == null || str.length() == 0)
			return false;
		
		return TextUtils.isDigitsOnly(str);
	}
	

	/**
	 * 检查邮箱正确性
	 * @param email
	 * @return false为错误
	 */
	public static boolean checkMailFormat(String email) {
		
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.find();
	}

	public static int calculateWeiboLength(CharSequence c) {
		
		double len = 0;
		for (int i = 0; i < c.length(); i++) {
			int temp = (int) c.charAt(i);
			if (temp > 0 && temp < 127) {
				len += 0.5;
			} else {
				len++;
			}
		}
		return (int) Math.round(len);
	}

	public static String resetTextBy100(String text) {
		
		double len = 0;
		for (int i = 0; i < text.length(); i++) {
			int temp = (int) text.charAt(i);
			if (temp > 0 && temp < 127) {
				len += 0.5;
			} else {
				len++;
			}
			if ((int) Math.round(len) == 100) {
				return text.substring(0, i) + "...";
			}
		}
		return text;
	}
	/**
	 * 验证手机号
	 * @param str
	 * @return
	 */
	public static boolean isMobile(CharSequence str) {
		
		if(isEmpty(str))
			return false;
		
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
