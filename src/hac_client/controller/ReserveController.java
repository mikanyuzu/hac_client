/**
 * [module]
 * ReserveController.java
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
import hac_client.component.beans.ReserveListData;
import hac_client.component.modal.ReserveModalDialog;
import hac_client.component.remote.HttpClientRemoteExecuter;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.ArrayList;
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
 * 予約確認画面コントローラ
 * 
 * @author sysusr1
 *
 */
public class ReserveController extends BaseController {

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
	private TableView<ReserveListData> reserveList;
	
	/** 予約名 */
	@FXML
	private TableColumn<ReserveListData, String> reserveName;

	/** 予約TEL */
	@FXML
	private TableColumn<ReserveListData, String> reserveTel;
	
	/** 指名 */
	@FXML
	private TableColumn<ReserveListData, String> designate;
	
	/** 予約時間 */
	@FXML
	private TableColumn<ReserveListData, String> reserveTime;
	
	/** 実施メニュー */
	@FXML
	private TableColumn<ReserveListData, String> reserveMenu;
	
	/** 備考 */
	@FXML
	private TableColumn<ReserveListData, String> remarks;
	
	/**
	 * コンストラクタ
	 * 
	 * @throws HacClientSystemException
	 */
	public ReserveController() throws HacClientSystemException {
		this.loadFxml("Reserve.fxml");
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
    	reserveName.setCellValueFactory(new PropertyValueFactory<ReserveListData, String>("reservename"));
    	reserveTel.setCellValueFactory(new PropertyValueFactory<ReserveListData, String>("reservetel"));
    	designate.setCellValueFactory(new PropertyValueFactory<ReserveListData, String>("designate"));
    	reserveTime.setCellValueFactory(new PropertyValueFactory<ReserveListData, String>("reservetime"));
    	reserveMenu.setCellValueFactory(new PropertyValueFactory<ReserveListData, String>("reservemenu"));
    	remarks.setCellValueFactory(new PropertyValueFactory<ReserveListData, String>("remarks"));
    }

