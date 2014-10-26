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
package de.kay_muench.reqif10.reqifcompiler.dto;

import org.eclipse.emf.common.util.Enumerator;

class EnumeratorDTO {

	private Enumerator enumerator;

	public void fromEnumerator(final Enumerator enumerator) {
		this.enumerator = enumerator;
	}

	public int getValue() {
		return enumerator.getValue();
	}
}