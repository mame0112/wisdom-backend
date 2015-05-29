package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.UserDAO;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.jsonbuilder.MessageLikeJsonBuilder;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.UserPointOption;

public class MessageLikeAction implements Action {

	private final static String TAG = MessageLikeAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "MessageLikeAction execute");

		JsonBuilder builder = new MessageLikeJsonBuilder();

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String param = request.getParameter(WConstant.SERVLET_PARAMS);

		if (responseId != null && param != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			JSONObject object = new JSONObject(param);
			long userId = object.getLong(JsonConstant.PARAM_USER_ID);
			long wisdomId = object.getLong(JsonConstant.PARAM_WISDOM_ID);
			long messageId = object.getLong(JsonConstant.PARAM_WISDOM_ITEM_ID);

			WisdomFacade facade = new WisdomFacade();
			boolean result = facade.updateMessageLikeNum(wisdomId, messageId);

			if (result) {
				// If user is already log in
				if (userId != WConstant.NO_USER) {
					UserDataFacade userFacade = new UserDataFacade();
					long updatedPoint = userFacade
							.updateUserStatus(
									userId,
									UserPointOption
											.getPoint(UserPointOption.POINT_LIKE_WISDOM),
									WConstant.NO_WISDOM, wisdomId);
					builder.addResponseParam(updatedPoint);
				} else {
					// If user is not log in
					builder.addResponseParam(WConstant.NO_USER);
				}
			} else {
				builder.addErrorMessage("Failed to update the number of like");
			}

		} else {
			builder.addErrorMessage("parameter is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
