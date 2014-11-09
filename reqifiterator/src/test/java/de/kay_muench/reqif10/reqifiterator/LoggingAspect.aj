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

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecRelation;

public aspect LoggingAspect {
	private Logger logger = Logger.getLogger("testlogger");

	pointcut incomingrelations(SpecObject target) : execution(* ReqIF10Finder.getIncomingSpecRelationsFor(..)) && args(target);

	after(SpecObject target) returning(List<SpecRelation> relations) : incomingrelations(target) {
		for (SpecRelation relation : relations) {
			logger.info("incoming for target " + target.getIdentifier() + ": "
					+ relation.getIdentifier());
		}
	}

	pointcut outgoingrelations(SpecObject source) : execution(* ReqIF10Finder.getOutgoingSpecRelationsFor(..)) && args(source);

	after(SpecObject source) returning(List<SpecRelation> relations) : outgoingrelations(source) {
		for (SpecRelation relation : relations) {
			logger.info("outgoing for source " + source.getIdentifier() + ": "
					+ relation.getIdentifier());
		}
	}

	pointcut registeredfor() : execution(* SpecObjectListener.registeredFor(..));

	after() returning(List<String> types) : registeredfor() {
		logger.info("Registered listener for: " + types.toString());
	}

	pointcut registeredforspecification() : execution(* SpecificationListener.registeredFor(..));

	after() returning(List<String> types) : registeredforspecification() {
		logger.info("Registered listener for specification type: " + types.toString());
	}

	pointcut registeredforspecrelation() : execution(* SpecRelationListener.registeredFor(..));

	after() returning(List<String> types) : registeredforspecrelation() {
		logger.info("Registered listener for spec-relation type: " + types.toString());
	}

	pointcut specobject_listenon(SpecObjectDTO dto) : execution(* SpecObjectListener.listenOn(..)) && args(dto);

	before(SpecObjectDTO dto) : specobject_listenon(dto) {
		logger.info("Listen on: " + dto.getTypeName());
	}

	pointcut nullspecobject_listenon(SpecObjectDTO dto) : execution(* *.NullSpecObjectListener.listenOn(..)) && args(dto);

	before(SpecObjectDTO dto) : nullspecobject_listenon(dto) {
		logger.info("Using noop listener for: " + dto.getTypeName());
	}

	pointcut specobjectcallback(SpecObjectDTO dto, int depth) : execution(* SpecObjectCallback.call(..)) && args(dto, depth);

	before(SpecObjectDTO dto, int depth) : specobjectcallback(dto, depth) {
		logger.info("Calling spec-object callback for " + dto.getTypeName());
	}

	pointcut specificationcallback(SpecificationDTO dto) : execution(* SpecificationCallback.call(..)) && args(dto);

	before(SpecificationDTO dto) : specificationcallback(dto) {
		logger.info("Calling specification callback for " + dto.getTypeName());
	}

	pointcut specification_listenon(SpecificationDTO dto) : execution(* SpecificationListener.listenOn(..)) && args(dto);

	before(SpecificationDTO dto) : specification_listenon(dto) {
		logger.info("Listen on specification type: " + dto.getTypeName());
	}

	pointcut nullspecification_listenon(SpecificationDTO dto) : execution(* *.NullSpecificationListener.listenOn(..)) && args(dto);

	before(SpecificationDTO dto) : nullspecification_listenon(dto) {
		logger.info("Using noop listener for: " + dto.getTypeName());
	}

	pointcut specrelationcallback(SpecRelationDTO dto) : execution(* SpecRelationCallback.call(..)) && args(dto);

	before(SpecRelationDTO dto) : specrelationcallback(dto) {
		logger.info("Calling spec-relation callback for " + dto.getTypeName());
	}

	pointcut specrelation_listenon(SpecRelationDTO dto) : execution(* SpecRelationListener.listenOn(..)) && args(dto);

	before(SpecRelationDTO dto) : specrelation_listenon(dto) {
		logger.info("Listen on specification type: " + dto.getTypeName());
	}

	pointcut nullspecrelation_listenon(SpecRelationDTO dto) : execution(* *.NullSpecRelationListener.listenOn(..)) && args(dto);

	before(SpecRelationDTO dto) : nullspecrelation_listenon(dto) {
		logger.info("Using noop listener for: " + dto.getTypeName());
	}
}
