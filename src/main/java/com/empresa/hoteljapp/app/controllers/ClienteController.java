package com.empresa.hoteljapp.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.hoteljapp.app.models.entities.Cliente;
import com.empresa.hoteljapp.app.service.interfaces.IClienteService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> getAll(){
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity <?>getById(@PathVariable Long id) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cliente = clienteService.findById(id);
		}catch(DataAccessException e) {
			response.put("message","Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
		}
		if(cliente == null) {
			response.put("message", "El cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> save(@Validated @RequestBody Cliente cliente, BindingResult result){
		//Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
				response.put("errors", errors);
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			if(clienteService.isExist(cliente).size() > 0) {
				response.put("message", "Ya existe un registro con este nombre y este correo en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
			}else {
				clienteService.save(cliente);
			}
		}catch(DataAccessException e){
			response.put("message", "Error al insertar el registro en la base de datos");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Cliente registrado con exito");
		response.put("cliente", cliente);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdated = null;
		Map<String, Object> response = new HashMap<>();
		if(clienteActual == null) {
			response.put("message", "Error: no se puede editar, el cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
		}
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setFecha_nacimiento(cliente.getFecha_nacimiento());
			clienteActual.setTelefono(cliente.getTelefono());
			clienteActual.setDireccion(cliente.getDireccion());
			clienteUpdated = clienteService.save(clienteActual);
		}catch(DataAccessException e){
			response.put("message", "Error al actualizar el registro en la base de datos");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Cliente actualizado con exito");
		response.put("cliente", clienteUpdated);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			clienteService.delete(id);
		}catch(DataAccessException e) {
			response.put("message", "Error al eliminar el cliente de la base de datos");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Cliente ha sido eliminado...");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}
