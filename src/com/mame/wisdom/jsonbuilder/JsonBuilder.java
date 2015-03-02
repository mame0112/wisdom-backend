package com.mame.wisdom.jsonbuilder;

import com.mame.wisdom.exception.JSONBuilderException;

public abstract class JsonBuilder {

	private final static String TAG = "JsonBuilder";

	public abstract void addResponseId(int id) throws JSONBuilderException;

	protected abstract void addVersion(String version)
			throws JSONBuilderException;

	public abstract void addResponseParam(Object param)
			throws JSONBuilderException;

	public abstract void addErrorMessage(String message)
			throws JSONBuilderException;

	public abstract String getResultJson() throws JSONBuilderException;

	protected String VERSION = "1.0";

}
