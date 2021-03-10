package it.akademija.gdprservice;

import it.akademija.user.User;

public interface JsonExporter {

	String export(User userData);
}
