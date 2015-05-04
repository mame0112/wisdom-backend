package com.mame.wisdom.datastore.blobstore;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.mame.wisdom.util.DbgUtil;

public class WisdomBlobstore extends HttpServlet {

	private final static String TAG = WisdomBlobstore.class.getSimpleName();

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		DbgUtil.showLog(TAG, "doGet");
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		try {
			blobstoreService.serve(blobKey, res);
		} catch (IOException e) {
			DbgUtil.showLog(TAG, "IOException: " + e.getMessage());
		}

	}

}
