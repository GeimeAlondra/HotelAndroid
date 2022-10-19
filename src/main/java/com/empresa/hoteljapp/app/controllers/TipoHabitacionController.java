package com.empresa.hoteljapp.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
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

import com.empresa.hoteljapp.app.models.entities.TipoHabitacion;
import com.empresa.hoteljapp.app.service.interfaces.ITipoHabitacionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TipoHabitacionController {
	
	@Autowired
	private ITipoHabitacionService tipoHabitacionService;
	
	@GetMapping("/tipoHabitaciones")
	public List<TipoHabitacion> getAll(){
		return tipoHabitacionService.findAll();
	}
	
	@GetMapping("/tipoHabitaciones/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		TipoHabitacion tipoHabitacion = null;
		Map<String, Object> response = new HashMap<>();
		try {
			tipoHabitacion = tipoHabitacionService.findById(id);
		}catch(DataAccessException e) {
			response.put("message", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
		}
		if(tipoHabitacion == null) {
			response.put("message", "El tipo de habitación con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TipoHabitacion>(tipoHabitacion, HttpStatus.OK);
	}
	

	@PostMapping("/tipoHabitaciones")
	public ResponseEntity<?> save(@Validated @RequestBody TipoHabitacion tipoHabitacion, BindingResult result){
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
			if(tipoHabitacionService.fyndByNombre(tipoHabitacion.getNombre()).size() > 0) {
				response.put("message", "Ya existe un registro con este nombre en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			}else {
				
				tipoHabitacionService.save(tipoHabitacion);
			}
			
			
		}catch(DataAccessException e) {
			
			response.put("message", "Error al insertar registro en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		response.put("message", "Tipo de habitación registrado con exito ");
		response.put("tipoHabitacion",tipoHabitacion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

	@PutMapping("/tipoHabitaciones/{id}")
	public ResponseEntity<?> update(@RequestBody TipoHabitacion tipoHabitacion, @PathVariable Long id){
		
		TipoHabitacion tipoHabitacionActual = tipoHabitacionService.findById(id);
		TipoHabitacion tipoHabitacionUpdated = null;
		Map<String, Object> response = new HashMap<>();
		if(tipoHabitacionActual == null) {
			response.put("message", "Error: no se puede editar, el tipo de habitación con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			tipoHabitacionActual.setNombre(tipoHabitacion.getNombre());
			tipoHabitacionActual.setCapacidad(tipoHabitacion.getCapacidad());
			tipoHabitacionUpdated = tipoHabitacionService.save(tipoHabitacionActual);
		}catch(DataAccessException e) {
			response.put("message", "Error al actualizar el tipo de habitación");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Tipo de habitación actualizado con exito...");
		response.put("tipoHabitacion", tipoHabitacionUpdated);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/tipoHabitaciones/{id}")
	public ResponseEntity<?>delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			tipoHabitacionService.delete(id);
		}catch(DataAccessException e) {
			response.put("message","Error al eliminar el tipo de habitación");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}response.put("message","El tipo de habitación ha sido eliminado...");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);	
	}
}
