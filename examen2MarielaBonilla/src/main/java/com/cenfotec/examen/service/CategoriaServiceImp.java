package com.cenfotec.examen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenfotec.examen.domain.Categoria;
import com.cenfotec.examen.repo.*;


@Service
public class CategoriaServiceImp implements CategoriaService {
	@Autowired 
	CategoriaRepository categoriaRepo;
	
	public CategoriaServiceImp() {
	}

	@Override
	public void saveCategoria(Categoria categoria) {
		categoriaRepo.save(categoria);
	}

	@Override
	public Optional<Categoria> get(Long id) {
		return categoriaRepo.findById(id);
	}

	@Override
	public List<Categoria> getAll() {
		return categoriaRepo.findAll();
	}
}
