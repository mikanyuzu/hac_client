/**
 * [module]
 * LocalStrageCommon.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.common;

import hac_client.component.strage.LocalStrageSqlMapExecuter;
import hac_client.exception.HacClientSystemException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ローカルストレージ共通クラス
 * @author sysusr1
 *
 */
public class LocalStrageCommon {
	
	/**
	 * 初期設定ステータスを更新する
	 * @param executer SQL実行クラス
	 * @param status ステータス
	 * @throws HacClientSystemException 想定外例外発生の場合
	 */
	public static void updateSettingStatus(int status) throws HacClientSystemException{
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		List<Map<String, Object>> statusDataMapList = executer.select(null, null, "show_setting_status");
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("status", status);
		if (statusDataMapList.size() > 0){
			executer.insert(inputData, null, "update_setting_status");
		}
		else {
			executer.update(inputData, null, "insert_setttin_status");
		}
	}
	
	/**
	 * 実行ユーザをローカルストレージから取得する
	 * @return 実行ユーザデータ
	 * @throws HacClientSystemException 想定外例外発生の場合
	 */
	public static Map<String, Object> getExecuteUserForLocal() throws HacClientSystemException{
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		List<Map<String, Object>> selectResult = executer.select(null, null, "select_execute_user");
		return selectResult.get(0);
	}
	
	/**
	 * 店舗管理Noを登録・更新する
	 * @param hubMngno 店舗管理No
	 * @throws HacClientSystemException 想定外例外が発生した場合
	 */
	public static void insertHubMngNoForLocal(String hubMngno) throws HacClientSystemException {
		List<Map<String, Object>> selectResult = getHubMngNoForLocal();
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("hub_mng_no", hubMngno);
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		if (selectResult.size() > 0){
			executer.update(inputData, "", "update_hub_mng_no");
		}
		else {
			executer.insert(inputData, "", "insert_hub_mng_no");			
		}
	}
	
	/**
	 * 店舗管理Noを取得する
	 * @return 店舗管理No
	 * @throws HacClientSystemException 想定外例外が発生した場合
	 */
	public static List<Map<String, Object>> getHubMngNoForLocal() throws HacClientSystemException{
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		return executer.select(new HashMap<String, Object>(), "", "select_hub_mng_no");
	}
	
	/**
	 * 顧客マスタを検索する
	 * @param condition
	 * @return
	 * @throws HacClientSystemException
	 */
	public static List<Map<String, Object>> getCustmerMstLocal(Map<String, Object> condition) throws HacClientSystemException{
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		return executer.select(condition, "", "select_custmer_detail_by_any");
	}
}
