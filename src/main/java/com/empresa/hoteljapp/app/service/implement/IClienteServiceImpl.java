package com.empresa.hoteljapp.app.service.implement;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.hoteljapp.app.models.dao.IClienteDAO;
import com.empresa.hoteljapp.app.models.entities.Cliente;
import com.empresa.hoteljapp.app.service.interfaces.IClienteService;

@Service
public class IClienteServiceImpl implements IClienteService{
	
	@Autowired
	private IClienteDAO clienteDAO;

	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>)clienteDAO.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		return clienteDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Cliente save(Cliente cliente) {
		return clienteDAO.save(cliente);
	}

	@Override
	public void delete(Long id) {
		clienteDAO.deleteById(id);
	}

	@Override
	public List<Cliente> isExist(Cliente cliente) {
		return clienteDAO.findByNombreTelefono(cliente);
	}
}
