package com.cenfotec.examen.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cenfotec.examen.domain.*;


import com.cenfotec.examen.service.CategoriaService;

@Controller
public class CategoriaController {

	@Autowired
	CategoriaService catService;
	
	
	@RequestMapping(value="/registrarCategoria", method= RequestMethod.GET)
	public String getFormCategoria(Model model) {
		model.addAttribute(new Categoria());
		return "registrarCategoria";
	}
	
	@RequestMapping(value="/registrarCategoria", method= RequestMethod.POST)
	public String registrarCategoria(Model model, Categoria categoria) {
		catService.saveCategoria(categoria);
		List<Categoria> categorias = new ArrayList();;
		for (Categoria c : catService.getAll()){
			if(c.getStatus()==1){
				categorias.add(c);
			}
		}

		model.addAttribute("categorias", categorias);
		return "listarCategoria";
	}

	@RequestMapping(value="/listarCategoria", method= RequestMethod.GET)
	public String listarCategorias(Model model) {
		List<Categoria> categorias = new ArrayList();
		for (Categoria c : catService.getAll()){
			if(c.getStatus()==1){
				categorias.add(c);
			}
		}
		model.addAttribute("mensaje", "No hay categorias registradas");
		model.addAttribute("categorias", categorias);
		return "listarCategoria";
	}
	
	@RequestMapping(value="/editar/categoria/{id}",  method= RequestMethod.GET)
	public String getFormEditarCategoria(@PathVariable("id") Optional<Long> id, Model model, Categoria categoria) {
		Optional<Categoria> cat = catService.get(id.get());

		if(cat.isPresent()) {
			if(cat.get().getStatus() == 1){
				model.addAttribute("categoria", cat.get());
				return "editarCategoria";
			}
		}
		return "404Categoria";
	}
	
	@RequestMapping(value="/editar/categoria/{id}",  method= RequestMethod.POST)
	public String editarCategoria(@PathVariable("id") Optional<Long> id, Model model, Categoria categoria) {
		catService.saveCategoria(categoria);
		List<Categoria> categorias = new ArrayList();;
		for (Categoria c : catService.getAll()){
			if(c.getStatus()==1){
				categorias.add(c);
			}
		}
		model.addAttribute("categorias", categorias);
		return "listarCategoria";
	}

	@RequestMapping(value="/eliminar/categoria/{id}")
	public String eliminarCategoria(@PathVariable("id") Optional<Long> id, Model model) {
		Optional<Categoria> catOptional = catService.get(id.get());

		if(catOptional.isPresent()) {
			if (catOptional.get().getStatus() == 1) {
				Categoria cat = catOptional.get();
				cat.setStatus(0);
				catService.saveCategoria(cat);
				List<Categoria> categorias = new ArrayList();;
				for (Categoria c : catService.getAll()){
					if(c.getStatus()==1){
						categorias.add(c);
					}
				}
				model.addAttribute("mensaje", "No hay categorias registradas");
				model.addAttribute("categorias", categorias);
				return "listarCategoria";
			}
		}
		return "404Categoria";
	}
}
