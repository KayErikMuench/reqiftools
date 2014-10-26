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
package de.kay_muench.reqif10.reqifcompiler;

import java.util.UUID;

public final class IdentifierManager {
	private static boolean predictabilityEnabled = false;
	private static long predictableId = 1L;

	public static void enablePredictableId() {
		predictabilityEnabled = true;
	}

	public static String generateIdentifier() {
		if (predictabilityEnabled) {
			predictableId += 1L;
			return "" + predictableId;
		}
		return "requie-tool-" + UUID.randomUUID().toString();
	}
}
