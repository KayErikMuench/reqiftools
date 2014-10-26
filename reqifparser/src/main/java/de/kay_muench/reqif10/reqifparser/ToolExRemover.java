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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.input.DOMBuilder;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class ToolExRemover {
	private boolean deleteOnExit = true;

	public String remove(final String reqifFile) {
		try {
			Document document = parse(reqifFile);

			removeProRSpecificAttributes(document);
			removeToolExtensions(document);

			File wkFile = createTemporaryFile();
			output(document, wkFile);

			return wkFile.getAbsolutePath();

		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Parser error", e);
		} catch (SAXException e) {
			throw new RuntimeException("SAX error", e);
		} catch (IOException e) {
			throw new RuntimeException("IO error", e);
		}
	}

	private Document parse(final String reqifFile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(reqifFile));
		return document;
	}

	private void removeProRSpecificAttributes(Document document) {
		List<String> removableAttributes = new ArrayList<String>();
		removableAttributes.add("xmlns:configuration");
		removableAttributes.add("xmlns:headline");
		removableAttributes.add("xmlns:id");
		removableAttributes.add("xmlns:linewrap");

		NodeList list = document.getElementsByTagName("REQ-IF");
		for (int i = 0; i < list.getLength(); i++) {
			final Node n = list.item(i);
			NamedNodeMap map = n.getAttributes();
			for (String attribute : removableAttributes) {
				if (map.getNamedItem(attribute) != null)
					map.removeNamedItem(attribute);
			}
		}
	}

	private void removeToolExtensions(Document document) {
		NodeList list;
		list = document.getElementsByTagName("TOOL-EXTENSIONS");
		for (int i = 0; i < list.getLength(); i++) {
			final Node n = list.item(i);
			final Node p = n.getParentNode();
			p.removeChild(n);
		}
	}

	private File createTemporaryFile() throws IOException {
		File folder = File.createTempFile("requie-tool-wk-", "");
		folder.delete();
		folder = new File(folder.getAbsolutePath() + "/");
		folder.mkdir();
		File wkFile = new File(folder, "wk.reqif");
		if (this.isDeleteOnExit())
			wkFile.deleteOnExit();
		return wkFile;
	}

	private void output(final Document document, final File tmp)
			throws IOException {
		DOMBuilder domBuilder = new DOMBuilder();
		org.jdom2.Document doc = domBuilder.build(document);
		XMLOutputter out = new XMLOutputter();

		Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF8");
		BufferedWriter writer = new BufferedWriter(w);
		out.output(doc, writer);
		writer.close();
	}

	private boolean isDeleteOnExit() {
		return deleteOnExit;
	}

	public void setDeleteOnExit(boolean deleteOnExit) {
		this.deleteOnExit = deleteOnExit;
	}

}
