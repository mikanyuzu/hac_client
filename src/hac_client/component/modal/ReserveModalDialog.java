/**
 * [module]
 * ReserveModalDialog.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.modal;

import hac_client.application.HacClientMainApplication;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.hac.exception.HACSystemException;

public class ReserveModalDialog {

	public static final int SELECT_OPTION_OK = 1;

	public static final int SELECT_OPTION_CANCEL = 0;

	private static FXMLLoader loader;

	/**
	 * 予約入力モーダルダイアログを表示する
	 * 
	 * @param dialogMessage
	 * @throws HACSystemException
	 */
	public static int displayModalDialog(String dialogMessage)
			throws HACSystemException {
		try {
			loader = new FXMLLoader(
					HacClientMainApplication.class
							.getResource("ReserveEdit.fxml"));
			loader.load();
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			Stage confirmDialog = new Stage(StageStyle.UTILITY);
			confirmDialog.setScene(scene);
			confirmDialog.initModality(Modality.APPLICATION_MODAL);
			confirmDialog.setResizable(false);
			confirmDialog.setTitle("予約情報編集");
			confirmDialog.showAndWait();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			throw new HACSystemException(e.getMessage());
		}

	}
}
