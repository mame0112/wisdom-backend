package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.util.DbgUtil;

public class HighlightInfoAction implements Action {

	private final static String TAG = HighlightInfoAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "HighlightInfoAction execute");
		return null;
	}

}
