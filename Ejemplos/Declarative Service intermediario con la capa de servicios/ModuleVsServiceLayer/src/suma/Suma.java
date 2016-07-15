package suma;

import operaciones.Operaciones;
import operaciones.impl.OperacionesImpl;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;

@Component
public class Suma {

	@Activate
	void activate() {
		Operaciones op = new OperacionesImpl();
		op.suma(3, 3);
	}

	@Deactivate
	void deactivate() {
		// TODO Auto-generated method stub
	}

}
