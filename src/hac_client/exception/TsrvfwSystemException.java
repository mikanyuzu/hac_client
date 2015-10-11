package hac_client.exception;

/**
 * 業務想定内システムエラークラス
 *
 * @author tsubaki
 *
 */
public class TsrvfwSystemException extends Exception {

	/** シリアルID */
	private static final long serialVersionUID = 4577916381043802072L;

	/**
	 * コンストラクタ
	 *
	 * @param e エラー要因
	 */
	public TsrvfwSystemException(Exception e){
		super(e);
	}

	/**
	 * コンストラクタ
	 *
	 * @param message メッセージ
	 */
	public TsrvfwSystemException(String message){
		super(message);
	}

	/**
	 * コンストラクタ
	 *
	 * @param e エラー要因
	 * @param message メッセージ
	 */
	public TsrvfwSystemException(Exception e, String message){
		super(message, e);
	}
}
