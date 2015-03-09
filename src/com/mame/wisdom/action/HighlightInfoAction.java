package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.PublicWisdomJsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class HighlightInfoAction implements Action {

	private final static String TAG = HighlightInfoAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "HighlightInfoAction execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		JsonBuilder builder = new PublicWisdomJsonBuilder();

		if (responseId != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			WisdomFacade facade = new WisdomFacade();
			List<WDWisdomData> wisdoms = facade
					.getPopularWisdoms(WConstant.PUBLIC_WISDOM_NUM);
			builder.addResponseParam(wisdoms);

		} else {
			builder.addErrorMessage("response id is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
