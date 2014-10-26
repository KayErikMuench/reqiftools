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

import javax.xml.datatype.DatatypeConfigurationException;

import org.eclipse.rmf.reqif10.AttributeValueDate;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecObject;

import de.kay_muench.reqif10.reqifcompiler.types.complex.ScheduledByAttribute;

class DateValue {
	private AttributeValueDate value;

	DateValue(final ScheduledByAttribute type,
			final SpecObject specObject) {
		value = ReqIF10Factory.eINSTANCE.createAttributeValueDate();
		value.setDefinition(type.getDef());
		specObject.getValues().add(value);
	}

	void setDate(final String date) throws DatatypeConfigurationException {
		value.setTheValue(DateManager.toDate(date));
	}
}