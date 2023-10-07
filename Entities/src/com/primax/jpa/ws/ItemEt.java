package com.primax.jpa.ws;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.enums.EstadoItemEnum;

@Entity
@Table(name = "ITEM_ET")
@JsonPropertyOrder({ "placa", "remolque", "conductor", "telefono", "horaInicio", "nroVuelta", "ordenDestino", "tipoViaje", "convoy", "zona",
		"operacion", "cliente", "documento", "numeroPedido", "cantidad", "volumen", "peso", "valor", "codigoOrigen", "codigoDestino", "origen",
		"destino", "minutosETA", "fechaETA", "horaETA" })
public class ItemEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "sec_item_et", sequenceName = "seq_item_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_item_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_item")

	private Long idItem;

	@Column(name = "placa", length = 50)
	private String placa;

	@Column(name = "remolque", length = 50)
	private String remolque;

	@Column(name = "conductor", length = 50)
	private String conductor;

	@Column(name = "telefono", length = 50)
	private String telefono;

	@Column(name = "hora_inicio", length = 50)
	private String horaInicio;

	@Column(name = "nro_vuelta", length = 3)
	private Integer nroVuelta;

	@Column(name = "orden_destino", length = 2)
	private Integer ordenDestino;

	@Column(name = "tipo_viaje", length = 50)
	private String tipoViaje;

	@Column(name = "convoy", length = 50)
	private String convoy;

	@Column(name = "zona", length = 50)
	private String zona;

	@Column(name = "operacion", length = 50)
	private String operacion;

	@Column(name = "cliente", length = 50)
	private String cliente;

	@Column(name = "documento", length = 50)
	private String documento;

	@Column(name = "numero_pedido", length = 50)
	private String numeroPedido;

	@Column(name = "cantidad", length = 5)
	private Integer cantidad;

	@Column(name = "volumen")
	private Double volumen;

	@Column(name = "peso")
	private Double peso;

	@Column(name = "valor")
	private Double valor;

	@Column(name = "codigo_origen", length = 50)
	private String codigoOrigen;

	@Column(name = "codigo_destino", length = 50)
	private String codigoDestino;

	@Column(name = "origen", length = 50)
	private String origen;

	@Column(name = "destino", length = 50)
	private String destino;

	@Column(name = "minutos_eta", length = 5)
	private Integer minutosETA;

	@Column(name = "fecha_eta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaETA;

	@Column(name = "hora_eta", length = 50)
	private String horaETA;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "estado_item", columnDefinition = "VARCHAR(10) default 'MIGRADO'")
	protected EstadoItemEnum estadoItem;

	public Long getIdItem() {
		return idItem;
	}

	@JsonIgnore
	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	@JsonProperty("placa")
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@JsonProperty("remolque")
	public String getRemolque() {
		return remolque;
	}

	public void setRemolque(String remolque) {
		this.remolque = remolque;
	}

	@JsonProperty("conductor")
	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	@JsonProperty("telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@JsonProperty("horaInicio")
	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	@JsonProperty("nroVuelta")
	public Integer getNroVuelta() {
		return nroVuelta;
	}

	public void setNroVuelta(Integer nroVuelta) {
		this.nroVuelta = nroVuelta;
	}

	@JsonProperty("ordenDestino")
	public Integer getOrdenDestino() {
		return ordenDestino;
	}

	public void setOrdenDestino(Integer ordenDestino) {
		this.ordenDestino = ordenDestino;
	}

	@JsonProperty("tipoViaje")
	public String getTipoViaje() {
		return tipoViaje;
	}

	public void setTipoViaje(String tipoViaje) {
		this.tipoViaje = tipoViaje;
	}

	@JsonProperty("convoy")
	public String getConvoy() {
		return convoy;
	}

	public void setConvoy(String convoy) {
		this.convoy = convoy;
	}

	@JsonProperty("zona")
	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	@JsonProperty("operacion")
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	@JsonProperty("cliente")
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	@JsonProperty("documento")
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@JsonProperty("numeroPedido")
	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	@JsonProperty("cantidad")
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@JsonProperty("volumen")
	public Double getVolumen() {
		return volumen;
	}

	public void setVolumen(Double volumen) {
		this.volumen = volumen;
	}

	@JsonProperty("peso")
	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@JsonProperty("valor")
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@JsonProperty("codigoOrigen")
	public String getCodigoOrigen() {
		return codigoOrigen;
	}

	public void setCodigoOrigen(String codigoOrigen) {
		this.codigoOrigen = codigoOrigen;
	}

	@JsonProperty("codigoDestino")
	public String getCodigoDestino() {
		return codigoDestino;
	}

	public void setCodigoDestino(String codigoDestino) {
		this.codigoDestino = codigoDestino;
	}

	@JsonProperty("origen")
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@JsonProperty("destino")
	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	@JsonProperty("minutosETA")
	public Integer getMinutosETA() {
		return minutosETA;
	}

	public void setMinutosETA(Integer minutosETA) {
		this.minutosETA = minutosETA;
	}

	@JsonProperty("fechaETA")
	public Date getFechaETA() {
		return fechaETA;
	}

	public void setFechaETA(Date fechaETA) {
		this.fechaETA = fechaETA;
	}

	@JsonProperty("horaETA")
	public String getHoraETA() {
		return horaETA;
	}

	public void setHoraETA(String horaETA) {
		this.horaETA = horaETA;
	}

	public EstadoItemEnum getEstadoItem() {
		return estadoItem;
	}

	@JsonIgnore
	public void setEstadoItem(EstadoItemEnum estadoItem) {
		this.estadoItem = estadoItem;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemEt) {
			ItemEt other = (ItemEt) obj;
			if (this.idItem == null)
				return this == other;

			return other.idItem.equals(this.idItem) ? true : false;
		}
		return false;
	}

}
