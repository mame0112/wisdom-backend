package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.SigninJsonBuilder;
import com.mame.wisdom.mock.MockJSONGenerator;
import com.mame.wisdom.util.DbgUtil;

public class SigninAction implements Action {

	private final static String TAG = "SigninAction";

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String twitterName = request
				.getParameter(WConstant.SERVLET_TWITTER_NAME);

		UserDataFacade facade = new UserDataFacade();
		long userId = WConstant.NO_USER;

		JsonBuilder builder = new SigninJsonBuilder();
		DbgUtil.showLog(TAG, "A");
		String result = null;
		builder.addResponseId(Integer.valueOf(responseId));
		DbgUtil.showLog(TAG, "B");
		if (twitterName != null) {
			WDUserData data = facade.getUserDataByTwitterName(twitterName);
			builder.addResponseParam(data);
			DbgUtil.showLog(TAG, "C");
		} else {
			builder.addErrorMessage("parameter is null");
		}

		result = builder.getResultJson();

		// String result = MockJSONGenerator.generateMockNewWisdomJSON();

		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
