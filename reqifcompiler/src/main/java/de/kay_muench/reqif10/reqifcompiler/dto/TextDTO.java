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

public final class TextDTO {

	private String value;

	public void setValue(final String value) {
		this.value = value;
	}

	public String getText() {
		return value;
	}

	public static TextDTO newInstance() {
		return new TextDTO();
	}

}
