package modelo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="empregados")
public class ListaDeEmpregados {
	
	private int contador;
	private List<Empregado> empregados;
	
	public ListaDeEmpregados() {}
	
	public ListaDeEmpregados(List<Empregado> empregados) {
		this.empregados = empregados;
		this.contador = empregados.size();
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}
	
	@XmlElement(name="empregado")
	public List<Empregado> getEmpregados() {
		return empregados;
	}
	public void setEmpregados(List<Empregado> empregados) {
		this.empregados = empregados;
	}
}
