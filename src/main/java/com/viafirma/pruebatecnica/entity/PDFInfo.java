package com.viafirma.pruebatecnica.entity;

public class PDFInfo {
	private String idDocument;
	private String url;
	
	public PDFInfo(String idDocument, String url) {
		this.idDocument = idDocument;
		this.url = url;
	}

	public String getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(String idDocument) {
		this.idDocument = idDocument;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PDFInfo [idDocument=" + idDocument + ", url=" + url + "]";
	}
    
}
