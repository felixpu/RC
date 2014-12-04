package com.prase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.enums.Edu;
import com.enums.Marriage;
import com.enums.Sex;
import com.prase.PraseZhiLianHtml;
import com.util.Constants;
import com.util.PraseXml;
import com.util.Utils;

public class PraseZhiLianHtmlImpl implements PraseZhiLianHtml {
	private static final String table = "table";

	Map<String, String> baseInfoMap = new HashMap<String, String>();
	Map<String, String> mapcl = null;
	Map<String, String> mappy = null;

	public PraseZhiLianHtmlImpl() {
		if (mapcl == null || mappy == null) {
			mapcl = PraseXml.getInstence().cl;
			mappy = PraseXml.getInstence().py;
		}
	}

	// private boolean isMobileNO(String mobiles) {
	// Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	// Matcher m = p.matcher(mobiles);
	// return m.matches();
	// }

	public Map<String, String> getMap(Document doc) {
		if (!baseInfoMap.isEmpty()) {
			baseInfoMap.clear();
		}

		// 个人信息
		this.praseBaseInfo(doc);

		Elements main = doc.select(".resume-preview-left").get(0).children();
		for (Element ele : main) {
			Elements h3ele = ele.select("h3");
			if (h3ele.isEmpty()) {
				continue;
			}
			h3ele.remove();
			String h3 = h3ele.text();
			if (h3.contains("自我评价")) {
				// 自我评价
				praseZWPJ(ele);
			} else if (h3.contains("工作经历")) {
				// 工作经历
				this.praseGZJL(ele);
			} else if (h3.contains("项目经历")) {
				// 项目经历
				this.praseXMJL(ele);
			} else if (h3.contains("教育经历")) {
				// 教育经历
				this.praseJYJL(ele);
			} else if (h3.contains("培训经历")) {

			} else if (h3.contains("语言能力")) {
				// 语言能力
				praseYYNL(ele);
			}
		}
		return baseInfoMap;
	}

	private void praseZWPJ(Element ele) {
		baseInfoMap.put(mappy.get("ZIWOPINGJIA"), ele.text());
	}

	// private void praseFJXX(Document doc) {
	// Elements other = doc.select(".resume-others");
	// baseInfoMap.put(mappy.get("FUJIAXIAOXI"), other.select(table).text());
	// }

	private void praseYYNL(Element ele) {
		baseInfoMap.put(mappy.get("YUYANNENGLI"), ele.text());
	}

	private void praseJYJL(Element ele) {
		String jyjl = ele.text();// .replaceAll(Jsoup.parse("&nbsp;").text(), "");
		baseInfoMap.put(mappy.get("JIAOYUJINGLI"), jyjl);
	}

