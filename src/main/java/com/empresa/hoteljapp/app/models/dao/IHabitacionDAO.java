package com.empresa.hoteljapp.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.hoteljapp.app.models.entities.Habitacion;

public interface IHabitacionDAO extends CrudRepository<Habitacion,Long>{
	
	@Query("FROM Habitacion h WHERE h.nombre=:#{#habitacion.nombre} and h.precio=:#{#habitacion.precio}")
	List<Habitacion> findByNombrePrecio(Habitacion habitacion);
	
	@Query("FROM Habitacion h WHERE h.estado = 'D' ORDER BY h.id DESC")
	List<Habitacion> findAll();
	
	@Query("FROM Habitacion h WHERE h.estado = 'I' ORDER BY h.id DESC")
	List<Habitacion> findAllInactivos();
}

