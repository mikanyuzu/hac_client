/**
 * [module]
 * HacClientMainApplication.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.application;

import java.io.IOException;

import hac_client.common.CommonLogger;
import hac_client.common.ControllerDictionary;
import hac_client.common.MessageConfigManager;
import hac_client.component.strage.LocalStrageManager;
import hac_client.controller.AbstractController;
import hac_client.exception.HacClientSystemException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

/**
 * 初期起動処理クラス
 * 
 * @author sysusr1
 */
public class HacClientMainApplication extends Application {

	/** 自クラスのインスタンス */
	private static HacClientMainApplication instance;

	/** メイン画面 */
	private Stage mainStage;
	
	/** ロガー */
    protected Logger log = CommonLogger.getLogger(this.getClass().getName());

	/**
	 * 初期起動処理
	 * 
	 * @param stage
	 * @throws Exception
	 */
	@Override
	public void start(Stage stage) throws Exception {
		instance = this;
		this.mainStage = stage;
		this.mainStage.setTitle("社内システム");
		// メッセージを初期化
		MessageConfigManager.init();
		int initSettingStatus = LocalStrageManager.getInstance().getInitSettingStatus();
		
		// 初期設定が未済の場合はローカルストレージを生成する
		if (initSettingStatus == 0){
			LocalStrageManager.getInstance().init();
			showInitDisplay();
		}
		else if (initSettingStatus == 1){
			showInitDisplay();
		}
		
		// 初期設定済みの場合はログイン画面を表示する
		else if (initSettingStatus == 2){
			sendPage("", ControllerDictionary.CONTROLLER_NAME_ENTRYLOGIN);
		}
	}
	
	/**
	 * 終了処理
	 */
	@Override
	public void stop() throws Exception {
		log.info("顧客管理システム終了");
	}

	/**
	 * シーン切り替え処理
	 * 
	 * @param controller
	 *            次画面コントローラクラス
	 */
	private void replaceSceneContent(Parent controller) {
		Scene scene = this.mainStage.getScene();
		if (scene == null) {
			scene = new Scene(controller);
			this.mainStage.setScene(scene);
			mainStage.setWidth(1024);
			mainStage.setHeight(815);
			this.mainStage.show();
		} 
		else {
			this.mainStage.getScene().setRoot(controller);
			mainStage.setWidth(1024);
			mainStage.setHeight(820);
			this.mainStage.show();
		}
	}

	/**
	 * 次画面のロード処理
	 * 
	 * @param labelText
	 * @param controllerClassName
	 * @throws HacClientSystemException 
	 */
	public void sendPage(String labelText, String controllerClassName) throws HacClientSystemException {
		try {
			Class<?> controllerClass = Class.forName(controllerClassName);
			AbstractController controller = (AbstractController) controllerClass.newInstance();
			this.replaceSceneContent(controller);
		} 
		catch (ClassNotFoundException ex) {
			log.error(ex);
			throw new HacClientSystemException(ex);
		} 
		catch (InstantiationException ex) {
			log.error(ex);
			throw new HacClientSystemException(ex);
		} 
		catch (IllegalAccessException ex) {
			log.error(ex);		
			throw new HacClientSystemException(ex);
		}
	}
	
	/**
	 * 初期画面起動
	 * @throws IOException ファイル入出力に失敗の場合
	 */
	private void showInitDisplay() throws IOException{
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("InitSetting_1.fxml")));
		this.mainStage.setScene(scene);
		this.mainStage.show();
	}

	/**
	 * 自クラスのインスタンスを返却する
	 * 
	 * @return
	 */
	public static HacClientMainApplication getInstance() {
		return instance;
	}

}
