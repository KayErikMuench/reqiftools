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

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.rmf.reqif10.XhtmlContent;

final class XhtmlContentProcessor {

	public static String generateXMLString(XhtmlContent content) {
		String xmlContent = "";
		try {
			xmlContent = generateXMLString((EObject) content.getXhtml());
			xmlContent = xmlContent.replaceAll("<.?reqif.*>", "");
			xmlContent = xmlContent.replaceAll("xhtml:", "");
			xmlContent = xmlContent.replaceAll("[\\n\\r]", "");
			xmlContent = xmlContent
					.replaceAll("<!\\[CDATA\\[(.*)\\]\\]>", "$1");
		} catch (IOException e) {
			xmlContent = "<p class=\"error\">" + e.getMessage() + "</p>";
		}

		return xmlContent.trim();
	}

	// Shameless copied from ProrXhtmlSimplifiedHelper
	private static String generateXMLString(EObject eobject) throws IOException {
		StringWriter sw = new StringWriter();
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ROOT_OBJECTS,
				Collections.singletonList(eobject));
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.FALSE);
		options.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.FALSE);
		options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
				Boolean.FALSE);
		XMLResourceImpl ri = new XMLResourceImpl();
		ri.save(sw, options);
		return sw.toString();
	}

}
