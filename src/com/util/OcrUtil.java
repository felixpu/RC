package com.util;

import java.io.File;

import com.asprise.ocr.Ocr;

public class OcrUtil {
	private String text;

	public String getText() {
		return text;
	}

	public OcrUtil(File file) {
		if (file.exists()) {
			Ocr.setUp();
			Ocr ocr = new Ocr();
			ocr.startEngine("eng", Ocr.SPEED_SLOW);
			String s = ocr.recognize(new File[] { file }, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT,
					new Object[] {});
			// if (file.exists()) {
			// file.delete();
			// }
			this.text = s.replaceAll(" ", "");
			// System.out.println(s);
		} else {
			this.text = "";
		}
	}
	// public static void main(String[] args) {
	// File pfile = new File("G:/oldimag/" + "111.jpg");
	// new OcrUtil(pfile);
	// }
}
