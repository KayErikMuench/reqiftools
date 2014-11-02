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
package de.kay_muench.reqif10.reqifiterator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.AttributeDefinition;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecRelation;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
import org.junit.Before;
import org.junit.Test;

import de.kay_muench.reqif10.reqifparser.ReqIF10Parser;

public class ReqIF10IteratorTest {

	private class TestRelation {
		private String id;
		private String relation;
		private String srcSpecId;
		private String tgtSpecId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getRelation() {
			return relation;
		}

		public void setRelation(String relation) {
			this.relation = relation;
		}

		public String getSrcSpecId() {
			return srcSpecId;
		}

		public void setSrcSpecId(String srcSpecId) {
			this.srcSpecId = srcSpecId;
		}

		public String getTgtSpecId() {
			return tgtSpecId;
		}

		public void setTgtSpecId(String tgtSpecId) {
			this.tgtSpecId = tgtSpecId;
		}

	}

	private class TestAttribute {
		private String name;
		private String value;
		private boolean xhtml;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@SuppressWarnings("unused")
		public boolean isXhtml() {
			return xhtml;
		}

		public void setXhtml(boolean xhtml) {
			this.xhtml = xhtml;
		}
	}

	private class TestSpecObject {
		private String type;
		private final List<TestRelation> relations = new ArrayList<TestRelation>();
		private final List<TestAttribute> attributes = new ArrayList<TestAttribute>();

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<TestRelation> getRelations() {
			return relations;
		}

		public List<TestAttribute> getAttributes() {
			return attributes;
		}

	}

	private class TestSpecification {

		private String longName;
		private String title;

		public String getLongName() {
			return longName;
		}

		public String getTitle() {
			return title;
		}

