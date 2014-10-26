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

import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecObject;

import de.kay_muench.reqif10.reqifcompiler.types.complex.HeadlineType;

class Headline {
	private IDValue attributeValueID;
	private SpecObject specObject;

	Headline(final HeadlineType type, final String id) {
		specObject = ReqIF10Factory.eINSTANCE.createSpecObject();
		specObject.setIdentifier(IdentifierManager.generateIdentifier());
		specObject.setType(type.getDef());

		attributeValueID = new IDValue(type.getId(), specObject);

		this.setId(id);
	}

	void setId(String id) {
		attributeValueID.setId(id);
	}

	SpecObject getObj() {
		return specObject;
	}
}
