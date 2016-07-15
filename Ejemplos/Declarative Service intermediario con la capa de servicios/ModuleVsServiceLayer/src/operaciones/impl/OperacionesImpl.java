package operaciones.impl;

import operaciones.Operaciones;
import aQute.bnd.annotation.component.Component;

@Component
public class OperacionesImpl implements Operaciones{

	@Override
	public int suma(int i, int j) {
		System.out.println(i+j);
		return i+j;
	}

	@Override
	public int resta(int i, int j) {
		System.out.println(i-j);
		return i-j;
	}

}
