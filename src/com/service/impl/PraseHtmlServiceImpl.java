package com.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dto.Info;
import com.entiy.BaseInfo;
import com.prase.PraseHtml;
import com.service.PraseHtmlService;
import com.util.Constants;
import com.util.PraseXml;
import com.util.Utils;

@Service
public class PraseHtmlServiceImpl implements PraseHtmlService {

	@Autowired
	private HttpServletRequest request;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.service.PraseHtmlService#getPersonInfo(com.entiy.BaseInfo)
	 */
	@Override
	public Info getPersonInfo(BaseInfo bf) {
		List<Map<String, String>> list = getMaps(bf);
		Info info = null;
		if (!Utils.nullOrEmpty(list)) {
			Map<String, String> map = list.get(0);
			if (!map.isEmpty()) {
				info = new Info(map.get("xingming"), map.get("lianxidianhua"), map.get("dianziyoujian"));
			}
		} else {
			info = new Info("非简历文件，请重新选择！");
		}
		return info;
	}

	@Override
	public String getPraseHtmlResult(BaseInfo bf) {
		List<Map<String, String>> list = getMaps(bf);
		if (list.isEmpty() || list == null) {
			return "非简历文件，请重新选择！";
		}
		Map<String, String> map = list.get(0);
		map.putAll(infoMap(bf.getName(), bf.getMobile(), bf.getEmail(), bf.getPosition()));

		return writeDoc(map, getAddr());

	}

	private String writeDoc(Map<String, String> map, String raddr) {
		try {
			// 读取word模板
			String path = this.getClass().getClassLoader().getResource("Templates.doc").getPath();
			File domeFile = new File(path);
			FileInputStream in = new FileInputStream(domeFile);
			HWPFDocument hdt = new HWPFDocument(in);

			// 读取word文本内容
			Range range = hdt.getRange();

			// 替换文本内容
			for (Map.Entry<String, String> entry : map.entrySet()) {
				range.replaceText(entry.getKey(), entry.getValue());
			}
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();

			String position = PraseXml.getInstence().py.get("POSITION");
			String fileName = "维森凯睿推荐-" + ((map.get(position) != null) ? map.get(position) : "") + "-"
					+ map.get("xingming") + ".doc";
			String p = PraseXml.getInstence().fileInfo.get("DOCFILE") + raddr + "/"
					+ Utils.formartDate(Utils.DATE_FORMAT_1) + "/";
			String tempPath = p + fileName;
			File t = new File(p);
			if (!t.exists()) {
				t.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(tempPath, true);

			hdt.write(ostream);
			// 输出字节
			out.write(ostream.toByteArray());
			out.close();
			ostream.close();
		} catch (IOException e) {
			// e.printStackTrace();
			return "请关闭当前打开的word!";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "转换成功，请到" + raddr + "下" + Utils.formartDate(Utils.DATE_FORMAT_1) + "文件夹中查看。";
	}

	private Map<String, String> infoMap(String name, String mobile, String email, String position) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> tmap = PraseXml.getInstence().py;

		if (!Utils.nullOrEmpty(name)) {
			map.put(tmap.get("XINGMING"), name);
		}
		if (!Utils.nullOrEmpty(email)) {
			map.put(tmap.get("DIANZIYOUJIAN"), email);
		}
		if (!Utils.nullOrEmpty(position)) {
			map.put(tmap.get("POSITION"), position);
		}
		if (!Utils.nullOrEmpty(mobile)) {
			map.put(tmap.get("LIANXIDIANHUA"), mobile);
		}
		return map;
	}

	/**
	 * delete temp file
	 * 
	 * @param f
	 */
	public void deleteTempFile(File f) {
		for (File file : f.listFiles()) {
			if (file.isDirectory()) {
				deleteTempFile(file);
			} else {
				file.delete();
			}
		}

	}

	private List<Map<String, String>> getMaps(BaseInfo bf) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		CommonsMultipartFile[] files = bf.getMfile();
		String tpath = PraseXml.getInstence().fileInfo.get("TEMPFILE") + getAddr() + "/";
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isEmpty()) {
				CommonsMultipartFile file = files[i];
				String path = tpath + file.getOriginalFilename();
				File tFile = new File(tpath);
				if (!tFile.exists()) {
					tFile.mkdirs();
				}
				File tfile = new File(path);
				try {
					file.transferTo(tfile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				if (file.getContentType().contains("text/html")) {
					Document doc = null;
					try {
						doc = Jsoup.parse(tfile, "utf-8", "http://h.liepin.com");
					} catch (IOException e) {
						e.printStackTrace();
					}
					String title = doc.title();
					String webSite = null;
					if (title.contains(Constants.LIEPIN)) {
						webSite = "LiePin";
					} else if (title.contains(Constants.ZHUOPIN)) {
						webSite = "ZhuoPin";
					} else if (title.contains(Constants.ZHILIAN)) {
						webSite = "ZhiLian";
					} else {
						return Collections.emptyList();
					}
					Class<?> clzss = null;
					try {
						clzss = Class.forName("com.prase.impl.Prase" + webSite + "HtmlImpl");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					PraseHtml clz = null;
					try {
						clz = (PraseHtml) clzss.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					list.add(clz.getMap(doc));
					// 删除临时文件
					if (tfile.exists()) {
						tfile.delete();
					}
					// 把生成的简历推送会客户机
				} else {
					// return "选一个网页（htm）文件。";
					// String key = Constants.LIANXIDIANHUA;
					// if (file.getOriginalFilename().contains("(1)")) {
					// key = Constants.DIANZIYOUJIAN;
					// }
					// String ocrStr = new OcrUtil(ScaleAndThresholdPic.getInstence().doPituer(tfile, tpath)).getText();
					// map.put(key, ocrStr);
				}
			}

		}
		deleteTempFile(new File(tpath));
		return list;
	}

	private String getAddr() {
		String raddr = request.getRemoteAddr();
		if (raddr.contains("0:0:0:0:0:0:0")) {
			raddr = "localhost";
		}
		return raddr;
	}
}
