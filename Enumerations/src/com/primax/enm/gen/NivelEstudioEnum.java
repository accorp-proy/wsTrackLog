package com.primax.enm.gen;

public enum NivelEstudioEnum {

	BAS("B�sica"), SEC("Secundaria"), SUP("Superior");

	private String descripcion;

	NivelEstudioEnum(String desc) {
		this.descripcion = desc;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
