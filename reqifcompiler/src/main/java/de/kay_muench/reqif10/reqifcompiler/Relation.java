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


public class Relation {
	private final RelationTypeDTO relationKind;
	private final RequirementDTO src;
	private final RequirementDTO dest;
	
	public Relation(RelationTypeDTO relationType, RequirementDTO src, RequirementDTO dest) {
		this.relationKind = relationType;
		this.src = src;
		this.dest = dest;
	}

	public RelationTypeDTO getRelationKind() {
		return relationKind;
	}

	public RequirementDTO getSrc() {
		return src;
	}

	public RequirementDTO getDest() {
		return dest;
	}
	
	public String getName() {
		return this.src.getName() + " -> " + this.dest.getName();
	}
}
