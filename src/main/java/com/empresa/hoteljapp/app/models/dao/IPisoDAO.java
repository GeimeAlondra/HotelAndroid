package com.empresa.hoteljapp.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.empresa.hoteljapp.app.models.entities.Piso;

public interface IPisoDAO extends CrudRepository<Piso,Long>{
	
	List<Piso> findByNombreIgnoreCase(String cadena);

}
