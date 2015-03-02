package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.mock.MockJSONGenerator;
import com.mame.wisdom.util.DbgUtil;

public class SigninAction implements Action {

	private final static String TAG = "SigninAction";

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "execute");

		String result = MockJSONGenerator.generateMockNewWisdomJSON();

		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
