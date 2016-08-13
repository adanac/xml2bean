package com.adanac.tool.xml2bean.support;

import com.adanac.tool.xml2bean.container.ConfigurableContainer;
import com.adanac.tool.xml2bean.processor.XmlBulider;
import com.adanac.tool.xml2bean.processor.XmlParser;

/**
 * 可配置容器抽象类，实现了一个可配置的容器所需要的基本方法。
 * @author adanac
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractConfigurableContainer extends AbstractContainer implements ConfigurableContainer{

	/**
	 * xml解析器
	 */
	protected XmlParser xmlParser;
	
	/**
	 * xml构建器
	 */
	protected XmlBulider xmlBulider;
	
	public <T> AbstractConfigurableContainer(Class<T> clazz) {
		super(clazz);
	}

	public <T> AbstractConfigurableContainer(int length, Class<T> clazz) {
		super(length, clazz);
	}

	public <T> AbstractConfigurableContainer(String xml, Class<T> clazz) {
		super(xml, clazz);
	}

	public <T> AbstractConfigurableContainer(String[] xmlArray, Class<T> clazz) {
		super(xmlArray, clazz);
	}

	public <T> AbstractConfigurableContainer(T object, Class<T> clazz) {
		super(object, clazz);
	}

	public <T> AbstractConfigurableContainer(T[] objectArray, Class<T> clazz) {
		super(objectArray, clazz);
	}

	public XmlParser getXmlParser() {
		return xmlParser;
	}

	public void setXmlParser(XmlParser xmlParser) {
		this.xmlParser = xmlParser;
	}

	public XmlBulider getXmlBulider() {
		return xmlBulider;
	}

	public void setXmlBulider(XmlBulider xmlBulider) {
		this.xmlBulider = xmlBulider;
	}

	public <T> void setClass(Class<T> clazz) {
		this.clazz = clazz;
	}
	
}
