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

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.Specification;

public final class ReqIF10Iterator {
	public void iterateRecursivelyThrough(
			final EList<Specification> specifications,
			final SpecObjectCallback specObjectCallback) {
		for (Specification specification : specifications) {
			this.iterateRecursivelyThrough(specification, specObjectCallback);
		}
	}

	public void iterateRecursivelyThrough(final Specification specification,
			final SpecObjectCallback specObjectCallback) {
		this.iterateRecursivelyThrough(specification.getChildren(), 0,
				specObjectCallback);
	}

	private void iterateRecursivelyThrough(
			final EList<SpecHierarchy> specHierarchies, final int depth,
			final SpecObjectCallback specObjectCallback) {
		for (SpecHierarchy hierarchy : specHierarchies) {
			if (hierarchy.getObject() != null) {
				SpecObjectDTO.Builder builder = SpecObjectDTO.Builder
						.newBuilder(hierarchy.getObject())
						.setOutgoingRelations(
								ReqIF10Finder.getOutgoingSpecRelationsFor(hierarchy
								.getObject()))
						.setIncomingRelations(
								ReqIF10Finder.getIncomingSpecRelationsFor(hierarchy
								.getObject()));
				specObjectCallback.call(builder.build(), depth);
			}

			this.iterateRecursivelyThrough(hierarchy.getChildren(), depth + 1,
					specObjectCallback);
		}

	}
}
