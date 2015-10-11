/**
 * [module]
 * CustmerController.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.CommonConst;
import hac_client.common.DateUtils;
import hac_client.common.LocalStrageCommon;
import hac_client.common.LogicUtils;
import hac_client.component.beans.CustmerListData;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.tsrvfw.exception.TsrvfwSystemException;

/**
 * 顧客管理コントローラクラス
 * @author sysusr1
 *
 */
public class CustmerController extends BaseController {

	/** 氏名（姓） */
	@FXML
	private TextField userNameFirst;

	/** 氏名（姓） */
	@FXML
	private TextField userNameLast;

	/** 連絡先 */
	@FXML
	private TextField userTel;

	/** 予約日 */
	@FXML
	private DatePicker targetReserveDate;
	
	/** 予約時間（時間） */
	@FXML
	private ComboBox<String> targetHour;

	/** 予約時間（分） */
	@FXML
	private ComboBox<String> targetMinute;
	
	/** 予約一覧 */
	@FXML
	private TableView<CustmerListData> custmerList;
	
	/** 予約名 */
	@FXML
	private TableColumn<CustmerListData, String> custmerName;

	/** 予約TEL */
	@FXML
	private TableColumn<CustmerListData, String> custmerTel;
	
	/** 指名 */
	@FXML
	private TableColumn<CustmerListData, String> reserveCount;
	
	/** 予約時間 */
	@FXML
	private TableColumn<CustmerListData, String> reserveTime;
	
	/** 実施メニュー */
	@FXML
	private TableColumn<CustmerListData, String> reserveMenu;
	
	/** 備考 */
	@FXML
	private TableColumn<CustmerListData, String> remarks;
	
	
	/**
	 * コンストラクタ
	 * @throws HacClientSystemException
	 */
	public CustmerController() throws HacClientSystemException {
		this.loadFxml("Custmer.fxml");
	}
	
	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// プロパティを初期化する
    	initProperty();
    	// 時間のプルダウンを作成
    	targetHour.setItems(FXCollections.observableArrayList(DateUtils.createTimeCombobox(true)));
    	// 分のプルダウンを作成
    	targetMinute.setItems(FXCollections.observableArrayList(DateUtils.createTimeCombobox(false)));
	}
	
	/**
     * プロパティを初期化する
     */
    private void initProperty(){
    	custmerName.setCellValueFactory(new PropertyValueFactory<CustmerListData, String>("custmername"));
    	custmerTel.setCellValueFactory(new PropertyValueFactory<CustmerListData, String>("custmertel"));
    	reserveCount.setCellValueFactory(new PropertyValueFactory<CustmerListData, String>("reservecount"));
    	reserveTime.setCellValueFactory(new PropertyValueFactory<CustmerListData, String>("reservetime"));
    	reserveMenu.setCellValueFactory(new PropertyValueFactory<CustmerListData, String>("reservemenu"));
    	remarks.setCellValueFactory(new PropertyValueFactory<CustmerListData, String>("remarks"));
    }
    
	/**
	 * 顧客検索処理
	 * 
	 * @param evnet
	 * @throws HacClientSystemException
	 * @throws TsrvfwSystemException
	 */
	@FXML
	private void clickSearch(ActionEvent evnet) throws HacClientSystemException, TsrvfwSystemException {
		// 検索結果を初期化する
		initDisplayResultList();
		
		// 入力チェック実施
		boolean checkResult = this.validate();

		// 検索項目がない場合は処理終了
		if (!checkResult) {
			setAppMessage(this.getClass().getName() + ":00001", CommonConst.MESSAGE_TYPE_WARN);
			return;
		}
		// 顧客マスタを検索する
		List<Map<String, Object>> custmerMngNoList = searchCustmerDataLocal();
		if (custmerMngNoList.size() == 0) {
			setAppMessage(this.getClass().getName() + ":00003", CommonConst.MESSAGE_TYPE_ERROR);
			return;
		}
		displayResultList(custmerMngNoList);
	}
	
	/**
	 * 検索結果を初期化する
	 */
	private void initDisplayResultList(){
		ObservableList<CustmerListData> reserveDataList = FXCollections.observableArrayList();
		custmerList.setItems(reserveDataList);
	}
	
	/**
	 * 入力チェック
	 * @return
	 * @throws TsrvfwSystemException
	 */
	private boolean validate() throws TsrvfwSystemException {
		boolean checkResult = false;
		String userNameFirst = this.userNameFirst.getText();
		if (LogicUtils.isNotEmptyString(userNameFirst)) {
			checkResult = true;
		}
		String userNameLast = this.userNameLast.getText();
		if (LogicUtils.isNotEmptyString(userNameLast)) {
			checkResult = true;
		}
		String userTel = this.userTel.getText();
		if (LogicUtils.isNotEmptyString(userTel)) {
			checkResult = true;
		}
		if (this.targetReserveDate.getValue() != null){
			String targetReserveDate = this.targetReserveDate.getValue().toString();
			if (LogicUtils.isNotEmptyString(targetReserveDate)) {
				checkResult = true;
			}
		}
		return checkResult;
	}
	
	/**
	 * ローカルストレージから顧客情報を取得する
	 * @return 顧客Noリスト
	 * @throws HacClientSystemException
	 */
	private List<Map<String, Object>> searchCustmerDataLocal() throws HacClientSystemException{
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("custmer_name_first_read", this.userNameFirst.getText());
		condition.put("custmer_name_last_read", this.userNameLast.getText());
		condition.put("custmer_tel", this.userTel.getText());
		List<Map<String, Object>> resultList = LocalStrageCommon.getCustmerMstLocal(condition);
//		List<String> custmerMngNoList = new ArrayList<String>();
//		for(Map<String, Object> data : resultList){
//			custmerMngNoList.add(LogicUtils.getMapValueToString(data, "CUSTMER_MNG_NO"));
//		}
		return resultList;
	}
	
	/**
	 * 検索結果を設定する
	 * @param selectResult
	 * @throws TsrvfwSystemException 
	 */
	private void displayResultList(List<Map<String, Object>> selectResult) throws TsrvfwSystemException{
		ObservableList<CustmerListData> reserveDataList = FXCollections.observableArrayList();
		for(Map<String, Object> data : selectResult){
			reserveDataList.add(new CustmerListData(
					LogicUtils.getMapValueToString(data, "CUSTMER_NAME_FIRST_READ") + " " + LogicUtils.getMapValueToString(data, "CUSTMER_NAME_LAST_READ"),
					LogicUtils.getMapValueToString(data, "custmer_tel"),
					LogicUtils.getMapValueToString(data, ""),
					DateUtils.editUnixtimestampToDate(LogicUtils.getMapValueToInt(data, "reserve_date"), DateUtils.DATE_FORMAT_FULL),
					LogicUtils.getMapValueToString(data, "used_menu"),
					LogicUtils.getMapValueToString(data, "remarks")));
		}
		setAppMessage(this.getClass().getName() + ":00002", CommonConst.MESSAGE_TYPE_INFO, String.valueOf(reserveDataList.size()));
		custmerList.setItems(reserveDataList);
	}
}
