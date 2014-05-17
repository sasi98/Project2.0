package dataManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;


//Estructura usada para almacenar los datos de una empresa
public class Company {

	private String id;
	private String name;
	private int quoteDays; //Número de dias de cotización
	private ArrayList<Date> dates;
	private ArrayList<BigDecimal> ris;
	private ArrayList<BigDecimal> rms;
	private static Logger log = Logger.getLogger(Company.class);
	
	
	public Company() {
		super();
	}
	
	public Company(String id, String name, int quoteDays, ArrayList<Date> dates,
		ArrayList<BigDecimal> ris, 
		ArrayList<BigDecimal> rms) {
		super();
		this.id = id;
		this.name = name;
		this.quoteDays = quoteDays;
		this.dates = dates;
		this.ris = ris;
		this.rms = rms;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuoteDays() {
		return quoteDays;
	}
	
	public void setQuoteDays(int quoteDays) {
		this.quoteDays = quoteDays;
	}
	
	public ArrayList<Date> getDates() {
		return dates;
	}
	
	public void setDates(ArrayList<Date> dates) {
		this.dates = dates;
	}
	
	public ArrayList<BigDecimal> getRis() {
		return ris;
	}
	
	public void setRi(ArrayList<BigDecimal> ris) {
		this.ris = ris;
	}
	
	public ArrayList<BigDecimal> getRms() {
		return rms;
	}
	
	public void setRms(ArrayList<BigDecimal> rms) {
		this.rms = rms;
	}
	
	public void printCompany (){
		log.trace("Id: " + this.id + "/n");
		log.trace("Name: " + this.name + "/n");
		log.trace("Nº quote days: " + this.quoteDays + "/n");
		System.out.println("Id: " + this.id + "/n");
		System.out.println("Name: " + this.name + "/n");
		System.out.println("Nº quote days: " + this.quoteDays + "/n");
		for(Date d: dates)
		{
			System.out.println( d + " ");
			log.trace( d + " ");
			
		}
		for(BigDecimal b: ris)
		{
			System.out.println( b + " ");
			log.trace( b + " ");
			
		}
		System.out.println("\n");
		for(BigDecimal b: rms)
		{
			System.out.println( b + " ");
			log.trace( b + " ");
		}	
		System.out.println("\n");
	}
	

}
