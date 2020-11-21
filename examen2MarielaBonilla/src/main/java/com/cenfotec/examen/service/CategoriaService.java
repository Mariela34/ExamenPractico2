package com.cenfotec.examen.service;

import java.util.List;
import java.util.Optional;

import com.cenfotec.examen.domain.Categoria;

public interface CategoriaService {
	public void saveCategoria(Categoria categoria);
	public Optional<Categoria> get(Long id);
	public List<Categoria> getAll();
}