	/**
	 * 项目经历
	 * 
	 * @param ele
	 */
	private void praseXMJL(Element ele) {
		Elements xmjls = ele.children();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Element xmjl : xmjls) {
			if (xmjl.tagName().equals("h2")) {
				if (i != 0) {
					sb.append(Constants.NEWLINE);
				}
				sb.append(xmjl.text() + Constants.NEWLINE);
				i++;
			} else {
				Elements trs = xmjl.select("tr");
				for (Element tr : trs) {
					sb.append(tr.text() + Constants.NEWLINE);
				}
			}
		}
		baseInfoMap.put(mappy.get("XIANGMUJINGLI"), sb.toString());
	}

	private void praseGZJL(Element ele) {
		Elements gzjls = ele.children();
		List<String> h2s = new ArrayList<>();
		List<String> h5s = new ArrayList<>();
		List<String> div = new ArrayList<>();
		for (Element gzjl : gzjls) {
			if (gzjl.tagName().contains("h2")) {
				String t = gzjl.text();
				h2s.add(t);// 时间 公司年限
			} else if (gzjl.tagName().contains("h5")) {
				h5s.add(gzjl.text().replace("|", "&").split("&")[0]);// 职位
			} else {
				String str = "";
				Elements table = gzjl.select("table");
				if (!table.isEmpty()) {
					Elements trs = gzjl.select("tr");
					for (Element tr : trs) {
						str += tr.text() + Constants.NEWLINE;
					}
				} else {
					String str2 = "";
					String str1 = gzjl.text();
					if (str1.contains("|")) {
						String[] s = str1.replace("|", "&").split("&");
						for (int i = 0; i < s.length; i++) {
							String ss = s[i];
							if (ss.contains("规模")) {
								str2 += "企业人数：" + ss.split("：")[1];
							} else if (ss.contains("：")) {
								str2 += Utils.trim(ss);
							} else {
								str2 += "所在行业：" + ss;
							}
							str2 += Constants.NEWLINE;
						}
						str = str2;
					} else {
						str = str1 + Constants.NEWLINE;
					}
				}
				div.add(str);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < h2s.size(); i++) {
			String str = h2s.get(i) + "  " + h5s.get(i) + Constants.NEWLINE;
			if (i != 0) {
				str = Constants.NEWLINE + str;
			}
			sb.append(str);
			sb.append(div.get(2 * i) + div.get(2 * i + 1));
		}

		baseInfoMap.put(mappy.get("GONGZUOJINGLI"), sb.toString());
	}

	/**
	 * 处理工作经历和项目经历的换行 最对TR里面的换行
	 * 
	 * @param sb
	 * @param tr
	 */
	private void chuLiHuanhang(StringBuilder sb, Element tr) {
		String tt = tr.toString();
		tt = tt.replaceAll("<br />", Constants.JH);
		Element e = new Element(Tag.valueOf(HTML.Tag.TR.toString()), Constants.NO_SAPCE).html(tt);
		Elements elms = e.children();
		if (!elms.isEmpty()) {
			for (Element elm : elms) {
				String strElm = elm.text();
				if (strElm.contains(Constants.JH)) {
					strElm = strElm.replaceAll(Constants.JH, Constants.NEWLINE);
				}
				sb.append(strElm + Constants.NEWLINE);
			}

		}
	}

	private void praseBaseInfo(Document doc) {
		Elements xmele = doc.select(".resume-preview-main-title");
		if (xmele.size() != 0) {
			baseInfoMap.put("xingming", Utils.trim(xmele.get(0).children().get(0).text()));// 获取姓名
		}

		Elements bInfo = doc.select(".summary");
		Element top = bInfo.select(".summary-top").get(0);
		Element span = top.select("span").get(0);
		// 获取性别年龄工作年限学历和婚姻状况信息
		String spanTxt = span.text().replaceAll(Jsoup.parse("&nbsp").text(), "&");
		String[] spanTexts = spanTxt.split("&&&&");
		for (int i = 0; i < spanTexts.length; i++) {
			String str = spanTexts[i];
			if (Sex.toList().contains(str)) {
				baseInfoMap.put("xingbie", str);
			} else if (str.indexOf("岁") > -1) {
				baseInfoMap.put("nianling", str);
			} else if (str.indexOf("工作经验") > -1) {
				baseInfoMap.put("gongzuonianxian", str);
			} else if (Edu.toList().contains(str)) {
				baseInfoMap.put("xueli", str);
			} else if (Marriage.toList().contains(str)) {
				baseInfoMap.put("hunyinzhuangkuang", str);
			}
		}
		// 移除span
		span.remove();
		// 获取住址，政治面貌，户口，和海外经历
		// 目前只解析所在地
		String[] topStrs = Utils.trim(top.text()).replace(Constants.FGF, Constants.AT).split(Constants.AT);
		for (int i = 0; i < topStrs.length; i++) {
			String topStr = topStrs[i];
			if (topStr.contains("现居住地")) {
				baseInfoMap.put("suozaidi", topStr.split("：")[1]);
			}
		}
		// 抓取手机号和邮箱
		Element bottom = bInfo.select(".summary-bottom").get(0);
		String t = bottom.text();
		int start = t.indexOf("手机：") + 3;
		if (start - 3 > -1) {
			int end = start + 11;
			String mobile = t.substring(start, end);
			String email = bottom.select("a").get(0).text();
			baseInfoMap.put("lianxidianhua", mobile);
			baseInfoMap.put("dianziyoujian", email);
		}
	}

	// private String getPinYinHeadChar(String str) {
	// String convert = Constants.NO_SAPCE;
	// for (int j = 0; j < str.length(); j++) {
	// char word = str.charAt(j);
	// String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
	// if (pinyinArray != null) {
	// convert += pinyinArray[0].substring(0, pinyinArray[0].length() - 1);
	// } else {
	// convert += word;
	// }
	// }
	// return convert;
	// }
}
