package com.mame.wisdom.action;

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.exception.WisdomFacadeException;
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

		try {
			ServletFileUpload upload = new ServletFileUpload();
			response.setContentType("text/plain");

			FileItemIterator iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();

				if (item.isFormField()) {
					DbgUtil.showLog(TAG,
							"Got a form field: " + item.getFieldName());
					byte[] bytes = IOUtils.toByteArray(stream);
					DbgUtil.showLog(TAG, "bytes.length: " + bytes.length);
				} else {
					DbgUtil.showLog(TAG,
							"Got an uploaded file: " + item.getFieldName()
									+ ", name = " + item.getName());
					byte[] bytes = IOUtils.toByteArray(stream);
					DbgUtil.showLog(TAG, "bytes.length: " + bytes.length);

					// You now have the filename (item.getName() and the
					// contents (which you can read from stream). Here we just
					// print them back out to the servlet output stream, but you
					// will probably want to do something more interesting (for
					// example, wrap them in a Blob and commit them to the
					// datastore).
					// int len;
					// byte[] buffer = new byte[8192];
					// while ((len = stream.read(buffer, 0, buffer.length)) !=
					// -1) {
					// response.getOutputStream().write(buffer, 0, len);
					// }
				}
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);

		if (responseId != null) {
			DbgUtil.showLog(TAG, "responseId: " + responseId);
		} else {
			DbgUtil.showLog(TAG, "responseId is null");
		}

		String params = request.getParameter(WConstant.SERVLET_WISDOM_PARAM);

		JsonBuilder builder = new NewWisdomJsonBuilder();
		WisdomFacade facade = new WisdomFacade();

		String result = null;

		if (responseId != null && params != null) {
			DbgUtil.showLog(TAG, "params: " + params);
			builder.addResponseId(Integer.valueOf(responseId));
			try {
				WDWisdomData newWisdom = facade.createNewWisdom(params);
				// If wisdom is newly created
				if (newWisdom != null) {
					builder.addResponseParam(newWisdom.getWisdomId());
				} else {
					// If new wisdom creation failed
					builder.addResponseParam(WConstant.NO_WISDOM);
				}

			} catch (WisdomFacadeException e) {
				DbgUtil.showLog(TAG, "WisdomFacadeException: " + e.getMessage());
				builder.addErrorMessage(e.getMessage());
			}

		} else {
			DbgUtil.showLog(TAG, "responseId or content is null");
			builder.addErrorMessage("responseId or content is null");
		}

		result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
