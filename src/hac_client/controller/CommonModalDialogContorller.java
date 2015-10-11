/**
 * [module]
 * CommonModalDialogContorller.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.ModalDialogCommon;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Window;

/**
 * 汎用モーダルダイアログコントローラクラス
 * @author sysusr1
 *
 */
public class CommonModalDialogContorller extends BaseController {

	/** ダイアログメッセージ */
	@FXML
	private Label dialogMessage;
	
	/**  */
	private int selectOption;
	
	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * コンストラクタ
	 * @throws HacClientSystemException
	 */
	public CommonModalDialogContorller() throws HacClientSystemException{
	}
	
	/**
	 * 
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickOk(ActionEvent event) throws HacClientSystemException {
		selectOption = ModalDialogCommon.SELECT_OPTION_OK;
		getWindow().hide();
	}
	
	/**
	 * 
	 * @param event
	 * @throws HacClientSystemException
	 */
	@FXML
	private void clickCancel(ActionEvent event) throws HacClientSystemException {
		selectOption = ModalDialogCommon.SELECT_OPTION_CANCEL;
		getWindow().hide();
	}
	
	/**
	 * 
	 * @param message
	 */
	public void setDialogMessage(String message){
		dialogMessage.setText(message);
	}
	
	/**
	 * 
	 * @return
	 */
	private Window getWindow() {
        return dialogMessage.getScene().getWindow();
    }
	
	/**
	 * 
	 * @return
	 */
	public int getSelectOption(){
		return this.selectOption;
	}
}
