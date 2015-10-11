/**
 * [module]
 * LocalStrageManager.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.strage;

import hac_client.common.CommonLogger;
import hac_client.common.LocalStrageCommon;
import hac_client.exception.HacClientSystemException;
import hac_client.exception.TsrvfwSystemException;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * ローカルストレージ管理クラス
 * @author sysusr1
 */
public class LocalStrageManager {
    
	/** ローカルストレージ管理クラスインスタンス */
    private static LocalStrageManager instance;
    
    /** ロガー */
    protected Logger log = CommonLogger.getLogger(this.getClass().getName());
    
    /**
     * 隠蔽コンストラクタ
     */
    private LocalStrageManager (){
    }
    
    /**
     * ローカルストレージ管理クラスインスタンス取得
     * @return ローカルストレージ管理クラス
     */
    public static LocalStrageManager getInstance(){
        if (instance == null){
            instance = new LocalStrageManager();
        }
        return instance;
    }
    
    /**
     * ローカルストレージ初期設定処理
     * @throws TsrvfwSystemException
     */
    public void init() throws TsrvfwSystemException{
        this.initSetting();
    }
    
    /**
	 * 初期設定
	 * @throws TsrvfwSystemException
	 */
	private void initSetting() throws HacClientSystemException {
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		executer.create(null, null, "create_table_setting_status");
		executer.create(null, null, "create_table_app_ver_mng");
		executer.create(null, null, "create_table_store_mst");
		executer.create(null, null, "create_table_org_mst");
		executer.create(null, null, "create_table_hub_custmer_mst");
		executer.create(null, null, "create_table_custmer_detail");
	}
	
	/**
	 * 初期設定ステータス取得
	 * @return 初期設定ステータス
	 * @throws TsrvfwSystemException DBアクセス失敗の場合
	 */
	public int getInitSettingStatus() throws HacClientSystemException{
		int initSettingStatus = 0;
		
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		List<Map<String, Object>> resultList = executer.select(null, null, "show_tables");
		boolean isComplete = false;
		for (Map<String, Object> dataMap : resultList) {
			String tableName = (String) dataMap.get("TABLE_NAME");
			if ("SETTING_STATUS".equals(tableName)) {
				isComplete = true;
				break;
			}
		}
		if (isComplete) {
			List<Map<String, Object>> statusDataMapList = executer.select(null, null, "show_setting_status");
			if (statusDataMapList.size() > 0) {
				for (Map<String, Object> dataMap : statusDataMapList) {
					initSettingStatus = (int) dataMap.get("STATUS");
				}
			} else {
				initSettingStatus = 1;
				LocalStrageCommon.updateSettingStatus(initSettingStatus);
			}
		}
		
		return initSettingStatus;
	}
}
