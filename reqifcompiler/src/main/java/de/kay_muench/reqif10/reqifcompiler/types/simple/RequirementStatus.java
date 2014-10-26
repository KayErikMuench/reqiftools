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
package de.kay_muench.reqif10.reqifcompiler.types.simple;

import java.math.BigInteger;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.rmf.reqif10.DatatypeDefinitionEnumeration;
import org.eclipse.rmf.reqif10.EmbeddedValue;
import org.eclipse.rmf.reqif10.EnumValue;
import org.eclipse.rmf.reqif10.ReqIF10Factory;

import de.kay_muench.reqif10.reqifcompiler.IdentifierManager;
import de.kay_muench.reqif10.reqifcompiler.dto.StatusDTO;

public class RequirementStatus {
	private DatatypeDefinitionEnumeration def;

	public RequirementStatus() {
		def = ReqIF10Factory.eINSTANCE.createDatatypeDefinitionEnumeration();
		def.setIdentifier(IdentifierManager.generateIdentifier());
		def.setLongName("T_ReqStatus");

		for (Enumerator status : StatusDTO.VALUES) {
			EnumValue enumValue = ReqIF10Factory.eINSTANCE.createEnumValue();
			enumValue.setIdentifier(IdentifierManager.generateIdentifier());
			enumValue.setLongName(status.getLiteral());

			EmbeddedValue embeddedValue = ReqIF10Factory.eINSTANCE
					.createEmbeddedValue();
			embeddedValue.setKey(BigInteger.valueOf(status.getValue()));
			embeddedValue.setOtherContent("");

			enumValue.setProperties(embeddedValue);

			def.getSpecifiedValues().add(enumValue);
		}
	}

	public DatatypeDefinitionEnumeration getDef() {
		return def;
	}

}
