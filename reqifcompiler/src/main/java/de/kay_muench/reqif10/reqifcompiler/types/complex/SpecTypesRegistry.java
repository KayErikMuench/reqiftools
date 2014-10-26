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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecType;
import org.eclipse.rmf.reqif10.SpecificationType;

import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;
import de.kay_muench.reqif10.reqifcompiler.types.simple.SimpleTypesRegistry;

public class SpecTypesRegistry {

	private HeadlineType headlineType;
	private SpecificationType specificationType;
	private AttributesRegistry attributesRegistry;
	private BaselineSpecObjectType specObjectTypeWithDate;
	private ConfigurationSpecObjectType specObjectTypeWithoutDate;
	private SpecRelationTypeWithRelationKind specRelationType;

	public AttributesRegistry getAttributesRegistry() {
		return attributesRegistry;
	}

	public SpecTypesRegistry(SimpleTypesRegistry registry) throws Exception {
		attributesRegistry = new AttributesRegistry(registry);
		headlineType = new HeadlineType(registry.getReqIFHeadlineType());
		specificationType = ReqIF10Factory.eINSTANCE.createSpecificationType();
		specificationType.setIdentifier(IdentifierManager.generateIdentifier());
		specificationType.setLongName("SpecificationType");
		specificationType.getSpecAttributes().add(
				attributesRegistry.getReqIFIDAttrib().getDef());
		specObjectTypeWithDate = new BaselineSpecObjectType(
				registry.getReqIFString32kType(), registry.getReqIFXhtmlType(),
				registry.getReqIFDateType(),
				registry.getReqIFRequirementStatusType(),
				registry.getReqIFRequirementRankingType());
		specObjectTypeWithoutDate = new ConfigurationSpecObjectType(
				registry.getReqIFString32kType(), registry.getReqIFXhtmlType(),
				registry.getReqIFRequirementStatusType(),
				registry.getReqIFRequirementRankingType());
		specRelationType = new SpecRelationTypeWithRelationKind(
				registry.getReqIFString32kType());
	}

	public HeadlineType getHeadlineType() {
		return this.headlineType;
	}

	public SpecificationType getSpecificationType() {
		return this.specificationType;
	}

	public BaselineSpecObjectType getSpecObjectTypeWithDate() {
		return this.specObjectTypeWithDate;
	}

	public ConfigurationSpecObjectType getSpecObjectTypeWithoutDate() {
		return this.specObjectTypeWithoutDate;
	}

	public SpecRelationTypeWithRelationKind getSpecRelationType() {
		return this.specRelationType;
	}

	public List<SpecType> getSpectypes() {
		List<SpecType> types = new LinkedList<SpecType>();
		types.add(this.specObjectTypeWithoutDate.getDef());
		types.add(this.specObjectTypeWithDate.getDef());
		types.add(this.specRelationType.getDef());
		types.add(this.specificationType);
		types.add(this.headlineType.getDef());
		return types;
	}

}
