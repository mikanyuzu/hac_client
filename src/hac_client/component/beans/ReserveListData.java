/**
 * [module]
 * ReserveData.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.beans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 当日予約データ保持クラス
 * @author sysusr1
 *
 */
public class ReserveListData {

	/** 予約名 */
	private StringProperty reserveName;
	
	/** 予約TEL */
	private StringProperty reserveTel;
	
	/** 指名 */
	private StringProperty designate;
	
	/** 予約時間 */
	private StringProperty reserveTime;
	
	/** 予約メニュー */
	private StringProperty reserveMenu;
	
	/** 備考 */
	private StringProperty remarks;
	
	/**
	 * コンストラクタ
	 * @param reserveName
	 * @param reserveTel
	 * @param designate
	 * @param reserveTime
	 * @param reserveMenu
	 * @param remarks
	 */
	public ReserveListData(String reserveName, 
			String reserveTel,
			String designate, 
			String reserveTime, 
			String reserveMenu,
			String remarks) {
		this.reserveName = new SimpleStringProperty(reserveName);
		this.reserveTel = new SimpleStringProperty(reserveTel);
		this.designate = new SimpleStringProperty(designate);
		this.reserveTime = new SimpleStringProperty(reserveTime);
		this.reserveMenu = new SimpleStringProperty(reserveMenu);
		this.remarks = new SimpleStringProperty(remarks);
	}

	/**
	 * 予約名を取得する
	 * @return 予約名
	 */
	public StringProperty reservenameProperty() {
		return reserveName;
	}

	/**
	 * 予約TELを取得する
	 * @return 予約TEL
	 */
	public StringProperty reservetelProperty() {
		return reserveTel;
	}

	/**
	 * 指名を取得する
	 * @return 指名
	 */
	public StringProperty designateProperty() {
		return designate;
	}

	/**
	 * 予約時間を取得する
	 * @return 予約時間
	 */
	public StringProperty reservetimeProperty() {
		return reserveTime;
	}

	/**
	 * 予約メニューを取得する
	 * @return 予約メニュー
	 */
	public StringProperty reservemenuProperty() {
		return reserveMenu;
	}

	/**
	 * 備考を取得する
	 * @return 備考
	 */
	public StringProperty remarksProperty() {
		return remarks;
	}
}
