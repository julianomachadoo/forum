package com.github.julianomachadoo.forumapi.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Resposta {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String mensagem;
	@ManyToOne
	private Topico topico;
	private LocalDateTime dataCriacao = now();
	@ManyToOne
	private Usuario autor;
	private Boolean solucao = false;

	public Resposta() {
	}

	public Resposta(String mensagem, Topico topico) {
		this.mensagem = mensagem;
		this.topico = topico;
	}

	public Resposta(String mensagem, Topico topico, Usuario autor) {
		this.mensagem = mensagem;
		this.topico = topico;
		this.autor = autor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Boolean getSolucao() {
		return solucao;
	}

	public void setSolucao(Boolean solucao) {
		this.solucao = solucao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resposta other = (Resposta) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}
}
