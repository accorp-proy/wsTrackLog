package com.primax.srv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.enm.gen.DbTypeEnum;
import com.primax.jpa.param.ConexionEt;
import com.primax.srv.idao.IConexionDao;
import com.primax.srv.idao.IDbHandler;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class DbHandler implements IDbHandler {

	@EJB
	private IConexionDao iConexionDao;

	private Map<DbTypeEnum, Connection> connectionMap = new HashMap<>();

	static {
		try {
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConexion(DbTypeEnum tipo) throws SQLException {
		Connection conexion = null;
		ConexionEt con = iConexionDao.getConexionById(1L);

		/**
		 * Ambiente Producción 10.45.1.2 Ambiente Desarrollo 10.45.1.3
		 */
		try {
			switch (tipo) {
			case DB2:
				conexion = connectionMap.get(DbTypeEnum.DB2);
				if (conexion != null && !conexion.isClosed())
					return conexion;
				conexion = DriverManager.getConnection(
						"jdbc:as400://" + con.getIp() + "/" + con.getTipoLibDb().getDescripcion() + ";prompt=false;translate-binary=true",
						con.getUsuario(), con.getContrasenia());
				connectionMap.put(DbTypeEnum.DB2, conexion);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		return conexion;
	}

	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		for (Map.Entry<DbTypeEnum, Connection> entry : connectionMap.entrySet()) {
			Connection cn = entry.getValue();
			try {
				if (!cn.isClosed())
					cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}

}
