/**
 * [module]
 * InitSetting2Controller.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.CommonConst;
import hac_client.common.LogicUtils;
import hac_client.component.remote.HttpClientRemoteExecuter;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.tsrvfw.exception.TsrvfwSystemException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * 初期設定2処理クラス
 * @author sysusr1
 *
 */
public class InitSetting2Controller extends BaseController{

	/** 会社名 */
	@FXML
	private TextField orgName;
	
	/** 店舗名 */
	@FXML
	private TextField hubName;
	
	/** 店舗連絡先（TEL） */
	@FXML
	private TextField hubTel;
	
	/** 店舗メールアドレス */
	@FXML
	private TextField hubMail;
	
	/** 店舗代表者名 */
	@FXML
	private TextField hubSysUser;
	
	/**
	 * コンストラクタ
	 * @throws HacClientSystemException 
	 */
	public InitSetting2Controller() throws HacClientSystemException{
		this.loadFxml("InitSetting_2.fxml");
	}
	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	/**
	 * 登録ボタン押下処理
	 */
	@FXML
	private void registOrg(ActionEvent event){
		try {
			// メッセージの初期化
			this.initAppMessage();
			// 入力チェック実施する
			if (validate()){
				// 会社情報登録リモート呼び出し
				remoteExecute();
			}
			
		} catch (TsrvfwSystemException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 入力チェック実施
	 * @return チェック結果
	 * @throws TsrvfwSystemException メッセージ取得失敗の場合
	 */
	private boolean validate() throws TsrvfwSystemException {
		boolean result = true;
		String inputOrgName = orgName.getText();
		String inputHubName = hubName.getText();
		String inputHubTel = hubTel.getText();
		String inputHubMail = hubMail.getText();
		String inputHubSysUser = hubSysUser.getText();
		// 会社名
		if (!LogicUtils.isNotEmptyString(inputOrgName)) {
			setAppMessage(this.getClass().getName() + ":00001",
					CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}

		// 店舗名
		else if (!LogicUtils.isNotEmptyString(inputHubName)) {
			setAppMessage(this.getClass().getName() + ":00002",
					CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}
		// 店舗連絡先
		else if (!LogicUtils.isNotEmptyString(inputHubTel)) {
			setAppMessage(this.getClass().getName() + ":00003",
					CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}
		// 店舗メールアドレス
		else if (!LogicUtils.isNotEmptyString(inputHubMail)) {
			setAppMessage(this.getClass().getName() + ":00004",
					CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}
		// 店舗代表者
		else if (!LogicUtils.isNotEmptyString(inputHubSysUser)) {
			setAppMessage(this.getClass().getName() + ":00005",
					CommonConst.MESSAGE_TYPE_ERROR);
			result = false;
		}
		return result;
	}
	
	/**
	 * 会社情報登録
	 */
	private Map<String, Object> remoteExecute() {
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("hub_mng_no", orgName.getText());
		inputData.put("user_id", hubName.getText());
		inputData.put("user_passwd", hubTel.getText());
		inputData.put("user_passwd", hubMail.getText());
		inputData.put("user_passwd", hubSysUser.getText());
		return HttpClientRemoteExecuter.getInstance().execute(inputData, "orgResgist");
	}

}
