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

class DefaultRegistrableListener implements RegistrableListener {
	private final List<String> register = new ArrayList<String>();

	@Override
	public List<String> registeredFor() {
		return this.register;
	}

	@Override
	public void registerFor(String... strings) {
		for (String s : strings) {
			this.register.add(s);
		}
	}

	@Override
	public void registerFor(List<String> strings) {
		for (String s : strings) {
			this.register.add(s);
		}
	}

}