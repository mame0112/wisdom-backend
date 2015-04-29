package com.mame.wisdom.action;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.exception.WisdomFacadeException;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.jsonbuilder.NewWisdomJsonBuilder;
import com.mame.wisdom.util.DatastoreUtil;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.UserPointOption;

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

		String params = null;
		Blob thumbnail = null;

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
					String value = Streams.asString(stream, "iso-8859-1");
					value = new String(value.getBytes("iso-8859-1"), "utf-8");
					// String value = Streams.asString(item.openStream());
					DbgUtil.showLog(TAG, "value: " + value);
					JSONObject object = new JSONObject(value);
					params = object.getString("data");
				} else {
					DbgUtil.showLog(TAG,
							"Got an uploaded file: " + item.getFieldName()
									+ ", name = " + item.getName());
					thumbnail = DatastoreUtil
							.transcodeInputStreamToBlob(stream);

					// BlobstoreService blobstoreService =
					// BlobstoreServiceFactory
					// .getBlobstoreService();
					// BlobKey blobKey = blobstoreService
					// .createGsBlobKey("/gs/bucket/object");
					// DbgUtil.showLog(TAG, "blobkey: " +
					// blobKey.getKeyString());
					// response.sendRedirect("/serve?blob-key="
					// + blobKey.getKeyString());

					// BlobstoreService blobstoreService =
					// BlobstoreServiceFactory
					// .getBlobstoreService();
					// String uploadUrl = blobstoreService
					// .createUploadUrl("/uploadUrl");

				}
			}

			// BlobstoreService blobstoreService = BlobstoreServiceFactory
			// .getBlobstoreService();
			// String uploadUrl =
			// blobstoreService.createUploadUrl("/uploadUrl");
			// response.sendRedirect("/upload");

		} catch (Exception e) {
			DbgUtil.showLog(TAG, "Exception: " + e.getMessage());
		}

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);

		if (responseId != null) {
			DbgUtil.showLog(TAG, "responseId: " + responseId);
		} else {
			DbgUtil.showLog(TAG, "responseId is null");
		}

		// String params = request.getParameter(WConstant.SERVLET_WISDOM_PARAM);

		JsonBuilder builder = new NewWisdomJsonBuilder();
		WisdomFacade facade = new WisdomFacade();

		String result = null;

		// TODO need to support for responseId
		if (params != null) {
			DbgUtil.showLog(TAG, "params: " + params);
			// TODO
			// builder.addResponseId(Integer.valueOf(responseId));
			try {
				WDWisdomData newWisdom = facade.createNewWisdom(params,
						thumbnail);
				// If wisdom is newly created
				if (newWisdom != null) {

					long userId = extractUserIdFromInputString(params);

					// Update user point
					// TODO This should be in transaction.
					UserDataFacade userFacade = new UserDataFacade();

					// Update user point
					long updatedPoint = userFacade
							.updateUserPoint(
									userId,
									UserPointOption
											.getPoint(UserPointOption.POINT_CREATE_WISDOM));

					builder.addResponseParam(newWisdom.getWisdomId(),
							updatedPoint);
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

	private long extractUserIdFromInputString(String input) {

		if (input != null) {
			JSONObject rootObject;
			try {
				rootObject = new JSONObject(input);
				return rootObject
						.getLong(JsonConstant.PARAM_WISDOM_CREATE_USER_ID);
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}

		}

		return WConstant.NO_USER;

	}
}
