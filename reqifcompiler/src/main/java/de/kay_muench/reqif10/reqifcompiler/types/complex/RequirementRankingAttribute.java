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

import org.eclipse.rmf.reqif10.AttributeDefinitionEnumeration;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;
import de.kay_muench.reqif10.reqifcompiler.dto.RankingDTO;
import de.kay_muench.reqif10.reqifcompiler.types.simple.RequirementRanking;

public class RequirementRankingAttribute {
	private AttributeDefinitionEnumeration def;

	public RequirementRankingAttribute(final String name, final RequirementRanking type) {
		def = ReqIF10Factory.eINSTANCE.createAttributeDefinitionEnumeration();
		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName(name);
		def.setType(type.getDef());
		def.setMultiValued(false);

		EnumeratedAttributeValue rankingValue = new EnumeratedAttributeValue(def);
		rankingValue.setValue(RankingDTO.DEFAULT);
		def.setDefaultValue(rankingValue.getValue());
	}

	public AttributeDefinitionEnumeration getDef() {
		return def;
	}
}
