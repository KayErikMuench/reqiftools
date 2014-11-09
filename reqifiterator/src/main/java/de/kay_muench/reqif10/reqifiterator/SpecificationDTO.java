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

import org.eclipse.rmf.reqif10.AttributeDefinition;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;

public class SpecificationDTO {
	private final Specification specification;

	private SpecificationDTO(final Specification specification) {
		this.specification = specification;
	}

	public String getTypeName() {
		return specification.getLongName();
	}

	public String getAttributeValue(int index) {
		if (specification.getValues().size() > index) {
			AttributeValue av = specification.getValues().get(index);
			AttributeDefinition ad = ReqIF10Util.getAttributeDefinition(av);
			if (ad != null) {
				return ReqIF10Util.getTheValue(av).toString();
			}
		}
		return null;
	}

	public static final class Builder {
		private final Specification specification;

		public static Builder newBuilder(final Specification specification) {
			return new Builder(specification);
		}

		private Builder(final Specification specification) {
			this.specification = specification;
		}

		public SpecificationDTO build() {
			return new SpecificationDTO(specification);
		}
	}
}
