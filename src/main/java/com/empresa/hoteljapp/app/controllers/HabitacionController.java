package com.empresa.hoteljapp.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empresa.hoteljapp.app.models.entities.Habitacion;
import com.empresa.hoteljapp.app.service.interfaces.IHabitacionService;
import com.empresa.hoteljapp.app.service.interfaces.IUploadFileService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")

public class HabitacionController {
	
	@Autowired
	private IUploadFileService uploadService;
	
	private Logger logger = LoggerFactory.getLogger(HabitacionController.class);
	
	@Autowired
	private IHabitacionService habitacionService;
	
	@GetMapping("/habitaciones/activos")
	public List<Habitacion> getAllActivos(){
		return habitacionService.findAllActivos();
	}
	
	@GetMapping("/habitaciones/inactivos")
	public List<Habitacion> getAllInactivos(){
		return habitacionService.findAllInactivos();
	}
	
	@GetMapping("/habitaciones/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Habitacion habitacion = null;
		Map<String, Object> response = new HashMap<>();
		try {
			habitacion = habitacionService.findById(id);
		}catch(DataAccessException e) {
			response.put("message", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
		}
		if(habitacion == null) {
			response.put("message", "La habitación con ID: " .concat(id.toString().concat(" No existen en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Habitacion>(habitacion,HttpStatus.OK);
	}
	
	@PostMapping("/habitaciones")
	public ResponseEntity<?> save(@Validated @RequestPart Habitacion habitacion, @RequestPart(name = "imagen", required = false) MultipartFile imagen, BindingResult result) throws IOException{
		String imageNewName = "";
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
			if(habitacionService.isExist(habitacion).size() > 0 && habitacion.getId()== null) {
				response.put("message", "Ya existe una habitacion conese nombre y precio en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
			}else {
				if(imagen != null) {
					imageNewName = uploadService.copyFile(imagen);
					habitacion.setImagen(imageNewName);
				}
				habitacionService.save(habitacion);
			}
		}catch(DataAccessException e){
			response.put("message", "Error al insertar el registro en la base de datos");
			logger.error("ERROR: ".concat(e.getMessage()));
			uploadService.deleteFile(imageNewName);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Habitacion registrada con exito...");
		response.put("habitacion", habitacion);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/habitaciones/{id}")
	public ResponseEntity<?> update(@RequestPart Habitacion habitacion, @PathVariable Long id, @RequestPart(name = "imagen", required = false) MultipartFile imagen)throws IOException{
		
		String imageNewName = habitacion.getImagen();
		Habitacion habitacionActual = habitacionService.findById(id);
		Habitacion habitacionUpdated = null;
		Map<String, Object> response = new HashMap<>();
			if(habitacionActual == null) {
				response.put("message", "Error: no se puede editar, la habitacion con ID: ".concat(id.toString().concat("no existe en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
			}
			try {
				habitacionActual.setNombre(habitacion.getNombre());
				habitacionActual.setPrecio(habitacion.getPrecio());
				habitacionActual.setTipoHabitacion(habitacion.getTipoHabitacion());
				habitacionActual.setPiso(habitacion.getPiso());
				if(imagen != null) {
					if(habitacionActual.getImagen() != null && habitacionActual.getImagen().length()>0) {
						String imgAnterior = habitacionActual.getImagen();
						Path rutaImgAnterior = uploadService.getPath(imgAnterior);
						File archivoImgAnterior = rutaImgAnterior.toFile();
						if(archivoImgAnterior.exists() && archivoImgAnterior.canRead()) {
							archivoImgAnterior.delete();
							}
						}
					imageNewName = uploadService.copyFile(imagen);
					habitacionActual.setImagen(imageNewName);
				}
				habitacionUpdated = habitacionService.save(habitacionActual);
				}
			catch(DataAccessException e) {
				response.put("message", "Error al actualizar la habitación");
				logger.error("ERROR: ".concat(e.getMessage()));
				uploadService.deleteFile(imageNewName);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("message", "Habitación actualizada con exito..");
			response.put("habitacion", habitacionUpdated);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/habitaciones/change-state")
	public ResponseEntity<?> changeState(@RequestBody Habitacion habitacion, @RequestParam(name = "estado") String estado){
			
		Map<String, Object> response = new HashMap<>();
			try {
				habitacion.setEstado(estado);
				habitacionService.save(habitacion);
				
			}catch(DataAccessException e) {
				response.put("message", "Error al cambiar estado del producto");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("message", "El estado del producto ha sido cambiado a"+estado.toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
