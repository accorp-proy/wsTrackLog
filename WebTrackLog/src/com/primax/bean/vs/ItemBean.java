package com.primax.bean.vs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.enm.gen.DBTypeLib;
import com.primax.enm.gen.DbTypeEnum;
import com.primax.jpa.enums.EstadoItemEnum;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.jpa.ws.ItemEt;
import com.primax.srv.idao.IDbHandler;
import com.primax.srv.idao.IItemDao;
import com.primax.srv.idao.IUsuarioDao;

@Named("ItemBn")
@ViewScoped
public class ItemBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -2498046553560301553L;

	@EJB
	private IItemDao iItemDao;

	private List<ItemEt> items;
	private ItemEt itemSeleccionado;

	@Inject
	private AppMain appMain;

	public void init() {
		initObj();
		buscar();
		// testWs();
		testSP();

	}

	public void testSP() {
		Date fechaD;
		Date fechaH;
		try {
			if (existeConexionJDE(DBTypeLib.PRIMAX)) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				String fechaDesdeString = "29-08-2023";
				String fechaHastaString = "30-08-2023";
				fechaD = formatter.parse(fechaDesdeString);
				fechaH = formatter.parse(fechaHastaString);
				String fechaDJDE = convertToJDEjulian(fechaD);
				String fechaHJDE = convertToJDEjulian(fechaH);
				consultaSP(fechaDJDE, fechaHJDE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void consultaSP(String fechaJDE, String fechaHJDE) {
		ItemEt item = null;
		Connection cn = null;
		IItemDao iItemDao = EJB(EnumNaming.IItemDao);
		IDbHandler iDbHandler = EJB(EnumNaming.IDbHandler);
		IUsuarioDao iUsuarioDao = EJB(EnumNaming.IUsuarioDao);

		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(1L);
			cn = iDbHandler.getConexion(DbTypeEnum.DB2);
			CallableStatement cs = cn.prepareCall(" CALL ECPRPDLOC.GODF(?,?) ");
			cs.setString(1, fechaJDE);
			cs.setString(2, fechaHJDE);
			cs.execute();
			PreparedStatement ps = cn.prepareStatement(" select PLACA from ecpxpdjde.f56406 ");
			// PreparedStatement ps = cn.prepareStatement(" SELECT * FROM ecpxpdjde.f56406
			// WHERE estado = 'INGRESADO' ");
			ResultSet rs = ps.getResultSet();
			if (rs != null) {
				while (rs.next()) {
					String placa = rs.getString("PLACA").trim();
					item = new ItemEt();
					item.setPlaca(placa);
					item.setEstadoItem(EstadoItemEnum.MIGRADO);
					iItemDao.guardarItem(item, usuario);
				}
				cs.close();
				ps.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Metodo consultaSP " + " " + e.getMessage());
		} finally {
			iItemDao.remove();
			iDbHandler.remove();
			iUsuarioDao.remove();
		}
	}

	public void initObj() {
		itemSeleccionado = null;
	}

	public void buscar() {
		try {
			items = iItemDao.getItemList("");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void testWs() {
		StringBuilder sql;
		Long orden = 0L;
		String objJson = "";
		try {
			sql = new StringBuilder("{" + "" + "items" + "" + ":" + "[");
			// sql = new StringBuilder("[");
			ObjectMapper objMapper = new ObjectMapper();
			JSONArray jsonArray = new JSONArray();
			for (ItemEt item : items) {
				orden += 1;
				objJson = "";

				// objMapper.enable(SerializationFeature.INDENT_OUTPUT);
				if (orden == 1L) {
					objJson = objMapper.writeValueAsString(item) + ",";
				} else {
					objJson = objMapper.writeValueAsString(item);
				}
				// objJson = objMapper.writeValueAsString(item);
				jsonArray.put(objJson);
				sql.append(objJson);
			}
			sql.append("] }");
			// sql.append("]");
			System.out.println(sql.toString());
			enviarJson(sql.toString(), jsonArray);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método testWs " + " " + e.getMessage());
		}
	}

	public void enviarJson(String json, JSONArray jsonArray) {
		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			System.out.println(jsonObject.toString());
			URL object = new URL("http://190.12.73.86/json/json_paneldist.php");
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization", "MYTOKEN");
			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonObject.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder respuesta = new StringBuilder();
				String acumuladorRespuesta = null;
				while ((acumuladorRespuesta = br.readLine()) != null) {
					respuesta.append(acumuladorRespuesta.trim());
				}
				System.out.println(respuesta.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método enviarJson " + " " + e.getMessage());
		}

	}

	private boolean existeConexionJDE(DBTypeLib dBTypeLib) {

		Connection cn = null;
		IDbHandler iDbHandler = EJB(EnumNaming.IDbHandler);
		boolean condicion = false;
		try {
			cn = iDbHandler.getConexion(DbTypeEnum.DB2);
			if (cn != null) {
				condicion = true;
				cn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método existeConexionJDE " + " " + e.getMessage());
		} finally {
			// iDbHandler.remove();

		}
		return condicion;
	}

	Throwable unrollException(Throwable exception, Class<? extends Throwable> expected) {
		while (exception != null && exception != exception.getCause()) {
			if (expected.isInstance(exception)) {
				return exception;
			}
			exception = exception.getCause();
		}
		return null;
	}

	private String convertToJDEjulian(Date fecha) {
		SimpleDateFormat sdf_1 = new SimpleDateFormat("DDD");
		Calendar date = Calendar.getInstance();
		date.setTime(fecha);
		int year = date.get(Calendar.YEAR) - 1900;
		return year + sdf_1.format(fecha);
	}

	public void modificar(ItemEt item) {
		itemSeleccionado = item;
	}

	public void guardar() {

	}

	public List<ItemEt> getItems() {
		return items;
	}

	public void setItems(List<ItemEt> items) {
		this.items = items;
	}

	public ItemEt getItemSeleccionado() {
		return itemSeleccionado;
	}

	public void setItemSeleccionado(ItemEt itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	@Override
	public void onDestroy() {
		iItemDao.remove();
	}
}
