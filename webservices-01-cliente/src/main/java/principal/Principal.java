package principal;

import modelo.Empregado;
import modelo.ListaDeEmpregados;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import corejava.Console;

public class Principal
{	public static void main (String[] args) 
	{	
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("restTemplate.xml"); 

		RestTemplate rest = (RestTemplate) ctx.getBean("restTemplate");
	
		String nome;
		double salario;
		Empregado umEmpregado;

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um empregado - Forma 1");
			System.out.println("2. Cadastrar um empregado - Forma 2");
			System.out.println("3. Alterar um empregado");
			System.out.println("4. Remover um empregado - Forma 1");
			System.out.println("5. Remover um empregado - Forma 2");
			System.out.println("6. Listar todos os empregados - Forma 1");
			System.out.println("7. Listar todos os empregados - Forma 2");
			System.out.println("8. Sair");
						
			// M�todos utilizados:
			
			// postForEntity
			// exchange
			// getForObject
			// put
			// delete
			
			
			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 8:");
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + "Informe o nome do empregado: ");
					salario = Console.readDouble("Informe o valor do sal�rio: ");

					umEmpregado = new Empregado(nome, salario);
					
					// O c�digo abaixo mostra como enviar (POST) um novo empregado para o servidor.    
					HttpEntity<Empregado> entity = new HttpEntity<Empregado>(umEmpregado);
					
					ResponseEntity<Empregado> response = rest.postForEntity(
							"http://localhost:8080/webservices-01/service/emp", 
							entity, Empregado.class);
					
					umEmpregado = response.getBody();
					
					System.out.println('\n' + "Empregado n�mero " + 
					    umEmpregado.getId() + " " + umEmpregado.getNome() + " inclu�do com sucesso!");	

