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

import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecObjectType;

import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;
import de.kay_muench.reqif10.reqifcompiler.types.simple.Headline;

public class HeadlineType {
	private SpecObjectType def;
	private IdentifierAttribute id;

	HeadlineType(Headline typeIdDesc)
			throws DatatypeConfigurationException {
		id = new IdentifierAttribute(typeIdDesc);

		def = ReqIF10Factory.eINSTANCE.createSpecObjectType();
		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName("HeadlineType");
		def.getSpecAttributes().add(id.getDef());
	}

	public IdentifierAttribute getId() {
		return id;
	}

	public SpecObjectType getDef() {
		return def;
	}
}
