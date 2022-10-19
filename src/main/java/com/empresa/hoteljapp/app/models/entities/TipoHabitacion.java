package com.empresa.hoteljapp.app.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="tipo_habitaciones", schema = "public", catalog = "db_hoteljapp")

public class TipoHabitacion implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min=3, max=100)
	@Column(name="nombre", nullable=false, length=50)
	private String nombre;
	
	@Column(name="capacidad", nullable=false)
	private Integer capacidad;

	//Getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
}
