package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.util.DbgUtil;

public class SearchAction implements Action {

	private final static String TAG = SearchAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "SearchAction execute");

		String searchParam = request.getParameter("searchParam");
		DbgUtil.showLog(TAG, "searchParam: " + searchParam);

		return null;
	}

}
