/**
 * [module]
 * CustmerManageController.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * 業務顧客管理コントローラクラス
 * @author sysusr1
 *
 */
public class CustmerManageRegistController extends BaseController {

	/** 氏名（姓） */
	@FXML
	private TextField custmerNameFirst;
	
	/** 氏名（名） */
	@FXML
	private TextField custmerNameLast;
	
	/** ふりがな（姓） */
	@FXML
	private TextField custmerNameFirstRead;
	
	/** ふりがな（名） */
	@FXML
	private TextField custmerNameLastRead;
	
	/** 性別 */
	@FXML
	private ComboBox<String> custmerGender;
	
	/** 年齢 */
	@FXML
	private TextField custmerAge;
	
	/** 住所（都道府県） */
	@FXML
	private ComboBox<String> custmerAddress1;
	
	/** 住所（市町村区） */
	@FXML
	private TextField custmerAddress2;
	
	/** 住所（その他） */
	@FXML
	private TextField custmerAddress3;
	
	/** TEL（自宅） */
	@FXML
	private TextField custmerTel;
	
	/** TEL（携帯） */
	@FXML
	private TextField custmerTelCell;
	
	/** Eメール（個人） */
	@FXML
	private TextField custmerEmail;
	
	/** 勤務先（会社名） */
	@FXML
	private TextField custmerEmployment;
	
	/** 勤務先（連絡先） */
	@FXML
	private TextField custmerEmploymentTel;
	
	/** Eメール（会社） */
	@FXML
	private TextField custmerEmploymentEmail;
	
	/** メールマガジン配信先 */
	@FXML
	private ComboBox<String> sendEmailType;
	
	/** カード会員種類 */
	@FXML
	private ComboBox<String> memberType;
	
	/** カード会員番号 */
	@FXML
	private TextField memberDist;
	
	/** 初回来店日 */
	@FXML
	private DatePicker fristVisitDate;
	
	/**
	 * コンストラクタ
	 * @throws HacClientSystemException
	 */
	public CustmerManageRegistController() throws HacClientSystemException{
		this.loadFxml("CustmerManageRegist.fxml");
	}
	
	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		custmerGender.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
	}

	/**
	 * 登録するボタン押下処理
	 * @param event
	 */
	@FXML
	private void clickRegist(ActionEvent event){
		log.debug("顧客登録開始");
		
		
		
		log.debug("顧客登録終了");
	}
}
