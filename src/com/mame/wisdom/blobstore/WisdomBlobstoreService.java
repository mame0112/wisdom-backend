package com.mame.wisdom.blobstore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.mame.wisdom.util.DbgUtil;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class WisdomBlobstoreService extends HttpServlet {

	private final static String TAG = WisdomBlobstoreService.class
			.getSimpleName();

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		DbgUtil.showLog(TAG, "size: " + blobs.size());
		List<BlobKey> blobKeys = blobs.get("file");

	}

}
