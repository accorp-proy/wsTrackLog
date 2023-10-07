package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.ws.ItemEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IItemDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class ItemDao extends GenericDao<ItemEt, Long> implements IItemDao {

	public ItemDao() {
		super(ItemEt.class);
	}
	
	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarItem(ItemEt item, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (item.getIdItem() == null) {
			crear(item);
		} else {
			actualizar(item);
		}
		em.flush();
		em.clear();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ItemEt> getItemList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM ItemEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND upper(o.placa) like :condicion ");
		}
		sql.append(" ORDER BY o.idItem ");
		TypedQuery<ItemEt> query = em.createQuery(sql.toString(), ItemEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + condicion.toUpperCase() + "%");
		}
		List<ItemEt> result = query.getResultList();
		return result;
	}
	
	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}


}