	/**
	 * 検索するボタン押下処理
	 * 
	 * @param evnet
	 * @throws HacClientSystemException
	 * @throws TsrvfwSystemException 
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void clickSearch(ActionEvent evnet) throws HacClientSystemException, TsrvfwSystemException {
		
		// 検索結果を初期化する
		initDisplayResultList();
		
		// 入力チェック実施
		boolean checkResult = this.validate();
		
		// 検索項目がない場合は処理終了
		if (!checkResult){
			setAppMessage(this.getClass().getName() + ":00001", CommonConst.MESSAGE_TYPE_WARN);
			return;
		}
		// 予約検索条件情報
		Map<String, Object> searchInputMap = new HashMap<String, Object>();
		
		// 名前の入力我ある場合は顧客マスタを検索する
		if (LogicUtils.isNotEmptyString(this.userNameFirst.getText()) 
				|| LogicUtils.isNotEmptyString(this.userNameLast.getText())){
			List<String> custmerMngNoList = searchCustmerDataLocal();
			if (custmerMngNoList.size() == 0){
				setAppMessage(this.getClass().getName() + ":00003", CommonConst.MESSAGE_TYPE_ERROR);
				return;
			}
			searchInputMap.put("custmer_mng_no", custmerMngNoList);
		}
		
		// 店舗管理No
		searchInputMap.put("hub_mng_no", LogicUtils.getMapValueToString(LocalStrageCommon.getHubMngNoForLocal().get(0), "HUB_MNG_NO"));
		
		// TEL
		if (LogicUtils.isNotEmptyString(this.userTel.getText())){
			searchInputMap.put("reserve_tel", this.userTel.getText());
		}
		
		// 予約日
		if (this.targetReserveDate.getValue() != null 
				&& LogicUtils.isNotEmptyString(this.targetReserveDate.getValue().toString())){
			String targetHour = "00";
			String targetMinute = "00";
			// 予約時間（時）
			if (LogicUtils.isNotEmptyString(this.targetHour.getSelectionModel().getSelectedItem())){
				targetHour = this.targetHour.getSelectionModel().getSelectedItem();
			}
			// 予約時間（分）
			if (LogicUtils.isNotEmptyString(this.targetMinute.getSelectionModel().getSelectedItem())){
				targetMinute = this.targetMinute.getSelectionModel().getSelectedItem();
			}
			
			searchInputMap.put("reserve_date_start", DateUtils.editStringDateToUnixTimestamp(this.targetReserveDate.getValue().toString(), targetHour + ":" + targetMinute, "-"));
			searchInputMap.put("reserve_date_end", DateUtils.editStringDateToUnixTimestamp(this.targetReserveDate.getValue().toString(), "23:59", "-"));
		}
		
		// リモート検索実行
		Map<String, Object> searchResultMap = HttpClientRemoteExecuter.getInstance().execute(searchInputMap, "reserveLookupByHunMngNo");
		Map<String, Object> reserveLookupProcessResult = (Map<String, Object>) searchResultMap.get(CommonConst.KEY_REMOTE_PROCESS_RESULT);
		Map<String, Object> remoteResultDatas = (Map<String, Object>) reserveLookupProcessResult.get(CommonConst.KEY_REMOTE_RESULT_DATE);
		List<Map<String, Object>> selectResult = (List<Map<String, Object>>)remoteResultDatas.get("select_result");
		// 表示する
		displayResultList(selectResult);
	}
	
	/**
	 * ローカルストレージから顧客情報を取得する
	 * @return 顧客Noリスト
	 * @throws HacClientSystemException
	 */
	private List<String> searchCustmerDataLocal() throws HacClientSystemException{
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("custmer_name_first_read", this.userNameFirst.getText());
		condition.put("custmer_name_last_read", this.userNameLast.getText());
		List<Map<String, Object>> resultList = LocalStrageCommon.getCustmerMstLocal(condition);
		List<String> custmerMngNoList = new ArrayList<String>();
		for(Map<String, Object> data : resultList){
			custmerMngNoList.add(LogicUtils.getMapValueToString(data, "CUSTMER_MNG_NO"));
		}
		return custmerMngNoList;
	}
	
	/**
	 * 検索結果を設定する
	 * @param selectResult
	 * @throws TsrvfwSystemException 
	 */
	private void displayResultList(List<Map<String, Object>> selectResult) throws TsrvfwSystemException{
		ObservableList<ReserveListData> reserveDataList = FXCollections.observableArrayList();
		for(Map<String, Object> data : selectResult){
			reserveDataList.add(new ReserveListData(
					LogicUtils.getMapValueToString(data, "reserve_name_first_read") + " " + LogicUtils.getMapValueToString(data, "reserve_name_last_read"),
					LogicUtils.getMapValueToString(data, "reserve_tel"),
					LogicUtils.getMapValueToString(data, ""),
					DateUtils.editUnixtimestampToDate(LogicUtils.getMapValueToInt(data, "reserve_date"), DateUtils.DATE_FORMAT_FULL),
					LogicUtils.getMapValueToString(data, "used_menu"),
					LogicUtils.getMapValueToString(data, "remarks")));
		}
		setAppMessage(this.getClass().getName() + ":00002", CommonConst.MESSAGE_TYPE_INFO, String.valueOf(reserveDataList.size()));
		reserveList.setItems(reserveDataList);
	}
	
	/**
	 * 検索結果を初期化する
	 */
	private void initDisplayResultList(){
		ObservableList<ReserveListData> reserveDataList = FXCollections.observableArrayList();
		reserveList.setItems(reserveDataList);
	}
	
	/**
	 * 予約を追加するボタン
	 * @param evnet
	 * @throws HacClientSystemException
	 * @throws TsrvfwSystemException
	 */
	@FXML
	private void clickReserveRegist(ActionEvent evnet) throws HacClientSystemException, TsrvfwSystemException {
		ReserveModalDialog.displayModalDialog("");
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
}
