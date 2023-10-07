package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaEt;
import com.primax.jpa.sec.PoliticaSeguridadEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IPersonaDao;
import com.primax.srv.idao.IPoliticaSeguridadDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.util.cons.StaticParameter;
import com.primax.web.sec.Encoder;

@Named("UsuarioBn")
@ViewScoped
public class UsuarioBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 7311247601199842130L;

	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IPersonaDao iPersonaDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private IPoliticaSeguridadDao iPoliticaSeguridadDao;

	@Inject
	private AppMain appMain;

	private UsuarioEt usuarioSeleccionado;

	private List<UsuarioEt> usuarios;

	private List<RolEt> rolesSeleccionados;

	private List<RolUsuarioEt> rolesEliminados;

	private String llaveComprobacion;

	private String llaveInicial;

	private boolean isPwdValida;

	private PoliticaSeguridadEt politicaSeg;

	private String PolRegex;

	private String msgRx;

	private String busquedaPersona;

	private List<PersonaEt> listaPersonas;

	private PersonaEt personaSeleccionada = new PersonaEt();

	private boolean okPolSeg;

	public void init() {
		usuarios = iUsuarioDao.getUsuariosByCondicion(null);
		rolesEliminados = new ArrayList<>();
		usuarioSeleccionado = new UsuarioEt();

		okPolSeg = false;
		politicaSeg = iPoliticaSeguridadDao.getpoliticaSeguridad();
		setPolicy(politicaSeg);

	}

	public void setPolicy(PoliticaSeguridadEt policy) {
		StringBuilder msgRegx = new StringBuilder();
		StringBuilder polReg = new StringBuilder("(");
		if (policy.getMayusculas()) {
			polReg.append(StaticParameter.Regex_Upper.getDescription());
			msgRegx.append("Mayusculas ");
		}
		if (policy.getMinusculas()) {
			polReg.append(StaticParameter.Regex_Lower.getDescription());
			msgRegx.append("Minusculas ");
		}
		if (policy.getSimbolos()) {
			polReg.append(StaticParameter.Regex_Symbol.getDescription());
			msgRegx.append("Simbolos ");
		}
		if (policy.getNumeros()) {
			polReg.append(StaticParameter.Regex_Digit.getDescription());
			msgRegx.append("Numeros");
		}
		polReg.append(".{" + policy.getLongitudMinContrasena() + "," + policy.getLongitudMaxContrasena() + "})");

		msgRx = msgRegx.toString().replace(" ", ",");
		msgRegx = new StringBuilder(msgRx);
		msgRegx.append(" con una longitud de: ");
		msgRegx.append(policy.getLongitudMinContrasena());
		msgRegx.append(" a ");
		msgRegx.append(policy.getLongitudMaxContrasena());
		msgRegx.append(" caracteres ");
		msgRx = msgRegx.toString();

		PolRegex = polReg.toString();

		if (!policy.getMayusculas() && !policy.getMinusculas() && !policy.getNumeros() && !policy.getSimbolos()) {
			PolRegex = StaticParameter.Regex_Any.getDescription();
		}

	}

	public void subirImagen(FileUploadEvent event) {
		try {
			String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
			String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();

		} catch (Exception e) {
			showInfo("Problemas al subir Imagen", FacesMessage.SEVERITY_ERROR);
		}
	}

	public PersonaEt getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(PersonaEt personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public List<UsuarioEt> getUsuarioBusqueda(String condicion) {
		usuarios = iUsuarioDao.getUsuariosByCondicion(condicion);
		return usuarios;
	}

	public void modificaUsuario(UsuarioEt usuario) {
		this.usuarioSeleccionado = usuario;

	}

	public UsuarioEt getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioEt usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public List<UsuarioEt> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioEt> usuarios) {
		this.usuarios = usuarios;
	}

	public String getBusquedaPersona() {
		return busquedaPersona;
	}

	public void setBusquedaPersona(String busquedaPersona) {
		this.busquedaPersona = busquedaPersona;
	}

	public List<PersonaEt> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<PersonaEt> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public void guardarUsuario() throws EntidadNoEncontradaException {
		usuarioSeleccionado.setUsuarioRegistra(appMain.getUsuario());

		if (rolesEliminados != null && !rolesEliminados.isEmpty()) {
			usuarioSeleccionado.getRolesUsario().addAll(rolesEliminados);
		}
		try {

			iUsuarioDao.crearUsuarioSistema(usuarioSeleccionado);
			showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, "Datos del usuario guardado");
			init();
		} catch (

		EntidadNoGrabadaException e) {
			e.printStackTrace();
			showInfo("Notificación", FacesMessage.SEVERITY_ERROR, null, "Se produjo un error al guardar el usuario");
		}

	}

	public List<RolEt> getRolesSeleccionados() {
		return rolesSeleccionados;
	}

	public void setRolesSeleccionados(List<RolEt> rolesSeleccionados) {
		this.rolesSeleccionados = rolesSeleccionados;
	}

	public void setSaltedPwd(String pwd) {
		this.llaveInicial = pwd;
		usuarioSeleccionado.setPwdUsuario(Encoder.encriptar(Encoder.strLlaveCifrado, pwd));
	}

	public String getSaltedPwd() {
		if (usuarioSeleccionado == null)
			return null;
		llaveInicial = Encoder.desencriptar(Encoder.strLlaveCifrado, usuarioSeleccionado.getPwdUsuario());
		return llaveInicial;
	}

	public String getLlaveComprobacion() {
		return llaveComprobacion;
	}

	public void setLlaveComprobacion(String llaveComprobacion) {
		this.llaveComprobacion = llaveComprobacion;
	}

	public String getLlaveInicial() {
		return llaveInicial;
	}

	public void setLlaveInicial(String llaveInicial) {
		this.llaveInicial = llaveInicial;
	}

	public boolean isPwdValida() {
		return isPwdValida;
	}

	public void setPwdValida(boolean isPwdValida) {
		this.isPwdValida = isPwdValida;
	}

	public List<RolUsuarioEt> getRolesEliminados() {
		return rolesEliminados;
	}

	public void setRolesEliminados(List<RolUsuarioEt> rolesEliminados) {
		this.rolesEliminados = rolesEliminados;
	}

	public void addToUserRoles() {
		if (usuarioSeleccionado != null) {
			List<RolUsuarioEt> roles = new ArrayList<>();
			for (RolEt rol : rolesSeleccionados) {
				boolean exist = false;
				for (RolUsuarioEt rolesUsuario : usuarioSeleccionado.getRolesUsario()) {
					if (rolesUsuario.getRol().equals(rol)) {
						exist = true;
						break;
					}
				}
				if (!exist) {
					RolUsuarioEt rolNuevo = new RolUsuarioEt();
					rolNuevo.setRol(rol);
					rolNuevo.setUsuario(usuarioSeleccionado);
					roles.add(rolNuevo);
				}
			}
			usuarioSeleccionado.getRolesUsario().removeAll(roles);
			usuarioSeleccionado.getRolesUsario().addAll(roles);
		}
	}

	public PoliticaSeguridadEt getPoliticaSeg() {
		return politicaSeg;
	}

	public void setPoliticaSeg(PoliticaSeguridadEt politicaSeg) {
		this.politicaSeg = politicaSeg;
	}

	public String getPolRegex() {
		return PolRegex;
	}

	public void setPolRegex(String polRegex) {
		PolRegex = polRegex;
	}

	public void validateUser(String userName) {
		UsuarioEt exist = iUsuarioDao.comprobarExisteUsuario(userName);
		if (!usuarioSeleccionado.equals(exist) && exist != null)
			showInfo("El usuario ya se encuentra registrado, ingrese otro", FacesMessage.SEVERITY_INFO);
	}

	public String getMsgRx() {
		return msgRx;
	}

	public void setMsgRx(String msgRx) {
		this.msgRx = msgRx;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public void nuevaBusquedaPersona() {
		busquedaPersona = "";
		buscarPersona();
	}

	public void buscarPersona() {
		listaPersonas = iPersonaDao.getPersonasUsuario(busquedaPersona);
	}

	public void cargarPersona() {
		usuarioSeleccionado.setPersonaUsuario(personaSeleccionada);
		// cargarImagen();
	}

	public void cargaPorCedula() {
		if (usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona() != null
				&& !usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona().isEmpty()
				&& !usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona().equals("_________-_")) {
			usuarioSeleccionado.getPersonaUsuario().setIdentificacionPersona(
					usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona().replace("-", ""));
			usuarioSeleccionado.setPersonaUsuario(iPersonaDao
					.getPersonasPorCedula(usuarioSeleccionado.getPersonaUsuario().getIdentificacionPersona()));
		}
	}

	public boolean isOkPolSeg() {
		return okPolSeg;
	}

	public void setOkPolSeg(boolean okPolSeg) {
		this.okPolSeg = okPolSeg;
	}

	public void cargarImagen() {
		
	}

	@Override
	public void onDestroy() {
		iUsuarioDao.remove();
		iPersonaDao.remove();
		iPoliticaSeguridadDao.remove();
	}

}
