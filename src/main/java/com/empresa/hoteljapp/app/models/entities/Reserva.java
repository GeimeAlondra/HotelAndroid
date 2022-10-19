package com.empresa.hoteljapp.app.models.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="reservas", schema = "public", catalog = "db_hoteljapp")

public class Reserva implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha_registro", nullable = false)
	private Date fecha_registro;
	
	@Column(name = "fecha_ingreso", nullable = false)
	private Date fecha_ingreso;
	
	@Column(name = "fecha_salida", nullable = false)
	private Date fecha_salida;
	
	
	@Column(name = "total", nullable = false, precision = 2)
	private Double total;
	
	@Column(name = "estado", nullable = false, length = 1)
	private String estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
	private Cliente cliente;
	
	//Relacion 1:N con detalleReserva
	@OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleReserva> detalleReserva;
	
	@PrePersist
	private void setEstado() {
		this.estado = "R";
	}

	//Getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Date getFecha_ingreso() {
		return fecha_ingreso;
	}
	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public Date getFecha_salida() {
		return fecha_salida;
	}
	public void setFecha_salida(Date fecha_salida) {
		this.fecha_salida = fecha_salida;
	}


	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<DetalleReserva> getDetalleReserva() {
		return detalleReserva;
	}

	public void setDetalleReserva(List<DetalleReserva> detalleReserva) {
		this.detalleReserva = detalleReserva;
	}

}