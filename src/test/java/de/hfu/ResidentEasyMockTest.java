package de.hfu;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import de.hfu.residents.service.BaseResidentService;

public class ResidentEasyMockTest {
	@Test
	public void easyMockTest() {
		SimpleDateFormat day_month_year=new SimpleDateFormat("dd/MM/yyyy");  
	    
		String can_date="11/04/2001";  
	    String max_date = "16/08/1991";  
	    String tolga_date = "11/11/2000";  
	    String matze_date = "28/04/2000";
		
	    Date can = null;
	    Date max = null;
	    Date tolga = null;
	    Date matze = null;
		
	    try {
			can = day_month_year.parse(can_date);
			max = day_month_year.parse(max_date);
			tolga = day_month_year.parse(tolga_date);
			matze = day_month_year.parse(matze_date);
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		List<Resident> residents = new ArrayList<Resident>(); //alle
		List<Resident> expectedResidents = new ArrayList<Resident>(); //Die residents die später erwartet werden
		
		residents.add(new Resident("Can","Demir", "Tumringerstrasse 281", "Loerrach", can));
		residents.add(new Resident("Max","Mustermann", "Maxmustermannstrasse 0", "Maxmustermannstadt", max));
		residents.add(new Resident("Tolga","Tolganachname", "Tolgastrasse 0", "Tolgastadt", tolga));
		residents.add(new Resident("Matze","Matzenachname", "Matzestrasse 0", "Matzestadt", matze));
		
		expectedResidents.add(new Resident("Max","Mustermann", "Maxmustermannstrasse 0", "Maxmustermannstadt", max));
		expectedResidents.add(new Resident("Matze","Matzenachname", "Matzestrasse 0", "Matzestadt", matze));
		
		Resident filter = new Resident();
		filter.setGivenName("Ma*");
		
		ResidentRepository mock = createMock(ResidentRepository.class);
		expect(mock.getResidents()).andReturn(residents);
		replay(mock);
		BaseResidentService service = new BaseResidentService();
		service.setResidentRepository(mock);
		List<Resident> result = service.getFilteredResidentsList(filter);
		
		assertThat(result, equalTo(expectedResidents));
	}
}