					break;
				}

				case 2:
				{	
					// O c�digo abaixo mostra  como utilizar a classe  RestTemplate.  
					HttpHeaders headers = new HttpHeaders();  
					
					headers.setContentType(MediaType.APPLICATION_XML);   // <== Se estiver enviando XML
					headers.add("Accept", "application/xml");  // <== Se aceita receber de volta XML
					
					nome = Console.readLine('\n' + "Informe o nome do empregado: ");
					salario = Console.readDouble("Informe o valor do sal�rio: ");

					umEmpregado = new Empregado(nome, salario);
					
					HttpEntity<Empregado> entity = new HttpEntity<Empregado>(umEmpregado, headers);
									
					// Executa o m�todo HTTP para o URI fornecido, escrevendo a entidade fornecida
					// e os headers no request e retornando a resposta como um objeto do tipo 
					// ResponseEntity.
					
					ResponseEntity<Empregado> response = rest.exchange(
							"http://localhost:8080/webservices-01/service/emp", 
							HttpMethod.POST, entity, Empregado.class);
					
					Empregado empregado = response.getBody();
	
					System.out.println('\n' + 
						"Id = " + empregado.getId() +
						"  Nome = " + empregado.getNome() +
						"  Sal�rio = " + empregado.getSalario());
					
					break;
				}
	
				case 3:
				{	long resposta = Console.readInt('\n' + 
						"Digite o n�mero do empregado que voc� deseja alterar: ");
										
					umEmpregado = rest.getForObject(
						"http://localhost:8080/webservices-01/service/emp/{id}", 
						Empregado.class, resposta);
					
					if (umEmpregado == null)
					{
						System.out.println("\nEmpregado n�o encontrado.");
						break;
					}
					
					System.out.println('\n' + 
						"N�mero = " + umEmpregado.getId() + 
						"    Nome = " + umEmpregado.getNome() +
						"    Sal�rio = " + umEmpregado.getSalario());
												
					System.out.println('\n' + "O que voc� deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Sal�rio");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um n�mero de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");
							
							umEmpregado.setNome(novoNome);

							// O  c�digo abaixo mostra como efetuar um PUT de um empregado modificado para       
							// atualizar  o empregado  original.  
							
							HttpEntity<Empregado> entity = new HttpEntity<Empregado>(umEmpregado);
							
							// M�todo put retorna sempre void.

							rest.put("http://localhost:8080/webservices-01/service/emp", entity);

							System.out.println('\n' + "Altera��o de nome efetuada com sucesso!");
								
							break;
					
						case 2:
							double novoSalario = Console.
									readDouble("Digite o novo sal�rio: ");
							
							umEmpregado.setSalario(novoSalario);

							entity = new HttpEntity<Empregado>(umEmpregado);
							
							// M�todo put retorna sempre void.
							
							rest.put("http://localhost:8080/webservices-01/service/emp", entity);
								
							System.out.println('\n' + "Altera��o de sal�rio efetuada com sucesso!");
							
							break;

						default:
							System.out.println('\n' + "Op��o inv�lida!");
					}

					break;
				}

				case 4:
				{	long resposta = Console.readInt('\n' + 
						"Digite o n�mero do empregado que voc� deseja remover: ");
									
					umEmpregado = rest.getForObject(
						"http://localhost:8080/webservices-01/service/emp/{id}", 
						Empregado.class, resposta);

					if (umEmpregado == null)
					{
						System.out.println("\nEmpregado n�o encontrado.");
						break;
					}

					System.out.println('\n' + 
						"N�mero = " + umEmpregado.getId() + 
						"    Nome = " + umEmpregado.getNome() +
						"    Sal�rio = " + umEmpregado.getSalario());
										
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o do empregado?");

					if(resp.equals("s"))
					{	
						// E o c�digo abaixo mostra como deletar um empregado existente.
						// M�todo delete retorna sempre void.
						
						rest.delete("http://localhost:8080/webservices-01/service/emp/{id}", resposta);
						
						System.out.println('\n' + "Empregado removido com sucesso!");
					}
					else
					{	System.out.println('\n' + "Empregado n�o removido.");
					}
					
					break;
				}

				case 5:
				{	long resposta = Console.readInt('\n' + 
						"Digite o n�mero do empregado que voc� deseja remover: ");
									
					umEmpregado = rest.getForObject(
						"http://localhost:8080/webservices-01/service/emp/{id}", 
						Empregado.class, resposta);

					if (umEmpregado == null)
					{
						System.out.println("\nEmpregado n�o encontrado.");
						break;
					}

					System.out.println('\n' + 
						"N�mero = " + umEmpregado.getId() + 
						"    Nome = " + umEmpregado.getNome() +
						"    Sal�rio = " + umEmpregado.getSalario());
										
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o do empregado?");

					if(resp.equals("s"))
					{	
						// HttpHeaders headers = new HttpHeaders();  
						// headers.setContentType(MediaType.APPLICATION_XML);   // <== Se estiver enviando XML
						// headers.add("Accept", "application/xml");  // <== Se aceita receber de volta XML
						// HttpEntity<String> entity = new HttpEntity<String>(headers);
					
						// Executa o m�todo HTTP para o URI fornecido, sem acrescentar nada no request e
						// retornando a resposta como um objeto do tipo ResponseEntity.
						
						ResponseEntity<Empregado> response = rest.exchange(
								"http://localhost:8080/webservices-01/service/removeEmp/{id}", 
								HttpMethod.DELETE, null, Empregado.class, resposta);
						
						// Onde aparece null  poderia ter sido  especificado um  objeto do tipo  HttpEntity.
						// Como n�o h� nada relevante para enviar foi especificado null. O objeto HttpEntity
						// cont�m um corpo e  headers.  O corpo pode ser  utilizado para conter um empregado 
						// que ser� cadastrado, por exemplo, e o header pode especificar o tipo de  conte�do  
						// que est� sendo enviado ou o tipo de conte�do que queremos receber de volta.
						
						Empregado empregado = response.getBody();

						if(empregado != null)
						{
							System.out.println('\n' + 
									"Id = " + empregado.getId() +
									"  Nome = " + empregado.getNome() +
									"  Sal�rio = " + empregado.getSalario());
						
							System.out.println('\n' + "Empregado removido com sucesso!");
						}
						else
						{
							System.out.println('\n' + "Empregado n�o encontrado.");
						}
					}
					else
					{	System.out.println('\n' + "Empregado n�o removido.");
					}
					
					break;
				}

				case 6:
				{	
					ListaDeEmpregados empregados = rest.getForObject(
							"http://localhost:8080/webservices-01/service/emps", 
							ListaDeEmpregados.class);

					for (Empregado empregado : empregados.getEmpregados())
					{	
						System.out.println('\n' + 
							"Id = " + empregado.getId() +
							"  Nome = " + empregado.getNome() +
							"  Lance m�nimo = " + empregado.getSalario());
					}
					
					break;
				}

				case 7:
				{	
					HttpHeaders headers = new HttpHeaders();  
						
					// headers.setContentType(MediaType.APPLICATION_XML);   // <== Se estiver enviando XML
					headers.add("Accept", "application/xml");  // <== Se aceita receber de volta XML
					HttpEntity<String> entity = new HttpEntity<String>(headers);
				
					// Executa o m�todo HTTP para o URI fornecido, escrevendo a entidade fornecida no
					// request e retornando a resposta como um objeto do tipo ResponseEntity.
					
					ResponseEntity<ListaDeEmpregados> response = rest.exchange(
							"http://localhost:8080/webservices-01/service/emps", 
							HttpMethod.GET, entity, ListaDeEmpregados.class);
					
					// Onde aparece entity poderia ser null pois n�o h� nada relevante nele. O entity
					// cont�m um corpo e headers. O corpo pode ser utilizado para conter um empregado 
					// que ser� cadastrado e o header pode especificar o tipo de  conte�do  que  est� 
					// sendo enviado ou o tipo de conte�do que queremos receber de volta.
					
					ListaDeEmpregados empregados = response.getBody();

					for (Empregado empregado : empregados.getEmpregados())
					{
						System.out.println('\n' + 
								"Id = " + empregado.getId() +
								"  Nome = " + empregado.getNome() +
								"  Sal�rio = " + empregado.getSalario());
					}
					
					break;
				}

				case 8:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Op��o inv�lida!");
			}
		}		
	}
}
