package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.util.DbgUtil;

public class UpdateWisdomAction implements Action {

	private final static String TAG = UpdateWisdomAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "UpdateWisdomAction execute");
		
		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		
		
		
		return null;
	}

}
