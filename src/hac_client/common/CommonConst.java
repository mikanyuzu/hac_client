/**
 * [module]
 * CommonConst.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.common;

/**
 * 共通定数クラス
 * @author sysusr1
 *
 */
public class CommonConst {

	/** メッセージ種類 情報 */
	public static final int MESSAGE_TYPE_INFO = 0;

	/** メッセージ種類 警告 */
	public static final int MESSAGE_TYPE_WARN = 1;
	
	/** メッセージ種類 エラー */
	public static final int MESSAGE_TYPE_ERROR = 2;
	
	/** リモート呼び出し結果 業務エラー */
	public static final int REMOTE_RESULT_BIZERR = 1;
	
	/** リモート呼び出し結果 システムエラー */
	public static final int REMOTE_RESULT_SYSERR = 2;
	
	/** リモート呼び出し結果 正常終了 */
	public static final int REMOTE_RESULT_NORMAL = 0;
	
	/** リモート呼び出し結果 結果取得キー */
	public static final String KEY_REMOTE_RESULT = "remote_result";
	
	/** リモート呼び出し結果 データ取得キー */
	public static final String KEY_REMOTE_RESULT_DATE = "remote_result_data";
	
	/** リモート呼び出し結果 リモート処理結果取得キー*/
	public static final String KEY_REMOTE_PROCESS_RESULT = "processResult";
}
