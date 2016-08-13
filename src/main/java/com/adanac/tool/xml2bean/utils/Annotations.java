package com.adanac.tool.xml2bean.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
/**
 * 注解工具类
 * @author adanac
 * @version 1.0.0
 * @since 1.0.0
 */
public class Annotations {
	
	private Annotations(){}

	/**
	 * 获取属性的所有注解
	 * @param field field
	 * @return Annotation[]
	 */
	public static Annotation[] getAnnotations(Field field){
		return field.getAnnotations();
	}
	
	/**
	 * 获取属性指定类型的注解
	 * @param field field
	 * @return Annotation
	 */
	public static Annotation getAnnotation(Field field,Class<? extends Annotation> clazz){
		return field.getAnnotation(clazz);
	}
	
}
