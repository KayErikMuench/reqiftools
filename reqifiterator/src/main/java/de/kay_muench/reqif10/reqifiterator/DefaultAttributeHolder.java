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

class DefaultAttributeHolder implements AttributeHolder {
	private List<String> attributes = new ArrayList<String>();

	@Override
	public void setRelevantAttributes(final List<String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void addRelevantAttributes(final List<String> attributes) {
		List<String> addAttributes = new ArrayList<String>(attributes);
		addAttributes.removeAll(this.attributes);
		this.attributes.addAll(addAttributes);

	}

	@Override
	public List<String> getAttributes() {
		return this.attributes;
	}

}