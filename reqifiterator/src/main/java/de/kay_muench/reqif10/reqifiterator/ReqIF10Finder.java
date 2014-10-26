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
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecRelation;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.common.util.ReqIF10Util;

public class ReqIF10Finder {
	/**
	 * 
	 * @param object
	 * @return Find the specification where the ${object} is contained
	 */
	static public Specification findSpecFor(SpecObject object) {
		ReqIFContent ric = (ReqIFContent) object.eContainer();
		for (Specification spec : ric.getSpecifications()) {
			if (has(spec, object))
				return spec;
		}
		return null;
	}

	/**
	 * 
	 * @param spec
	 * @param object
	 * @return Lookup the ${spec} if the ${object} is contained
	 */
	static private boolean has(Specification spec, SpecObject object) {
		return has(spec.getChildren(), object);
	}

	/**
	 * 
	 * @param children
	 * @param object
	 * @return Lookup the ${children} recursively if the ${object} is contained
	 */
	static private boolean has(final EList<SpecHierarchy> children,
			final SpecObject object) {
		for (SpecHierarchy child : children) {
			if (child.getObject() != null) {
				SpecObject specObject = child.getObject();
				if (specObject.getIdentifier().equals(object.getIdentifier()))
					return true;
			}
			if (has(child.getChildren(), object))
				return true;
		}
		return false;
	}
	/**
	 * Returns the SpecRelations that use the given SpecObject (via the
	 * given SpecHierarchy) as a source. Shameless copied from
	 * ProrAgileGridContentProvider
	 * @param source
	 */
	static public List<SpecRelation> getOutgoingSpecRelationsFor(SpecObject source) {
		if (source == null) {
			return Collections.emptyList();
		}
		ReqIF reqIF = ReqIF10Util.getReqIF(source);
		List<SpecRelation> list = new ArrayList<SpecRelation>();
		for (SpecRelation relation : reqIF.getCoreContent()
				.getSpecRelations()) {
			if (source.equals(relation.getSource())) {
				list.add(relation);
			}
		}
		return list;
	}

	/**
	 * Returns the SpecRelations that use the given SpecObject (via the
	 * given SpecHierarchy) as a source. Shameless copied from
	 * ProrAgileGridContentProvider
	 * @param target
	 */
	static public List<SpecRelation> getIncomingSpecRelationsFor(
			SpecObject target) {
		if (target == null) {
			return Collections.emptyList();
		}
		ReqIF reqIF = ReqIF10Util.getReqIF(target);
		List<SpecRelation> list = new ArrayList<SpecRelation>();
		for (SpecRelation relation : reqIF.getCoreContent()
				.getSpecRelations()) {
			if (target.equals(relation.getTarget())) {
				list.add(relation);
			}
		}
		return list;
	}
}