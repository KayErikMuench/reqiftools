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

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.SpecRelation;
import org.eclipse.rmf.reqif10.Specification;

class ReqIF10ListenerIterator {
	private final ReqIF10Iterator iterator = new ReqIF10Iterator();
	private final SpecObjectListenerRegistry specObjectListenerRegistry = new SpecObjectListenerRegistry();
	private final SpecificationListenerRegistry specificationRegistry = new SpecificationListenerRegistry();
	private final SpecRelationListenerRegistry specRelationRegistry = new SpecRelationListenerRegistry();

	public void iterateRecursivelyThrough(EList<Specification> specs) {
		this.iterator.iterateRecursivelyThrough(specs,
				new SpecificationCallback() {

					@Override
					public void call(SpecificationDTO specificationDto) {
						for (SpecificationListener listener : specificationRegistry
								.getListener(specificationDto.getTypeName())) {
							listener.listenOn(specificationDto);
						}

					}
				}, new SpecObjectCallback() {

					@Override
					public void call(SpecObjectDTO specObject, int depth) {
						specObjectListenerRegistry
								.getListener(specObject.getTypeName())
								.setDepth(depth).listenOn(specObject);
					}
				});

	}

	public void registerSpecObjectListeners(Set<SpecObjectListener> listeners) {
		this.specObjectListenerRegistry.registerListener(listeners);
	}

	public void registerSpecificationListeners(
			Set<SpecificationListener> listeners) {
		this.specificationRegistry.registerListener(listeners);
	}

	public void iterateThrough(EList<SpecRelation> relations) {
		this.iterator.iterateThrough(relations, new SpecRelationCallback() {

			@Override
			public void call(SpecRelationDTO relationDTO) {
				specRelationRegistry.getListener(relationDTO.getTypeName())
						.listenOn(relationDTO);
			}
		});

	}

	public void registerSpecRelationListeners(
			Set<SpecRelationListener> listeners) {
		this.specRelationRegistry.registerListener(listeners);
	}
}