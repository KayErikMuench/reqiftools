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
package de.kay_muench.reqif10.reqifcompiler.types.simple;

import org.eclipse.rmf.reqif10.DatatypeDefinitionString;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;

public class Headline {
	private DatatypeDefinitionString def;

	Headline() {
		def = ReqIF10Factory.eINSTANCE.createDatatypeDefinitionString();
		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName("T_Headline");
	}

	public DatatypeDefinitionString getDef() {
		return this.def;
	}

}
