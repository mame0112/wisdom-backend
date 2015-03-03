package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.SigninJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class InfobarAction implements Action {

	private final static String TAG = "InfobarAction";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);

		WisdomFacade facade = new WisdomFacade();
		List<WDWisdomData> data = facade.getPopularWisdoms();

		JsonBuilder builder = new SigninJsonBuilder();
		builder.addResponseId(Integer.valueOf(responseId));
		builder.addResponseParam(data);

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
