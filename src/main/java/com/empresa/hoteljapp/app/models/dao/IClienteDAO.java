package com.empresa.hoteljapp.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.empresa.hoteljapp.app.models.entities.Cliente;

public interface IClienteDAO extends CrudRepository<Cliente,Long>{
	
	@Query("FROM Cliente c WHERE c.nombre=:#{#cliente.nombre} and c.telefono=:#{#cliente.telefono}")
	List<Cliente> findByNombreTelefono(Cliente cliente);
}
