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
package de.kay_muench.reqif10.reqifcompiler.types.complex;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.AttributeDefinitionEnumeration;
import org.eclipse.rmf.reqif10.AttributeValueEnumeration;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

public class EnumeratedAttributeValue {
	private AttributeValueEnumeration value;

	public EnumeratedAttributeValue(AttributeDefinitionEnumeration def) {
		value = ReqIF10Factory.eINSTANCE.createAttributeValueEnumeration();
		value.setDefinition(def);
	}

	public AttributeValueEnumeration getValue() {
		return value;
	}

	public void setValue(int v) {
		List<EnumValue> newValues = new ArrayList<EnumValue>();
		final EList<EnumValue> enumValues = value.getDefinition().getType()
				.getSpecifiedValues();
		for (EnumValue enumValue : enumValues) {
			if (enumValue.getProperties().getKey()
					.equals(BigInteger.valueOf(v))) {
				newValues.add(enumValue);
				break;
			}
		}

		value.getValues().clear();
		value.getValues().addAll(newValues);
	}

}