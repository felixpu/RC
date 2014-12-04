package com.prase.impl;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.enums.Marriage;
import com.prase.PraseZhuoPinHtml;
import com.util.Constants;
import com.util.PraseXml;

public class PraseZhuoPinHtmlImpl implements PraseZhuoPinHtml {

	private int flag = 0;
	private static final String H4 = "h4";
	private static final String H5 = "h5";

	Map<String, String> baseInfoMap = new HashMap<String, String>();
	Map<String, String> mapcl = null;
	Map<String, String> mappy = null;

	public PraseZhuoPinHtmlImpl(){
		if(mapcl==null || mappy==null){
			mapcl = PraseXml.getInstence().cl;
			mappy = PraseXml.getInstence().py;
		}
	}
	public Map<String, String> getMap(Document doc) {
		// 新请求清空map对象
		if (!baseInfoMap.isEmpty()) {
			baseInfoMap.clear();
		}

		// 个人信息
		this.praseBaseInfo(doc);

		// 工作经历 项目经历 等。。。
		Elements details = doc.select(".detail-mar");
		for (Element detail : details) {
			Elements inners = detail.children();
			// 取h4 作为title来判断
			Elements h4s = inners.select(H4);
			Element h4 = h4s.get(0);
			String title = h4.text().trim();
			StringBuilder sb = new StringBuilder();
		
			if (title.contains(mapcl.get("ZP_ZWPJ"))) {
				this.praseZWPPJ(sb, inners);
			} else if (title.contains(mapcl.get("ZP_GZJY"))) {
				this.praseGZJY(sb, inners);
			} else if (title.contains(mapcl.get("ZP_XMJY"))) {
				this.praseXMJY(sb, inners);
			} else if (title.contains(mapcl.get("ZP_JYBJ"))) {
				this.praseJYJY(sb, inners);
			} else if (title.contains(mapcl.get("ZP_PXJL"))) {
				this.prasePXJL(sb, inners);
			} else if (title.contains(mapcl.get("ZP_QTNL"))) {

			} else if (title.contains(mapcl.get("ZP_BCXX"))) {
				this.praseOtherInfo(sb, inners);
			}
		}
		return baseInfoMap;
	}

