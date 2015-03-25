package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.jsonbuilder.UserStatusJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class UserStatusAction implements Action {

	private final static String TAG = UserStatusAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "UserStatusAction execute");

		JsonBuilder builder = new UserStatusJsonBuilder();

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String param = request.getParameter(WConstant.SERVLET_PARAMS);

		if (responseId != null && param != null) {

			builder.addResponseId(Integer.valueOf(responseId));

			JSONObject object = new JSONObject(param);
			long userId = object.getLong(JsonConstant.PARAM_USER_ID);
			int offset = object.getInt(JsonConstant.PARAM_CATEGORY_OFFSET);
			int limit = object.getInt(JsonConstant.PARAM_CATEGORY_LIMIT);
			if (userId != WConstant.NO_USER) {

				UserDataFacade userDatafacade = new UserDataFacade();
				WDUserData userData = userDatafacade.getUserData(userId);

				// Get wisdoms created by user.
				// TODO In the future, we should get "Liked" data
				WisdomFacade wisdomFacade = new WisdomFacade();
				List<WDWisdomData> wisdoms = wisdomFacade.getUserGeneratedData(
						userId, offset, limit);

//				((UserStatusJsonBuilder) builder).addResponseParamExtra(
//						userData, wisdoms);
				builder.addResponseParam(userData, wisdoms);
			} else {
				DbgUtil.showLog(TAG, "Illegal user Id -1");
				builder.addErrorMessage("Illegal user Id -1");
			}

		} else {
			DbgUtil.showLog(TAG, "responseId or param is null");
			builder.addErrorMessage("responseId or param is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
