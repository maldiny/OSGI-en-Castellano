package productor.impl;

import productor.Productor;
import aQute.bnd.annotation.component.Component;

@Component
public class ProductorImpl implements Productor {

	@Override
	public String saluda(String nombre) {
		String s = "Hola " + nombre;
		System.out.println(s);
		return s;
	}

}
