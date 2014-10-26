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

import org.eclipse.rmf.reqif10.AttributeValueEnumeration;
import org.eclipse.rmf.reqif10.SpecObject;

import de.kay_muench.reqif10.reqifcompiler.dto.StatusDTO;
import de.kay_muench.reqif10.reqifcompiler.types.complex.EnumeratedAttributeValue;
import de.kay_muench.reqif10.reqifcompiler.types.complex.RequirementStatusAttribute;

public class RequirementStatusValue {
	private EnumeratedAttributeValue value;

	public RequirementStatusValue(
			final RequirementStatusAttribute type,
			final SpecObject specObject) {
		this(type);
		specObject.getValues().add(value.getValue());
	}

	public RequirementStatusValue(
			final RequirementStatusAttribute type) {
		value = new EnumeratedAttributeValue(type.getDef());
	}

	void setStatus(StatusDTO status) {
		value.setValue(status.getValue());
	}

	public AttributeValueEnumeration getValue() {
		return value.getValue();
	}

}
