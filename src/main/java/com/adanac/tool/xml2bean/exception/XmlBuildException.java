package com.adanac.tool.xml2bean.exception;
/**
 * xml构建异常
 * @author adanac
 * @version 1.0.0
 * @since 1.0.0
 */
public class XmlBuildException extends RuntimeException{

	private static final long serialVersionUID = 3L;

	public XmlBuildException() {
		super();
	}

	public XmlBuildException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlBuildException(String message) {
		super(message);
	}

	public XmlBuildException(Throwable cause) {
		super(cause);
	}

}
