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
package de.kay_muench.reqif10.reqifparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.AttributeDefinition;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;
import org.junit.Before;
import org.junit.Test;

public class ReqIF10ParserTest {

	private class TestAttributeValueCollector {
		private String attributeDefinitionLongName = "";
		private String value = "";

		public String getAttributeDefinitionLongName() {
			return attributeDefinitionLongName;
		}

		public void setAttributeDefinitionLongName(
				String attributeDefinitionLongName) {
			this.attributeDefinitionLongName = attributeDefinitionLongName;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private class TestSpecObjectCollector {
		private String identifier = "";
		private String specObjectTypeLongName = "";
		private final List<TestAttributeValueCollector> attributeValueCollectors = new ArrayList<TestAttributeValueCollector>();

		public String getIdentifier() {
			return identifier;
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		public String getSpecObjectTypeLongName() {
			return specObjectTypeLongName;
		}

		public void setSpecObjectTypeLongName(String specObjectTypeLongName) {
			this.specObjectTypeLongName = specObjectTypeLongName;
		}

		public List<TestAttributeValueCollector> getAttributeValueCollectors() {
			return attributeValueCollectors;
		}
	}

	ReqIF10Parser parser;

	@Before
	public void setUp() throws Exception {
		parser = new ReqIF10Parser();
		parser.setReqIFFilename("src/test/resources/UT_1000.reqif");
	}

	@Test
	public void testParseReqIFContent() {
		List<TestSpecObjectCollector> collectors = new ArrayList<TestSpecObjectCollector>();

		final ReqIF reqif = parser.parseReqIFContent();
		final String reqSpecTitle = reqif.getTheHeader().getTitle();

		final EList<Specification> specs = reqif.getCoreContent()
				.getSpecifications();
		String specLongName = "";
		String specTitle = "";
		if (specs.size() > 0) {
			Specification spec = specs.get(0);
			specLongName = spec.getLongName();
			if (spec.getValues().size() > 0) {
				AttributeValue av = spec.getValues().get(0);
				AttributeDefinition ad = ReqIF10Util.getAttributeDefinition(av);
				if (ad != null) {
					specTitle = ReqIF10Util.getTheValue(av).toString();
				}
			}
			final EList<SpecHierarchy> children = spec.getChildren();
			if (children.size() > 0) {
				for (SpecHierarchy child : children) {

					if (child.getObject() != null) {
						SpecObject specObject = child.getObject();
						if (!specObject.eIsProxy()) {
							TestSpecObjectCollector collector = new TestSpecObjectCollector();
							collector.setIdentifier(specObject.getIdentifier());
							collector.setSpecObjectTypeLongName(specObject
									.getType().getLongName());
							if (specObject.getValues().size() > 0) {
								AttributeValue av1 = specObject.getValues()
										.get(0);
								AttributeDefinition ad1 = ReqIF10Util
										.getAttributeDefinition(av1);
								TestAttributeValueCollector attributeValueCollector = new TestAttributeValueCollector();
								attributeValueCollector
										.setAttributeDefinitionLongName(ad1
												.getLongName());
								attributeValueCollector.setValue(ReqIF10Util
										.getTheValue(av1).toString());
								collector.getAttributeValueCollectors().add(
										attributeValueCollector);
							}
							collectors.add(collector);
						} 
					}
				}
			}
		}

		assertTrue(parser.getDiagnostics().isEmpty());
		assertEquals("src/test/resources/UT_1000.reqif",
				parser.getReqIFFilename());
		assertEquals(1, specs.size());
		assertEquals("test", reqSpecTitle);
		assertEquals("Spec", specLongName);
		assertEquals("Test-Specification", specTitle);
		assertEquals(2, collectors.size());
		assertEquals("31", collectors.get(0).getIdentifier());
		assertEquals("35", collectors.get(1).getIdentifier());
		assertEquals("HeadlineType", collectors.get(0)
				.getSpecObjectTypeLongName());
		assertEquals("HeadlineType", collectors.get(1)
				.getSpecObjectTypeLongName());
		assertEquals(1, collectors.get(0).getAttributeValueCollectors().size());
		assertEquals("ID",
				collectors.get(0).getAttributeValueCollectors().get(0)
						.getAttributeDefinitionLongName());
		assertEquals("Chapter 1", collectors.get(0)
				.getAttributeValueCollectors().get(0).getValue());
		assertEquals(1, collectors.get(1).getAttributeValueCollectors().size());
		assertEquals("ID",
				collectors.get(1).getAttributeValueCollectors().get(0)
						.getAttributeDefinitionLongName());
		assertEquals("Chapter 2", collectors.get(1)
				.getAttributeValueCollectors().get(0).getValue());
	}

}
