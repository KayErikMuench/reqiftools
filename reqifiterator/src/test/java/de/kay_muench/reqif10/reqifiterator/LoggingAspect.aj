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

import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecRelation;

public aspect LoggingAspect {

	pointcut incomingrelations(SpecObject target) : execution(* ReqIF10Finder.getIncomingSpecRelationsFor(..)) && args(target);

	after(SpecObject target) returning(List<SpecRelation> relations) : incomingrelations(target) {
		for (SpecRelation relation : relations) {
			System.out.println("incoming for target " + target.getIdentifier() + ": " + relation.getIdentifier());		
		}
	}
	
	pointcut outgoingrelations(SpecObject source) : execution(* ReqIF10Finder.getOutgoingSpecRelationsFor(..)) && args(source);

	after(SpecObject source) returning(List<SpecRelation> relations) : outgoingrelations(source) {
		for (SpecRelation relation : relations) {
			System.out.println("outgoing for source " + source.getIdentifier() + ": " + relation.getIdentifier());		
		}
	}
}
