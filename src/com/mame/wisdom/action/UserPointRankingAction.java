package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.datastore.UserDAO;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.jsonbuilder.UserRankingJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class UserPointRankingAction implements Action {

	private final static String TAG = UserPointRankingAction.class
			.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String params = request.getParameter(WConstant.SERVLET_PARAMS);
		UserRankingJsonBuilder builder = new UserRankingJsonBuilder();

		if (responseId != null && params != null) {
			builder.addResponseId(Integer.valueOf(responseId));
			UserDataFacade facade = new UserDataFacade();

			JSONObject object = new JSONObject(params);
			int limit = (int) object
					.getInt(JsonConstant.PARAM_USER_RANKING_NUM);
			DbgUtil.showLog(TAG, "limit: " + limit);

			List<WDUserData> users = facade.getUserPointRankingList(limit);

			builder.addResponseParam(users);
		} else {
			builder.addErrorMessage("param is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;

	}
}
