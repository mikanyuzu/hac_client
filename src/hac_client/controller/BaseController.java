/**
 * [module]
 * IncMenuCommonController.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.BusinessLogicUtils;
import hac_client.common.CommonConst;
import hac_client.common.ControllerDictionary;
import hac_client.common.DateUtils;
import hac_client.common.LocalStrageCommon;
import hac_client.common.LogicUtils;
import hac_client.common.MessageConfigManager;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.tsrvfw.exception.TsrvfwSystemException;

/**
 * 業務共通エリアコントローラクラス
 * @author sysusr1
 *
 */
public class BaseController extends AbstractController {

	/** 共通メッセージエリア */
	@FXML
	protected Label appMessage;
	
	/** ログイン時間 */
	@FXML
	protected Label loginTime;
	
	/** ユーザアカウント名 */
	@FXML
	protected Label userAccount;
	
	/** メッセージフォントカラー エラー */
	private static final String MESSAGE_COLOR_ERR = "#ff0000";
	
	/** メッセージフォントカラー インフォメーション */
	private static final String MESSAGE_COLOR_INFO = "#00bfff";
	
	/** メッセージフォントカラー 警告 */
	private static final String MESSAGE_COLOR_WARN = "#ffd700";
	
	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		loginTime.setText(DateUtils.getFormatDate(DateUtils.getSystemDate(),
//				"yyyy-MM-dd hh:mm:ss"));
		// メッセージを初期化
		initAppMessage();
		
		try {
			Map<String, Object> userData = BusinessLogicUtils.getUserData();
			userAccount.setText(LogicUtils.getMapValueToString(userData, "user_name"));
			Map<String, Object> executeUser = LocalStrageCommon.getExecuteUserForLocal();
			long updateDate = LogicUtils.getMapValueToInt(executeUser, "UPDATE_DATE");
			loginTime.setText(DateUtils.getFormatDate(new Date(updateDate * 1000), "yyyy-MM-dd hh:mm:ss"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ログアウトボタン押下処理
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
    private void clickLogout(ActionEvent event) throws HacClientSystemException {
    	this.sendPage(ControllerDictionary.CONTROLLER_NAME_ENTRYLOGIN);
    }
	
	/**
	 * TOPボタン押下処理
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickTop(ActionEvent event) throws HacClientSystemException {
		this.sendPage(ControllerDictionary.CONTROLLER_NAME_MAINMENU);
	}
	
	/**
	 * 予約確認ボタン押下処理
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickReserve(ActionEvent event) throws HacClientSystemException {
		this.sendPage(ControllerDictionary.CONTROLLER_NAME_RESERVE);
	}
	
	/**
	 * 顧客管理登録ボタン
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickCustmerManagerRegist(ActionEvent event) throws HacClientSystemException {
		this.sendPage(ControllerDictionary.CONTROLLER_NAME_CUSTMERMANAGERREGIST);
	}
	
	/**
	 * 顧客管理検索ボタン
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickCustmerManagerSearch(ActionEvent event) throws HacClientSystemException {
		this.sendPage(ControllerDictionary.CONTROLLER_NAME_ENTRYLOGIN);
	}
	
	/**
	 * 顧客管理ボタン
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickCustmer(ActionEvent event) throws HacClientSystemException {
		this.sendPage(ControllerDictionary.CONTROLLER_NAME_CUSTMER);
	}
	
	/**
	 * ユーザ設定ボタン
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickUserSetting(ActionEvent event) throws HacClientSystemException {
		this.sendPage(ControllerDictionary.CONTROLLER_NAME_USERSETTING);
	}
	

    /**
     * メッセージ格納
     * @param innerCode 内部コード
     * @param type メッセージ種類
     * @throws TsrvfwSystemException メッセージが取得できない場合
     */
    protected void setAppMessage(String innerCode, int type, String ...params) throws TsrvfwSystemException{
    	if (this.appMessage == null){
    		this.appMessage = new Label();
    	}
    	// メッセージ本文を設定
    	this.appMessage.setText(MessageConfigManager.getViewMessageFromInnerCode(innerCode, params));
    	
    	// テキストカラーの設定
    	switch (type) {
    	// エラー
		case CommonConst.MESSAGE_TYPE_ERROR:
			this.appMessage.setStyle("-fx-text-fill:" + MESSAGE_COLOR_ERR + ";");
			break;
		// 警告
		case CommonConst.MESSAGE_TYPE_WARN:
			this.appMessage.setStyle("-fx-text-fill:" + MESSAGE_COLOR_WARN + ";");
			break;
		// 通常（インフォメーション）
		default:
			this.appMessage.setStyle("-fx-text-fill:" + MESSAGE_COLOR_INFO + ";");
			break;
		}
    	
    }
    
    /**
     * メッセージの初期化
     */
    protected void initAppMessage(){
    	if (appMessage == null){
    		appMessage = new Label();
    	}
    	this.appMessage.setText("");
    }
}
