package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.DAOFactory;
import com.mame.wisdom.datastore.WisdomDAO;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.SearchJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class SearchAction implements Action {

	private final static String TAG = SearchAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "SearchAction execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String searchParam = request
				.getParameter(WConstant.SERVLET_WISDOM_SEARCH_PARAM);
		DbgUtil.showLog(TAG, "searchParam: " + searchParam);

		JsonBuilder builder = new SearchJsonBuilder();
		String result = null;

		if (responseId != null && searchParam != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			WisdomFacade facade = new WisdomFacade();
			List<WDWisdomData> results = facade.searchWisdom(searchParam);
			builder.addResponseParam(results);
		} else {
			builder.addErrorMessage("parameter is null");
		}

		result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
