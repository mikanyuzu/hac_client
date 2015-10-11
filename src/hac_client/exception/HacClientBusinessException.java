/**
 * [module]
 * HacClientBusinessException.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.exception;

public class HacClientBusinessException extends TsrvfwBusinessException {

	/** シリアルID */
	private static final long serialVersionUID = -971812531078519407L;
	
	/**
	 * コンストラクタ
	 * @param code インナーコード
	 * @param message メッセージ
	 * @param params 埋め込み文字列
	 */
	public HacClientBusinessException(String code, String message, String[] params) {
		super(code, message, params);
	}

}
