/**
 * [module]
 * MessageConfig.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.tsrvfw.common.logger.CommonLogger;
import org.tsrvfw.exception.TsrvfwSystemException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 業務メッセージ解析クラス
 *
 * @author tsubaki
 *
 */
class MessageConfig {

	/**
	 * 隠蔽コンストラクタ
	 */
	private MessageConfig() {
	}

	/** 基底ロガー */
	protected Logger log = CommonLogger.getLogger(this.getClass().getName());

	/** アプリケーションメッセージ定義ファイルパス */
	private static final String RESOURCE_MESSAGE_XML_PATH = "/ApplicationMessageDefinition.xml";

	/**
	 * インスタンス処理
	 * 
	 * @return 当クラスインスタンス
	 */
	static MessageConfig newInstance() {
		return new MessageConfig();
	}

	/**
	 * アプリケーションメッセージ定義ファイル解析処理
	 *
	 * @return アプリケーションメッセージ定義ファイルMap
	 * @throws TsrvfwSystemException
	 *             想定内システムエラーが発生した場合
	 */
	Map<String, Object> parse() throws TsrvfwSystemException {
		log.debug(MessageConfig.class.getName() + " parse start----");

		// XML解析クラス
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Map<String, Object> rootMap = new HashMap<String, Object>();
		try {
			// XML解析クラスインスタンス生成
			builder = factory.newDocumentBuilder();

			// クラスパス内のメッセージ定義ファイルを取得
			InputStream is = this.getClass().getResourceAsStream(
					RESOURCE_MESSAGE_XML_PATH);

			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			NodeList children = root.getChildNodes();

			// 解析処理
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (MessageConfigManager.TAG_NAME_CONTENTS.equals(child
						.getNodeName())) {
					NamedNodeMap nodeMap = child.getAttributes();

					// innerCodeを取得
					String rootId = nodeMap.getNamedItem(
							MessageConfigManager.TAG_NAME_INNER_CD)
							.getNodeValue();
					NodeList nodeList = ((Element) child).getChildNodes();
					Map<String, String> dataMap = new HashMap<String, String>();
					for (int j = 0; j < nodeList.getLength(); j++) {
						Node messageNode = nodeList.item(j);

						// idを取得
						if (MessageConfigManager.TAG_NAME_ID.equals(messageNode
								.getNodeName())) {
							dataMap.put(MessageConfigManager.TAG_NAME_ID,
									messageNode.getTextContent());
							rootMap.put(rootId, dataMap);
						}

						// outerCodeを取得
						if (MessageConfigManager.TAG_NAME_OUTER_CD
								.equals(messageNode.getNodeName())) {
							dataMap.put(MessageConfigManager.TAG_NAME_OUTER_CD,
									messageNode.getTextContent());
							rootMap.put(rootId, dataMap);
						}

						// view-messageを取得
						if (MessageConfigManager.TAG_NAME_VIEW_MESSAGE
								.equals(messageNode.getNodeName())) {
							dataMap.put(
									MessageConfigManager.TAG_NAME_VIEW_MESSAGE,
									messageNode.getTextContent());
							rootMap.put(rootId, dataMap);
						}

						// log-messageを取得
						if (MessageConfigManager.TAG_NAME_LOG_MESSAGE
								.equals(messageNode.getNodeName())) {
							dataMap.put(
									MessageConfigManager.TAG_NAME_LOG_MESSAGE,
									messageNode.getTextContent());
							rootMap.put(rootId, dataMap);
						}
					}
				}
			}
			log.debug(rootMap);
		}
		// 読み込み失敗
		catch (ParserConfigurationException e) {
			throw new TsrvfwSystemException(e,
					"アプリケーションメッセージ定義ファイルの読み込みに失敗しました。");
		}
		// XML妥当性エラー
		catch (SAXException e) {
			throw new TsrvfwSystemException(e, "アプリケーションメッセージ定義ファイルの解析に失敗しました。");
		}
		// 入出力エラー
		catch (IOException e) {
			throw new TsrvfwSystemException(e, "アプリケーションメッセージ定義ファイルの解析に失敗しました。");
		}
		return rootMap;
	}
}
