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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.emf.common.util.Enumerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.kay_muench.reqif10.reqifcompiler.dto.ImageRefDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.RankingDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.RelationTypeDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.RequirementDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.StatusDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.TextDTO;
import de.kay_muench.reqif10.reqifcompiler.xhtml.XhtmlBuilder;

public class ReqIF10CompilerTest {

	private ReqIF10Compiler compiler;

	private enum StatusTestEnumerator implements Enumerator {
		TEST(0, "StatusTest", "StatusTest");
		private final int value;
		private final String name;
		private final String literal;

		public static final List<StatusTestEnumerator> VALUES = Collections
				.unmodifiableList(Arrays.asList(TEST));

		private StatusTestEnumerator(int value, String name, String literal) {
			this.value = value;
			this.name = name;
			this.literal = literal;
		}

		public String getLiteral() {
			return this.literal;
		}

		public String getName() {
			return this.name;
		}

		public int getValue() {
			return this.value;
		}

	}

	private enum RankingTestEnumerator implements Enumerator {
		RTEST(0, "RankingTest", "RankingTest");
		private final int value;
		private final String name;
		private final String literal;

		public static final List<RankingTestEnumerator> VALUES = Collections
				.unmodifiableList(Arrays.asList(RTEST));

		private RankingTestEnumerator(int value, String name, String literal) {
			this.value = value;
			this.name = name;
			this.literal = literal;
		}

		public String getLiteral() {
			return this.literal;
		}

		public String getName() {
			return this.name;
		}

		public int getValue() {
			return this.value;
		}
	}

	private enum RelationTestEnumerator implements Enumerator {
		RELATESTO(2, "relatesto", "relatesto");
		private final int value;
		private final String name;
		private final String literal;

		private RelationTestEnumerator(int value, String name, String literal) {
			this.value = value;
			this.name = name;
			this.literal = literal;
		}

		public String getLiteral() {
			return this.literal;
		}

		public String getName() {
			return this.name;
		}

		public int getValue() {
			return this.value;
		}

	}

	@BeforeClass
	public static void setUpBeforeClass() {
		ConsoleAppender appender = new ConsoleAppender();
		appender.setName("testappender");
		appender.setLayout(new PatternLayout("%-5p [%d{dd MMM yyyy HH:mm:ss,SSS}]: %m%n"));
		appender.setTarget(ConsoleAppender.SYSTEM_OUT);
		appender.activateOptions();
		
		Logger.getLogger("org.apache.xmlgraphics").addAppender(appender);
		Logger.getLogger("org.apache.xmlgraphics").setLevel(Level.INFO);
		
		Logger.getLogger("testlogger").addAppender(appender);
		
		RelationTypeDTO.DEFAULT = RelationTestEnumerator.RELATESTO;
		RankingDTO.DEFAULT = RankingTestEnumerator.RTEST.getValue();
		RankingDTO.VALUES = RankingTestEnumerator.VALUES;
		StatusDTO.DEFAULT = StatusTestEnumerator.TEST.getValue();
		StatusDTO.VALUES = StatusTestEnumerator.VALUES;
		IdentifierManager.enablePredictableId();
		DateManager.enablePredictableId();
	}

	@Before
	public void setUp() throws Exception {
		compiler = ReqIF10Compiler.newInstance();
		compiler.initialize();
	}

	@Test
	public void testUT_1000() throws Exception {

		compiler.addSpecification("Test-Specification");
		compiler.shiftLevelDown("Chapter 1");

		TextDTO textDTO = TextDTO.newInstance();
		textDTO.setValue("Hello");
		XhtmlBuilder builder = XhtmlBuilder.newInstance();
		builder.append(textDTO);

		StatusDTO status = StatusDTO.newInstance();
		status.fromEnumerator(StatusTestEnumerator.TEST);
		RankingDTO priority = RankingDTO.newInstance();
		priority.fromEnumerator(RankingTestEnumerator.RTEST);
		RankingDTO risk = RankingDTO.newInstance();
		risk.fromEnumerator(RankingTestEnumerator.RTEST);

		compiler.addSpecObject("REQ_1", builder, null, status, priority, risk);

		compiler.shiftLevelUp();

		compiler.shiftLevelDown("Chapter 2");
		compiler.shiftLevelDown("Section 2.2");

		XhtmlBuilder builder2 = XhtmlBuilder.newInstance();
		builder2.append(textDTO);
		compiler.addSpecObject("REQ_2", builder2, null, status, priority,
				risk);
		RelationTypeDTO type = RelationTypeDTO.newInstance();
		type.fromEnumerator(RelationTestEnumerator.RELATESTO);
		RequirementDTO src = RequirementDTO.newInstance();
		src.setName("REQ_1");
		RequirementDTO dest = RequirementDTO.newInstance();
		dest.setName("REQ_2");
		compiler.addRelation(type, src, dest);

		compiler.shiftLevelUp();
		compiler.shiftLevelUp();

		compiler.addSpecRelations();

		final String expected = FileUtils.readFileToString(new File(
				"src/test/resources/UT_1000.reqif"), "UTF-8");
		final String actual = compiler.export("test");
		//System.out.println(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testUT_1001() throws Exception {

		compiler.addSpecification("Test-Specification");
		compiler.shiftLevelDown("Chapter 1");

		TextDTO textDTO = TextDTO.newInstance();
		textDTO.setValue("Hello");

		ImageRefDTO imageRefDTO = ImageRefDTO.newInstance();
		imageRefDTO.setUri("src/test/resources/flowers.jpg");

		XhtmlBuilder builder = XhtmlBuilder.newInstance();
		builder.append(textDTO);
		builder.append(imageRefDTO);

		StatusDTO status = StatusDTO.newInstance();
		status.fromEnumerator(StatusTestEnumerator.TEST);
		RankingDTO importance = RankingDTO.newInstance();
		importance.fromEnumerator(RankingTestEnumerator.RTEST);
		RankingDTO risk = RankingDTO.newInstance();
		risk.fromEnumerator(RankingTestEnumerator.RTEST);

		compiler.addSpecObject("REQ_1", builder, null, status, importance, risk);

		compiler.shiftLevelUp();

		final String expected = FileUtils.readFileToString(new File(
				"src/test/resources/UT_1001.reqif"), "UTF-8");
		final String actual = compiler.export("test").replaceAll("&#xD;", "")
				.replaceAll("&#xA;", "");
		// System.out.println(actual);
		assertEquals(expected, actual);
	}
}
