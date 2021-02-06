package it.akademija.kindergarten;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KindergartenInit {

	@Autowired
	KindergartenDAO gartenDao;

	@PostConstruct
	public void uploadKindergartenData() throws IOException {
		
		// pirmas darželis sąraše pavadinimu test dėl bug
		if (gartenDao.findAll().size() == 0) {
			Kindergarten obj = new Kindergarten();
			
			InputStream inputStream = obj.getClass().getClassLoader().getResourceAsStream("darzeliu_adresai.csv");

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] data = line.split(";");
					Kindergarten kindergarten = new Kindergarten();
					kindergarten.setId(data[0]);
					kindergarten.setName(data[1].trim());
					kindergarten.setAddress(data[2]);
					kindergarten.setElderate(data[3]);
					kindergarten.setCapacityAgeGroup2to3(0);
					kindergarten.setCapacityAgeGroup3to6(0);

					gartenDao.save(kindergarten);
				}
				
			// ištrinam pirmą darželį test -- apėjimas bug, kadangi pirmo įkelto darželio DB nenuskaito pagal pavadinimą
				//gartenDao.deleteById(1l);

			}
		

		}
	}

}
