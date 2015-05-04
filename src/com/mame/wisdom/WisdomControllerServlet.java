package com.mame.wisdom;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.action.Action;
import com.mame.wisdom.action.ActionFactory;
import com.mame.wisdom.action.DebugAction;
import com.mame.wisdom.action.InitializeAction;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.exception.ActionException;
import com.mame.wisdom.util.DbgUtil;

public class WisdomControllerServlet extends HttpServlet {

	private final static String TAG = WisdomControllerServlet.class
			.getSimpleName();

	@Override
	public void init() {
		InitializeAction action = new InitializeAction();
		action.initializeService();
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		try {
			Action action = ActionFactory.getAction(request);
		} catch (ActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		DbgUtil.showLog(TAG, "service:" + request.getPathInfo());
		String result = null;
		Action action;
		try {
			action = ActionFactory.getAction(request);

			// If passed action is expected
			if (action != null) {
				try {
					result = action.execute(request, response);
					response.setStatus(200);
				} catch (Exception e) {
					DbgUtil.showLog(TAG, "Exception: " + e.getMessage());
					response.setStatus(405);
				}
			} else {
				// If passed action is not expected (meaning it is not defined
				// in ActionFactory)
				response.setStatus(404);
			}
		} catch (ActionException e1) {
			response.setStatus(400);
		}

		// TODO need to consider authentication (401)

		// TODO Need to remove this part
		// if (WConstant.IS_LOCAL) {
		// DebugAction debugAction = new DebugAction();
		// try {
		// debugAction.execute(request, response);
		// } catch (Exception e) {
		// DbgUtil.showLog(TAG, "Exception: " + e.getMessage());
		// }
		// }

		if (result != null) {
			try {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(result);
			} catch (IOException e) {
				DbgUtil.showLog(TAG, "IOException: " + e.getMessage());
				response.setStatus(500);
			}
		} else {
			response.setStatus(500);
		}

	}

	private void errorHandling(HttpServletResponse response) {

	}
}
