package com.adanac.tool.xml2bean.support;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import com.adanac.tool.xml2bean.annotation.DateAnnotationHandler;
import com.adanac.tool.xml2bean.exception.XmlBuildException;
import com.adanac.tool.xml2bean.utils.Commons;
import com.adanac.tool.xml2bean.utils.Reflects;


/**
 * 默认的xml构建器实现
 * @author adanac
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultConfigurableXmlBuilder extends AbstractXmlBuilder{
	
	/**
	 * 默认的初始层次
	 */
	public static final int START_LEVEL = 1;
	
	public DefaultConfigurableXmlBuilder() {
		super();
	}

	public DefaultConfigurableXmlBuilder(Format format) {
		super(format);
	}

	public DefaultConfigurableXmlBuilder(String header, Format format) {
		super(header, format);
	}

	public DefaultConfigurableXmlBuilder(String header) {
		super(header);
	}

	public void appendHeader(final StringBuffer stringBuffer, final Object object) {
		stringBuffer.append(header).append(LINE);
    }

	public void appendBody(final StringBuffer stringBuffer, final Object object){
		if (Commons.isNull(object)) {
			return;
		}
		try {
			appendRecursion(stringBuffer, object, object.getClass().getSimpleName(), START_LEVEL);
		} catch (IllegalArgumentException e) {
			throw new XmlBuildException("xml构建异常，此异常是由于采用反射获取属性中的值时发现参数不合法，请仔细检查你传入的对象。",e);
		} catch (IllegalAccessException e) {
			throw new XmlBuildException("xml构建异常，此异常是由于采用反射获取属性中的值时发现访问被禁止，请仔细检查你传入的对象的属性都可以正常访问。",e);
		} catch (Exception e) {
			throw new XmlBuildException(e);
		}
	}
	
	private void appendRecursion(final StringBuffer stringBuffer,Object object,String tagName,int level) throws IllegalArgumentException, IllegalAccessException{
		if (Commons.isNull(object)) {
			return;
		}
		final int currentLevel = level;
		Class<?> clazz = object.getClass();
		if (!Reflects.hasField(clazz)) {
			insertHeadTag(stringBuffer, tagName);
			if (needLine(format)) {
				insertLine(stringBuffer);
			}
			insertEndTag(stringBuffer, tagName);
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		insertHeadTag(stringBuffer, tagName);
		if (needLine(format)) {
			insertLine(stringBuffer);
		}
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			Object fieldValue = field.get(object);
			if (Commons.isNull(fieldValue) || Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
				if (i == 0) {
					deleteLine(stringBuffer);
				}
				continue;
			}
			appendHead(stringBuffer, i, currentLevel);
			if (!Iterable.class.isAssignableFrom(field.getType())) {
				if (Reflects.isComplexType(field.getType())) {
					appendRecursion(stringBuffer,fieldValue,field.getName(),currentLevel + 1);
				}else {
					insertSimpleType(stringBuffer, field, field.getName(), fieldValue);
				}
				continue;
			}
			final int currentArrayLevel = currentLevel + 1;
			insertHeadTag(stringBuffer, field.getName());
			if (needLine(format)) {
				insertLine(stringBuffer);
			}
			Class<?> iterableGenericType = Reflects.getArrayGenericType(field);
			int index = 0;
			for (Object tempObject : ((Iterable)fieldValue)) {
				appendHead(stringBuffer, index++, currentArrayLevel);
				if (Reflects.isComplexType(iterableGenericType)) {
					appendRecursion(stringBuffer,tempObject,tempObject.getClass().getSimpleName(),currentArrayLevel + 1);
				}else {
					insertSimpleType(stringBuffer, field, iterableGenericType.getSimpleName(), tempObject);
				}
			}
			appendEnd(stringBuffer, field.getName(), currentArrayLevel);
		}
		appendEnd(stringBuffer, tagName, currentLevel);
	}
	
	private void appendHead(final StringBuffer stringBuffer, int index, int currentLevel){
		if (needLine(format) && index != 0) {
			insertLine(stringBuffer);
		}
		if (needTab(format)) {
			insertTab(stringBuffer, currentLevel);
		}
	}
	
	private void appendEnd(final StringBuffer stringBuffer,String tagName, int currentLevel){
		if (needLine(format)) {
			insertLine(stringBuffer);
		}
		if (needTab(format)) {
			insertTab(stringBuffer, currentLevel - 1);
		}
		insertEndTag(stringBuffer, tagName);
	}
	
	private void insertSimpleType(final StringBuffer stringBuffer,Field field,String tagName,Object fieldValue){
		stringBuffer.append(TAG_HEAD_PREFIX).append(tagName).append(TAG_SUFFIX);
		if (field.getType() == Date.class) {
			stringBuffer.append(DateAnnotationHandler.handle(field, fieldValue));
		}else {
			stringBuffer.append(fieldValue);
		}
		stringBuffer.append(TAG_END_PREFIX).append(tagName).append(TAG_SUFFIX);
	}
	
	private void insertHeadTag(final StringBuffer stringBuffer,String tagName){
		stringBuffer.append(TAG_HEAD_PREFIX).append(tagName).append(TAG_SUFFIX);
	}
	
	private void insertEndTag(final StringBuffer stringBuffer,String tagName){
		stringBuffer.append(TAG_END_PREFIX).append(tagName).append(TAG_SUFFIX);
	}
	
	private void insertTab(final StringBuffer stringBuffer,int number){
		for (int i = 0; i < number; i++) {
			stringBuffer.append(TAB);
		}
	}
	
	private void insertLine(final StringBuffer stringBuffer){
		stringBuffer.append(LINE);
	}
	
	private void deleteLine(final StringBuffer stringBuffer){
		if (stringBuffer.lastIndexOf(LINE) < 0 || stringBuffer.length() - stringBuffer.lastIndexOf(LINE) != LINE.length()) {
			return;
		}
		stringBuffer.delete(stringBuffer.lastIndexOf(LINE), stringBuffer.length());
	}
	
	private boolean needLine(Format format){
		if (format.equals(Format.ONLY_LINE) || format.equals(Format.TAB_AND_LINE)) {
			return true;
		}
		return false;
	}
	
	private boolean needTab(Format format){
		if (format.equals(Format.TAB_AND_LINE)) {
			return true;
		}
		return false;
	}
	
	public void prepareBuild() {}

	public void finishBuild() {}

}
