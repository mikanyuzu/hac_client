/**
 * [module]
 * ModalDialogCommon.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.common;

import hac_client.application.HacClientMainApplication;
import hac_client.controller.CommonModalDialogContorller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.hac.exception.HACSystemException;

/**
 * モーダルダイアログ共通クラス
 * @author sysusr1
 *
 */
public class ModalDialogCommon {

	public static final int SELECT_OPTION_OK = 1;
	
	public static final int SELECT_OPTION_CANCEL = 0;
	
	private static FXMLLoader loader;
	
	/**
	 * 汎用モーダルダイアログを表示する
	 * @param dialogMessage
	 * @throws HACSystemException
	 */
	public static int displayModalDialog(String dialogMessage) throws HACSystemException {
		try {
			loader = new FXMLLoader(HacClientMainApplication.class.getResource("CommonModalDialog.fxml"));
			loader.load();
			Parent root = loader.getRoot();
			CommonModalDialogContorller controller = loader.getController();
			controller.setDialogMessage(dialogMessage);
			Scene scene = new Scene(root);
			Stage confirmDialog = new Stage(StageStyle.UTILITY);
			confirmDialog.setScene(scene);
			confirmDialog.initModality(Modality.WINDOW_MODAL);
			confirmDialog.setResizable(false);
			confirmDialog.setTitle("確認");
			confirmDialog.showAndWait();
			return controller.getSelectOption();
		} catch (IOException e) {
			e.printStackTrace();
			throw new HACSystemException(e.getMessage());
		}
		
	}
}
