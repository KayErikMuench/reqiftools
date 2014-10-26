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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.DatatypeDefinition;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.ReqIFHeader;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecRelation;
import org.eclipse.rmf.reqif10.SpecType;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.serialization.XMLPersistenceMappingResourceFactoryImpl;

import de.kay_muench.reqif10.reqifcompiler.dto.RankingDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.RelationTypeDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.RequirementDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.StatusDTO;
import de.kay_muench.reqif10.reqifcompiler.types.complex.SpecTypesRegistry;
import de.kay_muench.reqif10.reqifcompiler.types.simple.SimpleTypesRegistry;
import de.kay_muench.reqif10.reqifcompiler.xhtml.XhtmlBuilder;

public final class ReqIF10Compiler {

	private class TypeRegistry {

		private SimpleTypesRegistry simpleTypesRegistry = new SimpleTypesRegistry();
		private SpecTypesRegistry specTypesRegistry = new SpecTypesRegistry(
				simpleTypesRegistry);

		public TypeRegistry() throws Exception {
		}

		public List<DatatypeDefinition> getDatatypes() {
			List<DatatypeDefinition> definitions = new LinkedList<DatatypeDefinition>();
			definitions.addAll(simpleTypesRegistry.getDatatypes());
			return definitions;
		}

		public List<SpecType> getSpectypes() {
			List<SpecType> types = new LinkedList<SpecType>();
			types.addAll(specTypesRegistry.getSpectypes());
			return types;
		}

	}

	private TypeRegistry typeRegistry;
	private ReqIF reqIF;
	private SpecObjectFactory specObjectFactory;
	private SpecificationOutline outline = SpecificationOutline.newInstance();
	private List<Relation> relations;
	private Map<String, SpecObject> idSpecobjectMap;

	public void initialize() throws Exception {
		typeRegistry = new TypeRegistry();
		reqIF = ReqIF10Factory.eINSTANCE.createReqIF();
		specObjectFactory = new SpecObjectFactory(
				typeRegistry.specTypesRegistry.getSpecObjectTypeWithDate(),
				typeRegistry.specTypesRegistry.getSpecObjectTypeWithoutDate());
		outline.setReqIF(getReqIF());
		relations = new LinkedList<Relation>();
		idSpecobjectMap = new HashMap<String, SpecObject>();

		ReqIFContent reqIFContent = ReqIF10Factory.eINSTANCE
				.createReqIFContent();
		getReqIF().setCoreContent(reqIFContent);

		this.createDatatypes();
		this.createSpecTypes();
	}

	public String export(String id) throws Exception {
		this.createHeader(id);

		ResourceFactoryImpl resourceFactory = new XMLPersistenceMappingResourceFactoryImpl();
		URI emfURI = URI.createURI("http://kay-muench.de", true);
		XMLResource resource = (XMLResource) resourceFactory
				.createResource(emfURI);

		resource.setEncoding("UTF-8");
		resource.getContents().add(getReqIF());

		OutputStream os = new ByteArrayOutputStream();
		resource.save(os, null);
		return os.toString();
	}

	private void createHeader(String id) throws Exception {
		ReqIFHeader reqIfHeader = ReqIF10Factory.eINSTANCE.createReqIFHeader();
		reqIfHeader.setComment("RequieTool export");
		reqIfHeader.setIdentifier(IdentifierManager.generateIdentifier());
		reqIfHeader.setReqIFToolId("RequieTool (c) Kay Münch");
		reqIfHeader.setSourceToolId("RequieTool (c) Kay Münch");
		reqIfHeader.setReqIFVersion("1.0");
		reqIfHeader.setTitle(id);
		reqIfHeader.setCreationTime(DateManager.getCurrentDate());
		getReqIF().setTheHeader(reqIfHeader);
	}

	private void createDatatypes() throws Exception {
		// datatypeDefinitionBoolean =
		// ReqIF10Factory.eINSTANCE.createDatatypeDefinitionBoolean();
		// datatypeDefinitionBoolean.setIdentifier("ID_TC1000_DatatypeDefinitionBoolean");
		// datatypeDefinitionBoolean.setLongName("TC1000 DatatypeDefinitionBoolean");
		// datatypeDefinitionBoolean.setLastChange(toDate(LAST_CHANGE_STRING));
		//
		// datatypeDefinitionInteger =
		// ReqIF10Factory.eINSTANCE.createDatatypeDefinitionInteger();
		// datatypeDefinitionInteger.setIdentifier("ID_TC1000_DatatypeDefinitionInteger");
		// datatypeDefinitionInteger.setLongName("TC1000 DatatypeDefinitionInteger");
		// datatypeDefinitionInteger.setLastChange(toDate(LAST_CHANGE_STRING));
		// datatypeDefinitionInteger.setMin(new BigInteger("-17496"));
		// datatypeDefinitionInteger.setMax(new BigInteger("5000"));
		//

		getReqIF().getCoreContent().getDatatypes()
				.addAll(this.typeRegistry.getDatatypes());
	}

