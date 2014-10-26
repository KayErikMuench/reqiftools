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
package de.kay_muench.reqif10.reqifparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xml.namespace.XMLNamespacePackage;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIF10Package;
import org.eclipse.rmf.reqif10.datatypes.DatatypesPackage;
import org.eclipse.rmf.reqif10.xhtml.XhtmlPackage;
import org.eclipse.rmf.serialization.XMLPersistenceMappingResourceFactoryImpl;
import org.eclipse.rmf.serialization.XMLPersistenceMappingResourceSetImpl;

public final class ReqIF10Parser {
	private String reqIFFilename;
	private List<String> diagnostics = new ArrayList<String>();
	private boolean removeToolExtensions = false;
	private boolean removeTemporaries = true;

	public ReqIF parseReqIFContent() {
		String wkFile = this.getReqIFFilename();
		if (isRemoveToolExtensions()) {
			ToolExRemover remover = new ToolExRemover();
			remover.setDeleteOnExit(this.isRemoveTemporaries());
			wkFile = remover.remove(this.getReqIFFilename());
		}

		registerEPackageStd();
		ReqIF reqif = this.parse(wkFile);
		return reqif;
	}

	public String getReqIFFilename() {
		return reqIFFilename;
	}

	public void setReqIFFilename(String filename) {
		if (filename != null) {
			reqIFFilename = filename;
		}
	}

	public boolean isRemoveToolExtensions() {
		return removeToolExtensions;
	}

	public void setRemoveToolExtensions(boolean removeToolExtensions) {
		this.removeToolExtensions = removeToolExtensions;
	}

	public boolean isRemoveTemporaries() {
		return removeTemporaries;
	}

	public void setRemoveTemporaries(boolean removeTemporaries) {
		this.removeTemporaries = removeTemporaries;
	}

	private ReqIF parse(final String fileName) {
		try {
			URI uri = URI.createFileURI(fileName);
			ResourceFactoryImpl resourceFactory = new XMLPersistenceMappingResourceFactoryImpl();
			XMLResource resource = (XMLResource) resourceFactory
					.createResource(uri);
			Map<?, ?> options = null;
			resource.load(options);
			
			this.getDiagnostics().clear();
			for (Diagnostic d : resource.getErrors()) {
				this.getDiagnostics().add("ERROR " + d.getLocation() + " "
						+ d.getLine() + " " + d.getMessage());
			}
			for (Diagnostic d : resource.getWarnings()) {
				this.getDiagnostics().add("WARNING " + d.getLocation() + " "
						+ d.getLine() + " " + d.getMessage());
			}

			ResourceSet resourceSet = new XMLPersistenceMappingResourceSetImpl();
			resourceSet.getResources().add(resource);

			EList<EObject> rootObjects = resource.getContents();
			if (rootObjects.isEmpty()) {
				throw new RuntimeException("The resource parsed from '"
						+ uri.toString() + "' seems to be empty.");
			}
			ReqIF reqif = (ReqIF) rootObjects.get(0);
			
			return reqif;

		} catch (Exception e) {
			throw new RuntimeException("Parsing '" + fileName + "' failed.",
					e.getCause());
		}
	}

	private final void registerEPackageStd() {
		EPackage.Registry.INSTANCE.clear();
		EPackage.Registry.INSTANCE.put(ReqIF10Package.eNS_URI,
				ReqIF10Package.eINSTANCE);
		EPackage.Registry.INSTANCE.put(XhtmlPackage.eNS_URI,
				XhtmlPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(DatatypesPackage.eNS_URI,
				DatatypesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(XMLNamespacePackage.eNS_URI,
				XMLNamespacePackage.eINSTANCE);
	}

	public List<String> getDiagnostics() {
		return diagnostics;
	}
}
