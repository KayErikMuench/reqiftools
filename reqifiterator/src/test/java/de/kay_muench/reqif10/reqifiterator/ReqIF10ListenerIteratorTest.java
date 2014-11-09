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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecRelation;
import org.eclipse.rmf.reqif10.Specification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.kay_muench.reqif10.reqifparser.ReqIF10Parser;

public class ReqIF10ListenerIteratorTest {

	private ReqIF reqif;
	private SpecObjectListener specObjectListener;
	private SpecificationListener specificationListener;
	private SpecRelationListener specRelationListener;
	private final List<String> listenedTypes = new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() {
		ConsoleAppender appender = new ConsoleAppender();
		appender.setName("testappender");
		appender.setLayout(new PatternLayout(
				"%-5p [%d{dd MMM yyyy HH:mm:ss,SSS}]: %m%n"));
		appender.setTarget(ConsoleAppender.SYSTEM_OUT);
		appender.activateOptions();

		Logger.getLogger("testlogger").addAppender(appender);

	}

	@Before
	public void setUp() throws Exception {
		ReqIF10Parser parser = new ReqIF10Parser();
		parser.setReqIFFilename("src/test/resources/UT_1000.reqif");
		this.reqif = parser.parseReqIFContent();
		specObjectListener = new SpecObjectListener() {

			RegistrableListener delegateRegistrableListener = new DefaultRegistrableListener();
			AttributeHolder delegateAttributeHolder = new DefaultAttributeHolder();

			@Override
			public void setMessageBundle(ResourceBundle messages) {
			}

			@Override
			public List<String> registeredFor() {
				return this.delegateRegistrableListener.registeredFor();
			}

			@Override
			public void registerFor(List<String> strings) {
				this.delegateRegistrableListener.registerFor(strings);
			}

			@Override
			public void registerFor(String... strings) {
				this.delegateRegistrableListener.registerFor(strings);
			}

			@Override
			public SpecObjectListener setDepth(int depth) {
				return this;
			}

			@Override
			public void setRelevantAttributes(List<String> attributes) {
				this.delegateAttributeHolder.setRelevantAttributes(attributes);
			}

			@Override
			public void listenOn(SpecObjectDTO object) {
				listenedTypes.add(object.getTypeName());
			}

			@Override
			public void addRelevantAttributes(List<String> attributes) {
				this.delegateAttributeHolder.addRelevantAttributes(attributes);
			}
		};

		specificationListener = new SpecificationListener() {

			RegistrableListener delegateRegistrableListener = new DefaultRegistrableListener();

			@Override
			public void setRelevantAttributes(List<String> attributes) {
			}

			@Override
			public void addRelevantAttributes(List<String> attributes) {
			}

			@Override
			public List<String> registeredFor() {
				return this.delegateRegistrableListener.registeredFor();
			}

			@Override
			public void registerFor(List<String> strings) {
				this.delegateRegistrableListener.registerFor(strings);
			}

			@Override
			public void registerFor(String... strings) {
				this.delegateRegistrableListener.registerFor(strings);
			}

			@Override
			public void listenOn(SpecificationDTO specification) {
				listenedTypes.add(specification.getTypeName());
			}
		};

		specRelationListener = new SpecRelationListener() {

			RegistrableListener delegateRegistrableListener = new DefaultRegistrableListener();

			@Override
			public void setRelevantAttributes(List<String> attributes) {
			}

			@Override
			public void addRelevantAttributes(List<String> attributes) {
			}

			@Override
			public void setMessageBundle(ResourceBundle messages) {
			}

			@Override
			public List<String> registeredFor() {
				return this.delegateRegistrableListener.registeredFor();
			}

			@Override
			public void registerFor(List<String> strings) {
				this.delegateRegistrableListener.registerFor(strings);
			}

			@Override
			public void registerFor(String... strings) {
				this.delegateRegistrableListener.registerFor(strings);
			}

			@Override
			public void listenOn(SpecRelationDTO specRelationDTO) {
				listenedTypes.add(specRelationDTO.getTypeName());
			}
		};
	}

	@Test
	public void testSpecObjectListener_shouldListenOnHeadlineTypes() {
		final EList<Specification> specs = reqif.getCoreContent()
				.getSpecifications();

		ReqIF10ListenerIterator iterator = new ReqIF10ListenerIterator();

		specObjectListener.registerFor("HeadlineType");
		Set<SpecObjectListener> listeners = new HashSet<>();
		listeners.add(specObjectListener);
		iterator.registerSpecObjectListeners(listeners);
		iterator.iterateRecursivelyThrough(specs);

		assertEquals(
				Arrays.asList("HeadlineType", "HeadlineType", "HeadlineType"),
				listenedTypes);
	}

	@Test
	public void testSpecObjectListener_shouldListenOnHeadlineTypesAndConfigurationSpecObjectTypes() {
		final EList<Specification> specs = reqif.getCoreContent()
				.getSpecifications();

		ReqIF10ListenerIterator iterator = new ReqIF10ListenerIterator();

		specObjectListener.registerFor("HeadlineType",
				"ConfigurationSpecObjectType");
		Set<SpecObjectListener> listeners = new HashSet<>();
		listeners.add(specObjectListener);
		iterator.registerSpecObjectListeners(listeners);
		iterator.iterateRecursivelyThrough(specs);

		assertEquals(Arrays.asList("HeadlineType",
				"ConfigurationSpecObjectType", "HeadlineType", "HeadlineType",
				"ConfigurationSpecObjectType"), listenedTypes);
	}

	@Test
	public void testSpecObjectListener_shouldListenOnSpecifications() {
		final EList<Specification> specs = reqif.getCoreContent()
				.getSpecifications();

		ReqIF10ListenerIterator iterator = new ReqIF10ListenerIterator();

		specificationListener.registerFor("Spec");
		Set<SpecificationListener> listeners = new HashSet<>();
		listeners.add(specificationListener);
		iterator.registerSpecificationListeners(listeners);
		iterator.iterateRecursivelyThrough(specs);

		assertEquals(Arrays.asList("Spec"), listenedTypes);
	}

	@Test
	public void testSpecObjectListener_shouldListenOnRelations() {
		final EList<SpecRelation> relations = reqif.getCoreContent()
				.getSpecRelations();

		ReqIF10ListenerIterator iterator = new ReqIF10ListenerIterator();

		specRelationListener.registerFor("SpecRelation");
		Set<SpecRelationListener> listeners = new HashSet<>();
		listeners.add(specRelationListener);
		iterator.registerSpecRelationListeners(listeners);
		iterator.iterateThrough(relations);

		assertEquals(Arrays.asList("SpecRelation"), listenedTypes);
	}

}
