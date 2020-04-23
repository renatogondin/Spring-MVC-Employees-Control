package modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="empregado")
public class Empregado {

	private long id;
	private String nome;
	private double salario;
	
	public Empregado() {}
	
	public Empregado(String nome, double salario) {
		this.nome = nome;
		this.salario = salario;
	}

	public Empregado(long id, String nome, double salario) {
		this.id = id;
		this.nome = nome;
		this.salario = salario;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
}
