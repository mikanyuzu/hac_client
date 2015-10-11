/**
 * [module]
 * EntryLoginEithoutHubMngNoController.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.BusinessLogicUtils;
import hac_client.common.CommonConst;
import hac_client.common.ControllerDictionary;
import hac_client.common.LocalStrageCommon;
import hac_client.common.LogicUtils;
import hac_client.component.remote.HttpClientRemoteExecuter;
import hac_client.component.strage.LocalStrageSqlMapExecuter;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.tsrvfw.exception.TsrvfwSystemException;

/**
 * ログイン処理クラス
 * @author sysusr1
 *
 */
public class EntryLogin extends BaseController {

	/** 店舗名プルダウン */
	@FXML
	private ComboBox<String> hubMngNames;
	
	/** 店舗管理Noリスト */
	private List<String> hubMngKeyList;

	/** ユーザID */
	@FXML
	private TextField userId;
	
	/** パスワード */
	@FXML
	private PasswordField passwd;
	
	/**
	 * コンストラクタ
	 * @throws HacClientSystemException 
	 */
	public EntryLogin() throws HacClientSystemException{
		this.loadFxml("EntryLogin.fxml");
	}

	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		// 店舗名のプルダウンを作成
		try {
			makePulldownListHubName(executer);
		} catch (HacClientSystemException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ログインボタン押下処理
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void clickLogin(ActionEvent event) {
		
		try {
			// 入力チェック実行
			if (validate()){
				// メッセージエリアクリア
				initAppMessage();
				Map<String, Object> lookupReult = (Map<String, Object>)remoteExecuteUserInfo().get("processResult");
				if ((int)lookupReult.get("remote_result") == 0){
					Map<String, Object> remoteResultData = (Map<String, Object>)lookupReult.get("remote_result_data");
					Map<String, Object> userData = (Map<String, Object>)remoteResultData.get("user_data");
					
					LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
					updateExecuteUser(executer, userData);
					LocalStrageCommon.insertHubMngNoForLocal(hubMngKeyList.get(hubMngNames.getSelectionModel().getSelectedIndex()));
					// メインメニュー画面へ遷移
					this.sendPage(ControllerDictionary.CONTROLLER_NAME_MAINMENU);
				}
				else {
					setAppMessage(this.getClass().getName() + ":00004", CommonConst.MESSAGE_TYPE_ERROR);
				}
			}
		} 
		catch (TsrvfwSystemException e) {
			e.printStackTrace();
			log.error(e);
		} 
		catch (HacClientSystemException e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	/**
	 * 会社管理Noを取得する
	 * @param connection コネクション
	 * @param executer SQL実行クラス
	 * @throws HacClientSystemException 想定外例外発生時
	 */
	private List<Map<String, Object>> selectOrgMstLocal(LocalStrageSqlMapExecuter executer) throws HacClientSystemException{
		return executer.select(new HashMap<String, Object>(), null, "select_now_org_mng_no");
	}
	
	/**
	 * 店舗情報取得リモート呼び出し処理
	 * @throws HacClientSystemException 
	 */
	private Map<String, Object> remoteExecuteStoresInfo(String orgMngNo) throws HacClientSystemException {
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("org_mng_no", orgMngNo);
		return HttpClientRemoteExecuter.getInstance().execute(inputData, "storesLookup");
	}

	/**
	 * ユーザ情報取得リモート呼び出し処理
	 * 
	 * @return ユーザ情報取得結果
	 * @throws HacClientSystemException 
	 */
	private Map<String, Object> remoteExecuteUserInfo() throws HacClientSystemException {
		return BusinessLogicUtils.getUserInfoForRemote(hubMngKeyList.get(hubMngNames.getSelectionModel().getSelectedIndex()),
				userId.getText(), passwd.getText());
	}

	/**
	 * 店舗のプルダウンを作成する
	 * @param connection コネクション
	 * @param executer SQL実行クラス
	 * @throws HacClientSystemException 想定外例外が発生下場合
	 */
	@SuppressWarnings("unchecked")
	private void makePulldownListHubName(LocalStrageSqlMapExecuter executer) throws HacClientSystemException{
		List<Map<String, Object>> resultLocalDataListOrgMst = selectOrgMstLocal(executer);
		String orgMngNo = "";
		for (Map<String, Object> data : resultLocalDataListOrgMst){
			orgMngNo = LogicUtils.getMapValueToString(data, "ORG_MNG_NO");
		}
		Map<String, Object>remoteStoresLookupResult = remoteExecuteStoresInfo(orgMngNo);
		Map<String, Object> storeLookupProcessResult = (Map<String, Object>) remoteStoresLookupResult.get(CommonConst.KEY_REMOTE_PROCESS_RESULT);
		Map<String, Object> remoteResultDatas = (Map<String, Object>) storeLookupProcessResult.get(CommonConst.KEY_REMOTE_RESULT_DATE);
		List<String> itemList = new ArrayList<String>();
		hubMngKeyList = new ArrayList<String>();
		for (Map<String, Object> data : (List<Map<String, Object>>)((Map<String, Object>)remoteResultDatas.get("stores_data")).get("select_result")){
			itemList.add(LogicUtils.getMapValueToString(data, "hub_mng_no") + ":" + LogicUtils.getMapValueToString(data, "hub_name"));
			hubMngKeyList.add(LogicUtils.getMapValueToString(data, "hub_mng_no"));
		}
		hubMngNames.setItems(FXCollections.observableArrayList(itemList));
	}
	
	/**
	 * 実行ユーザを更新する
	 * @param executer SQL実行クラス
	 * @throws HacClientSystemException 想定外例外が発生した場合
	 */
	private void updateExecuteUser(LocalStrageSqlMapExecuter executer, Map<String, Object> userData) throws HacClientSystemException{
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("execute_user", userData.get("user_id"));
		inputData.put("execute_userpasswd", passwd.getText());
		executer.update(inputData, "", "update_execute_user");
	}
	
	/**
	 * 入力値チェック
	 * @return チェック結果
	 * @throws TsrvfwSystemException メッセージ読み込みに失敗した場合
	 */
	private boolean validate() 
			throws TsrvfwSystemException{
		boolean result = true;
		int selectIndex = hubMngNames.getSelectionModel().getSelectedIndex();
		String inputUserId = userId.getText();
		String inputPasswd = passwd.getText();
		if (selectIndex < 0) {
			setAppMessage(this.getClass().getName() + ":00001", CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}
		else if (!LogicUtils.isNotEmptyString(inputUserId)) {
			setAppMessage(this.getClass().getName() + ":00002", CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}
		else if (!LogicUtils.isNotEmptyString(inputPasswd)) {
			setAppMessage(this.getClass().getName() + ":00003", CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}
		return result;
	}
}
