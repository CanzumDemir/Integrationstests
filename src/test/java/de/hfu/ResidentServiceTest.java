package de.hfu;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import de.hfu.residents.service.BaseResidentService;

public class ResidentServiceTest {
	@Test
	public void getFilteredResidentsTest() throws Exception {
		Resident sample_one = new Resident();
		Resident sample_two = new Resident();
		Resident sample_three = new Resident();
		Resident sample_four = new Resident();
		
		BaseResidentService testService = new BaseResidentService();
		testService.setResidentRepository(new ResidentRepositoryStub());
		
		  
	    
	    SimpleDateFormat day_month_year=new SimpleDateFormat("dd/MM/yyyy");  
	    String can_date="11/04/2001";
  
	    Date can = null;

		try {
			can = day_month_year.parse(can_date);
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		sample_one.setGivenName("Ma*"); //Max, Matze
		sample_one.setFamilyName("Mus*"); //Max
		
		sample_two.setDateOfBirth(can); //Can
		
		sample_three.setGivenName("M*"); //Max, Matze
		sample_three.setFamilyName("M*"); //Max, Matze
		
		sample_four.setFamilyName("Tolganachname"); //Max, Matze
		 
		
		List<Resident> list_one = testService.getFilteredResidentsList(sample_one); //Max
		List<Resident> list_two = testService.getFilteredResidentsList(sample_two); //Can
		List<Resident> list_three = testService.getFilteredResidentsList(sample_three); //Max, Matze
		List<Resident> list_four = testService.getFilteredResidentsList(sample_four); //Tolga
		
		assertEquals(1, list_one.size()); //Max
		assertEquals(1, list_two.size()); //Can
		assertEquals(2, list_three.size()); // Max, Matze
		assertEquals(1, list_four.size()); //Tolga
	}
	
	@Test
	public void getUniqueResidentTest() throws Exception {
		BaseResidentService testService = new BaseResidentService();
		testService.setResidentRepository(new ResidentRepositoryStub());
		
		SimpleDateFormat day_month_year=new SimpleDateFormat("dd/MM/yyyy");  
	    
		String can_date="11/04/2001";  
	    String max_date = "16/08/1991";  
	    String tolga_date = "11/11/2000";  
		
	    Date can = null;
	    Date max = null;
	    Date tolga = null;
		
	    try {
			can = day_month_year.parse(can_date);
			max = day_month_year.parse(max_date);
			tolga = day_month_year.parse(tolga_date);
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		Resident resident_one = new Resident("Max", null, null, null, max);
		Resident resident_two = new Resident("Tolga", "Tolganachname", null, null, null);
		Resident resident_three = new Resident(null, "Demir", null, "Loerrach", null);
		
		
		
		Resident resident = testService.getUniqueResident(resident_one);
			
		assertEquals("Max", resident.getGivenName());
		assertEquals("Mustermann", resident.getFamilyName());
		assertEquals("Maxmustermannstrasse 0", resident.getStreet());
		assertEquals("Maxmustermannstadt", resident.getCity());
		assertEquals(max, resident.getDateOfBirth());
			
		
		resident = testService.getUniqueResident(resident_two);
		
		assertEquals("Tolga", resident.getGivenName());
		assertEquals("Tolganachname", resident.getFamilyName());
		assertEquals("Tolgastrasse 0", resident.getStreet());
		assertEquals("Tolgastadt", resident.getCity());
		assertEquals(tolga, resident.getDateOfBirth());
		
		
		resident = testService.getUniqueResident(resident_three);
		
		assertEquals("Can", resident.getGivenName());
		assertEquals("Demir", resident.getFamilyName());
		assertEquals("Tumringerstrasse 281", resident.getStreet());
		assertEquals("Loerrach", resident.getCity());
		assertEquals(can, resident.getDateOfBirth());
	}
}
