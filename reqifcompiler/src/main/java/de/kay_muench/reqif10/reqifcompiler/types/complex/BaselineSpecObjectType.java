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

import org.eclipse.rmf.reqif10.SpecObjectType;

import de.kay_muench.reqif10.reqifcompiler.types.simple.DateType;
import de.kay_muench.reqif10.reqifcompiler.types.simple.RequirementRanking;
import de.kay_muench.reqif10.reqifcompiler.types.simple.RequirementStatus;
import de.kay_muench.reqif10.reqifcompiler.types.simple.String32k;
import de.kay_muench.reqif10.reqifcompiler.types.simple.XhtmlType;

public class BaselineSpecObjectType {
	private CommonSpecObjectTypeDefinitions commondefs;

	BaselineSpecObjectType(String32k typeId, XhtmlType reqifXhtml,
			DateType typeDate, RequirementStatus typeStatus,
			RequirementRanking typeRanking)
			throws DatatypeConfigurationException {
		this.commondefs = new CommonSpecObjectTypeDefinitions(typeId, reqifXhtml, typeDate,
				typeStatus, typeRanking);
		this.commondefs.setName(this.getClass().getSimpleName());
	}

	public IdentifierAttribute getId() {
		return this.commondefs.getIdAttribute();
	}

	public DescriptionAttribute getDesc() {
		return this.commondefs.getDescAttribute();
	}

	public ScheduledByAttribute getScheduledBy() {
		return this.commondefs.getScheduledByAttribute();
	}

	public RequirementStatusAttribute getStatus() {
		return this.commondefs.getStatusAttribute();
	}

	public RequirementRankingAttribute getPriority() {
		return this.commondefs.getPriorityAttribute();
	}

	public RequirementRankingAttribute getRisk() {
		return this.commondefs.getRiskAttribute();
	}

	public SpecObjectType getDef() {
		return this.commondefs.getObjectType();
	}
}