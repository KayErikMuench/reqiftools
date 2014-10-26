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

import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecElementWithAttributes;

import de.kay_muench.reqif10.reqifcompiler.types.complex.IdentifierAttribute;

class IDValue {
	private AttributeValueString value;

	IDValue(final IdentifierAttribute type,
			final SpecElementWithAttributes elementWithAttributes) {
		value = ReqIF10Factory.eINSTANCE.createAttributeValueString();
		value.setDefinition(type.getDef());
		elementWithAttributes.getValues().add(value);
	}

	void setId(final String id) {
		value.setTheValue(id);
	}
}