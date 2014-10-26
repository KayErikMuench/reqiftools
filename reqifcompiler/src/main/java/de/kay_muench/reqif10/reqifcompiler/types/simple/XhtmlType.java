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

import org.eclipse.rmf.reqif10.DatatypeDefinitionXHTML;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;

public class XhtmlType {
	private DatatypeDefinitionXHTML def;
	
	public XhtmlType() {
		this.def = ReqIF10Factory.eINSTANCE.createDatatypeDefinitionXHTML();
		this.def.setIdentifier(IdentifierManager.generateIdentifier());
		this.def.setLongName("T_Xhtml");
	}

	public DatatypeDefinitionXHTML getDef() {
		return def;
	}
}
