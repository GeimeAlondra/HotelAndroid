package com.empresa.hoteljapp.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.empresa.hoteljapp.app.models.entities.TipoHabitacion;

public interface ITipoHabitacionDAO extends CrudRepository<TipoHabitacion,Long>{
	
	List<TipoHabitacion> findByNombreIgnoreCase(String cadena);
}
