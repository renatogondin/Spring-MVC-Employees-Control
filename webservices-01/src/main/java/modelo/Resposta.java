package modelo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resposta")
public class Resposta {
	private String codigoDeRetorno;
	private Empregado empregado;
	
	public Resposta() {}
	
	public Resposta(String codigoDeRetorno, Empregado empregado) {
		this.codigoDeRetorno = codigoDeRetorno;
		this.empregado = empregado;
	}
	
	public String getCodigoDeRetorno() {
		return codigoDeRetorno;
	}
	
	public void setCodigoDeRetorno(String codigoDeRetorno) {
		this.codigoDeRetorno = codigoDeRetorno;
	}
	
	@XmlElement(name="empregado")
	public Empregado getEmp() {
		return empregado;
	}
	
	public void setEmp(Empregado empregado) {
		this.empregado = empregado;
	}
	
	public String toString()
	{
		return "Codigo de retorno = " + codigoDeRetorno + " Empregado = " + empregado;
	}
}
