package com.cenfotec.examen.domain;

import javax.persistence.*;

import java.util.Set;


@Entity
@Table(name="categoria")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="nombre")
	private String name;
	
	@Column(name="status")
	private int status = 1;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="categoria")
	private Set<Workshop> workshop;
	
	public Categoria(String name) {
		this.name = name;
	}

	public Categoria() {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Workshop> getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Set<Workshop> workshop) {
		this.workshop = workshop;
	}

	@Override
	public String toString() {
		return "Categoria{" +
				"id:" + id +
				", name:'" + name + '\'' +
				'}';
	}
}
