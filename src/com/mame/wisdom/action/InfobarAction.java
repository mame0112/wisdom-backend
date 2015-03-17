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

	private final static int NUM_OF_WISDOM = 10;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String requestWisdomNum = request
				.getParameter(WConstant.SERVLET_WISDOM_REQUEST_NUM);

		DbgUtil.showLog(TAG, "responseId: " + responseId);

		int num = NUM_OF_WISDOM;
		if (requestWisdomNum != null) {
			num = Integer.valueOf(requestWisdomNum);
		}

		WisdomFacade facade = new WisdomFacade();
		// TODO
		List<WDWisdomData> data = facade.getPopularWisdoms(0, num);
		DbgUtil.showLog(TAG, "AA");

		JsonBuilder builder = new SigninJsonBuilder();
		builder.addResponseId(Integer.valueOf(responseId));
		DbgUtil.showLog(TAG, "BB");
		builder.addResponseParam(data);

		DbgUtil.showLog(TAG, "CC");

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
