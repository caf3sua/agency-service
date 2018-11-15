package com.baoviet.agency.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateUtils {

	private final Logger log = LoggerFactory.getLogger(ValidateUtils.class);

	public static Boolean isEmail(String email) {
		try {
			Pattern p = Pattern.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			Matcher m = p.matcher(email);
			if (m.find()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception Ex) {
			return false;
		}
	}

	public static Boolean isPhone(String phone) {
		Pattern pattern = null;
		Matcher matcher = null;
		try {
			// matches 10-digit numbers only
			String regexStr1 = "^[0-9]{10,11}$";

			pattern = Pattern.compile(regexStr1);
			matcher = pattern.matcher(phone);
			if (matcher.matches()) {
				return true;
			}
			//matches +84xxxxxxxxx
			String regexStr2 = "^(\\+)?(84)[0-9]{9,10}$";

			pattern = Pattern.compile(regexStr2);
			matcher = pattern.matcher(phone);
			if (matcher.matches()) {
				return true;
			}

			return false;
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
	}

}
