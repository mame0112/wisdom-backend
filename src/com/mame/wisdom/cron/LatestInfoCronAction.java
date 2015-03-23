package com.mame.wisdom.cron;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.util.DbgUtil;

public class LatestInfoCronAction implements CronAction {

	private final static String TAG = LatestInfoCronAction.class
			.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "LatestInfoCronAction  execute");

		WisdomFacade facade = new WisdomFacade();

		// Refresh old data that is stored on memcache
		facade.refreshOldWisdomData();
		return null;
	}

}
