/**
 * [module]
 * ReserveEditController.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.common.BusinessLogicUtils;
import hac_client.common.DateUtils;
import hac_client.common.LocalStrageCommon;
import hac_client.common.LogicUtils;
import hac_client.component.remote.HttpClientRemoteExecuter;
import hac_client.component.strage.LocalStrageSqlMapExecuter;
import hac_client.exception.HacClientSystemException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Window;

/**
 * 予約入力コントローラ
 * @author sysusr1
 *
 */
public class ReserveEditController extends BaseController {
	
	/** 予約日 */
	@FXML
	private DatePicker reserveDate;
	
	/** 氏名（姓　かな） */
	@FXML
	private TextField reserveNameFirstKana;

	/** 氏名（名　かな） */
	@FXML
	private TextField reserveNameLastKana;

	/** 氏名（姓　漢字） */
	@FXML
	private TextField reserveNameFirstKanji;

	/** 氏名（名　漢字） */
	@FXML
	private TextField reserveNameLastKanji;

	/** 連絡先（TEL） */
	@FXML
	private TextField reserveTel;
	
	/** 指名 */
	@FXML
	private TextField designate;
	
	/** 予約時間（時間） */
	@FXML
	private ComboBox<String> reserveHour;

	/** 予約時間（分） */
	@FXML
	private ComboBox<String> reserveMinute;
	
	/** 備考 */
	@FXML
	private TextArea remarks;
	
	/** 年齢 */
	@FXML
	private TextField age;
	
	/** 性別（男） */
	@FXML
	private RadioButton genderm;

	/** 性別（女） */
	@FXML
	private RadioButton genderf;
	
	/** 性別のグループ */
	@FXML
	private ToggleGroup toggleGender;
	
	/** 選択された性別 */
	private int selectedGender;
	
	/**
	 * コンストラクタ
	 * @throws HacClientSystemException 
	 * 
	 */
	public ReserveEditController() throws HacClientSystemException{
	}
	
	/**
	 * 初期処理
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		toggleGender.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					@Override
					public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
						if (genderm.isSelected()){
							selectedGender = 1;
						}
						if (genderf.isSelected()){
							selectedGender = 2;
						}
					}
				});
		// 時間のプルダウンを作成
		reserveHour.setItems(FXCollections.observableArrayList(DateUtils.createTimeCombobox(true)));
    	// 分のプルダウンを作成
		reserveMinute.setItems(FXCollections.observableArrayList(DateUtils.createTimeCombobox(false)));
	}
	
	/**
	 * 登録ボタン押下処理
	 * @param evnet
	 * @throws HacClientSystemException 
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void clickRegist(ActionEvent evnet) throws HacClientSystemException{
		Map<String, Object> registInputData = new HashMap<String, Object>();
		
		registInputData.put("reserve_date", 
				DateUtils.editStringDateToUnixTimestamp(reserveDate.getValue().toString(), 
						reserveHour.getSelectionModel().getSelectedItem() + ":" + reserveMinute.getSelectionModel().getSelectedItem(),
						"-")
		);
		registInputData.put("hub_mng_no", LogicUtils.getMapValueToString(LocalStrageCommon.getHubMngNoForLocal().get(0), "HUB_MNG_NO"));
		registInputData.put("reserve_name_first_read", reserveNameFirstKana.getText());
		registInputData.put("custmer_name_first_read", reserveNameFirstKana.getText());
		registInputData.put("reserve_name_last_read", reserveNameLastKana.getText());
		registInputData.put("custmer_name_last_read", reserveNameLastKana.getText());
		registInputData.put("reserve_name_first", reserveNameFirstKanji.getText());
		registInputData.put("custmer_name_first", reserveNameFirstKanji.getText());
		registInputData.put("reserve_name_last", reserveNameLastKanji.getText());
		registInputData.put("custmer_name_last", reserveNameLastKanji.getText());
		registInputData.put("custmer_age", age.getText());
		registInputData.put("custmer_gender", selectedGender);
		registInputData.put("custmer_regist_date", DateUtils.getSystemDateUnixTimestamp());
//		registInputData.put("custmer_mng_no", null);
		registInputData.put("reserve_tel", reserveTel.getText());
		registInputData.put("custmer_tel", reserveTel.getText());
		registInputData.put("intr_custmer_mng_no", "0");
		registInputData.put("used_menu", "00001");
		registInputData.put("remarks", remarks.getText());
		registInputData.put("create_user", LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USER"));
		registInputData.put("update_user", LogicUtils.getMapValueToString(LocalStrageCommon.getExecuteUserForLocal(), "EXECUTE_USER"));
		
		// 顧客登録
		Map<String, Object> resultData = HttpClientRemoteExecuter.getInstance().execute(registInputData, "custmerRegistByHunMngNo");
		Map<String, Object> remoteResult = BusinessLogicUtils.getRemoteResultData(resultData);
		Map<String, Object> registDataMap = (Map<String, Object>)remoteResult.get("regist_data");
		String custmerMngNo = LogicUtils.getMapValueToString(registDataMap, "custmer_mng_no");
		registInputData.put("custmer_mng_no", custmerMngNo);
		LocalStrageSqlMapExecuter executer = LocalStrageSqlMapExecuter.getInstance();
		executer.insert(registInputData, "", "inert_custmer_detail");
		// 予約情報登録
		HttpClientRemoteExecuter.getInstance().execute(registInputData, "reserveRegistByHunMngNo");
		getWindow().hide();
	}
	
	/**
	 * 更新ボタン押下処理
	 * @param evnet
	 */
	@FXML
	private void clickUpdate(ActionEvent evnet){
		getWindow().hide();
	}

	/**
	 * 
	 * @return
	 */
	private Window getWindow() {
        return reserveDate.getScene().getWindow();
    }
}