	/**
	 * 个人基本信息处理
	 * 
	 * @param doc
	 */
	private void praseBaseInfo(Document doc) {
		Elements baseInfo = doc.select(".simple-info-ul");
		Elements lis = baseInfo.select("li");
		for (Element li : lis) {
			// 姓名 性别 工作年限 学历-》处理
			if (li.className().equals("d-name")) {
				String str = li.text().replaceAll(Jsoup.parse("&nbsp").text(), Constants.NO_SAPCE)
						.split(Constants.ZH_COLON)[1];
				String[] tstr = str.replace(Constants.FGF, Constants.AT).split(Constants.AT);
				baseInfoMap.put(mappy.get("XINGMING"), tstr[0].trim().contains("*") ? "" : tstr[0].trim());
				baseInfoMap.put(mappy.get("XINGBIE"), tstr[1].trim());
				baseInfoMap.put(mappy.get("GONGZUONIANXIAN"), tstr[2].trim());
				if(tstr.length > 3){
					baseInfoMap.put(mappy.get("XUELI"), tstr[3].trim());
				}
			}
			// 年龄 出生年月 婚姻状况 所在地 是否有海外经验 -》处理
			else if (li.className().equals("gray-line")) {
				String str = li.text().replaceAll(Jsoup.parse(Constants.NBSP).text(), Constants.NO_SAPCE);
				String[] tstr = str.replace(Constants.FGF, Constants.AT).split(Constants.AT);
				for (int i = 0; i < tstr.length; i++) {
					String tempStr = tstr[i].trim();
					if (tempStr.contains(Constants.SUI)) {
						baseInfoMap.put(mappy.get("NIANLING"), tempStr);
					} else if (tempStr.contains(Constants.ZH_YEAR)) {

					} else if (tempStr.contains(Constants.ABROAD)) {

					} else if (Marriage.toList().contains(tempStr.trim())) {
						baseInfoMap.put(mappy.get("HUNYINZHUANGKUANG"), tempStr.trim());
					} else {
						baseInfoMap.put(mappy.get("SUOZAIDI"), tempStr);
					}
				}
			}
			// 手机 邮箱 处理
			else if (li.className().equals("sim")) {
				String val = li.text().split(Constants.ZH_COLON)[1];
				if (!li.select(".phone").isEmpty()) {
					baseInfoMap.put(mappy.get("LIANXIDIANHUA"), val.trim().contains("*") ? "" : val.trim());
				} else if (!li.select(".email").isEmpty()) {
					baseInfoMap.put(mappy.get("DIANZIYOUJIAN"), val.trim().contains("*") ? "" : val.trim());
				} else {
					baseInfoMap.put(mappy.get("GERENZHUYE"), val.trim());
				}
			}
		}

		Element elms = doc.select(".detail-tabs-new").get(0);
		Elements childs = elms.children();
		for (Element _child : childs) {
			_child.attributes().get("id");
			if (_child.attributes().get("id").equals("resumeDetailTab")) {
				// ----各种处理---
				Elements mchilds = _child.children();
				// 移出简历标签
				if (!mchilds.select("addBottompad").isEmpty()) {
					mchilds.select("addBottompad").remove();
				}

				// 获取目前薪资和期望薪资
				Elements xinziInfos = mchilds.select(".new-border-bottom");
				for (Element xinziInfo : xinziInfos) {
					Elements singelms = xinziInfo.children();
					for (Element singelm : singelms) {
						// 去除非div的元素
						if (singelm.tagName().equals("div")) {
							String[] str = singelm.text().split(Constants.ZH_COLON);
							if (str[0].contains(mapcl.get("MQXZ"))) {
								baseInfoMap.put(mappy.get("MUQIANXINZI"), str[str.length-1].trim());
							 } else if (str[0].contains(mapcl.get("QWXZ"))) {
								baseInfoMap.put(mappy.get("QIWANGYUEXIN"), str[1].trim());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 补充信息处理
	 * 
	 * @param sb
	 * @param inners
	 */
	private void praseOtherInfo(StringBuilder sb, Elements inners) {
		for (Element inner : inners) {
			if (inner.tagName().equals("h6")) {
				joinStr(sb, inner.text());
			} else {
				if (!inner.tagName().equals(H4)) {
					sb.append(inner.text() + Constants.NEWLINE);
				}
			}
		}
		flag = 0;
		baseInfoMap.put(mappy.get("BUCHONGXINXI"), sb.toString());

	}

	/**
	 * 培训经历
	 * 
	 * @param sb
	 * @param inners
	 */
	private void prasePXJL(StringBuilder sb, Elements inners) {
		for (Element inner : inners) {
			if (inner.tagName().equals(H5)) {
				String proinfo = inner.text().replaceAll(Jsoup.parse(Constants.NBSP).text(), Constants.NO_SAPCE);
				joinStr(sb, proinfo);
			} else {
				if (!inner.tagName().equals(H4)) {
					sb.append(inner.text() + Constants.NEWLINE);
				}
			}
		}
		flag = 0;
		baseInfoMap.put(mappy.get("PEIXUNJINGLI"), sb.toString());

	}

	/**
	 * 教育经历
	 * 
	 * @param sb
	 * @param inners
	 */
	private void praseJYJY(StringBuilder sb, Elements inners) {
		for (Element inner : inners) {
			if (inner.tagName().equals(H5)) {
				String proinfo = inner.text().replaceAll(Jsoup.parse(Constants.NBSP).text(), Constants.NO_SAPCE);
				sb.append(Constants.NEWLINE + proinfo + Constants.TWO_SAPCE);
			} else {
				if (!inner.tagName().equals(H4)) {
					String str = inner.text();
					if (str.contains(mapcl.get("ZYMC"))) {
						sb.append(str.split(Constants.ZH_COLON)[1].trim() + Constants.TWO_SAPCE);
					} else if (str.contains(mapcl.get("DEGREE"))) {
						Elements spans = inner.select("span");
						for (Element span : spans) {
							String strspan = span.text();
							if (strspan.contains(Constants.ZH_COLON)) {
								String t = strspan.split(Constants.ZH_COLON)[1].trim();
								sb.append(t.contains(Constants.YES) ? Constants.COMMUNICATION
										: Constants.NOCOMMUNICATION);
							} else {
								sb.append(strspan + Constants.TWO_SAPCE);
							}
						}
					}
				}
			}
		}
		baseInfoMap.put(mappy.get("JIAOYUJINGLI"), sb.toString());

	}

	/**
	 * 项目经历处理
	 * 
	 * @param sb
	 * @param inners
	 */
	private void praseXMJY(StringBuilder sb, Elements inners) {
		for (Element inner : inners) {
			if (inner.tagName().equals(H5)) {
				String proinfo = inner.text().replaceAll(Jsoup.parse(Constants.NBSP).text(), Constants.NO_SAPCE);
				joinStr(sb, proinfo);
			} else {
				if (!inner.tagName().equals(H4)) {
					this.chuLiHuanhang(sb, inner);
				}
			}
		}
		flag = 0;
		baseInfoMap.put(mappy.get("XIANGMUJINGLI"), sb.toString());

	}

	/**
	 * 工作经历处理
	 * 
	 * @param sb
	 * @param inners
	 */
	private void praseGZJY(StringBuilder sb, Elements inners) {
		for (Element inner : inners) {
			if (inner.tagName().equals(H5)) {
				String compinfo = inner.text().replaceAll(Jsoup.parse(Constants.NBSP).text(), Constants.NO_SAPCE)
						.replace(Constants.FGF, Constants.AT);
				String[] compinfos = compinfo.split(Constants.AT);
				String temp = null;
				if (compinfos.length == 2) {
					int index = compinfos[1].indexOf(Constants.ZKH);
					temp = compinfos[0] + compinfos[1].substring(index) + compinfos[1].substring(0, index);
				} else {
					int index = compinfos[2].indexOf(Constants.ZKH);
					temp = compinfos[0] + compinfos[2].substring(index) + compinfos[2].substring(0, index);
				}
				joinStr(sb, temp);
			} else {
				if (!inner.tagName().equals(H4)) {
					this.chuLiHuanhang(sb, inner);
				}
			}
		}
		flag = 0;
		baseInfoMap.put(mappy.get("GONGZUOJINGLI"), sb.toString());
	}

	/**
	 * 工作经历和醒目经理处理换行
	 * 
	 * @param sb
	 * @param inner
	 */
	private void chuLiHuanhang(StringBuilder sb, Element inner) {
		if (!inner.select("br").isEmpty()) {
			String div = inner.toString();
			div = div.replaceAll("<br />", Constants.JH);
			Element e = new Element(Tag.valueOf(HTML.Tag.DIV.toString()), Constants.NO_SAPCE).html(div);
			if (e.children().isEmpty()) {
				return;
			}
			Elements elms = e.children().get(0).children();
			if (elms.isEmpty()) {
				return;
			}
			if (!elms.isEmpty()) {
				for (Element elm : elms) {
					String strElm = elm.text();
					if (strElm.contains(Constants.JH)) {
						strElm = strElm.replaceAll(Constants.JH, Constants.NEWLINE);
					}
					sb.append(strElm + Constants.NEWLINE);
				}

			}
		}else{
			sb.append(inner.text() + Constants.NEWLINE);
		}
	}

	/**
	 * 自我评级处理
	 * 
	 * @param sb
	 * @param inners
	 */
	private void praseZWPPJ(StringBuilder sb, Elements inners) {
		for (Element inner : inners) {
			if (!inner.tagName().equals(H4)) {
				sb.append(inner.text());
			}
		}
		baseInfoMap.put(mappy.get("ZIWOPINGJIA"), sb.toString().trim());
	}

	private void joinStr(StringBuilder sb, String str) {
		if (flag == 0) {
			sb.append(str + Constants.NEWLINE);
			flag++;
		} else {
			sb.append(Constants.NEWLINE + str + Constants.NEWLINE);
		}
	}
}
