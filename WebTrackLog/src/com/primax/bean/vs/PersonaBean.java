package com.primax.bean.vs;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.gen.PersonaEt;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IPersonaDao;

@Named("personaBn")
@ViewScoped
public class PersonaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -73098689965679881L;

	@EJB
	private IPersonaDao personaDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;

	@Inject
	private AppMain appMain;

	private PersonaEt personaSeleccionada;

	private String textoBusqueda;

	private List<PersonaEt> personas;

	@Override
	public void init() {
		personaSeleccionada = null;
		buscar();
	}

	public void buscar() {
		personas = personaDao.getPersonasCondicion(textoBusqueda);
	}

	public void nuevo() {
		personaSeleccionada = new PersonaEt();
	}

	public void validarPersona() {
		if (personaSeleccionada.getIdentificacionPersona() != null
				&& !personaSeleccionada.getIdentificacionPersona().isEmpty()) {
			PersonaEt per = personaDao.getPersonasPorCedula(personaSeleccionada.getIdentificacionPersona());
			if (per != null) {
				personaSeleccionada = per;
			}
			cargarImagen();
		}
	}

	public void guardar() {
		try {
			personaDao.guardarPersona(personaSeleccionada, appMain.getUsuario());
			showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, "Dato de persona guardado");
			getRequestContext().execute("PF('dlg_per_01').hide()");
			nuevo();
			buscar();
		} catch (EntidadNoGrabadaException e) {
			showInfo("Alerta", FacesMessage.SEVERITY_ERROR, "msg", "Ocurrio un error al guardar el usuario");
		}
	}

	public void modificar(PersonaEt persona) {
		personaSeleccionada = persona;
		cargarImagen();
	}

	@Override
	public void onDestroy() {
		personaDao.remove();
	}

	public List<PersonaEt> getPersonas() {
		return personas == null ? Collections.emptyList() : personas;
	}

	public PersonaEt getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(PersonaEt personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	// =================IMAGEN======================
	public void subirImagen(FileUploadEvent event) {
		try {

		} catch (Exception e) {
			showInfo("Alerta", FacesMessage.SEVERITY_ERROR, null, "Ocurrió un problema al subir la imagen");
		}
	}

	public void cargarImagen() {

	}

}
