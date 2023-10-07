package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.ws.ItemEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IItemDao extends IGenericDao<ItemEt, Long> {

	public void remove();

	public List<ItemEt> getItemList(String condicion) throws EntidadNoEncontradaException;

	public void guardarItem(ItemEt item, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
