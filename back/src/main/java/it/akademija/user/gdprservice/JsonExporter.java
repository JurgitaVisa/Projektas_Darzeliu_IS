package it.akademija.user.gdprservice;

import it.akademija.user.User;

public interface JsonExporter {

	String export(User userData);
}
