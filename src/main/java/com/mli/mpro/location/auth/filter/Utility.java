package com.mli.mpro.location.auth.filter;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utility {
	private Utility() {

	}
	public static String getExceptionAsString(Exception exp) {
		StringWriter sw = new StringWriter();
		exp.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
