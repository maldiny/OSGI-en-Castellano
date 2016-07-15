package consumidor;

import productor.Productor;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;

@Component
public class Consumidor {
	
	Productor productor;
	
	/**
	 * @return the productor
	 */
	public Productor getProductor() {
		return productor;
	}

	/**
	 * @param productor the productor to set
	 */
	@Reference(name="productor", service=Productor.class)
	public void setProductor(Productor productor) {
		this.productor = productor;
	}

	@Activate
	void activate() {
		this.productor.saluda("tomate");
	}

	@Deactivate
	void deactivate() {
		// TODO Auto-generated method stub
	}

}
