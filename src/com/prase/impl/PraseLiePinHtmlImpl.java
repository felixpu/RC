package com.prase.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.prase.PraseLiePinHtml;
import com.util.Constants;
import com.util.PraseXml;

public class PraseLiePinHtmlImpl implements PraseLiePinHtml {
	private static final String table = "table";
	private static final String td = "td";
	private static final String tr = "tr";

	Map<String, String> baseInfoMap = new HashMap<String, String>();
	Map<String, String> mapcl = null;
	Map<String, String> mappy = null;

	public PraseLiePinHtmlImpl(){
		if(mapcl==null || mappy==null){
			mapcl = PraseXml.getInstence().cl;
			mappy = PraseXml.getInstence().py;
		}
	}
	private boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public Map<String, String> getMap(Document doc) {
		if (!baseInfoMap.isEmpty()) {
			baseInfoMap.clear();
		}

		// 个人信息
		this.praseBaseInfo(doc);

		// 工作经历
		this.praseGZJL(doc);

		// 项目经历
		this.praseXMJL(doc);

		// 教育经历
		this.praseJYJL(doc);

		// 语言能力
		praseYYNL(doc);

		// 附加消息
		praseFJXX(doc);

		// 自我评价
		praseZWPJ(doc);

		return baseInfoMap;
	}

	private void praseZWPJ(Document doc) {
		Elements comments = doc.select(".resume-comments");
		baseInfoMap.put(mappy.get("ZIWOPINGJIA"), comments.select(table).text());
	}

	private void praseFJXX(Document doc) {
		Elements other = doc.select(".resume-others");
		baseInfoMap.put(mappy.get("FUJIAXIAOXI"), other.select(table).text());
	}

	private void praseYYNL(Document doc) {
		Elements language = doc.select(".resume-language");
		baseInfoMap.put(mappy.get("YUYANNENGLI"), language.select(table).text());
	}

	private void praseJYJL(Document doc) {
		Elements education = doc.select(".resume-education").select(table);
		StringBuilder sb = new StringBuilder();
		for (Element tab : education) {
			Elements tds = tab.select(td);
			for (Element td : tds) {
				Elements strong = td.children().select("strong");
				if (!strong.isEmpty()) {
					strong.remove();
					String time = td.text();
					time = time.substring(1, td.text().length() - 1);
					sb.append(time);
					sb.append(Constants.TWO_SAPCE);
					sb.append(strong.text());
					sb.append(Constants.TWO_SAPCE);
				} else {
					String str = td.text();
					String[] strs = str.split(Constants.ZH_COLON);
					if (strs[1].equals(Constants.YES) || strs[1].equals(Constants.FOU)) {
						sb.append(strs[1].equals(Constants.YES) ? Constants.COMMUNICATION : Constants.NOCOMMUNICATION);
					} else {
						sb.append(strs[1]);
					}
					sb.append(Constants.TWO_SAPCE);
				}
			}
			sb.append(Constants.NEWLINE);
		}
		baseInfoMap.put(mappy.get("JIAOYUJINGLI"), sb.toString());
	}

	private void praseXMJL(Document doc) {
		Elements project = doc.select(".resume-project");
		Elements tables = project.select(table);
		StringBuilder sb = new StringBuilder();
		if (tables.isEmpty()) {
			return;
		}
		for (Element tab : tables) {
			Elements trs = tab.select(tr);
			if (trs.isEmpty()) {
				return;
			}
			for (Element tr : trs) {
				if (tr.select(".project-list-title").hasText()) {
					sb.append(tr.text());
					sb.append(Constants.NEWLINE);
				} else {
					if (!tr.select("br").isEmpty()) {
						this.chuLiHuanhang(sb, tr);
					} else {
						sb.append(tr.text()+Constants.NEWLINE);
					}
				}
			}
			sb.append(Constants.NEWLINE);
		}
		baseInfoMap.put(mappy.get("XIANGMUJINGLI"), sb.toString());
	}

	private void praseGZJL(Document doc) {
		Elements work = doc.select(".resume-work");
		Elements els = work.get(0).children();

		Map<Integer, String> jobTitles = new HashMap<Integer, String>();
		Map<Integer, String> perlTitles = new HashMap<Integer, String>();
		Map<Integer, String> jobList = new HashMap<Integer, String>();
		int i = 1, j = 1;
		for (Element el : els) {
			StringBuilder sb = new StringBuilder();
			if (el.tagName().equals("div")) {
				if (el.className().equals("resume-job-title")) {
					el.select("a").remove();
					jobTitles.put(i, el.text());
					i++;
				}
				if (el.className().equals("resume-indent")) {
					Elements trs = el.select(tr);
					// 获取个人Title
					Elements eTitle = trs.select(".job-list-title").select("strong");
					if (!eTitle.isEmpty()) {
						String sTitle = eTitle.get(0).text();
						perlTitles.put(j, sTitle);
						trs.select(".job-list-title").get(0).remove();
					}

					for (Element tr : trs) {
						if (!tr.select("br").isEmpty()) {
							this.chuLiHuanhang(sb, tr);
						} else {
							String str = tr.text();
							if (!str.isEmpty()) {
								str = str.replaceAll(Jsoup.parse(Constants.NBSP).text(), Constants.NO_SAPCE).replace(
										Jsoup.parse(Constants.FGF).text(), Constants.NEWLINE);
								sb.append(str);
								sb.append(Constants.NEWLINE);
							}
						}

					}
					sb.append(Constants.NEWLINE);
					jobList.put(j, sb.toString());
					j++;
				}
			}
		}
		String temp = "";
		for (Integer key : jobTitles.keySet()) {
			if (!perlTitles.get(key).isEmpty()) {
				jobTitles.put(key, jobTitles.get(key) + Constants.TWO_SAPCE + perlTitles.get(key) + Constants.NEWLINE
						+ jobList.get(key));
			}
			temp += jobTitles.get(key);
		}

		baseInfoMap.put(mappy.get("GONGZUOJINGLI"), temp);
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
		Elements bInfo = doc.select(".resume-basic").select(table).select(td);
		for (Element ele : bInfo) {
			String str = ele.text();
			if (str.contains(Constants.ZH_COLON)) {
				String[] strs = str.split(Constants.ZH_COLON);
				if (strs.length != 2) {
					baseInfoMap.put(getPinYinHeadChar(strs[0]), Constants.NO_SAPCE);
				} else {
					String val = strs[1].trim();
					if ((strs[0].contains(mapcl.get("LXDH")) && isMobileNO(strs[1].trim()) == false)
							|| (strs[0].contains(mapcl.get("XM")) && strs[1].contains("*") == true)) {
						val = Constants.NO_SAPCE;
					}
					baseInfoMap.put(getPinYinHeadChar(strs[0]), val);
				}
			}
		}
	}

	private String getPinYinHeadChar(String str) {
		String convert = Constants.NO_SAPCE;
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].substring(0, pinyinArray[0].length() - 1);
			} else {
				convert += word;
			}
		}
		return convert;
	}
}
