/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hac_client.common;

import org.apache.log4j.Logger;

/**
 * ログ出力クラス
 * @author sysusr1
 */
public class CommonLogger  extends Logger{
    
    /**
     * コンストラクタ
     * @param name クラス名
     */
    protected CommonLogger(String name) {
        super(name);
    }
    
    /**
     * infoレベルログ出力
     *
     * @param className 実行クラス名称
     * @param message ログメッセージ
     */
    public void info(String className, String message){
        this.info(className, message);
    }
}
