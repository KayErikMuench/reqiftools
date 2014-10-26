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


import de.kay_muench.reqif10.reqifcompiler.dto.RelationTypeDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.RequirementDTO;

/**
 * @author Kay Erik Münch
 * 
 */
public aspect LoggingAspect {
	pointcut addrelation(RelationTypeDTO type, RequirementDTO src,
			RequirementDTO dest) : execution(* ReqIF10Compiler.addRelation(..)) && args(type, src, dest);

	before(RelationTypeDTO type, RequirementDTO src, RequirementDTO dest) : addrelation(type, src, dest) {
		System.out.println(type.name() + " " + src.getName() + " "
				+ dest.getName());
	}

	pointcut export(String id) : execution(* ReqIF10Compiler.export(..)) && args(id);

	before(String id) : export(id) {
		System.out.println("creating export for " + id);
	}

	pointcut initialize() : execution(* ReqIF10Compiler.initialize(..));

	before() : initialize() {
		System.out.println("initializing compiler");
	}
}
