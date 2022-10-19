package com.empresa.hoteljapp.app.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "habitaciones", schema = "public", catalog = "db_hoteljapp")

public class Habitacion implements Serializable{
	
private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min=3, max=100)
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	@Column(name = "precio", nullable = false, precision = 2)
	private Double precio;
	
	@Column(name = "servicio", nullable = true, length = 50)
	private String servicio;
	
	@Column(name = "imagen", nullable = true)
	private String imagen;
	
	@Column(name = "estado", nullable = false, length = 1)
	private String estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JoinColumn(name = "tipoHabitacion_id", referencedColumnName = "id", nullable = false)
	private TipoHabitacion tipoHabitacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JoinColumn(name = "piso_id", referencedColumnName = "id", nullable = false)
	private Piso piso;
	
	@PrePersist
	private void setEstado() {
		this.estado = "D";
	}

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

	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}
	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public Piso getPiso() {
		return piso;
	}
	public void setPiso(Piso piso) {
		this.piso = piso;
	}

	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	
}
