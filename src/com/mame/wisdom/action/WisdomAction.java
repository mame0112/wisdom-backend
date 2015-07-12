package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.WisdomJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class WisdomAction implements Action {

	private final static String TAG = WisdomAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "WisdomAction execute");

		JsonBuilder builder = new WisdomJsonBuilder();

		String responseId = "1";
		// String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String wisdomId = request.getParameter(WConstant.SERVLET_WISDOM_ID);

		DbgUtil.showLog(TAG, "input: " + responseId + " / " + wisdomId);

		if (responseId != null && wisdomId != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			WisdomFacade facade = new WisdomFacade();
			WDWisdomData data = facade
					.findWisdomById(Integer.valueOf(wisdomId));

			if (data != null) {
				builder.addResponseParam(data);
			} else {
				DbgUtil.showLog(TAG, "Response param is null");
				builder.addErrorMessage("Response param is null");
			}

		} else {
			DbgUtil.showLog(TAG, "Response id or wisdom id is null");
			builder.addErrorMessage("Response id or wisdom id is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
