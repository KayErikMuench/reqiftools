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
package de.kay_muench.reqif10.reqifcompiler;

import org.eclipse.rmf.reqif10.AttributeValueXHTML;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.XhtmlContent;
import org.eclipse.rmf.reqif10.xhtml.XhtmlDivType;
import org.eclipse.rmf.reqif10.xhtml.XhtmlFactory;

import de.kay_muench.reqif10.reqifcompiler.types.complex.DescriptionAttribute;
import de.kay_muench.reqif10.reqifcompiler.xhtml.XhtmlBuilder;

class DescriptionValue {
	private AttributeValueXHTML value;

	DescriptionValue(final DescriptionAttribute type,
			final SpecObject specObject) {
		value = ReqIF10Factory.eINSTANCE.createAttributeValueXHTML();
		value.setDefinition(type.getDef());
		specObject.getValues().add(value);
	}

	void setDescription(final XhtmlBuilder builder) {
		XhtmlContent content = ReqIF10Factory.eINSTANCE.createXhtmlContent();
		
		XhtmlDivType type = XhtmlFactory.eINSTANCE.createXhtmlDivType();
		type.setClass("requirement");
		type.getDiv().addAll(builder.build());
		content.setXhtml(type);
		
		value.setTheValue(content);
	}
}