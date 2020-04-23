package ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modelo.Empregado;

import org.springframework.stereotype.Service;

@Service
public class EmpregadoDS {

	private static Map<Long, Empregado> todosEmpregados;
	private static long contador = 2L;
	
	static {
		todosEmpregados = new HashMap<Long, Empregado>();
		Empregado e1 = new Empregado(1L, "Luis Carlos Nogueira", 4000);
		Empregado e2 = new Empregado(2L, "Pedro Arnaldo Soares", 6800);
		todosEmpregados.put(e1.getId(), e1);
		todosEmpregados.put(e2.getId(), e2);
	}
	
	public void adiciona(Empregado e) {
		contador++;
		e.setId(contador);
		todosEmpregados.put(e.getId(), e);
	}

	public Empregado recuperaPeloId(long id) {
		return todosEmpregados.get(id);
	}

	public List<Empregado> recuperaTodos() {
		List<Empregado> empregados = new ArrayList<Empregado>();
		for( Iterator<Empregado> it = todosEmpregados.values().iterator(); it.hasNext(); ) {
			Empregado e = it.next();
			empregados.add(e);
		}
		return empregados;
	}

	public void exclui(long id) {
		todosEmpregados.remove(id);
	}

	public void altera(Empregado e) {
		todosEmpregados.put(e.getId(), e);
	}
}
