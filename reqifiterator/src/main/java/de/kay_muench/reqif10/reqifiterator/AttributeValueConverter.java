/**
 * Copyright (c) 2014 Kay Erik Münch.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.spdx.org/licenses/EPL-1.0
 * 
 * Contributors:
 *     Kay Erik Münch - initial API and implementation
 * 
 */
package de.kay_muench.reqif10.reqifiterator;

import java.util.Iterator;
import java.util.List;

import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.XhtmlContent;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;

interface AttributeEnumValueConverter {
	String convert(final String orig);
}

final class AttributeValueConverter {
	private class DefaultEnumValueConverter implements AttributeEnumValueConverter {

		public String convert(String orig) {
			return orig;
		}

	}

	public static String getDefaultValue(final AttributeValue av) {
		AttributeValueConverter converter = new AttributeValueConverter();
		return getDefaultValue(av, converter.new DefaultEnumValueConverter());
	}

	public static String getDefaultValue(final AttributeValue av,
			AttributeEnumValueConverter converter) {
		final Object value = getTheValue(av);
		String textValue;
		if (value == null) {
			textValue = "";
		} else if (value instanceof List<?>) {
			textValue = "";
			for (Iterator<?> i = ((List<?>) value).iterator(); i.hasNext();) {
				EnumValue enumValue = (EnumValue) i.next();
				textValue += converter.convert(enumValue.getLongName());
				if (i.hasNext()) {
					textValue += ", ";
				}
			}
		} else if (value instanceof XhtmlContent) {
			textValue = XhtmlContentProcessor
					.generateXMLString((XhtmlContent) value);
		} else {
			textValue = value.toString();
		}
		return textValue;
	}

	public static boolean hasXhtmlContent(final AttributeValue av) {
		final Object value = getTheValue(av);
		return value != null && value instanceof XhtmlContent;
	}

	private static Object getTheValue(final AttributeValue av) {
		final Object value = av == null ? null : ReqIF10Util.getTheValue(av);
		return value;
	}
}
