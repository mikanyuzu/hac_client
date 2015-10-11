/**
 * [module]
 * InitSetting1Controller.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.CommonConst;
import hac_client.common.ControllerDictionary;
import hac_client.common.LocalStrageCommon;
import hac_client.common.LogicUtils;
import hac_client.component.remote.HttpClientRemoteExecuter;
import hac_client.component.strage.LocalStrageSqlMapExecuter;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.tsrvfw.exception.TsrvfwSystemException;

/**
 * 初期設定1処理クラス
 * @author sysusr1
 *
 */
public class InitSetting1Controller extends BaseController{

	/** 会社管理No */
	@FXML
	private TextField orgMngNo;
	
	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * 次へボタン押下処理
	 * @param event
	 * @throws TsrvfwSystemException メッセージ取得失敗の場合
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void clickNext(ActionEvent event) throws TsrvfwSystemException{
		// 入力値を取得
		String inputOrgMngNo = orgMngNo.getText();
		
		try {
			LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
			
			// 会社管理Noの入力がある場合は既に登録済みユーザと判定する
			if (LogicUtils.isNotEmptyString(inputOrgMngNo)){
				// 会社管理Noの登録が存在するか判定する
				Map<String, Object> lookupReult = (Map<String, Object>) remoteExecute().get(CommonConst.KEY_REMOTE_PROCESS_RESULT);
				int remoteResult = (int)lookupReult.get(CommonConst.KEY_REMOTE_RESULT);
				// 入力エラーの場合
				if (remoteResult == CommonConst.REMOTE_RESULT_BIZERR){
					setAppMessage(this.getClass().getName() + ":00001", CommonConst.MESSAGE_TYPE_ERROR);
				}
				// 会社情報が登録済みの場合
				else if (remoteResult == CommonConst.REMOTE_RESULT_NORMAL){
					Map<String, Object> remoteResultData = (Map<String, Object>) lookupReult.get(CommonConst.KEY_REMOTE_RESULT_DATE);
					Map<String, Object> orgData = (Map<String, Object>) remoteResultData.get("org_data");
					List<Map<String, Object>> orgSelectResult = (List<Map<String, Object>>)orgData.get("select_result");
					String orgMstNo = "";
					for(Map<String, Object> orgSelectResultMap : orgSelectResult){
						orgMstNo = LogicUtils.getMapValueToString(orgSelectResultMap, "org_mng_no");
					}
					
					// 会社管理Noを保存
					insertOrgMstNo(executer, orgMstNo);
					insertSettingStatus(2);
					
					// ログインへ遷移
					this.sendPage(ControllerDictionary.CONTROLLER_NAME_ENTRYLOGIN);
				}
			}
			// 未入力の場合は会社へ遷移する
			else {
				// メインメニュー画面へ遷移
				this.sendPage(ControllerDictionary.CONTROLLER_NAME_INITSETTING_2);
			}
		} catch (HacClientSystemException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	/**
	 * 会社管理Noを保存する
	 * @param connection DBコネクション
	 * @param executer SQL実行クラス
	 * @param orgMstNo 会社管理No
	 * @throws HacClientSystemException 想定外例外発生の場合
	 */
	private void insertOrgMstNo(LocalStrageSqlMapExecuter executer, String orgMstNo)
			throws HacClientSystemException {
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("org_mst_no", orgMstNo);
		executer.insert(inputData, null, "insert_org_mst");
	}
	
	/**
	 * 初期設定ステータスを更新する
	 * @param connection DBコネクション
	 * @param executer SQL実行クラス
	 * @param stauts 初期設定ステータス
	 * @throws HacClientSystemException 想定外例外発生の場合
	 */
	private void insertSettingStatus(int stauts) 
			throws HacClientSystemException{
		LocalStrageCommon.updateSettingStatus(stauts);
	}
	
	/**
	 * サーバチェック
	 */
	private Map<String, Object> remoteExecute() {
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("org_mng_no", orgMngNo.getText());
		return HttpClientRemoteExecuter.getInstance().execute(inputData, "orgLookup");
	}
	
}
