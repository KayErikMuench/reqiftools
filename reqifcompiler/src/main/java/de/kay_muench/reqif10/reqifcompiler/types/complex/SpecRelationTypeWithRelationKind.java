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

import org.eclipse.rmf.reqif10.AttributeDefinitionString;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecRelationType;

import de.kay_muench.reqif10.reqifcompiler.DateManager;
import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;
import de.kay_muench.reqif10.reqifcompiler.dto.RelationTypeDTO;
import de.kay_muench.reqif10.reqifcompiler.types.simple.String32k;

public class SpecRelationTypeWithRelationKind {
	private SpecRelationType def;
	private IdentifierAttribute id;
	private AttributeDefinitionString specRelationTypeAttributeDefinitionString;

	public SpecRelationTypeWithRelationKind(String32k typeId)
			throws DatatypeConfigurationException {
		id = new IdentifierAttribute(typeId);
		specRelationTypeAttributeDefinitionString = ReqIF10Factory.eINSTANCE
				.createAttributeDefinitionString();
		specRelationTypeAttributeDefinitionString
				.setIdentifier(IdentifierManager.generateIdentifier());
		specRelationTypeAttributeDefinitionString.setLongName("Relation");
		specRelationTypeAttributeDefinitionString.setLastChange(DateManager
				.getCurrentDate());
		specRelationTypeAttributeDefinitionString.setType(typeId.getDef());

		AttributeValueString attributeValueString = ReqIF10Factory.eINSTANCE
				.createAttributeValueString();
		attributeValueString.setTheValue(RelationTypeDTO.DEFAULT.toString()
				.toLowerCase());
		specRelationTypeAttributeDefinitionString
				.setDefaultValue(attributeValueString);

		def = ReqIF10Factory.eINSTANCE.createSpecRelationType();

		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName("SpecRelationType");
		def.setLastChange(DateManager.getCurrentDate());
		def.getSpecAttributes().add(id.getDef());
		def.getSpecAttributes().add(specRelationTypeAttributeDefinitionString);
	}

	public IdentifierAttribute getId() {
		return id;
	}

	public AttributeDefinitionString getSpecRelationTypeAttributeDefinitionString() {
		return specRelationTypeAttributeDefinitionString;
	}

	public SpecRelationType getDef() {
		return def;
	}

}
