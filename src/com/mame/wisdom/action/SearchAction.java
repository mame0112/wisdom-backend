package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.DAOFactory;
import com.mame.wisdom.datastore.WisdomDAO;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.SearchJsonBuilder;
import com.mame.wisdom.jsonbuilder.SigninJsonBuilder;
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
		builder.addResponseId(Integer.valueOf(responseId));

		if (responseId != null || searchParam != null) {

			DAOFactory factory = DAOFactory.getDAOFactory();
			WisdomDAO dao = factory.getWisdomDAO();
			List<WDWisdomData> foundItems = dao.searchWisdoms(searchParam, 0,
					WConstant.SEARCH_LIMIT_NUM);

			builder.addResponseParam(foundItems);
		} else {
			builder.addErrorMessage("parameter is null");
		}

		return result;
	}

}
