package controle;

import java.util.List;

import modelo.Empregado;
import modelo.ListaDeEmpregados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ds.EmpregadoDS;
// @RequestMapping(value="/empregado")
@Controller
public class EmpregadoController {

	@Autowired
	private EmpregadoDS empregadosDS;
	
	// ******************************** @ResponseBody ************************************
	
	// A anotação @ResponseBody é utilizada para fazer com que o objeto retornado (do tipo
	// Empregado ou do tipo ListaDeEmpregados) seja o conteúdo do corpo da  resposta  http 
	// que   será   mapeada  para  XML   pela   classe    MarshallingHttpMessageConverter. 
	// Utilizando  um   HttpMessageConverter  e  a  anotação   @ResponseBody,  você   pode 
	// implementar várias  representações (xml, json, etc).
	
	// Agora podemos  utilizar a ferramenta cURL ou a ferramente 'REST Client', plug-in do 
	// Firefox, para  invocar  a  requisição.  Usando o plugin do firefox não é necessário
	// adicionar o header HTTP: Accept=application/xml para ver a saída em xml. 

	// Para instalar o plugin:
	// https://addons.mozilla.org/en-US/firefox/addon/restclient/
	
	// É importante  entender que JAXB 2 não possui um  bom suporte  para mapear a  classe     
	// java.util.List<T>   para  XML.   Uma  prática  comum  é  adicionar  uma  classe  de 
	// empacotamento  para a  coleção  de  objetos.  
	
	// O conversor irá mapear o objeto para o tipo que for requisitado (XML). 
	
	// Método: GET
	// http://localhost:8080/webservices-01/service/emps
	// Se você quiser que a resposta seja em XML não é preciso fazer nada.
	// Isto é, o header Accept: application/xml não é necessário.

	@RequestMapping(method=RequestMethod.GET, value="/emps") 
	public @ResponseBody ListaDeEmpregados recuperaEmpregados() {
		List<Empregado> empregados = empregadosDS.recuperaTodos();
		ListaDeEmpregados lista = new ListaDeEmpregados(empregados);
		return lista;
	}
	
	// Método: GET
	// http://localhost:8080/webservices-01/service/emp/1
	// Header: Accept: application/xml (com firefox não é preciso para ver a saída em XML)

	@RequestMapping(method=RequestMethod.GET, value="/emp/{id}") 
	public @ResponseBody Empregado recuperaUmEmpregado(@PathVariable String id) {
		Empregado e = empregadosDS.recuperaPeloId(Long.parseLong(id));
		return e;
	}

	// A anotação @RequestBody  foi utilizada nos  métodos adicionaUmEmpregado() e alteraUmEmpregado()  abaixo.    
	// Ela recupera o  corpo da  requisição HTTP e  tenta convertê-lo  na classe Empregado 
	// utilizando o HttpMessageConverter registrado. 
	
	// Método: POST
	// http://localhost:8080/webservices-01/service/emp
	// Header: Content-type:application/xml 			<<<=== IMPORTANTE
	// Request Body: <empregado><nome>Sergio de Oliveira</nome><salario>7000</salario></empregado>
	// Se você quiser que a resposta seja em XML não é preciso fazer nada.
	// Isto é, o header Accept: application/xml não é necessário.

	@RequestMapping(method=RequestMethod.POST, value="/emp") 
	public @ResponseBody Empregado adicionaUmEmpregado(@RequestBody Empregado e) {
		empregadosDS.adiciona(e);
		return e;
	}
	
	// Método: PUT
	// http://localhost:8080/webservices-01/service/emp
	// Header: Content-type:application/xml             <<<=== IMPORTANTE
	// Request Body: <empregado><id>2</id><nome>Novo Nome</nome><salario>3000.0</salario></empregado>
	// Se você quiser que a resposta seja em XML não é preciso fazer nada.
	// Isto é, o header Accept: application/xml não é necessário.

	@RequestMapping(method=RequestMethod.PUT, value="/emp")
	public @ResponseBody Empregado alteraUmEmpregado(@RequestBody Empregado e) {
		empregadosDS.altera(e);
		return e;
	}

//	@RequestMapping(method=RequestMethod.PUT, value="/emp/{id}")
//	public @ResponseBody Empregado alteraUmEmpregado(@RequestBody Empregado e, @PathVariable String id) {
//		empregadosDS.altera(e);
//		return e;
//	}
	
	// Método: DELETE
	// http://localhost:8080/webservices-01/service/emp/1
	// Se você quiser que a resposta seja em XML não é preciso fazer nada.
	// Isto é, o header Accept: application/xml não é necessário.

	@RequestMapping(method=RequestMethod.DELETE, value="/emp/{id}")
	public @ResponseBody Empregado excluiUmEmpregado(@PathVariable String id) {
		Empregado e = empregadosDS.recuperaPeloId(Long.parseLong(id));
		empregadosDS.exclui(Long.parseLong(id));
		return e;
	}

	// Método: DELETE
	// http://localhost:8080/webservices-01/service/removeEmp/2
	// Se você quiser que a resposta seja em XML não é preciso fazer nada.
	// Isto é, o header Accept: application/xml não é necessário.
	@RequestMapping(method=RequestMethod.DELETE, value="/removeEmp/{id}")
	public @ResponseBody Empregado removeEmpregado(@PathVariable long id) {
		Empregado e = empregadosDS.recuperaPeloId(id);
		empregadosDS.exclui(id);
		return e;
	}
}
