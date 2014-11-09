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


import org.apache.log4j.Logger;

import de.kay_muench.reqif10.reqifcompiler.dto.RelationTypeDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.RequirementDTO;

/**
 * @author Kay Erik Münch
 * 
 */
public aspect LoggingAspect {
	private Logger logger = Logger.getLogger("testlogger");
	
	pointcut addrelation(RelationTypeDTO type, RequirementDTO src,
			RequirementDTO dest) : execution(* ReqIF10Compiler.addRelation(..)) && args(type, src, dest);

	before(RelationTypeDTO type, RequirementDTO src, RequirementDTO dest) : addrelation(type, src, dest) {
		logger.info(type.name() + " " + src.getName() + " "
				+ dest.getName());
	}

	pointcut export(String id) : execution(* ReqIF10Compiler.export(..)) && args(id);

	before(String id) : export(id) {
		logger.info("creating export for " + id);
	}

	pointcut initialize() : execution(* ReqIF10Compiler.initialize(..));

	before() : initialize() {
		logger.info("initializing compiler");
	}
}
