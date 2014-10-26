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

import de.kay_muench.reqif10.reqifcompiler.types.simple.SimpleTypesRegistry;

public class AttributesRegistry {
	private IdentifierAttribute idAttribute;

	public AttributesRegistry(SimpleTypesRegistry registry) throws Exception {
		idAttribute = new IdentifierAttribute(registry.getReqIFString32kType());
	}
	
	public IdentifierAttribute getReqIFIDAttrib() {
		return this.idAttribute;
	}
}
