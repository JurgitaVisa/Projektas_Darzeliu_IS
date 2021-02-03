package it.akademija.kindergarten;

import java.io.IOException;
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
	public void uploadKindergartenData() {

		Path path = Paths.get("src/main/resources/darzeliu_adresai.csv");

		if (gartenDao.findAll().size() == 0) {

			try {

				List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);

				for (String line : list) {
					String[] data = line.split(";");
					Kindergarten kindergarten = new Kindergarten();
					kindergarten.setName(data[0].trim());
					kindergarten.setAddress(data[1]);

					gartenDao.save(kindergarten);

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
