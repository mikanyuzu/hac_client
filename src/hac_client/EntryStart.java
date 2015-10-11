/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hac_client;

import hac_client.application.HacClientMainApplication;

/**
 * エントリポイントクラス
 * @author sysusr1
 */
public class EntryStart {
    
	/**
	 * 業務システム起動処理
	 * @param args
	 */
    public static void main(String[] args){
        HacClientMainApplication.launch(HacClientMainApplication.class, args);
    }
}
