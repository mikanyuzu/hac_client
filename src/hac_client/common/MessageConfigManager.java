/**
 * [module]
 * MessageConfigManager.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.common;

import java.text.MessageFormat;
import java.util.Map;

import org.tsrvfw.common.util.LogicUtils;
import org.tsrvfw.exception.TsrvfwSystemException;

/**
 * 業務メッセージ管理クラス
 * 
 * @author tsubaki
 * 
 */
public class MessageConfigManager {

	/** アプリケーションメッセージ定義ファイルデータMap */
	private static Map<String, Object> config;

	/** タグ名称 message */
	static final String TAG_NAME_CONTENTS = "message";

	/** タグ名称 id */
	static final String TAG_NAME_ID = "id";

	/** タグ名称 innerCode */
	static final String TAG_NAME_INNER_CD = "innerCode";

	/** タグ名称 outerCode */
	static final String TAG_NAME_OUTER_CD = "outerCode";

	/** タグ名称 view-message */
	static final String TAG_NAME_VIEW_MESSAGE = "view-message";

	/** タグ名称 log-message */
	static final String TAG_NAME_LOG_MESSAGE = "log-message";

	/** メッセージセパレータ */
	static final String MESSAGE_SEPARATOR = ":";

	/**
	 * 初期解析処理
	 * 
	 * @throws TsrvfwSystemException
	 *             想定内システムエラーが発生した場合
	 */
	public static void init() throws TsrvfwSystemException {
		if (config == null) {
			MessageConfig configure = MessageConfig.newInstance();
			config = configure.parse();
		}
	}

	/**
	 * 画面表示用メッセージ取得処理
	 * 
	 * @param innerCode
	 *            内部コード
	 * @return 画面表示メッセージ
	 * @throws TsrvfwSystemException
	 *             想定内システムエラーが発生した場合
	 */
	@SuppressWarnings("unchecked")
	public static String getViewMessageFromInnerCode(String innerCode, String ...arguments)
			throws TsrvfwSystemException {

		// 内部コード入力チェック
		if (!LogicUtils.isNotEmptyString(innerCode)) {
			throw new TsrvfwSystemException("内部コードが指定されていません。");
		}
		Map<String, Object> targetMessageMap = (Map<String, Object>) config
				.get(innerCode);

		StringBuilder message = new StringBuilder();
		message.append((String) targetMessageMap.get(TAG_NAME_OUTER_CD));
		message.append(MESSAGE_SEPARATOR);
		message.append(MessageFormat.format((String) targetMessageMap.get(TAG_NAME_VIEW_MESSAGE), arguments));
		return message.toString();
	}

	/**
	 * ログメッセージ取得処理
	 * 
	 * @param innerCode
	 *            内部コード
	 * @return ログメッセージ
	 * @throws TsrvfwSystemException
	 *             想定内システムエラーが発生した場合
	 */
	@SuppressWarnings("unchecked")
	public static String getLogMessageFromInnerCode(String innerCode)
			throws TsrvfwSystemException {
		// 内部コード入力チェック
		if (!LogicUtils.isNotEmptyString(innerCode)) {
			throw new TsrvfwSystemException("内部コードが指定されていません。");
		}
		Map<String, Object> targetMessageMap = (Map<String, Object>) config
				.get(innerCode);

		StringBuilder message = new StringBuilder();
		message.append((String) targetMessageMap.get(TAG_NAME_OUTER_CD));
		message.append(MESSAGE_SEPARATOR);
		message.append((String) targetMessageMap.get(TAG_NAME_LOG_MESSAGE));
		return message.toString();
	}
}
