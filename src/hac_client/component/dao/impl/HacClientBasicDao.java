/**
 * [module]
 * HacClientBasicDao.java
 *
 * Copyright (c) 2014 M.Tsubaki
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package hac_client.component.dao.impl;

import hac_client.component.dao.AbstractDao;

import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 基本処理Daoクラス
 * @author sysusr1
 *
 */
public class HacClientBasicDao extends AbstractDao{

	/**
	 * コンストラクタ
	 * @param sqlMapClient
	 */
	public HacClientBasicDao(SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 基本検索処理
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<Map<String, Object>> select(Map<String, Object> inputMap,
			String target) {
		// 検索条件保持オブジェクト
		Object conditions = inputMap;
		// 検索条件オブジェクトに指定がある場合
		if (inputMap != null && inputMap.containsKey("conditionDtoClass")) {
			conditions = inputMap.get("conditionDtoClass");
		}
		return (List<Map<String, Object>>) getSqlMapClientTemplate()
				.queryForList(target, conditions);
	}

	/**
	 * 基本登録処理
	 */
	@Override
	protected void insert(Map<String, Object> inputMap, String target) {
		getSqlMapClientTemplate().insert(target, inputMap);
	}

	/**
	 * 基本更新処理
	 */
	@Override
	protected void update(Map<String, Object> inputMap, String target) {
		getSqlMapClientTemplate().update(target, inputMap);
	}

	/**
	 * 基本削除処理
	 */
	@Override
	protected void delete(Map<String, Object> inputMap, String target) {
		getSqlMapClientTemplate().delete(target, inputMap);
	}

	/**
	 * 基本テーブル作成処理
	 */
	@Override
	protected void create(Map<String, Object> inputMap, String target) {
		getSqlMapClientTemplate().update(target, inputMap);
	}

}
