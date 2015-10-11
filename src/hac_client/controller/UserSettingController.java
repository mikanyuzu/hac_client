/**
 * [module]
 * UserSettingController.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.BusinessLogicUtils;
import hac_client.common.CommonConst;
import hac_client.common.LocalStrageCommon;
import hac_client.common.LogicUtils;
import hac_client.common.ModalDialogCommon;
import hac_client.component.remote.HttpClientRemoteExecuter;
import hac_client.component.strage.LocalStrageSqlMapExecuter;
import hac_client.exception.HacClientSystemException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.tsrvfw.exception.TsrvfwSystemException;

/**
 * ユーザ情報設定
 * @author sysusr1
 *
 */
public class UserSettingController extends BaseController {

	/** パスワード */
	@FXML
	private TextField userPasswd;
	
	/** ユーザ名 */
	@FXML
	private TextField userName;
	
	/** ユーザメールアドレス */
	@FXML
	private TextField userMail;
	
	/** ユーザTEL */
	@FXML
	private TextField userTel;
	
	/** 店舗名 */
	@FXML
	private TextField hubName;
	
	/** 店舗連絡先（TEL） */
	@FXML
	private TextField hubTel;
	
	/** 店舗メールアドレス */
	@FXML
	private TextField hubMail;
	
	/**  */
	@FXML
	private TextField hubPrintName;
	
	/**  */
	@FXML
	private TextField sendMail;
	
	/**  */
	@FXML
	private TextField sendSmtp;
	
	/**  */
	@FXML
	private TextField sendPort;
	
	/**  */
	@FXML
	private TextField sendAccount;
	
	/**  */
	@FXML
	private TextField sendPasswd;
	
	/**  */
	@FXML
	private TableView hubUserList;
	
	
	/**
	 * コンストラクタ
	 * @throws HacClientSystemException
	 */
	public UserSettingController() throws HacClientSystemException{
		this.loadFxml("UserSetting.fxml");
	}
	
	/**
	 * 初期処理
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// ユーザ情報取得
			Map<String, Object> userData = BusinessLogicUtils.getUserData();
			userPasswd.setText(LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USERPASSWD"));
			userName.setText(LogicUtils.getMapValueToString(userData, "user_name"));
			userMail.setText(LogicUtils.getMapValueToString(userData, "user_mail"));
			userTel.setText(LogicUtils.getMapValueToString(userData, "user_tel"));
			
			// 店舗情報取得
			Map<String, Object> inputData = new HashMap<String, Object>();
			inputData.put("hub_mng_no", LogicUtils.getMapValueToString(LocalStrageCommon.getHubMngNoForLocal().get(0), "HUB_MNG_NO"));
			Map<String, Object> remoteStoresLookupResult = HttpClientRemoteExecuter.getInstance().execute(inputData, "storeDataLookup");
			Map<String, Object> storeLookupProcessResult = (Map<String, Object>) remoteStoresLookupResult.get(CommonConst.KEY_REMOTE_PROCESS_RESULT);
			Map<String, Object> remoteResultDatas = (Map<String, Object>) storeLookupProcessResult.get(CommonConst.KEY_REMOTE_RESULT_DATE);
			Map<String, Object> stoesDataMap = (Map<String, Object>)remoteResultDatas.get("stores_data");
			List<Map<String, Object>> stoesDataList = (List<Map<String, Object>>)stoesDataMap.get("select_result");
			for(Map<String, Object> dataMap : stoesDataList){
				hubName.setText(LogicUtils.getMapValueToString(dataMap, "hub_name"));
				hubTel.setText(LogicUtils.getMapValueToString(dataMap, "hub_tel"));
				hubMail.setText(LogicUtils.getMapValueToString(dataMap, "hub_mail"));
				hubPrintName.setText(LogicUtils.getMapValueToString(dataMap, "hub_print_name"));
				sendMail.setText(LogicUtils.getMapValueToString(dataMap, "email_send_address"));
				sendSmtp.setText(LogicUtils.getMapValueToString(dataMap, "email_send_smtp"));
				sendPort.setText(LogicUtils.getMapValueToString(dataMap, "email_send_port"));
				sendAccount.setText(LogicUtils.getMapValueToString(dataMap, "email_send_account"));
				sendPasswd.setText(LogicUtils.getMapValueToString(dataMap, "email_send_passwd"));
			}
			
		} catch (HacClientSystemException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新ボタン処理
	 * @param event
	 * @throws HacClientSystemException
	 * @throws IOException 
	 * @throws TsrvfwSystemException 
	 */
	@FXML
	private void clickUpdate(ActionEvent event) throws HacClientSystemException, IOException, TsrvfwSystemException {
		
		// 確認ダイアログを表示する
		int selectResult = ModalDialogCommon.displayModalDialog("基本データを更新します。よろいですか？");
		// OKの場合
		if (selectResult == ModalDialogCommon.SELECT_OPTION_OK){
			// サーバデータ更新（ユーザ情報）
			Map<String, Object> inputUpdateUserData = new HashMap<String, Object>();
			inputUpdateUserData.put("hub_mng_no", LogicUtils.getMapValueToString(LocalStrageCommon.getHubMngNoForLocal().get(0), "HUB_MNG_NO"));
			inputUpdateUserData.put("user_id", LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USER"));
			inputUpdateUserData.put("changed_passwd", userPasswd.getText());
			inputUpdateUserData.put("user_passwd", LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USERPASSWD"));
			inputUpdateUserData.put("user_name", userName.getText());
			inputUpdateUserData.put("user_tel", userTel.getText());
			inputUpdateUserData.put("user_mail", userMail.getText());
			inputUpdateUserData.put("user_role_type", "001");
			inputUpdateUserData.put("update_user", LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USER"));
			HttpClientRemoteExecuter.getInstance().execute(inputUpdateUserData, "userDataUpdate");
			
			// サーバデータ更新（店舗情報）
			Map<String, Object> inputUpdateHubData = new HashMap<String, Object>();
			inputUpdateHubData.put("hub_mng_no", LogicUtils.getMapValueToString(LocalStrageCommon.getHubMngNoForLocal().get(0), "HUB_MNG_NO"));
			inputUpdateHubData.put("hub_name", hubName.getText());
			inputUpdateHubData.put("hub_tel", hubTel.getText());
			inputUpdateHubData.put("hub_mail", hubMail.getText());
			inputUpdateHubData.put("hub_print_name", hubPrintName.getText());
			inputUpdateHubData.put("email_send_address", sendMail.getText());
			inputUpdateHubData.put("email_send_smtp", sendSmtp.getText());
			inputUpdateHubData.put("email_send_port", sendPort.getText());
			inputUpdateHubData.put("email_send_account", sendAccount.getText());
			inputUpdateHubData.put("email_send_passwd", sendPasswd.getText());
			inputUpdateHubData.put("hub_custmer_prop_setting", null);
			inputUpdateHubData.put("update_user", LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USER"));
			HttpClientRemoteExecuter.getInstance().execute(inputUpdateHubData, "updateStoreData");
			
			// パスワードが変更担った場合はローカルストレージもアップデートする
			if (userPasswd.getText() != LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USERPASSWD")){
				Map<String, Object> updatePasswdData = new HashMap<String, Object>();
				updatePasswdData.put("execute_userpasswd", userPasswd.getText());
				LocalStrageSqlMapExecuter.getInstance().update(updatePasswdData, "", "update_execute_user_passwd");
			}
			// メッセージ格納
			setAppMessage(this.getClass().getName() + ":00001", CommonConst.MESSAGE_TYPE_INFO);
		}
	}
}
