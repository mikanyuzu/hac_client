/**
 * [module]
 * HttpClientRemoteExecuter.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.remote;

import hac_client.common.CommonLogger;
import hac_client.exception.HacClientSystemException;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * リモート呼び出し実行クラス
 * @author sysusr1
 *
 */
public class HttpClientRemoteExecuter {

	/** リモート呼び出し実行クラスのインスタンス */
	private static HttpClientRemoteExecuter instance;
	
	/** Spring定義情報 */
	private ApplicationContext context;
	
	/** ロガー */
    protected Logger log = CommonLogger.getLogger(this.getClass().getName());
	
	/**
	 * 隠蔽コンストラクタ
	 */
	private HttpClientRemoteExecuter(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	/**
	 * リモート呼び出し実行クラスインスタンス取得
	 * @return リモート呼び出し実行クラス
	 */
	public static HttpClientRemoteExecuter getInstance(){
		if (instance == null){
			instance = new HttpClientRemoteExecuter();
		}
		return instance;
	}
	
	/**
	 * リモート呼び出し実行
	 * @param inputData 入力データ
	 * @param remoteBeanId リモート呼び出しbeanID
	 * @return 処理結果
	 * @throws HacClientSystemException 
	 */
	public Map<String, Object> execute(Map<String, Object> inputData, String remoteBeanId){
		log.info("リモート呼び出し開始");
		AbstractHttpClientRemoteComponent component = (AbstractHttpClientRemoteComponent) context.getBean(remoteBeanId);
		Map<String, Object> remoteResult = component.execute(inputData);
		log.info("リモート呼び出し終了");
		return remoteResult;
	}
}
