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

import org.eclipse.rmf.reqif10.AttributeDefinitionString;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

import de.kay_muench.reqif10.reqifcompiler.DateManager;
import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;
import de.kay_muench.reqif10.reqifcompiler.types.simple.Headline;
import de.kay_muench.reqif10.reqifcompiler.types.simple.String32k;

public class IdentifierAttribute {
	public static String NAME = "ID";
	private AttributeDefinitionString def;

	IdentifierAttribute(String32k type) throws DatatypeConfigurationException {
		def = ReqIF10Factory.eINSTANCE.createAttributeDefinitionString();
		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName(IdentifierAttribute.NAME);
		def.setType(type.getDef());
		def.setLastChange(DateManager.getCurrentDate());
	}

	IdentifierAttribute(Headline type) throws DatatypeConfigurationException {
		def = ReqIF10Factory.eINSTANCE.createAttributeDefinitionString();
		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName(IdentifierAttribute.NAME);
		def.setType(type.getDef());
		def.setLastChange(DateManager.getCurrentDate());
	}

	public AttributeDefinitionString getDef() {
		return this.def;
	}
}