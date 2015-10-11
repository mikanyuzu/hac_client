/**
 * [module]
 * AbstractDao.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.tsrvfw.common.logger.CommonLogger;
import org.tsrvfw.common.util.DateUtils;

/**
 * 業務DBアクセス基底クラス
 *
 * @author tsubaki
 *
 */
public abstract class AbstractDao  extends SqlMapClientDaoSupport {

	/** ロガー */
	protected Logger log = CommonLogger.getLogger(this.getClass().getName());
	
	/**
	 * 基底検索処理
	 * 
	 * @param inputData 指定条件Map
	 * @param target 指定SQL
	 * @return 処理結果
	 */
	public Map<String, Object> executeSelect(Map<String, Object> inputData, String target){
		List<Map<String, Object>> selectResult = select(inputData, target);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("select_result", selectResult);
		resultMap.put("result_code", "success");
		return resultMap;
	}
	
	/**
	 * 基底登録処理
	 * 
	 * @param inputData 登録データMap
	 * @param target 指定SQL
	 * @return 処理結果
	 */
	public Map<String, Object> executeInsert(Map<String, Object> inputData, String target){
		if (inputData == null){
			inputData = new HashMap<String, Object>();
		}
		long nowTime = DateUtils.getSystemDateUnixTimestamp();
		inputData.put("create_date", nowTime);
		inputData.put("update_date", nowTime);
		insert(inputData, target);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result_code", "success");
		return resultMap;
	}
	
	/**
	 * 基底更新処理
	 * 
	 * @param inputData 更新データMap
	 * @param target 指定SQL
	 * @return 処理結果
	 */
	public Map<String, Object> executeUpdate(Map<String, Object> inputData, String target){
		if (inputData == null){
			inputData = new HashMap<String, Object>();
		}
		long nowTime = DateUtils.getSystemDateUnixTimestamp();
		inputData.put("update_date", nowTime);
		update(inputData, target);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result_code", "success");
		return resultMap;
	}
	
	/**
	 * 基底削除処理
	 * 
	 * @param inputData 削除データMap
	 * @param target 指定SQL
	 * @return 処理結果
	 */
	public Map<String, Object> executeDelete(Map<String, Object> inputData, String target){
		delete(inputData, target);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result_code", "success");
		return resultMap;
	}
	
	/**
	 * 基底テーブル作成処理
	 * 
	 * @param inputData データMap
	 * @param target 指定SQL
	 * @return 処理結果
	 */
	public Map<String, Object> executeCreate(Map<String, Object> inputData, String target){
		if (inputData == null){
			inputData = new HashMap<String, Object>();
		}
		create(inputData, target);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result_code", "success");
		return resultMap;
	}
	
	/**
	 * 業務DB検索処理実行
	 *
	 * @param inputMap 条件Map
	 * @param target 対象SQL
	 * @return 処理結果
	 */
	protected abstract List<Map<String, Object>> select(Map<String, Object> inputMap, String target);
	
	/**
	 * 業務DB登録処理実行
	 *
	 * @param inputMap 登録データMap
	 * @param target 対象SQL
	 */
	protected abstract void insert(Map<String, Object> inputMap, String target);
	
	/**
	 * 業務DB更新処理実行
	 *
	 * @param inputMap 登録データMap
	 * @param target 対象SQL
	 */
	protected abstract void update(Map<String, Object> inputMap, String target);
	
	/**
	 * 業務DB削除処理実行
	 *
	 * @param inputMap 登録データMap
	 * @param target 対象SQL
	 */
	protected abstract void delete(Map<String, Object> inputMap, String target);
	
	/**
	 * 業務テーブル作成処理実行
	 *
	 * @param inputMap 入力データMap
	 * @param target 対象SQL
	 */
	protected abstract void create(Map<String, Object> inputMap, String target);
}
