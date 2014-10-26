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

import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.rmf.reqif10.xhtml.XhtmlFactory;
import org.eclipse.rmf.reqif10.xhtml.XhtmlLiType;
import org.eclipse.rmf.reqif10.xhtml.XhtmlUlType;

public final class XhtmlUlDTO {
	private final XhtmlUlType ul = XhtmlFactory.eINSTANCE.createXhtmlUlType();
	private String title;

	public void addEntry(final String entry) {
		XhtmlLiType li = XhtmlFactory.eINSTANCE.createXhtmlLiType();
		li.getXhtmlFlowMix().add(FeatureMapUtil.createTextEntry(entry));
		getUl().getLi().add(li);
	}

	public void add(XhtmlUlDTO dto) {
		if (dto.getTitle() != null) {
			XhtmlLiType li = XhtmlFactory.eINSTANCE.createXhtmlLiType();
			li.getXhtmlFlowMix().add(
					FeatureMapUtil.createTextEntry(dto.getTitle()));
			li.getUl().add(dto.getUl());
			getUl().getLi().add(li);
		}
	}

	private String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public XhtmlUlType getUl() {
		return ul;
	}

	public static XhtmlUlDTO newInstance() {
		return new XhtmlUlDTO();
	}
}
