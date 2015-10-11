package hac_client.exception;

/**
 * 業務エラークラス
 *
 * @author tsubaki
 *
 */
public class TsrvfwBusinessException extends Exception {

	/** シリアルID */
	private static final long serialVersionUID = 6291862878438765056L;

	/** 内部コード */
	private String innerCode;
	
	/** メッセージ埋め込み文字列 */
	private String[] params;

	/**
	 * コンストラクタ
	 *
	 * @param code 内部コード
	 * @param message メッセージ
	 */
	public TsrvfwBusinessException(String code, String message, String[] params){
		super(message);
		innerCode = code;
		this.params = params;
	}

	/**
	 * 内部コード取得処理
	 *
	 * @return 内部コード
	 */
	public String getInnerCode(){
		return this.innerCode;
	}
	
	/**
	 * 埋め込み文字列取得処理
	 * @return 埋め込み文字列
	 */
	public String[] getParams(){
		return this.params;
	}
}
