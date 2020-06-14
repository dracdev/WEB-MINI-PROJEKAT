
public class Aranzman {

	private String id_aranzmana;		// broj zdravstvenog osiguranja
	private String full_name; //ime i prezime putnika
	private String passport_number; //broj pasosa
	private String destination_name; //ime destinacije
	private String vacation_type; //drop down list
	private String price; //cena
	private String confirmation; //hiperlink potvrda
	private boolean confirmed;
	
	public Aranzman() {		
		super();
	}

	public Aranzman(String id_aranzmana, String full_name, String passport_number, String destination_name,
			String vacation_type, String price, String confirmation) {
		super();
		this.id_aranzmana = id_aranzmana;
		this.full_name = full_name;
		this.passport_number = passport_number;
		this.destination_name = destination_name;
		this.vacation_type = vacation_type;
		this.price = price;
		this.confirmation = confirmation;
		this.confirmed= false;
		
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getId_aranzmana() {
		return id_aranzmana;
	}

	public void setId_aranzmana(String id_aranzmana) {
		this.id_aranzmana = id_aranzmana;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getPassport_number() {
		return passport_number;
	}

	public void setPassport_number(String passport_number) {
		this.passport_number = passport_number;
	}

	public String getDestination_name() {
		return destination_name;
	}

	public void setDestination_name(String destination_name) {
		this.destination_name = destination_name;
	}

	public String getVacation_type() {
		return vacation_type;
	}

	public void setVacation_type(String vacation_type) {
		this.vacation_type = vacation_type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	
	
	
	
	
}
