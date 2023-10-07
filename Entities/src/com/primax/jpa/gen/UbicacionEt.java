package com.primax.jpa.gen;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "UBICACION_ET")
@Audited
public class UbicacionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3445333126446300203L;

	@Id
	@SequenceGenerator(name = "sec_per_ubicacion", allocationSize = 1, initialValue = 1, sequenceName = "seq_per_ubicacion")
	@GeneratedValue(generator = "sec_per_ubicacion", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_ubicacion")
	private Long idUbicacion;

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UbicacionEt) {

			UbicacionEt other = (UbicacionEt) obj;

			if (this.idUbicacion == null)
				return this == other;

			return this.idUbicacion.equals(other.idUbicacion);
		} else
			return false;

	}

}
