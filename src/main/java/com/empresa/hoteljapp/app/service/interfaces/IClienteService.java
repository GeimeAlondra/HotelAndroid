package com.empresa.hoteljapp.app.service.interfaces;

import java.util.List;

import com.empresa.hoteljapp.app.models.entities.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Cliente> isExist(Cliente cliente);
}
