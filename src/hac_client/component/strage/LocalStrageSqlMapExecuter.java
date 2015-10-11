/**
 * [module]
 * LocalStrageSqlMapExecuter.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.strage;

import hac_client.common.CommonLogger;
import hac_client.component.dao.AbstractDao;
import hac_client.exception.HacClientSystemException;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tsrvfw.common.util.LogicUtils;

/**
 * ローカルストレージアクセス実行クラス(sqlMap利用)
 * @author sysusr1
 *
 */
public class LocalStrageSqlMapExecuter {

	/** ロガー */
    protected Logger log = CommonLogger.getLogger(this.getClass().getName());
    
    /** ローカルストレージアクセス実行クラスインスタンス */
    private static LocalStrageSqlMapExecuter instance;
    
    /** Spring定義情報 */
	private ApplicationContext context;
    
    /**
     * ローカルストレージアクセス実行クラスインスタンス取得
     * @return ローカルストレージアクセス実行クラス
     */
    public static LocalStrageSqlMapExecuter getInstance(){
    	if (instance == null) {
    		instance = new LocalStrageSqlMapExecuter();
    	}
    	return instance;
    }
    
    /**
     * 検索処理実行
     * @param inputData 検索条件データ
     * @param executeComponentId 実行コンポーネントID
     * @param sqlMapConfigId 検索処理ID
     * @return 検索結果
     * @throws HacClientSystemException DB接続に失敗した場合
     */
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> select(Map<String, Object> inputData, String executeComponentId, String sqlMapConfigId) throws HacClientSystemException{
    	String componentId = executeComponentId;
    	if (!LogicUtils.isNotEmptyString(executeComponentId)){
    		componentId = "basicDao";
    	}
    	AbstractDao dao = (AbstractDao)context.getBean(componentId);
    	Map<String, Object> resultMap = dao.executeSelect(inputData, sqlMapConfigId);
    	if (resultMap == null || !"success".equals(resultMap.get("result_code"))){
    		throw new HacClientSystemException("ローカルストレージ接続失敗");
    	}
    	return (List<Map<String, Object>>)dao.executeSelect(inputData, sqlMapConfigId).get("select_result");
    }
    
    /**
     * 更新処理実行
     * @param inputData 更新データ
     * @param executeComponentId 実行コンポーネントID
     * @param sqlMapConfigId 更新処理ID
     * @return 更新処理結果
     * @throws HacClientSystemException DB接続失敗時
     */
    public int update(Map<String, Object> inputData, String executeComponentId, String sqlMapConfigId) throws HacClientSystemException {
    	int result = 0;
    	String componentId = executeComponentId;
    	if (!LogicUtils.isNotEmptyString(executeComponentId)){
    		componentId = "basicDao";
    	}
    	AbstractDao dao = (AbstractDao)context.getBean(componentId);
    	Map<String, Object> resultMap = dao.executeUpdate(inputData, sqlMapConfigId);
    	if (resultMap == null || !"success".equals(resultMap.get("result_code"))){
    		throw new HacClientSystemException("ローカルストレージ接続失敗");
    	}
    	return result;
    }
    
    /**
     * 登録処理実行
     * @param inputData 登録データ
     * @param executeComponentId 実行コンポーネントID
     * @param sqlMapConfigId 登録処理ID
     * @return 登録処理結果
     * @throws HacClientSystemException DB接続失敗時
     */
    public int insert(Map<String, Object> inputData, String executeComponentId, String sqlMapConfigId) throws HacClientSystemException {
    	int result = 0;
    	String componentId = executeComponentId;
    	if (!LogicUtils.isNotEmptyString(executeComponentId)){
    		componentId = "basicDao";
    	}
    	AbstractDao dao = (AbstractDao)context.getBean(componentId);
    	Map<String, Object> resultMap = dao.executeInsert(inputData, sqlMapConfigId);
    	if (resultMap == null || !"success".equals(resultMap.get("result_code"))){
    		throw new HacClientSystemException("ローカルストレージ接続失敗");
    	}
    	return result;
    }
    
    /**
     * 削除処理実行
     * @param inputData 削除条件データ
     * @param executeComponentId 実行コンポーネントID
     * @param sqlMapConfigId 削除処理ID
     * @return 削除処理結果
     * @throws HacClientSystemException DB接続失敗時
     */
    public int delete(Map<String, Object> inputData, String executeComponentId, String sqlMapConfigId) throws HacClientSystemException {
    	int result = 0;
    	String componentId = executeComponentId;
    	if (!LogicUtils.isNotEmptyString(executeComponentId)){
    		componentId = "basicDao";
    	}
    	AbstractDao dao = (AbstractDao)context.getBean(componentId);
    	Map<String, Object> resultMap = dao.executeDelete(inputData, sqlMapConfigId);
    	if (resultMap == null || !"success".equals(resultMap.get("result_code"))){
    		throw new HacClientSystemException("ローカルストレージ接続失敗");
    	}
    	return result;
    }
    
    /**
     * テーブル作成処理実行
     * @param inputData データ
     * @param executeComponentId 実行コンポーネントID
     * @param sqlMapConfigId テーブル作成処理ID
     * @return テーブル作成処理結果
     * @throws HacClientSystemException DB接続失敗時
     */
    public int create(Map<String, Object> inputData, String executeComponentId, String sqlMapConfigId) throws HacClientSystemException {
    	int result = 0;
    	String componentId = executeComponentId;
    	if (!LogicUtils.isNotEmptyString(executeComponentId)){
    		componentId = "basicDao";
    	}
    	AbstractDao dao = (AbstractDao)context.getBean(componentId);
    	Map<String, Object> resultMap = dao.executeCreate(inputData, sqlMapConfigId);
    	if (resultMap == null || !"success".equals(resultMap.get("result_code"))){
    		throw new HacClientSystemException("ローカルストレージ接続失敗");
    	}
    	return result;
    }
    
    /**
     * 隠蔽コンストラクタ
     */
    private LocalStrageSqlMapExecuter(){
    	context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
