/**
 * [module]
 * LogicUtils.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.common;

import java.util.Map;

/**
 * 業務ロジックユーティリティクラス
 * 
 * @author tsubaki
 * 
 */
public class LogicUtils {

	/**
	 * 隠蔽コンストラクタ
	 */
	private LogicUtils() {
	}

	/**
	 * 文字列存在チェック処理
	 * 
	 * @param target
	 *            検査対象文字列
	 * @return 存在する場合：true
	 */
	public static boolean isNotEmptyString(String target) {
		boolean result = false;
		if (target != null && target.length() != 0) {
			result = true;
		}
		return result;
	}

	/**
	 * Mapの存在チェック
	 * 
	 * @param target 検査対象Map
	 * @return 存在する場合：true
	 */
	public static boolean isNotEmptyMap(Map<?, ?> target) {
		boolean result = false;
		if (target != null && !target.isEmpty()){
			result = true;
		}
		return result;
	}
	
	/**
	 * Mapオブジェクトから指定のキーにてStringで取得する
	 * 
	 * @param input 取得元Map
	 * @param key 取得キー
	 * @return マッピングデータ（String）
	 */
	public static String getMapValueToString(Map<String, ?> input, String key){
		String retVal = null;
		if (isNotEmptyMap(input) && isNotEmptyString(key)){
			Object value = input.get(key);
			if (value != null && value instanceof String){
				retVal = (String) value;
			}
		}
		return retVal;
	}
	
	/**
	 * Mapオブジェクトから指定のキーにてStringで取得する
	 * 
	 * @param input 取得元Map
	 * @param key 取得キー
	 * @return マッピングデータ（String）
	 */
	public static int getMapValueToInt(Map<String, Object> input, String key){
		int retVal = 0;
		if (isNotEmptyMap(input) && isNotEmptyString(key) && input.get(key) != null){
			retVal = Integer.parseInt(String.valueOf(input.get(key)));
		}
		return retVal;
	}
}
