package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.jsonbuilder.PublicWisdomJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class FacebookSignupAction implements Action {

	private final static String TAG = FacebookSignupAction.class
			.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "FacebookSignupAction execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String param = request.getParameter(WConstant.SERVLET_PARAMS);
		JsonBuilder builder = new PublicWisdomJsonBuilder();

		if (responseId != null && param != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			JSONObject object = new JSONObject(param);
			// int start = object.getInt(JsonConstant.PARAM_CATEGORY_OFFSET);
			//
			// WisdomFacade facade = new WisdomFacade();
			// List<WDWisdomData> wisdoms = facade.getPopularWisdoms(start,
			// WConstant.PUBLIC_WISDOM_NUM);
			// builder.addResponseParam(wisdoms, start);

		} else {
			builder.addErrorMessage("response id is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;

	}
}
