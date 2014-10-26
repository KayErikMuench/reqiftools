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

import java.util.Stack;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.Specification;

public final class SpecificationOutline {
	private Stack<EList<SpecHierarchy>> stack = new Stack<EList<SpecHierarchy>>();
	private ReqIF reqIF;

	public void shiftLevelDown(final Headline headline) {
		getReqIF().getCoreContent().getSpecObjects().add(headline.getObj());
		SpecHierarchy specHierarchy = ReqIF10Factory.eINSTANCE
				.createSpecHierarchy();
		specHierarchy.setIdentifier(IdentifierManager.generateIdentifier());
		specHierarchy.setLongName("SpecHierarchy");
		specHierarchy.setObject(headline.getObj());

		this.stack.peek().add(specHierarchy);
		this.stack.push(specHierarchy.getChildren());
	}

	public void shiftLevelUp() {
		this.stack.pop();
	}

	public ReqIF getReqIF() {
		return reqIF;
	}

	public void setReqIF(ReqIF reqIF) {
		this.reqIF = reqIF;
	}

	public static SpecificationOutline newInstance() {
		return new SpecificationOutline();
	}

	private void push(EList<SpecHierarchy> children) {
		this.stack.push(children);
	}

	public void push(Specification specification) {
		this.push(specification.getChildren());
	}

	public void add(SpecHierarchy specHierarchy) {
		this.stack.peek().add(specHierarchy);
	}

}
