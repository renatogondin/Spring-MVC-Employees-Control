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
	
	// A anota��o @ResponseBody � utilizada para fazer com que o objeto retornado (do tipo
	// Empregado ou do tipo ListaDeEmpregados) seja o conte�do do corpo da  resposta  http 
	// que   ser�   mapeada  para  XML   pela   classe    MarshallingHttpMessageConverter. 
	// Utilizando  um   HttpMessageConverter  e  a  anota��o   @ResponseBody,  voc�   pode 
	// implementar v�rias  representa��es (xml, json, etc).
	
	// Agora podemos  utilizar a ferramenta cURL ou a ferramente 'REST Client', plug-in do 
	// Firefox, para  invocar  a  requisi��o.  Usando o plugin do firefox n�o � necess�rio
	// adicionar o header HTTP: Accept=application/xml para ver a sa�da em xml. 

	// Para instalar o plugin:
	// https://addons.mozilla.org/en-US/firefox/addon/restclient/
	
	// � importante  entender que JAXB 2 n�o possui um  bom suporte  para mapear a  classe     
	// java.util.List<T>   para  XML.   Uma  pr�tica  comum  �  adicionar  uma  classe  de 
	// empacotamento  para a  cole��o  de  objetos.  
	
	// O conversor ir� mapear o objeto para o tipo que for requisitado (XML). 
	
	// M�todo: GET
	// http://localhost:8080/webservices-01/service/emps
	// Se voc� quiser que a resposta seja em XML n�o � preciso fazer nada.
	// Isto �, o header Accept: application/xml n�o � necess�rio.

	@RequestMapping(method=RequestMethod.GET, value="/emps") 
	public @ResponseBody ListaDeEmpregados recuperaEmpregados() {
		List<Empregado> empregados = empregadosDS.recuperaTodos();
		ListaDeEmpregados lista = new ListaDeEmpregados(empregados);
		return lista;
	}
	
	// M�todo: GET
	// http://localhost:8080/webservices-01/service/emp/1
	// Header: Accept: application/xml (com firefox n�o � preciso para ver a sa�da em XML)

	@RequestMapping(method=RequestMethod.GET, value="/emp/{id}") 
	public @ResponseBody Empregado recuperaUmEmpregado(@PathVariable String id) {
		Empregado e = empregadosDS.recuperaPeloId(Long.parseLong(id));
		return e;
	}

	// A anota��o @RequestBody  foi utilizada nos  m�todos adicionaUmEmpregado() e alteraUmEmpregado()  abaixo.    
	// Ela recupera o  corpo da  requisi��o HTTP e  tenta convert�-lo  na classe Empregado 
	// utilizando o HttpMessageConverter registrado. 
	
	// M�todo: POST
	// http://localhost:8080/webservices-01/service/emp
	// Header: Content-type:application/xml 			<<<=== IMPORTANTE
	// Request Body: <empregado><nome>Sergio de Oliveira</nome><salario>7000</salario></empregado>
	// Se voc� quiser que a resposta seja em XML n�o � preciso fazer nada.
	// Isto �, o header Accept: application/xml n�o � necess�rio.

	@RequestMapping(method=RequestMethod.POST, value="/emp") 
	public @ResponseBody Empregado adicionaUmEmpregado(@RequestBody Empregado e) {
		empregadosDS.adiciona(e);
		return e;
	}
	
	// M�todo: PUT
	// http://localhost:8080/webservices-01/service/emp
	// Header: Content-type:application/xml             <<<=== IMPORTANTE
	// Request Body: <empregado><id>2</id><nome>Novo Nome</nome><salario>3000.0</salario></empregado>
	// Se voc� quiser que a resposta seja em XML n�o � preciso fazer nada.
	// Isto �, o header Accept: application/xml n�o � necess�rio.

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
	
	// M�todo: DELETE
	// http://localhost:8080/webservices-01/service/emp/1
	// Se voc� quiser que a resposta seja em XML n�o � preciso fazer nada.
	// Isto �, o header Accept: application/xml n�o � necess�rio.

	@RequestMapping(method=RequestMethod.DELETE, value="/emp/{id}")
	public @ResponseBody Empregado excluiUmEmpregado(@PathVariable String id) {
		Empregado e = empregadosDS.recuperaPeloId(Long.parseLong(id));
		empregadosDS.exclui(Long.parseLong(id));
		return e;
	}

	// M�todo: DELETE
	// http://localhost:8080/webservices-01/service/removeEmp/2
	// Se voc� quiser que a resposta seja em XML n�o � preciso fazer nada.
	// Isto �, o header Accept: application/xml n�o � necess�rio.
	@RequestMapping(method=RequestMethod.DELETE, value="/removeEmp/{id}")
	public @ResponseBody Empregado removeEmpregado(@PathVariable long id) {
		Empregado e = empregadosDS.recuperaPeloId(id);
		empregadosDS.exclui(id);
		return e;
	}
}
