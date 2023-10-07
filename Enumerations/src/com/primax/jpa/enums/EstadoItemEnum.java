package com.primax.jpa.enums;

public enum EstadoItemEnum {

	MIGRADO("MIGRADO"), ENVIADO("ENVIADO");

	private String estado;	

	EstadoItemEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
