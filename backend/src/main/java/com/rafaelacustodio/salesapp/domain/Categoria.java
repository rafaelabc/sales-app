package com.rafaelacustodio.salesapp.domain;
import com.rafaelacustodio.salesapp.domain.Produto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos = new ArrayList<>();
	public Categoria() {}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Categoria)) return false;
		Categoria categoria = (Categoria) o;
		return Objects.equals(getId(), categoria.getId()) && Objects.equals(getNome(), categoria.getNome()) && Objects.equals(getProdutos(), categoria.getProdutos());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getNome(), getProdutos());
	}
}
