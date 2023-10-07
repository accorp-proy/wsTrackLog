package com.primax.ejb.lkp;

public enum EnumNaming {

	IItemDao("ItemDao"),
    IRolDao("RolEtDao"),
	IDbHandler("DbHandler"),
	IMenuEtDao("MenuEtDao"),	
	IMensajeDao("MensajeDao"),
	IUsuarioDao("UsuarioDao"),
	IPersonaDao("PersonaDao"),
	IRolUsuarioDao("RolUsuarioDao"),
	IGeneralUtilsDao("GeneralUtilsDao"),
	IParametroDao("ParametroGeneralDao"),
	IRolMenuAccesoDao("RolMenuAccesoDao"),
	INotificacionService("SrvNotificacion"),
	IPoliticaSeguridadDao("PoliticaSeguridadDao");
	
	private String naming;
	private String ruta = "java:global/TrackLogEAR/DataService/";

	EnumNaming(String name) {
		this.naming = ruta + name;
	}

	public String getNaming() {
		return naming;
	}

}
