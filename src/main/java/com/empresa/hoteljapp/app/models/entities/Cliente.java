package com.empresa.hoteljapp.app.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="clientes", schema = "public", catalog = "db_hoteljapp")

public class Cliente implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min=3, max=100)
	@Column(name="nombre", nullable=false, length=100)
	private String nombre;
	
	@Column(name = "fecha_nacimiento", nullable = false)
	private Date fecha_nacimiento;
	
	@Column(name="direccion", nullable=false, length=200)
	private String direccion;
	
	@Column(name="telefono", nullable=true, length=20)
	private String telefono;
	
	
	/*@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
	private Usuario usuario;*/

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

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}

	

