/**
 * [module]
 * MenuController.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import org.hac.exception.HACSystemException;
import org.tsrvfw.exception.TsrvfwSystemException;

/**
 * メインメニューコントローラクラス
 * @author sysusr1
 */
public class MenuController  extends BaseController {
	
	/** 当日予約一覧 */
	@FXML
	private TableView<ReserveListData> reserveList;
	
	/**  */
	@FXML
	private TableColumn<ReserveListData, String> reserveName;

	/**  */
	@FXML
	private TableColumn<ReserveListData, String> reserveTel;
	
	/**  */
	@FXML
	private TableColumn<ReserveListData, String> designate;
	
	/**  */
	@FXML
	private TableColumn<ReserveListData, String> reserveTime;
	
	/**  */
	@FXML
	private TableColumn<ReserveListData, String> reserveMenu;
	
	/**  */
	@FXML
	private TableColumn<ReserveListData, String> remarks;
	
	/**  */
	@FXML
	private ContextMenu contextMenu;
	
	/**
	 * コンストラクタ
	 * @throws HacClientSystemException
	 */
    public MenuController() throws HacClientSystemException{
        this.loadFxml("MainMenu.fxml");
    }
    
    /**
     * 初期処理
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// プロパティを初期化する
    	initProperty();
    	
    	// 予約一覧を取得する
		try {
			ObservableList<ReserveListData> reserveDataList = getReserveList();
			reserveList.setItems(reserveDataList);
			setAppMessage(this.getClass().getName() + ":00001", CommonConst.MESSAGE_TYPE_INFO);
		} catch (HacClientSystemException e) {
			e.printStackTrace();
		} catch (TsrvfwSystemException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 予約情報取得
     * @return 予約情報
     * @throws HacClientSystemException 想定外例外が発生した場合
     */
    @SuppressWarnings("unchecked")
	private ObservableList<ReserveListData> getReserveList() throws HacClientSystemException{
    	ObservableList<ReserveListData> reserveDataList = FXCollections.observableArrayList();
    	Map<String, Object> inputReserveHubData = new HashMap<String, Object>();
    	inputReserveHubData.put("hub_mng_no", LogicUtils.getMapValueToString(LocalStrageCommon.getHubMngNoForLocal().get(0), "HUB_MNG_NO"));
    	String nowDate = DateUtils.getFormatDate(DateUtils.getSystemDate(), "yyyy-MM-dd");
    	inputReserveHubData.put("reserve_date_start", DateUtils.editStringDateToUnixTimestamp(nowDate, "-", false));
    	inputReserveHubData.put("reserve_date_end", DateUtils.editStringDateToUnixTimestamp(nowDate, "-", true));
    	Map<String, Object> remoteReserveLookupLookupResult = HttpClientRemoteExecuter.getInstance().execute(inputReserveHubData, "reserveLookupByHunMngNo");
    	Map<String, Object> reserveLookupProcessResult = (Map<String, Object>) remoteReserveLookupLookupResult.get(CommonConst.KEY_REMOTE_PROCESS_RESULT);
		Map<String, Object> remoteResultDatas = (Map<String, Object>) reserveLookupProcessResult.get(CommonConst.KEY_REMOTE_RESULT_DATE);
		List<Map<String, Object>> selectResult = (List<Map<String, Object>>)remoteResultDatas.get("select_result");
		for(Map<String, Object> data : selectResult){
			reserveDataList.add(new ReserveListData(
					LogicUtils.getMapValueToString(data, "reserve_name_first_read") + " " + LogicUtils.getMapValueToString(data, "reserve_name_last_read"),
					LogicUtils.getMapValueToString(data, "reserve_tel"),
					LogicUtils.getMapValueToString(data, ""),
					DateUtils.editUnixtimestampToDate(LogicUtils.getMapValueToInt(data, "reserve_date"), DateUtils.DATE_FORMAT_FULL),
					LogicUtils.getMapValueToString(data, "used_menu"),
					LogicUtils.getMapValueToString(data, "remarks")));
		}
    	return reserveDataList;
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
     * 本日の予約を追加するボタン押下処理
     * @param event
     * @throws HacClientSystemException 
     * @throws HACSystemException 
     */
    @FXML
    private void clickReserveToday(ActionEvent event) throws HacClientSystemException, HACSystemException{
    	ReserveModalDialog.displayModalDialog("");
    	ObservableList<ReserveListData> reserveDataList = getReserveList();
		reserveList.setItems(reserveDataList);
    }
    
    @FXML
    private void showMenu(ContextMenuEvent event){
    	Popup popup = new Popup();

        VBox content = new VBox();
        Button b = new Button("Click Me!");
        b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			} });
        content.getChildren().addAll(b);

        popup.getContent().add(content);
        popup.setAutoHide(true);
        popup.show(reserveList.getScene().getWindow());
    }
}
