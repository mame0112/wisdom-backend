package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonConstant;
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
		String param = request.getParameter(WConstant.SERVLET_PARAMS);
		// String requestWisdomNum = request
		// .getParameter(WConstant.SERVLET_WISDOM_REQUEST_NUM);

		JsonBuilder builder = new SigninJsonBuilder();

		if (responseId != null && param != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			JSONObject object = new JSONObject(param);
			int offset = object.getInt(JsonConstant.PARAM_CATEGORY_OFFSET);

			WisdomFacade facade = new WisdomFacade();
			List<WDWisdomData> data = facade.getPopularWisdoms(offset,
					WConstant.PUBLIC_WISDOM_NUM);
			builder.addResponseParam(data);
		} else {
			builder.addErrorMessage("responseId or param is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
