package resta;

import operaciones.Operaciones;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;

@Component
public class Resta {
	
	Operaciones operaciones;
	
	/**
	 * @return the operaciones
	 */
	public Operaciones getOperaciones() {
		return operaciones;
	}

	/**
	 * @param operaciones the operaciones to set
	 */
	@Reference(name="operaciones", service=Operaciones.class)
	public void setOperaciones(Operaciones operaciones) {
		this.operaciones = operaciones;
	}

	@Activate
	void activate() {
		this.operaciones.resta(3, 2);
	}

	@Deactivate
	void deactivate() {
		// TODO Auto-generated method stub
	}

}
