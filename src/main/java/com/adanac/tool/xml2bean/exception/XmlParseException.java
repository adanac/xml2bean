package com.adanac.tool.xml2bean.exception;
/**
 * xml解析异常
 * @author adanac
 * @version 1.0.0
 * @since 1.0.0
 */
public class XmlParseException extends RuntimeException{

	private static final long serialVersionUID = 2L;

	public XmlParseException() {
		super();
	}

	public XmlParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlParseException(String message) {
		super(message);
	}

	public XmlParseException(Throwable cause) {
		super(cause);
	}

}
