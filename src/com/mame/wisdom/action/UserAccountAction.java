package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDUserDataBuilder;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.jsonbuilder.UserAccountJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class UserAccountAction implements Action {

	private final static String TAG = UserAccountAction.class.getSimpleName();

	private final static int NUM_OF_WISDOM = 10;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "UserAccountAction execute");

		UserAccountJsonBuilder builder = new UserAccountJsonBuilder();

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String param = request.getParameter(WConstant.SERVLET_PARAMS);

		if (responseId != null || param != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			try {
				JSONObject object = new JSONObject(param);
				String userName = object
						.getString(JsonConstant.PARAM_ACCOUNT_USER_NAME);
				String password = object
						.getString(JsonConstant.PARAM_ACCOUNT_PASSWORD);
				String mailAddress = object
						.getString(JsonConstant.PARAM_ACCOUNT_MAIL_ADDRESS);
				if (userName != null && password != null && mailAddress != null) {
					DbgUtil.showLog(TAG, "userName: " + userName
							+ " password: " + password + " mailAddress: "
							+ mailAddress);
					UserDataFacade facade = new UserDataFacade();
					WDUserData data = facade.getUserDataByUserName(userName);

					// If given user name is not occupied by another user
					if (data == null) {
						WDUserDataBuilder dataBuilder = WDUserDataBuilder
								.createFrom(null);
						dataBuilder.setUserId(WConstant.NO_USER)
								.setUsername(userName).setPassword(password)
								.setMailAddress(mailAddress);
						facade.createNewUserData(dataBuilder
								.getConstructedData());
						builder.addResponseParam(dataBuilder
								.getConstructedData().getUserId());
					} else {
						// If given user name is already occupied by another
						// user
						builder.addResponseParam(WConstant.NO_USER);
					}

				} else {
					builder.addErrorMessage("Illegal argument. user name, password or mail address is null");
				}
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
				builder.addErrorMessage("Illegal argument");
			}

		} else {
			builder.addErrorMessage("responseId or param is null");
		}

		String result = builder.getResultJson();

		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
