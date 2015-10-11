/**
 * [module]
 * AbstractHttpClientRemoteComponent.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.remote;

import hac_client.common.CommonConst;

import java.util.HashMap;
import java.util.Map;

import org.hac.remote.HttpRemoteCallInterface;

/**
 * リモート呼び出し処理基底クラス
 * @author sysusr1
 *
 */
public abstract class AbstractHttpClientRemoteComponent {
	
	/** リモート呼び出しクラスインスタンス */
	protected HttpRemoteCallInterface remoteService;
	
	/**
	 * リモート呼び出し実行
	 * @param inputData 入力データ
	 * @return 処理結果
	 */
	public Map<String, Object> execute(Map<String, Object> inputData){
		beforeProcess(inputData);
		Map<String, Object> remoteResult = remoteService.execute(inputData);
		Map<String, Object> processResult = new HashMap<String, Object>();
		processResult.put(CommonConst.KEY_REMOTE_PROCESS_RESULT, remoteResult);
		return processResult;
	}
	
	/**
	 * 各業務事前処理
	 * @param inputData 入力データ
	 */
	protected abstract void beforeProcess(Map<String, Object> inputData);
	
	/**
	 * リモート呼び出し処理クラスインスタンス設定
	 * @param service リモート呼び出し処理クラスインスタンス 
	 */
	public void setRemoteService(HttpRemoteCallInterface service){
		this.remoteService = service;
	}
}
