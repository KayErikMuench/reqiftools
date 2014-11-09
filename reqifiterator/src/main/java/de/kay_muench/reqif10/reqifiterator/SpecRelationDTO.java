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

import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.SpecRelation;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;

public final class SpecRelationDTO {
	private final SpecRelation relation;

	private SpecRelationDTO(final SpecRelation relation) {
		this.relation = relation;
	}

	public SpecRelation getRelation() {
		return relation;
	}

	public String getValueFor(final String label) {
		String value = "";
		AttributeValue avRel = ReqIF10Util.getAttributeValueForLabel(
				this.getRelation(), label);
		if (avRel != null && avRel instanceof AttributeValueString) {
			value = ((AttributeValueString) avRel).getTheValue();
		}
		return value;
	}

	public String getTypeName() {
		return this.relation.getLongName();
	}

	public static final class Builder {
		private SpecRelation relation;

		private Builder() {
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder fromRelation(final SpecRelation relation) {
			this.relation = relation;
			return this;
		}

		public SpecRelationDTO build() {
			return new SpecRelationDTO(this.relation);
		}
	}
}
