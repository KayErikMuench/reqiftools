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

import de.kay_muench.reqif10.reqifcompiler.dto.RankingDTO;
import de.kay_muench.reqif10.reqifcompiler.types.complex.EnumeratedAttributeValue;
import de.kay_muench.reqif10.reqifcompiler.types.complex.RequirementRankingAttribute;

public class RequirementRankingValue {
	private EnumeratedAttributeValue value;

	public RequirementRankingValue(
			final RequirementRankingAttribute type,
			final SpecObject specObject) {
		this(type);
		specObject.getValues().add(value.getValue());
	}

	public RequirementRankingValue(
			final RequirementRankingAttribute type) {
		value = new EnumeratedAttributeValue(type.getDef());
	}

	void setRanking(RankingDTO ranking) {
		value.setValue(ranking.getValue());
	}

	public AttributeValueEnumeration getValue() {
		return value.getValue();
	}

}
