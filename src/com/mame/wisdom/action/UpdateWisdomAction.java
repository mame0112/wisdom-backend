package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.jsonbuilder.UpdateWisdomJsonBuilder;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;
import com.mame.wisdom.util.UserPointOption;

public class UpdateWisdomAction implements Action {

	private final static String TAG = UpdateWisdomAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "UpdateWisdomAction execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String param = request.getParameter(WConstant.SERVLET_PARAMS);

		UpdateWisdomJsonBuilder builder = new UpdateWisdomJsonBuilder();

		if (param != null && responseId != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			DbgUtil.showLog(TAG, "param: " + param);

			JSONObject obj = new JSONObject(param);
			String wisdom = obj.getString("wisdom");
			long userId = obj.getLong("userId");
			// JSONObject object = new JSONObject(param);

			WDWisdomData data = JsonParseUtil.createWisdomDataFromJson(wisdom);
			// long id = (long) object
			// .getLong(JsonConstant.PARAM_CATEGORY_WISDOM_ID);
			if (data != null) {
				WisdomFacade facade = new WisdomFacade();
				if (facade.updateWisdom(data)) {

					UserDataFacade userFacade = new UserDataFacade();

					// Update user point
					long updatedPoint = userFacade
							.updateUserStatus(
									userId,
									UserPointOption
											.getPoint(UserPointOption.POINT_MODIFY_WISDOM),
									WConstant.NO_WISDOM, WConstant.NO_WISDOM);
					builder.addResponseParam(data.getWisdomId(), updatedPoint);
				} else {
					DbgUtil.showLog(TAG, "Failed to update wisdom");
					builder.addErrorMessage("Failed to update wisdom");
				}
			} else {
				DbgUtil.showLog(TAG, "Illegal input wisdom data format");
				builder.addErrorMessage("Illegal input wisdom data format");
			}

			// int id = object.getInt(arg0);
		} else {
			DbgUtil.showLog(TAG, "param is null");
			builder.addErrorMessage("responseId or param is null");
		}

		String result = builder.getResultJson();

		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
