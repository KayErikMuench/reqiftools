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

class SpecObjectListenerRegistry {
	/**
	 * Null Object
	 */
	private class NullSpecObjectListener implements SpecObjectListener {

		@Override
		public void listenOn(SpecObjectDTO object) {
		}

		@Override
		public SpecObjectListener setDepth(int depth) {
			return this;
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
		public void setRelevantAttributes(List<String> columns) {
		}

		@Override
		public void addRelevantAttributes(List<String> columns) {

		}

		@Override
		public void setMessageBundle(ResourceBundle messages) {
		}
	}

	private Map<String, SpecObjectListener> listeners = new HashMap<String, SpecObjectListener>();

	/**
	 * 
	 * @param type
	 * @param listener
	 *            Register a listener for a type
	 */
	public void registerListener(final SpecObjectListener listener) {
		if (listener == null)
			return;
		for (String type : listener.registeredFor()) {
			if (type != null && !type.isEmpty())
				this.listeners.put(type, listener);
		}
	}

	/**
	 * 
	 * @param types
	 * @param listener
	 *            Register listener for types
	 */
	public void registerListener(final Set<SpecObjectListener> listeners) {
		if (listeners == null)
			return;
		for (SpecObjectListener listener : listeners)
			this.registerListener(listener);
	}

	/**
	 * 
	 * @param type
	 * @return Always a valid listener is returned
	 */
	public SpecObjectListener getListener(final String type) {
		SpecObjectListener listener = this.listeners.get(type);
		if (listener == null)
			return new NullSpecObjectListener();
		return listener;
	}
}