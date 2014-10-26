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
package de.kay_muench.reqif10.reqifcompiler.types.simple;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rmf.reqif10.DatatypeDefinition;

public class SimpleTypesRegistry {
	public SimpleTypesRegistry() throws Exception {
	}

	private String32k reqifString = new String32k();
	private DateType reqifDate = new DateType();
	private XhtmlType reqifXhtml = new XhtmlType();
	private Headline reqifHeadline = new Headline();
	private RequirementStatus reqifRequirementStatus = new RequirementStatus();
	private RequirementRanking reqifRequirementRanking = new RequirementRanking();

	public String32k getReqIFString32kType() {
		return this.reqifString;
	}

	public DateType getReqIFDateType() {
		return this.reqifDate;
	}

	public XhtmlType getReqIFXhtmlType() {
		return this.reqifXhtml;
	}

	public Headline getReqIFHeadlineType() {
		return this.reqifHeadline;
	}

	public RequirementStatus getReqIFRequirementStatusType() {
		return reqifRequirementStatus;
	}

	public RequirementRanking getReqIFRequirementRankingType() {
		return this.reqifRequirementRanking;
	}

	public List<DatatypeDefinition> getDatatypes() {
		List<DatatypeDefinition> definitions = new LinkedList<DatatypeDefinition>();
		definitions.add(this.reqifString.getDef());
		definitions.add(this.reqifDate.getDef());
		definitions.add(this.reqifXhtml.getDef());
		definitions.add(this.reqifHeadline.getDef());
		definitions.add(this.reqifRequirementStatus.getDef());
		definitions.add(this.reqifRequirementRanking.getDef());
		return definitions;
	}
}
