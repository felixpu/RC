package com.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.MsgService;
import com.util.PraseXml;
import com.util.Utils;

@Service
public class MsgServiceImpl implements MsgService {

	@Autowired
	private HttpServletRequest request;

	@Override
	public boolean writeTxt() {

		String txt = null;
		try {
			txt = URLDecoder.decode(request.getParameter("txt"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (Utils.nullOrEmpty(txt.trim())) {
			return false;
		}

		// String addr = request.getRemoteAddr();
		// String username = PraseXml.getInstence().fileInfo.get(addr);
		// if (Utils.nullOrEmpty(username)) {
		// username = addr + "：";
		// }
		FileWriter writer = null;
		BufferedWriter bw = null;
		try {
			writer = new FileWriter(PraseXml.getInstence().fileInfo.get("PROFILE"), true);
			bw = new BufferedWriter(writer);
			bw.append(txt);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public String readTxt() {
		File file = new File(PraseXml.getInstence().fileInfo.get("PROFILE"));
		if (!file.exists()) {
			return "";
		}
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			InputStream inputStream = new FileInputStream(file);
			inputReader = new InputStreamReader(inputStream);
			bufferReader = new BufferedReader(inputReader);

			// 读取一行
			String line = null;

			while ((line = bufferReader.readLine()) != null) {
				strBuffer.append(line);
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputReader.close();
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strBuffer.toString();
	}

}
