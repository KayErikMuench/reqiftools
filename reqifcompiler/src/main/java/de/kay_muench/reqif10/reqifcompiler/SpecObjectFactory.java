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

import javax.xml.datatype.DatatypeConfigurationException;

import org.eclipse.rmf.reqif10.ReqIF10Factory;
import org.eclipse.rmf.reqif10.SpecObject;

import de.kay_muench.reqif10.reqifcompiler.dto.RankingDTO;
import de.kay_muench.reqif10.reqifcompiler.dto.StatusDTO;
import de.kay_muench.reqif10.reqifcompiler.types.complex.BaselineSpecObjectType;
import de.kay_muench.reqif10.reqifcompiler.types.complex.ConfigurationSpecObjectType;
import de.kay_muench.reqif10.reqifcompiler.xhtml.XhtmlBuilder;

class SpecObjectFactory {
	private class BaselineSpecObject {
		private IDValue attributeValueID;
		private DescriptionValue attributeValueDescription;
		private DateValue attributeValueDate;
		private RequirementStatusValue attributeValueStatus;
		private RequirementRankingValue attributeValueImportance;
		private RequirementRankingValue attributeValueRisk;
		private SpecObject specObject;

		BaselineSpecObject(final BaselineSpecObjectType type, final String id,
				final XhtmlBuilder builder, final String date,
				final StatusDTO status, RankingDTO importance, RankingDTO risk)
				throws DatatypeConfigurationException {
			specObject = ReqIF10Factory.eINSTANCE.createSpecObject();
			specObject.setIdentifier(IdentifierManager.generateIdentifier());
			specObject.setType(type.getDef());

			attributeValueID = new IDValue(type.getId(), specObject);
			attributeValueDescription = new DescriptionValue(
					type.getDesc(), specObject);
			attributeValueDate = new DateValue(type.getScheduledBy(),
					specObject);
			attributeValueStatus = new RequirementStatusValue(
					type.getStatus(), specObject);
			attributeValueImportance = new RequirementRankingValue(
					type.getPriority(), specObject);
			attributeValueRisk = new RequirementRankingValue(
					type.getRisk(), specObject);

			this.setId(id);
			this.setDescription(builder);
			this.setDate(date);
			this.setStatus(status);
			this.setImportance(importance);
			this.setRisk(risk);
		}

		void setId(final String id) {
			attributeValueID.setId(id);
		}

		void setDescription(final XhtmlBuilder builder) {
			attributeValueDescription.setDescription(builder);
		}

		void setDate(final String date) throws DatatypeConfigurationException {
			attributeValueDate.setDate(date);
		}

		void setStatus(final StatusDTO status) {
			attributeValueStatus.setStatus(status);
		}

		void setImportance(final RankingDTO importance) {
			attributeValueImportance.setRanking(importance);
		}

		void setRisk(final RankingDTO risk) {
			attributeValueRisk.setRanking(risk);
		}

		SpecObject getObj() {
			return specObject;
		}
	}

	private class ConfigurationSpecObject {
		private IDValue attributeValueID;
		private DescriptionValue attributeValueDescription;
		private RequirementStatusValue attributeValueStatus;
		private RequirementRankingValue attributeValueImportance;
		private RequirementRankingValue attributeValueRisk;
		private SpecObject specObject;

		ConfigurationSpecObject(final ConfigurationSpecObjectType type,
				final String id, final XhtmlBuilder builder, StatusDTO status,
				RankingDTO importance, RankingDTO risk)
				throws DatatypeConfigurationException {
			specObject = ReqIF10Factory.eINSTANCE.createSpecObject();
			specObject.setIdentifier(IdentifierManager.generateIdentifier());
			specObject.setType(type.getDef());

			attributeValueID = new IDValue(type.getId(), specObject);
			attributeValueDescription = new DescriptionValue(
					type.getDesc(), specObject);
			attributeValueStatus = new RequirementStatusValue(
					type.getStatus(), specObject);
			attributeValueImportance = new RequirementRankingValue(
					type.getImportance(), specObject);
			attributeValueRisk = new RequirementRankingValue(
					type.getRisk(), specObject);

			this.setId(id);
			this.setDescription(builder);
			this.setStatus(status);
			this.setImportance(importance);
			this.setRisk(risk);
		}

		void setId(final String id) {
			attributeValueID.setId(id);
		}

		void setDescription(final XhtmlBuilder builder) {
			attributeValueDescription.setDescription(builder);
		}

		void setStatus(final StatusDTO status) {
			attributeValueStatus.setStatus(status);
		}

		void setImportance(final RankingDTO importance) {
			attributeValueImportance.setRanking(importance);
		}

		void setRisk(final RankingDTO risk) {
			attributeValueRisk.setRanking(risk);
		}

		SpecObject getObj() {
			return specObject;
		}
	}

	private BaselineSpecObjectType typeWD;
	private ConfigurationSpecObjectType typeWoD;

	SpecObjectFactory(final BaselineSpecObjectType typeWD,
			final ConfigurationSpecObjectType typeWoD) {
		this.typeWD = typeWD;
		this.typeWoD = typeWoD;
	}

	SpecObject newSpecObject(final String id, final XhtmlBuilder builder,
			final String date, final StatusDTO status, RankingDTO importance,
			RankingDTO risk) throws DatatypeConfigurationException {
		SpecObject specObject;
		if (date != null) {
			specObject = new BaselineSpecObject(this.typeWD, id, builder, date,
					status, importance, risk).getObj();
		} else {
			specObject = new ConfigurationSpecObject(this.typeWoD, id, builder,
					status, importance, risk).getObj();
		}
		return specObject;
	}
}