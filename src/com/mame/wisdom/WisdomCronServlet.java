package com.mame.wisdom;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.action.ActionFactory;
import com.mame.wisdom.cron.CronAction;
import com.mame.wisdom.cron.CronActionFactory;
import com.mame.wisdom.exception.ActionException;
import com.mame.wisdom.exception.CronActionException;
import com.mame.wisdom.util.DbgUtil;

public class WisdomCronServlet extends HttpServlet {

	private final static String TAG = WisdomCronServlet.class.getSimpleName();

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		DbgUtil.showLog(TAG, "service:" + request.getPathInfo());
		String result = null;
		CronAction action = null;

		try {
			action = CronActionFactory.getCronAction(request);

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
		} catch (CronActionException e1) {
			response.setStatus(400);
		}

	}

}
