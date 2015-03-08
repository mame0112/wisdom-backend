package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.NewWisdomJsonBuilder;
import com.mame.wisdom.util.DbgUtil;

public class CreateWisdomAction implements Action {

	private final static String TAG = CreateWisdomAction.class.getSimpleName();

	// We expect to get parameter from client side in below format.
	// (Meaning client side should not rely on backend datastore architecture
	// (especially entity part))
	// {
	// requestid:1,
	// params{
	// id: xxx,
	// title: "title",
	// createdUserId: 4,
	// lastUpdatedDate: 2015/01/01,
	// description: "description",
	// category: xxx,
	// subcategory: yyy,
	// tag: zzz,
	// thumbnail: xxx,
	// content:[ //JSONArray
	// {
	// entry: title text1 //JSONObject,
	// type: 0,
	// },
	// {
	// entry: desc text1
	// type: 1,
	// },
	// {
	// ]
	// },
	// version: 1.0
	// }
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "CreateWisdomAction execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String params = request.getParameter(WConstant.SERVLET_WISDOM_PARAM);

		JsonBuilder builder = new NewWisdomJsonBuilder();
		WisdomFacade facade = new WisdomFacade();

		String result = null;

		if (responseId != null && params != null) {
			DbgUtil.showLog(TAG, "params: " + params);
			builder.addResponseId(Integer.valueOf(responseId));
			facade.createNewWisdom(params);
		} else {
			DbgUtil.showLog(TAG, "responseId or content is null");
			builder.addErrorMessage("responseId or content is null");
		}

		result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
