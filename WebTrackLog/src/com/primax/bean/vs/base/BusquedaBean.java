package com.primax.bean.vs.base;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IUsuarioDao;

@Named("BusquedaBn")
@ViewScoped
public class BusquedaBean extends BaseBean implements Serializable {

	/**
	 * Primax
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IUsuarioDao iUsuarioDao;

	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;

	private ParametrosGeneralesEt anioSeleccionado;

	private List<ParametrosGeneralesEt> mesesSeleccionados;
	private EstadoCheckListEnum estadoCheckListSeleccionado;
	private List<ParametrosGeneralesEt> anioVariacionSeleccionados;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Inject
	private AppMain appMain;

	@Override
	public void init() {

	}

	public String dateToFormatedDate(String formato, Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(fecha);
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public List<ParametrosGeneralesEt> getTipoAnioList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("106");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoAnioList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoAnioVariacionList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			if (anioSeleccionado != null) {
				ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("126");
				parametrosGenerales = iParametrolGeneralDao.getListAnioVariacion(parametrosGeneral,
						Long.parseLong(anioSeleccionado.getValorLista()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoAnioVariacionList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoMesList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("108");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoMesList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getNivelRiesgoList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("121");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getNivelRiesgoList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public SelectItem[] getEstadoCheckList() {
		SelectItem[] items = new SelectItem[4];
		items[0] = new SelectItem(EstadoCheckListEnum.ESTADO_CHECK, EstadoCheckListEnum.ESTADO_CHECK.getDescripcion());
		items[1] = new SelectItem(EstadoCheckListEnum.EJECUTADO, EstadoCheckListEnum.EJECUTADO.getDescripcion());
		items[2] = new SelectItem(EstadoCheckListEnum.AGENDADA, EstadoCheckListEnum.AGENDADA.getDescripcion());
		items[3] = new SelectItem(EstadoCheckListEnum.NO_EJECUTADO, EstadoCheckListEnum.NO_EJECUTADO.getDescripcion());
		return items;
	}

	public Date getFechaDesde(int mes, int anio) {
		Calendar calDesd = Calendar.getInstance();
		calDesd.setTime(new Date());
		calDesd.set(Calendar.MONTH, mes);
		calDesd.set(Calendar.YEAR, anio);
		calDesd.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("Fecha Desde" + " " + calDesd.getTime());
		return calDesd.getTime();

	}

	public Date getFechaHasta(int mes, int anio) {
		Calendar calHast = Calendar.getInstance();
		calHast.setTime(new Date());
		calHast.set(Calendar.MONTH, mes);
		calHast.set(Calendar.YEAR, anio);
		int ultimoDiaMes = calHast.getActualMaximum(Calendar.DAY_OF_MONTH);
		calHast.set(anio, mes, ultimoDiaMes);
		// System.out.println("Fecha Hasta" + " " + calHast.getTime());
		return calHast.getTime();
	}

	public ParametrosGeneralesEt getAnioSeleccionado() {
		return anioSeleccionado;
	}

	public void setAnioSeleccionado(ParametrosGeneralesEt anioSeleccionado) {
		this.anioSeleccionado = anioSeleccionado;
	}

	public List<ParametrosGeneralesEt> getMesesSeleccionados() {
		return mesesSeleccionados;
	}

	public void setMesesSeleccionados(List<ParametrosGeneralesEt> mesesSeleccionados) {
		this.mesesSeleccionados = mesesSeleccionados;
	}

	public EstadoCheckListEnum getEstadoCheckListSeleccionado() {
		return estadoCheckListSeleccionado;
	}

	public void setEstadoCheckListSeleccionado(EstadoCheckListEnum estadoCheckListSeleccionado) {
		this.estadoCheckListSeleccionado = estadoCheckListSeleccionado;
	}

	public List<ParametrosGeneralesEt> getAnioVariacionSeleccionados() {
		return anioVariacionSeleccionados;
	}

	public void setAnioVariacionSeleccionados(List<ParametrosGeneralesEt> anioVariacionSeleccionados) {
		this.anioVariacionSeleccionados = anioVariacionSeleccionados;
	}

	@Override
	public void onDestroy() {
		iParametrolGeneralDao.remove();

	}

}
