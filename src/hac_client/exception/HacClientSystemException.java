/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hac_client.exception;

/**
 * システム例外クラス
 * @author sysusr1
 */
public class HacClientSystemException extends TsrvfwSystemException {

    /** シリアルバージョンID */
	private static final long serialVersionUID = -5724228230160524423L;

	/**
	 * コンストラクタ
	 */
	public HacClientSystemException(Exception e) {
        super(e);
    }
	
	/**
	 * コンストラクタ
	 */
	public HacClientSystemException(String message) {
        super(message);
    }
    
}
