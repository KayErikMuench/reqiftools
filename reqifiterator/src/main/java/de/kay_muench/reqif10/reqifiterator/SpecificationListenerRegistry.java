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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SpecificationListenerRegistry {
	private class NullSpecificationListener implements SpecificationListener {

		@Override
		public void listenOn(SpecificationDTO spec) {
		}

		@Override
		public List<String> registeredFor() {
			return new ArrayList<String>();
		}

		@Override
		public void registerFor(String... strings) {
		}

		@Override
		public void registerFor(List<String> strings) {
		}

		@Override
		public void setRelevantAttributes(List<String> attributes) {
		}

		@Override
		public void addRelevantAttributes(List<String> attributes) {
		}
	}

	private Map<String, HashSet<SpecificationListener>> listeners = new HashMap<>();

	/**
	 * 
	 * @param type
	 * @return a listener. Always a valid listener is returned.
	 */
	public Set<SpecificationListener> getListener(final String type) {
		Set<SpecificationListener> listeners = this.listeners.get(type);
		if (listeners == null) {
			listeners = new HashSet<>();
			listeners.add(new NullSpecificationListener());
		}
		return listeners;
	}

	/**
	 * Register a listener
	 * 
	 * @param listener
	 */
	public void registerListener(final SpecificationListener listener) {
		if (listener == null)
			return;
		for (String type : listener.registeredFor()) {
			if (!this.listeners.containsKey(type)) {
				this.listeners.put(type, new HashSet<SpecificationListener>());
			}
			this.listeners.get(type).add(listener);
		}

	}

	/**
	 * Register a listener
	 * 
	 * @param type
	 * @param listener
	 */
	public void registerListener(final Set<SpecificationListener> listeners) {
		if (listeners == null)
			return;
		for (SpecificationListener listener : listeners)
			this.registerListener(listener);
	}
}