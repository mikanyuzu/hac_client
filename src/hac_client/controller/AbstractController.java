/**
 * [module]
 * AbstractController.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.controller;

import hac_client.application.HacClientMainApplication;
import hac_client.common.CommonLogger;
import hac_client.exception.HacClientSystemException;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;

/**
 * コントローラ基底クラス
 * @author sysusr1
 */
public abstract class AbstractController extends AnchorPane implements Initializable{
    
	/** ユーザアカウント名 */
	@FXML
	protected Label userAccount;
	
	/** ログイン時間 */
	@FXML
	protected Label loginTime;
	
    /** ロガー */
    protected Logger log = CommonLogger.getLogger(this.getClass().getName());
        
    /**
     * 画面遷移処理
     * @param controllerClassName 次画面コントローラクラス名完全修飾名 
     * @throws HacClientSystemException 
     */
    protected void sendPage(String controllerClassName) throws HacClientSystemException{
        HacClientMainApplication.getInstance().sendPage("", controllerClassName);
    }
    
    /**
     * 画面定義ファイルのロード
     * @param fxmlFileName 画面定義ファイル
     * @throws HacClientSystemException 
     */
    protected  FXMLLoader loadFxml(String fxmlFileName) throws HacClientSystemException {

        FXMLLoader fxmlLoader = new FXMLLoader(HacClientMainApplication.class.getResource(fxmlFileName));
        fxmlLoader.setRoot(this);
        
        // 自分自身をコントロールとして設定
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
            
            return fxmlLoader;
        } catch (IOException e) {
            throw new HacClientSystemException(e);
        }
    }
}
