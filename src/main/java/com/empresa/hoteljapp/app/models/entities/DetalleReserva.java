package com.empresa.hoteljapp.app.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "detalle_reservas", schema = "public", catalog = "db_hoteljapp")

public class DetalleReserva implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "reserva_id", referencedColumnName = "id", nullable = false)
	private Reserva reserva;
	
	@ManyToOne
	@JoinColumn(name = "habitacion_id", referencedColumnName = "id", nullable = false)
	private Habitacion habitacion;
	
	@Column(name = "dia", nullable = false)
	private Integer dia;

	//Getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	
	
}