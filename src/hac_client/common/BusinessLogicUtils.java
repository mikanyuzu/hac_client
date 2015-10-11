/**
 * [module]
 * BusinessLogicUtils.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.common;

import hac_client.component.remote.HttpClientRemoteExecuter;
import hac_client.exception.HacClientSystemException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 業務処理ユーティリティ
 * @author sysusr1
 *
 */
public class BusinessLogicUtils {
	
	/**
	 * ユーザ情報取得リモート呼び出し処理
	 * @param hubMngNo 店舗管理No
	 * @param userId ユーザID
	 * @param userPasswd パスワード
	 * @return ユーザ情報取得結果
	 * @throws HacClientSystemException 
	 */
	public static Map<String, Object> getUserInfoForRemote(String hubMngNo, String userId, String userPasswd) throws HacClientSystemException{
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("hub_mng_no", hubMngNo);
		inputData.put("user_id", userId);
		inputData.put("user_passwd", userPasswd);
		return HttpClientRemoteExecuter.getInstance().execute(inputData, "accountLookup");
	}
	
	/**
	 * ユーザ情報取得処理
	 * @return ユーザ情報
	 * @throws HacClientSystemException 想定外例外が発生した場合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getUserData() throws HacClientSystemException{
		Map<String, Object> executeUser = LocalStrageCommon.getExecuteUserForLocal();
		String hubMngNo = "";
		List<Map<String, Object>> resultList = LocalStrageCommon
				.getHubMngNoForLocal();
		for (Map<String, Object> dataMap : resultList) {
			hubMngNo = LogicUtils
					.getMapValueToString(dataMap, "HUB_MNG_NO");
		}
		Map<String, Object> lookupReult = getUserInfoForRemote(hubMngNo, LogicUtils
						.getMapValueToString(executeUser, "EXECUTE_USER"),
						LogicUtils.getMapValueToString(executeUser,
								"EXECUTE_USERPASSWD"));
		Map<String, Object> processResult = (Map<String, Object>) lookupReult.get("processResult");
		Map<String, Object> resultData = (Map<String, Object>) processResult.get("remote_result_data");
		return (Map<String, Object>)resultData.get("user_data");
	}
	
	/**
	 * リモート呼び出し結果取得
	 * @param remoteResult
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> getRemoteResultData(Map<String, Object> remoteResult){
		Map<String, Object> processResult = (Map<String, Object>)remoteResult.get("processResult");
		Map<String, Object> remoteResuyltData = null;
//		if ("0".equals(processResult.get("remote_result"))){
			remoteResuyltData = (Map<String, Object>)processResult.get("remote_result_data");
//		}
		return remoteResuyltData;
	}
	
	public static void setAppMessage(String message){
		
	}
}
