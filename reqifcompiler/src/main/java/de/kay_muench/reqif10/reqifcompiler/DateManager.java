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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

public class DateManager {
	private static boolean predictabilityEnabled = false;

	public static void enablePredictableId() {
		predictabilityEnabled = true;
	}

	public static GregorianCalendar getCurrentDate()
			throws DatatypeConfigurationException {
		GregorianCalendar calendar = new GregorianCalendar();
		if (predictabilityEnabled) {
			calendar.setTimeInMillis(0);
			calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		} else {
			calendar.setTime(new Date());
		}
		return calendar;
	}

	public static GregorianCalendar toDate(String date)
			throws DatatypeConfigurationException {
		XMLGregorianCalendar xmlGregoriaCalendar = (XMLGregorianCalendar) EcoreUtil
				.createFromString(XMLTypePackage.eINSTANCE.getDateTime(), date);
		return xmlGregoriaCalendar.toGregorianCalendar();
	}

}
