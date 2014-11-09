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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

class SpecRelationListenerRegistry {
	private class NullSpecRelationListener implements SpecRelationListener {

		@Override
		public void listenOn(SpecRelationDTO specRelationDTO) {
		}

		@Override
		public List<String> registeredFor() {
			return Collections.emptyList();
		}

		@Override
		public void registerFor(String... strings) {
		}

		@Override
		public void registerFor(List<String> strings) {
		}

		@Override
		public void setMessageBundle(ResourceBundle messages) {
		}

		@Override
		public void setRelevantAttributes(List<String> attributes) {
		}

		@Override
		public void addRelevantAttributes(List<String> attributes) {
		}
	}

	private Map<String, SpecRelationListener> listeners = new HashMap<>();

	public void registerListener(final SpecRelationListener listener) {
		if (listener == null)
			return;
		for (String type : listener.registeredFor()) {
			this.listeners.put(type, listener);
		}
	}

	public void registerListener(final Set<SpecRelationListener> listeners) {
		if (listeners == null)
			return;
		for (SpecRelationListener listener : listeners)
			this.registerListener(listener);
	}

	public SpecRelationListener getListener(final String type) {
		SpecRelationListener listener = this.listeners.get(type);
		if (listener == null)
			return new NullSpecRelationListener();
		return listener;

	}
}