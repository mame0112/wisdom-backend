package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDUserDataBuilder;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
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

			String facebookName = object
					.getString(WConstant.SERVLET_FACEBOOK_NAME);
			String accessToken = object
					.getString(WConstant.SERVLET_FACEBOOK_ACCESS_TOKEN);
			String thumbnail = object
					.getString(WConstant.SERVLET_THUMBNAIL_URL);

			if (facebookName != null && accessToken != null) {
				UserDataFacade facade = new UserDataFacade();

				WDUserDataBuilder userDataBuilder = WDUserDataBuilder
						.createFrom(null);
				WDUserData data = userDataBuilder.setFacebookName(facebookName)
						.setThumbnail(thumbnail).getConstructedData();

				long newUserId = facade.createNewUserData(data);
				DbgUtil.showLog(TAG, "newUserId: " + newUserId);
				
				builder.addResponseParam(newUserId);
			} else {
				builder.addErrorMessage("facebookName or accessToken is null");
			}

		} else {
			builder.addErrorMessage("response id is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;

	}
}
