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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecRelation;

public final class SpecObjectDTO {
	private final SpecObject specObject;
	private final List<SpecRelationDTO> outgoingRelations = new ArrayList<SpecRelationDTO>();
	private final List<SpecRelationDTO> incomingRelations = new ArrayList<SpecRelationDTO>();

	private SpecObjectDTO(SpecObject specObject, List<SpecRelation> outgoing,
			List<SpecRelation> incoming) {
		this.specObject = specObject;

		SpecRelationDTO.Builder builder = SpecRelationDTO.Builder.newBuilder();
		for (SpecRelation relation : outgoing) {
			this.outgoingRelations.add(builder.fromRelation(relation).build());
		}
		for (SpecRelation relation : incoming) {
			this.incomingRelations.add(builder.fromRelation(relation).build());
		}
	}

	public SpecObject getSpecObject() {
		return specObject;
	}

	public List<SpecRelationDTO> getOutgoingRelations() {
		return outgoingRelations;
	}

	public List<SpecRelationDTO> getIncomingRelations() {
		return incomingRelations;
	}

	public String getTypeName() {
		return this.getSpecObject().getType().getLongName();
	}

	public static final class Builder {
		private final SpecObject specObject;
		private List<SpecRelation> outgoingRelations = new ArrayList<SpecRelation>();
		private List<SpecRelation> incomingRelations = new ArrayList<SpecRelation>();

		public static Builder newBuilder(final SpecObject specObject) {
			return new Builder(specObject);
		}

		private Builder(final SpecObject specObject) {
			this.specObject = specObject;
		}

		public Builder setOutgoingRelations(List<SpecRelation> outgoingRelations) {
			this.outgoingRelations = outgoingRelations;
			return this;
		}

		public Builder setIncomingRelations(List<SpecRelation> incomingRelations) {
			this.incomingRelations = incomingRelations;
			return this;
		}

		public SpecObjectDTO build() {
			return new SpecObjectDTO(specObject, outgoingRelations,
					incomingRelations);
		}
	}
}
