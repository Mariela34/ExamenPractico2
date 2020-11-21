package com.cenfotec.examen.domain;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "workshop")
public class Workshop {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="nombre")
	private String name;

	@Column(name="objective")
	private String objective;

	@Column(name="keywords")
	private String keywords;

	@Column(name="autor")
	private String autor;
	
	@ManyToOne
    @JoinColumn(name="id_categoria", nullable=false)
	private Categoria categoria;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="workshop")
	private Set<Actividad> actividades;

	@Transient
	private String[] listaKeys;


	@Transient
	private String tiempoDuracion;


	public Workshop(){
		
	}

	public Workshop(String name, String objective, String keywords, String autor, Categoria categoria) {
		this.name = name;
		this.objective = objective;
		this.keywords = keywords;
		this.autor = autor;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(Set<Actividad> actividades) {
		this.actividades = actividades;
	}




	public String getTiempoDuracion() {
		return tiempoDuracion;
	}

	public void setTiempoDuracion(String tiempoDuracion) {
		this.tiempoDuracion = tiempoDuracion;
	}

	public String[] getListaKeys() {
		return listaKeys;
	}

	public void setListaKeys(String[] listaKeys) {
		this.listaKeys = listaKeys;
	}


	@Override
	public String toString() {
		return "Workshop{" +
				"id:" + id +
				", name:'" + name + '\'' +
				", objective:'" + objective + '\'' +
				", keywords:'" + keywords + '\'' +
				", autor:'" + autor + '\'' +
				", categoria:" + categoria +
				", actividades:" + actividades +
				", tiempoDuracion=:" + tiempoDuracion + '\'' +
				'}';
	}
}