		public void setLongName(String longName) {
			this.longName = longName;

		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	ReqIF reqif;

	@Before
	public void setUp() throws Exception {
		ReqIF10Parser parser = new ReqIF10Parser();
		parser.setReqIFFilename("src/test/resources/UT_1000.reqif");
		this.reqif = parser.parseReqIFContent();
	}

	@Test
	public void testReqIFIterator() {
		final TestSpecification testSpecification = new TestSpecification();
		final List<TestSpecObject> testSpecObjects = new ArrayList<TestSpecObject>();
		final List<TestRelation> testRelations = new ArrayList<TestRelation>();

		ReqIF10Iterator iterator = new ReqIF10Iterator();
		final EList<Specification> specs = reqif.getCoreContent()
				.getSpecifications();
		final EList<SpecRelation> relations = reqif.getCoreContent()
				.getSpecRelations();

		iterator.iterateRecursivelyThrough(specs, new SpecificationCallback() {

			@Override
			public void call(SpecificationDTO specificationDto) {
				testSpecification.setLongName(specificationDto.getLongName());
				testSpecification.setTitle(specificationDto.getAttributeValue(0));
			}
		}, new SpecObjectCallback() {

			public void call(final SpecObjectDTO specObject, final int depth) {
				TestSpecObject testSpecObject = new TestSpecObject();
				testSpecObject.setType(specObject.getTypeName());
				for (AttributeValue av : specObject.getSpecObject().getValues()) {
					AttributeDefinition ad = ReqIF10Util
							.getAttributeDefinition(av);
					TestAttribute attribute = new TestAttribute();
					attribute.setName(ad.getLongName());
					attribute.setValue(AttributeValueConverter
							.getDefaultValue(av));
					attribute.setXhtml(AttributeValueConverter
							.hasXhtmlContent(av));
					testSpecObject.getAttributes().add(attribute);
				}

				for (SpecRelationDTO out : specObject.getOutgoingRelations()) {
					TestRelation relation = new TestRelation();
					relation.setId(out.getValueFor("ID"));
					relation.setRelation(out.getValueFor("Relation"));
					Specification specSrc = ReqIF10Finder.findSpecFor(out
							.getRelation().getSource());
					relation.setSrcSpecId(specSrc.getIdentifier());
					Specification specTgt = ReqIF10Finder.findSpecFor(out
							.getRelation().getTarget());
					relation.setTgtSpecId(specTgt.getIdentifier());
					testSpecObject.getRelations().add(relation);
				}
				for (SpecRelationDTO in : specObject.getIncomingRelations()) {
					TestRelation relation = new TestRelation();
					relation.setId(in.getValueFor("ID"));
					relation.setRelation(in.getValueFor("Relation"));
					Specification specSrc = ReqIF10Finder.findSpecFor(in
							.getRelation().getSource());
					relation.setSrcSpecId(specSrc.getIdentifier());
					Specification specTgt = ReqIF10Finder.findSpecFor(in
							.getRelation().getTarget());
					relation.setTgtSpecId(specTgt.getIdentifier());
					testSpecObject.getRelations().add(relation);
				}
				testSpecObjects.add(testSpecObject);
			}
		});

		iterator.iterateThrough(relations, new SpecRelationCallback() {

			@Override
			public void call(SpecRelationDTO relationDTO) {
				TestRelation relation = new TestRelation();
				relation.setId(relationDTO.getValueFor("ID"));
				relation.setRelation(relationDTO.getValueFor("Relation"));
				Specification specSrc = ReqIF10Finder.findSpecFor(relationDTO
						.getRelation().getSource());
				relation.setSrcSpecId(specSrc.getIdentifier());
				Specification specTgt = ReqIF10Finder.findSpecFor(relationDTO
						.getRelation().getTarget());
				relation.setTgtSpecId(specTgt.getIdentifier());
				testRelations.add(relation);
			}
		});

		assertEquals("Spec", testSpecification.getLongName());
		assertEquals("Test-Specification", testSpecification.getTitle());

		assertEquals(5, testSpecObjects.size());
		assertEquals("HeadlineType", testSpecObjects.get(0).getType());
		assertEquals("ConfigurationSpecObjectType", testSpecObjects.get(1)
				.getType());
		assertEquals("HeadlineType", testSpecObjects.get(2).getType());
		assertEquals("HeadlineType", testSpecObjects.get(3).getType());
		assertEquals("ConfigurationSpecObjectType", testSpecObjects.get(4)
				.getType());

		this.assertSpecObjectAttributes(testSpecObjects.get(0), "ID",
				"Chapter 1");
		this.assertSpecObjectAttributes(testSpecObjects.get(1), "ID", "REQ_1",
				"Description", "", "Status", "StatusTest", "Priority",
				"RankingTest", "Risk", "RankingTest");
		this.assertSpecObjectAttributes(testSpecObjects.get(2), "ID",
				"Chapter 2");
		this.assertSpecObjectAttributes(testSpecObjects.get(3), "ID",
				"Section 2.2");
		this.assertSpecObjectAttributes(testSpecObjects.get(4), "ID", "REQ_2",
				"Description", "", "Status", "StatusTest", "Priority",
				"RankingTest", "Risk", "RankingTest");

		assertEquals("REQ_1 -> REQ_2", testSpecObjects.get(1).getRelations()
				.get(0).getId());
		assertEquals("relatesto", testSpecObjects.get(1).getRelations().get(0)
				.getRelation());
		assertEquals("30", testSpecObjects.get(1).getRelations().get(0)
				.getSrcSpecId());
		assertEquals("30", testSpecObjects.get(1).getRelations().get(0)
				.getTgtSpecId());

		assertEquals("REQ_1 -> REQ_2", testSpecObjects.get(4).getRelations()
				.get(0).getId());
		assertEquals("relatesto", testSpecObjects.get(4).getRelations().get(0)
				.getRelation());
		assertEquals("30", testSpecObjects.get(4).getRelations().get(0)
				.getSrcSpecId());
		assertEquals("30", testSpecObjects.get(4).getRelations().get(0)
				.getTgtSpecId());

		assertEquals(1, testRelations.size());
		assertEquals("REQ_1 -> REQ_2", testRelations.get(0).getId());
		assertEquals("relatesto", testRelations.get(0)
				.getRelation());
		assertEquals("30", testRelations.get(0)
				.getSrcSpecId());
		assertEquals("30", testRelations.get(0)
				.getTgtSpecId());
	}

	@Test
	public void testReqIFIterator_ShouldFilterOutline() {
		final TestSpecification testSpecification = new TestSpecification();
		final List<TestSpecObject> testSpecObjects = new ArrayList<TestSpecObject>();

		ReqIF10Iterator iterator = new ReqIF10Iterator();
		final EList<Specification> specs = reqif.getCoreContent()
				.getSpecifications();
		iterator.iterateRecursivelyThrough(specs, new SpecificationCallback() {

			@Override
			public void call(SpecificationDTO specificationDto) {
				testSpecification.setLongName(specificationDto.getLongName());
				testSpecification.setTitle(specificationDto.getAttributeValue(0));
			}
		}, new SpecObjectCallback() {

			public void call(final SpecObjectDTO specObject, final int depth) {
				if (specObject.getTypeName().equals("HeadlineType")) {
					TestSpecObject testSpecObject = new TestSpecObject();
					testSpecObject.setType(specObject.getTypeName());
					for (AttributeValue av : specObject.getSpecObject()
							.getValues()) {
						AttributeDefinition ad = ReqIF10Util
								.getAttributeDefinition(av);
						TestAttribute attribute = new TestAttribute();
						attribute.setName(ad.getLongName());
						attribute.setValue(AttributeValueConverter
								.getDefaultValue(av));
						attribute.setXhtml(AttributeValueConverter
								.hasXhtmlContent(av));
						testSpecObject.getAttributes().add(attribute);
					}
					testSpecObjects.add(testSpecObject);
				}
			}
		});

		assertEquals("Spec", testSpecification.getLongName());
		assertEquals("Test-Specification", testSpecification.getTitle());

		assertEquals(3, testSpecObjects.size());
		this.assertSpecObjectAttributes(testSpecObjects.get(0), "ID",
				"Chapter 1");
		this.assertSpecObjectAttributes(testSpecObjects.get(1), "ID",
				"Chapter 2");
		this.assertSpecObjectAttributes(testSpecObjects.get(2), "ID",
				"Section 2.2");
	}

	private void assertAttribute(final String expectedName,
			final String expectedValue, final TestAttribute attribute) {
		assertEquals(expectedName, attribute.getName());
		assertEquals(expectedValue, attribute.getValue());

	}

	private void assertSpecObjectAttributes(final TestSpecObject specObject,
			String... args) {
		assertEquals(args.length / 2, specObject.getAttributes().size());
		for (int i = 0; i < specObject.getAttributes().size(); i++) {
			this.assertAttribute(args[2 * i], args[2 * i + 1], specObject
					.getAttributes().get(i));
		}
	}
}
