package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PraseXml {
	/**
	 * 解析常量 value=1
	 */
	public Map<String, String> cl = new HashMap<String, String>();
	/**
	 * 写入word配置 value=2
	 */
	public Map<String, String> py = new HashMap<String, String>();
	/**
	 * ip姓名对应 value=3
	 */
	public Map<String, String> ipToName = new HashMap<String, String>();
	/**
	 * 文件保存配置 value=4
	 */
	public Map<String, String> fileInfo = new HashMap<String, String>();

	public PraseXml() {
		if (cl.isEmpty() || py.isEmpty() || ipToName.isEmpty() || fileInfo.isEmpty()) {
			prasexml();
		}
	}

	public static PraseXml getInstence() {
		return innerParseXMl.praseXML;
	}

	public static class innerParseXMl {
		private static final PraseXml praseXML = new PraseXml();
	}

	private void prasexml() {
		Element root = getRootNode("/info.xml");
		NodeList list = root.getElementsByTagName("type");
		if (null != list) {
			Element elePara = null;
			for (int i = 0; i < list.getLength(); i++) {
				elePara = (Element) list.item(i);
				if (null != elePara) {
					Map<String, String> map = null;
					int type = Integer.parseInt(elePara.getAttribute("value"));
					switch (type) {
					case 1:
						map = cl;
						break;
					case 2:
						map = py;
						break;
					case 3:
						map = ipToName;
						break;
					case 4:
						map = fileInfo;
						break;
					default:
						break;
					}
					parseRespNode(map, elePara);
				}
			}
		}
	}

	private void parseRespNode(Map<String, String> map, Element elePara) {
		NodeList list = elePara.getChildNodes();
		for (int k = 0; k < list.getLength(); k++) {
			Node node = list.item(k);
			if (node instanceof Element) {
				Element ele = (Element) node;
				String name = ele.getAttribute("name");
				String value = ele.getAttribute("value");
				map.put(name, value);
			}
		}
	}

	private Element getRootNode(String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = getClass().getResourceAsStream(fileName);
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = parser.parse(inputStream);
			return document.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		PraseXml px = new PraseXml();
		px.prasexml();
	}
}
