package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.jsonbuilder.ContactJsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.mail.WisdomMailSender;
import com.mame.wisdom.util.DbgUtil;

public class ContactAction implements Action {

	private final static String TAG = ContactAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "ContactAction execute");

		ContactJsonBuilder builder = new ContactJsonBuilder();

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String params = request.getParameter(WConstant.SERVLET_PARAMS);

		if (responseId != null && params != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			JSONObject object = new JSONObject(params);
			String name = object.getString(JsonConstant.PARAM_CONTACT_NAME);
			String email = object.getString(JsonConstant.PARAM_CONTACT_EMAIL);
			String message = object
					.getString(JsonConstant.PARAM_CONTACT_MESSAGE);
			DbgUtil.showLog(TAG, "name: " + name + " email: " + email
					+ " message: " + message);
			boolean result = WisdomMailSender.sendContactMail(name, email,
					message);
			builder.addResponseParam(result);

		} else {
			builder.addErrorMessage("responseId or params is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
