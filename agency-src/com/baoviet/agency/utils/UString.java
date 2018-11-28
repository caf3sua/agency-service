/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baoviet.agency.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ha
 */
public class UString {
//	HieuNC 05/10/2017
	private static String pattern = ",";
//	End
	/**
	 * Kiểm tra chuỗi ký tự có phải null hoặc toàn dấu whitespace, tab, enter...
	 *
	 * @param str
	 * @return true nếu str là null hoặc toàn dấu whitespace, tab, enter...
	 */
	public static boolean isNullOrWhitespace(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * Kiểm tra chuỗi ký tự có phải null hoặc toàn dấu whitespace, tab, enter...
	 *
	 * @param str
	 * @return false nếu str là null hoặc toàn dấu whitespace, tab, enter...
	 */
	public static boolean isNotNullAndWhitespace(String str) {
		return !(str == null || "".equals(str.trim()));
	}

	public static boolean isEqual(String str1, String str2) {
		if (str1 == null) {
			str1 = "";
		}
		if (str2 == null) {
			str2 = "";
		}

		return str1.equals(str2);
	}

	/**
	 * So sanh 2 chuoi, bao gom ca null, tu dong cat chuoi va bo qua chu
	 * hoa/thuong
	 *
	 * @param str1
	 * @param str2
	 * @return true nếu bằng nhau, false nếu không bằng.
	 */
	public static boolean compareIgnoreAll(String str1, String str2) {
		str1 = trim(str1);
		str2 = trim(str2);
		if (str1 == null) {
			str1 = "";
		}
		if (str2 == null) {
			str2 = "";
		}

		if (str1.equalsIgnoreCase(str2)) {
			return true;
		}
		return false;
	}

	/**
	 * Loai bo dau trang o 2 dau cua 1 String, neu null hoac '' chuyển thành
	 * null
	 *
	 * @param arg
	 * 
	 * @return String chuoi sau khi duoc loai bo dau trang o 2 dau
	 * @throws Exception
	 */
	public static String trim(String arg) {
		String result = null;
		if (arg != null && !"".equals(arg.trim())) {
			result = arg.trim();
		}
		return result;
	}

	/**
	 * Kiem tra xem String co bang true hay khong
	 *
	 * @param arg
	 *            tham số truy�?n vào kiểu String
	 * @return Boolean false neu null/empty , true neu khong
	 * @throws Exception
	 */
	public static boolean isTrue(String arg) {
		if (arg != null && "true".equalsIgnoreCase(arg.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Convert sang dữ liệu BigDecimal
	 *
	 * @param str
	 * @return null nếu empty string hoặc null
	 */
	public static BigDecimal parseToBigDecimal(String str) throws NumberFormatException {
		return isNullOrWhitespace(str) ? null : new BigDecimal(str);
	}

	/**
	 * Chuyen string sang mang byte
	 * 
	 * @param inputString
	 * @return Mang byte tuong ung voi chuoi string dau vao
	 */
	public static byte[] getBase64Bytes(String inputString) {
		return Base64.getDecoder().decode(inputString);
	}

	/**
	 * Cat string dai qua xuong dung do dai mong muon (da tinh them "..."), phan
	 * con thua de la "..."
	 * 
	 * @param inputString
	 * @param length
	 * @return ket qua sau khi cat giam
	 */
	public static String reduceLength(String inputString, int length) {
		if (inputString != null && inputString.length() > length) {
			inputString = inputString.substring(0, length - 3) + "...";
		}
		return inputString;
	}

	public static String toLikeString(String likeString) {
		if (likeString == null) {
			return "%";
		} else
			return "%" + likeString + "%";
	}

	public static String getSafeFileName(String input) {
		StringBuilder sb = new StringBuilder();
		if (input != null) {
			for (int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				if (c != '/' && c != '\\' && c != 0) {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	public static String extractFileNameNotExt(String fileName) {
		fileName = UString.getSafeFileName(fileName);
		int dotPos = fileName.lastIndexOf(".");
		String fileNameNotExt = dotPos > 0 ? fileName.substring(0, dotPos) : fileName;

		return fileNameNotExt;
	}

	public static String extractFileNameFromPath(String fileFullPath) {
		// fileFullPath = UString.getSafeFileName(fileFullPath);
		int dotPos = fileFullPath.lastIndexOf(File.separatorChar);
		String fileNameNotExt = dotPos > 0 ? fileFullPath.substring(dotPos + 1) : fileFullPath;

		return fileNameNotExt;
	}

	public static String extractFileExt(String fileName) {
		int dotPos = fileName.lastIndexOf(".");
		return dotPos > 0 ? fileName.substring(dotPos) : "";
	}
	
	public static String extractOriginalFileName(String filename) {
		int index = filename.indexOf("_");
		String filenameOriginal = index > 0 ? filename.substring(index + 1) : filename;
		return filenameOriginal;
	}
}