	private void createSpecTypes() throws Exception {
		getReqIF().getCoreContent().getSpecTypes()
				.addAll(this.typeRegistry.getSpectypes());
	}

	public void addSpecObject(String id, XhtmlBuilder builder, String date,
			StatusDTO status, RankingDTO importance, RankingDTO risk)
			throws Exception {
		SpecObject specObject = this.specObjectFactory.newSpecObject(id,
				builder, date, status, importance, risk);
		idSpecobjectMap.put(id, specObject);
		getReqIF().getCoreContent().getSpecObjects().add(specObject);
		SpecHierarchy specHierarchy = ReqIF10Factory.eINSTANCE
				.createSpecHierarchy();
		specHierarchy.setIdentifier(IdentifierManager.generateIdentifier());
		specHierarchy.setLongName("SpecHierarchy");
		specHierarchy.setObject(specObject);

		this.outline.add(specHierarchy);
	}

	public void addSpecification(String idSpec) throws Exception {

		Specification specification = ReqIF10Factory.eINSTANCE
				.createSpecification();
		specification.setIdentifier(IdentifierManager.generateIdentifier());
		specification.setLongName("Spec");
		specification.setType(this.typeRegistry.specTypesRegistry
				.getSpecificationType());

		IDValue attributeValueID = new IDValue(
				this.typeRegistry.specTypesRegistry.getAttributesRegistry()
						.getReqIFIDAttrib(), specification);
		attributeValueID.setId(idSpec);

		getReqIF().getCoreContent().getSpecifications().add(specification);
		this.outline.push(specification);
	}

	public void addRelation(RelationTypeDTO type, RequirementDTO src,
			RequirementDTO dest) {
		relations.add(new Relation(type, src, dest));
	}

	public void addSpecRelations() throws Exception {
		for (Relation link : this.relations) {
			if (idSpecobjectMap.containsKey(link.getSrc().getName())
					&& idSpecobjectMap.containsKey(link.getDest().getName())) {
				SpecObject srcObject = idSpecobjectMap.get(link.getSrc()
						.getName());
				SpecObject destObject = idSpecobjectMap.get(link.getDest()
						.getName());
				this.addSpecRelation(link, srcObject, destObject);
			}
		}
	}

	private void addSpecRelation(Relation link, SpecObject source,
			SpecObject target) throws Exception {
		SpecRelation specRelation = ReqIF10Factory.eINSTANCE
				.createSpecRelation();
		specRelation.setIdentifier(IdentifierManager.generateIdentifier());
		specRelation.setLongName("SpecRelation");
		specRelation.setLastChange(DateManager.getCurrentDate());

		specRelation.setSource(source);
		specRelation.setTarget(target);
		specRelation.setType(this.typeRegistry.specTypesRegistry
				.getSpecRelationType().getDef());

		IDValue attributeValueId = new IDValue(
				this.typeRegistry.specTypesRegistry.getSpecRelationType()
						.getId(), specRelation);
		attributeValueId.setId(link.getName());

		AttributeValueString attributeValueString = ReqIF10Factory.eINSTANCE
				.createAttributeValueString();
		attributeValueString.setDefinition(this.typeRegistry.specTypesRegistry
				.getSpecRelationType()
				.getSpecRelationTypeAttributeDefinitionString());
		attributeValueString.setTheValue(link.getRelationKind().toString()
				.toLowerCase(Locale.ENGLISH));
		specRelation.getValues().add(attributeValueString);

		getReqIF().getCoreContent().getSpecRelations().add(specRelation);
	}

	public void shiftLevelDown(final String title) {
		Headline headline = new Headline(
				this.typeRegistry.specTypesRegistry.getHeadlineType(), title);
		this.outline.shiftLevelDown(headline);
	}

	public void shiftLevelUp() {
		this.outline.shiftLevelUp();
	}

	private ReqIF getReqIF() {
		return this.reqIF;
	}

	public static ReqIF10Compiler newInstance() {
		return new ReqIF10Compiler();
	}
}
