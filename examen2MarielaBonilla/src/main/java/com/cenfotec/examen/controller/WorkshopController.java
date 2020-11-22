package com.cenfotec.examen.controller;

import com.cenfotec.examen.domain.Actividad;
import com.cenfotec.examen.domain.Categoria;
import com.cenfotec.examen.domain.Workshop;
import com.cenfotec.examen.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class WorkshopController {

	@Autowired
	WorkshopService workService;

	@Autowired
	ActividadService actService;

	@Autowired
	CategoriaService catService;

	@RequestMapping("/")
	public String home(Model model) {
		
		return "home";
	}

	@RequestMapping("/mantenimientoCategorias")
	public String mantenimientoCategorias(Model model) {

		return "mantenimientoCategorias";
	}

	@RequestMapping("/obtenerCategorias")
	public String obtenerCategorias(Model model) {

		return "mantenimientoCategorias";
	}


	@RequestMapping(value="/registrarWorkshop", method= RequestMethod.GET)
	public String getFormWorkshop(Model model) {
		List<Categoria> categorias = new ArrayList<Categoria>();
		for (Categoria c : catService.getAll()){
			if (c != null){
				if(c.getStatus()==1){
					categorias.add(c);
				}
			}
		}

		model.addAttribute("categorias", categorias);

		model.addAttribute("workshop", new Workshop());

		return "registrarWorkshop";
	}

	@RequestMapping(value="/registrarWorkshop", method= RequestMethod.POST)
	public String registrarWorkshop(Model model, Workshop workshop) {
		workService.saveWorkshop(workshop);
		List<Workshop> ws = workService.getAll();
		for(Workshop w : ws){
			int horas = 0, minutos = 0, segundos = 0;

			if(w.getActividades()==null){
				w.setTiempoDuracion("0h 0min 0s");
			}else {
				for (Actividad a : w.getActividades()){
					LocalTime t =  a.getTime();
					horas += t.getHour();
					minutos += t.getMinute();
					segundos += t.getSecond();
				}
				w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));
			}
		}
		model.addAttribute("mensaje", "No hay workshops registrados");
		model.addAttribute("workshops", ws);
		return "listarWorkshop";
	}


	@RequestMapping(value = "/listarWorkshops")
	public String listarWorkshops(Model model){
		List<Workshop> ws = workService.getAll();
		for(Workshop w : ws){
			int horas = 0, minutos = 0, segundos = 0;

			if(w.getActividades().isEmpty()){
				w.setTiempoDuracion("0h 0min 0s");
			}else {
				for (Actividad a : w.getActividades()){
					LocalTime t =  a.getTime();
					horas += t.getHour();
					minutos += t.getMinute();
					segundos += t.getSecond();
				}
				w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));
			}
		}
		model.addAttribute("mensaje", "No hay workshops registrados");

		model.addAttribute("workshops", ws);
		return "listarWorkshop";
	}

	@RequestMapping(value="/editar/workshop/{id}", method= RequestMethod.GET)
	public String getFormEditarWorkshop(Model model, @PathVariable("id") Optional<Long> id) {

		List<Categoria> categorias = new ArrayList<Categoria>();
		for (Categoria c : catService.getAll()){
			if (c != null){
				if(c.getStatus()==1){
					categorias.add(c);
				}
			}
		}

		model.addAttribute("categorias", categorias);
		Optional<Workshop> workshopOptional = workService.get(id.get());
		if(workshopOptional.isPresent()){
			workshopOptional.get().setListaKeys(workshopOptional.get().getKeywords().split(","));

			model.addAttribute("workshop", workshopOptional.get());
			return "editarWorkshop";
		}
		return "404";
	}

	@RequestMapping(value="/editar/workshop/{id}", method= RequestMethod.POST)
	public String editarWorkshop(Model model,Workshop workshop,@PathVariable("id") Optional<Long> id) {
		String[] keys = workshop.getListaKeys();
		workshop.setKeywords(keys[0]+","+keys[1]+","+keys[2]);

		workService.saveWorkshop(workshop);
		Optional<Workshop> workshopOptional = workService.get(id.get());
		Workshop w = workshopOptional.get();
		int horas = 0, minutos = 0, segundos = 0;
		if(w.getActividades()==null){
			w.setTiempoDuracion("0h 0min 0s");
		}else {
			for (Actividad a : w.getActividades()){
				LocalTime t =  a.getTime();
				horas += t.getHour();
				minutos += t.getMinute();
				segundos += t.getSecond();
			}
			w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));
		}
		model.addAttribute("mensaje", "No hay actividades registradas");
		model.addAttribute("workshop", w);
		return "perfilWorkshop";
	}


	@RequestMapping(value = "/perfilWorkshop/{id}")
	public String getPerfilWorkshop(Model model, @PathVariable("id") Optional<Long> id){
		int horas = 0, minutos = 0, segundos = 0;
		Optional<Workshop> workshopOptional = workService.get(id.get());
		if(workshopOptional.isPresent()){
			Workshop  w = workshopOptional.get();
			if(w.getActividades() != null){
				for(Actividad a : w.getActividades()){
					LocalTime t = a.getTime();
					horas += t.getHour();
					minutos += t.getMinute();
					segundos += t.getSecond();
				}
			}
			w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));
			model.addAttribute("mensaje", "No hay actividades registradas");

			model.addAttribute("workshop", w);
			return "perfilWorkshop";
		}
		return "404";
	}

	@RequestMapping(value="/registrarActividad/{id}", method= RequestMethod.GET)
	public String getFormActividad(Model model, @PathVariable("id") Optional<Long> id) {

		Optional<Workshop> workshopOptional = workService.get(id.get());
		if(workshopOptional.isPresent()){
			Actividad act = new Actividad();
			act.setWorkshop(workshopOptional.get());
			model.addAttribute("actividad", act);
			return "registrarActividad";
		}
		return "404";
	}

	@RequestMapping(value="/registrarActividad/{id}", method= RequestMethod.POST)
	public String registrarActividad(Model model, Actividad actividad,@PathVariable("id") Optional<Long> id) {
		int horas = 0, minutos = 0, segundos = 0;
		Optional<Workshop> workshopOptional = workService.get(id.get());
		Integer[] horasInt = new Integer[3];
		String[] horasStr = actividad.getTiempoTemporal().split(":");
		for (int i = 0; i < horasStr.length; i++){
			horasInt[i] = Integer.parseInt(horasStr[i]);
		}

		if(workshopOptional.isPresent()){
			LocalTime localTime = LocalTime.of(horasInt[0],horasInt[1],horasInt[2],0);
			actividad.setTime(localTime);
			actividad.setWorkshop(workshopOptional.get());
			Actividad act = actService.saveActividad(actividad);
			workshopOptional.get().getActividades().add(act);
			workService.addActivity(workshopOptional.get());
			TemplateEngine templateEngine =  new  TemplateEngine ();

			Workshop w = workService.get(id.get()).get();
			for(Actividad a : w.getActividades()){
				LocalTime t = a.getTime();
				a.setTiempoTemporal(obtenerTiempoDuracion(t.getHour(),t.getMinute(),t.getSecond()));
				horas += t.getHour();
				minutos += t.getMinute();
				segundos += t.getSecond();
			}
			w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));
			model.addAttribute("workshop", w);
			model.addAttribute("mensaje", "No hay actividades registradas");

			return "perfilWorkshop";
		}
		return "errorRegistro";
	}

	@RequestMapping(value="/verActividades/{id}", method= RequestMethod.GET)
	public String getActividadesWorkshop(Model model, @PathVariable("id") Optional<Long> id) {

		Optional<Workshop> workshopOptional = workService.get(id.get());
		if(workshopOptional.isPresent()){
			model.addAttribute("mensaje", "No hay actividades registradas");
			model.addAttribute("actividades", workshopOptional.get().getActividades());
			model.addAttribute("workshop", workshopOptional.get());
			return "listarActividadesWorkshop";
		}
		return "404";
	}

	@RequestMapping(value="/listarActividades", method= RequestMethod.GET)
	public String getActividades(Model model) {
		model.addAttribute("mensaje", "No hay activadades registradas");
		model.addAttribute("actividades", actService.getAll());
		return "listarTodasActividades";
	}


	@RequestMapping(value="/buscar/autor", method= RequestMethod.GET)
	public String getAWorkshopsAutor(Model model) {
		model.addAttribute("mensaje", "No hay workshops registrados");
		model.addAttribute("workshop", new Workshop());
		model.addAttribute("workshops", new ArrayList<Workshop>());
		return "listarWorkshopAutor";
	}

	@RequestMapping(value="/buscar/autor", method= RequestMethod.POST)
	public String searchWorkshopsAutor(Model model,@ModelAttribute("workshop") Workshop workshop) {
		List<Workshop> ws = new ArrayList<Workshop>();
		if(workService.getAll() != null){
			for(Workshop w :  workService.getAll()){
				int horas = 0, minutos = 0, segundos = 0;
				if(w.getAutor().equals(workshop.getAutor())){
					if(w.getActividades() != null){
						for(Actividad a : w.getActividades()){
							LocalTime t = a.getTime();
							horas += t.getHour();
							minutos += t.getMinute();
							segundos += t.getSecond();
						}
					}
					w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));
					ws.add(w);
				}
			}
		}
		model.addAttribute("mensaje", "No hay workshops registrados");

		model.addAttribute("workshops", ws);
		return "listarWorkshopAutor";
	}

	@RequestMapping(value="/buscar/categoria", method= RequestMethod.GET)
	public String getAWorkshopsCategoria(Model model) {
		List<Categoria> categorias = new ArrayList<Categoria>();
		for (Categoria c : catService.getAll()){
			if (c != null){

				categorias.add(c);
			}
		}
		model.addAttribute("mensaje", "No hay workshops registrados");
		model.addAttribute("categorias", categorias);
		model.addAttribute("workshop", new Workshop());
		model.addAttribute("workshops", new ArrayList<Workshop>());
		return "listarWorkshopCategoria";
	}

	@RequestMapping(value="/buscar/categoria", method= RequestMethod.POST)
	public String searchWorkshopsCategoria(Model model,@ModelAttribute("workshop") Workshop workshop) {
		List<Categoria> categorias = new ArrayList<Categoria>();
		for (Categoria c : catService.getAll()){
			if (c != null){
				categorias.add(c);
			}
		}

		List<Workshop> ws = new ArrayList<Workshop>();
		if(workService.getAll() != null){
			for(Workshop w :  workService.getAll()){
				int horas = 0, minutos = 0, segundos = 0;
				if(w.getCategoria().getId().equals(workshop.getCategoria().getId())){
					if(w.getActividades() != null){
						for(Actividad a : w.getActividades()){
							LocalTime t = a.getTime();
							horas += t.getHour();
							minutos += t.getMinute();
							segundos += t.getSecond();
						}
					}
					w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));

					ws.add(w);
				}
			}

		}
		model.addAttribute("mensaje", "No hay workshops registrados");
		model.addAttribute("categorias", categorias);
		model.addAttribute("workshops", ws);
		return "listarWorkshopCategoria";
	}


	@RequestMapping(value="/buscar/keywords", method= RequestMethod.GET)
	public String getAWorkshopsKeywords(Model model) {
		model.addAttribute("mensaje", "No hay workshops registrados");
		model.addAttribute("workshop", new Workshop());
		model.addAttribute("workshops", new ArrayList<Workshop>());
		return "listarWorkshopKeywords";
	}

	@RequestMapping(value="/buscar/keywords", method= RequestMethod.POST)
	public String searchWorkshopsKeywords(Model model,@ModelAttribute("workshop") Workshop workshop) {

		List<Workshop> ws = new ArrayList<Workshop>();
		if(workService.getAll() != null){
			for(Workshop w :  workService.getAll()){

				String[] keywords = w.getKeywords().split(",");
				if(keywords[0].equals(workshop.getKeywords()) ||
						keywords[1].equals(workshop.getKeywords()) ||
						keywords[2].equals(workshop.getKeywords()) ){

					if(keywords[0].equals(workshop.getKeywords())){
						w.setKeywords( "*1-"+keywords[0] + "*, 2-" +keywords[1]+", 3-" + keywords[2]);
					}
					if(keywords[1].equals(workshop.getKeywords())){
						w.setKeywords( "1-"+keywords[0] + ", *2-" +keywords[1]+"*, 3-" + keywords[2]);
					}
					if(keywords[2].equals(workshop.getKeywords())){
						w.setKeywords( "1-"+keywords[0] + ", 2-" +keywords[1]+", *3-" + keywords[2] + "*");
					}

					int horas = 0, minutos = 0, segundos = 0;

					if(w.getActividades() != null){
						for(Actividad a : w.getActividades()){
							LocalTime t = a.getTime();
							horas += t.getHour();
							minutos += t.getMinute();
							segundos += t.getSecond();
						}
					}
					w.setTiempoDuracion(obtenerTiempoDuracion(horas,minutos,segundos));

					ws.add(w);
				}
			}
		}
		model.addAttribute("mensaje", "Debe realizar primero una busqueda");
		model.addAttribute("workshops", ws);
		return "listarWorkshopKeywords";
	}

	@RequestMapping(value = "/listardatatable", method = RequestMethod.GET)
	public String obtenerdatatable(){
		return "listaDatatableWorkshop";
	}

	/**
	 * Funcion para poder obtener en un String el tiempo total de duracion entre horas, minutos y segundos
	 * @param h = horas
	 * @param m = minutos
	 * @param s = segundos
	 * @return un texto que indica la cantidad de horas en total
	 */

	public static String obtenerTiempoDuracion(int h, int m, int s){
		String tiempoDuracion = "";
		int horas = 0, minutos = 0, segundos = 0;
		int horasAdd = 0, minutosAdd = 0, segundosAdd = 0;
		int horastotal= 0, minutostotal= 0,segundostotal= 0;
		segundostotal = s;

		if(segundostotal>=60){
			minutosAdd = segundostotal/60;
			segundos = segundostotal%60;
		}else {
			segundos = segundostotal;
		}
		minutostotal = m + minutosAdd;
		if(minutostotal>=60){
			horasAdd = minutostotal/60;
			minutos = minutostotal%60;
		} else {
			minutos = minutostotal;
		}
		horas = h + horasAdd;
		tiempoDuracion = horas + "h " + minutos + "min " + segundos + "s";
		return tiempoDuracion;
	}
}
