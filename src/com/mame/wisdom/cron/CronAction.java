package com.mame.wisdom.cron;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CronAction {
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
