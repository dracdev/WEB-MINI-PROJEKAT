package dao;

import java.util.Collection;
import java.util.HashMap;

import beans.Aranzman;

public class AranzmanDAO {
	
	private HashMap<String, Aranzman> aranzmani = new HashMap<String, Aranzman>();
	
	
	public AranzmanDAO() {	
	}
	public void addAranzman(Aranzman a) {
		//provera da li je jedinstven aranzman po id
		aranzmani.put(a.getId_aranzmana(),a);		
	}
	
	public Collection<Aranzman> findAll(){
		return aranzmani.values();
	}
	
	public void confirmAranzman(String id) {
		Aranzman a = aranzmani.get(id);
		a.setConfirmed(true);
		aranzmani.put(id,a);
	}
	
	public Aranzman find(String id) {		
		return aranzmani.get(id);
	}
	
	
	
}
