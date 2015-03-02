package com.mame.wisdom;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.action.Action;
import com.mame.wisdom.action.ActionFactory;
import com.mame.wisdom.util.DbgUtil;

public class WisdomControllerServlet extends HttpServlet {

	private final static String TAG = "WisdomControllerServlet";

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		DbgUtil.showLog(TAG, "service:" + request.getPathInfo());
		Action action = ActionFactory.getAction(request);

		String result = null;
		try {
			result = action.execute(request, response);
			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			DbgUtil.showLog(TAG, "Exception: " + e.getMessage());
			response.setStatus(405);
		}

	}

}
