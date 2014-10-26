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
package de.kay_muench.reqif10.reqifcompiler.xhtml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.rmf.reqif10.xhtml.XhtmlDivType;
import org.eclipse.rmf.reqif10.xhtml.XhtmlFactory;
import org.eclipse.rmf.reqif10.xhtml.XhtmlObjectType;
import org.eclipse.rmf.reqif10.xhtml.XhtmlPType;

import de.kay_muench.imageinformationprovider.ImageDataUriProvider;
import de.kay_muench.imageinformationprovider.ImageInformation;
import de.kay_muench.imageinformationprovider.ImageInformationProvider;
import de.kay_muench.reqif10.reqifcompiler.dto.ImageRefDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.TextDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.XhtmlUlDTO;

public class XhtmlBuilder {
	private Collection<XhtmlDivType> collection = new ArrayList<XhtmlDivType>();

	public XhtmlBuilder append(ImageRefDTO ref) {
		XhtmlDivType d = XhtmlFactory.eINSTANCE.createXhtmlDivType();

		ImageInformationProvider provider = new ImageInformationProvider();

		try {
			ImageInformation info = provider.provideFor(ref.getUri());
			ImageDataUriProvider dataUriProvider = new ImageDataUriProvider();

			XhtmlObjectType o = XhtmlFactory.eINSTANCE.createXhtmlObjectType();
			o.setClass("figure");
			o.setData(dataUriProvider.provideFor(info));
			o.setType(info.getMimeType());
			o.setWidth(Integer.valueOf(info.getWidth()).toString());
			o.setHeight(Integer.valueOf(info.getHeight()).toString());
			o.getBr().add(XhtmlFactory.eINSTANCE.createXhtmlBrType());

			d.getObject().add(o);
		} catch (RuntimeException e) {
			XhtmlPType p = XhtmlFactory.eINSTANCE.createXhtmlPType();
			p.getXhtmlInlineMix().add(
					FeatureMapUtil.createTextEntry(e.getMessage()));
			d.getP().add(p);
		} catch (IOException e) {
			XhtmlPType p = XhtmlFactory.eINSTANCE.createXhtmlPType();
			p.getXhtmlInlineMix().add(
					FeatureMapUtil.createTextEntry(e.getMessage()));
			d.getP().add(p);
		}

		this.collection.add(d);
		return this;
	}

	public XhtmlBuilder append(final TextDTO txt) {
		XhtmlDivType d = XhtmlFactory.eINSTANCE.createXhtmlDivType();
		XhtmlPType p = XhtmlFactory.eINSTANCE.createXhtmlPType();
		final String[] lines = txt.getText().split("\n");
		for (int i = 0; i < lines.length; i++) {
			p.getXhtmlInlineMix().add(FeatureMapUtil.createTextEntry(lines[i]));
			if (i < lines.length - 1)
				p.getBr().add(XhtmlFactory.eINSTANCE.createXhtmlBrType());
		}
		d.getP().add(p);
		this.collection.add(d);
		return this;
	}

	public XhtmlBuilder append(final XhtmlUlDTO dto) {
		XhtmlDivType d = XhtmlFactory.eINSTANCE.createXhtmlDivType();
		d.getUl().add(dto.getUl());
		this.collection.add(d);
		return this;
	}

	public Collection<? extends XhtmlDivType> build() {
		return this.collection;
	}

	public static XhtmlBuilder newInstance() {
		return new XhtmlBuilder();
	}

}