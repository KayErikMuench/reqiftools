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
package de.kay_muench.reqif10.reqifcompiler.types.complex;

import javax.xml.datatype.DatatypeConfigurationException;

import org.eclipse.rmf.reqif10.AttributeDefinitionDate;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

import de.kay_muench.reqif10.reqifcompiler.DateManager;
import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;
import de.kay_muench.reqif10.reqifcompiler.types.simple.DateType;

public class ScheduledByAttribute {
	private static final String SCHEDULEDBY = "Scheduled by";
	private AttributeDefinitionDate def;

	ScheduledByAttribute(DateType type)
			throws DatatypeConfigurationException {
		def = ReqIF10Factory.eINSTANCE.createAttributeDefinitionDate();
		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName(SCHEDULEDBY);
		def.setType(type.getDef());
		def.setLastChange(DateManager.getCurrentDate());
	}

	public AttributeDefinitionDate getDef() {
		return def;
	}

}