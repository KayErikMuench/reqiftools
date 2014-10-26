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
import de.kay_muench.reqif10.reqifcompiler.types.simple.DateType;
import de.kay_muench.reqif10.reqifcompiler.types.simple.RequirementRanking;
import de.kay_muench.reqif10.reqifcompiler.types.simple.RequirementStatus;
import de.kay_muench.reqif10.reqifcompiler.types.simple.String32k;
import de.kay_muench.reqif10.reqifcompiler.types.simple.XhtmlType;

final class CommonSpecObjectTypeDefinitions {
	final static String PRIORITY = "Priority";
	final static String RISK = "Risk";

	private SpecObjectType objectType;
	private IdentifierAttribute idAttribute;
	private DescriptionAttribute descAttribute;
	private ScheduledByAttribute scheduledByAttribute;
	private RequirementStatusAttribute statusAttribute;
	private RequirementRankingAttribute priorityAttribute;
	private RequirementRankingAttribute riskAttribute;

	public CommonSpecObjectTypeDefinitions(String32k typeId,
			XhtmlType reqifXhtml, DateType typeDate,
			RequirementStatus typeStatus,
			RequirementRanking typeRanking)
			throws DatatypeConfigurationException {
		idAttribute = new IdentifierAttribute(typeId);
		descAttribute = new DescriptionAttribute(reqifXhtml);
		statusAttribute = new RequirementStatusAttribute(typeStatus);
		priorityAttribute = new RequirementRankingAttribute(PRIORITY,
				typeRanking);
		riskAttribute = new RequirementRankingAttribute(RISK, typeRanking);

		objectType = ReqIF10Factory.eINSTANCE.createSpecObjectType();
		objectType.setIdentifier(IdentifierManager.generateIdentifier());
		objectType.getSpecAttributes().add(idAttribute.getDef());
		objectType.getSpecAttributes().add(descAttribute.getDef());
		objectType.getSpecAttributes().add(statusAttribute.getDef());
		objectType.getSpecAttributes().add(priorityAttribute.getDef());
		objectType.getSpecAttributes().add(riskAttribute.getDef());
		this.setScheduledByAttribute(typeDate);
		this.setName("T_SpecObject");
	}

	public void setName(final String name) {
		objectType.setLongName(name);
	}

	public SpecObjectType getObjectType() {
		return objectType;
	}

	public IdentifierAttribute getIdAttribute() {
		return idAttribute;
	}

	public DescriptionAttribute getDescAttribute() {
		return descAttribute;
	}

	public ScheduledByAttribute getScheduledByAttribute() {
		return scheduledByAttribute;
	}

	private void setScheduledByAttribute(DateType typeDate)
			throws DatatypeConfigurationException {
		if (typeDate != null) {
			scheduledByAttribute = new ScheduledByAttribute(typeDate);
			objectType.getSpecAttributes().add(scheduledByAttribute.getDef());
		}
	}

	public RequirementStatusAttribute getStatusAttribute() {
		return statusAttribute;
	}

	public RequirementRankingAttribute getPriorityAttribute() {
		return priorityAttribute;
	}

	public RequirementRankingAttribute getRiskAttribute() {
		return riskAttribute;
	}

}