package it.akademija.user.gdprservice;

import java.lang.reflect.Type;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import it.akademija.user.User;

@Service
public class JsonEcporterImpl implements JsonExporter {	

	@Override
	public String export(User userData) {
		
		Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new JsonSerializer<User>() {       

			@Override
			public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
				 JsonObject jsonObject = new JsonObject();		            
		            jsonObject.addProperty("Naudotojo_vardas", user.getUsername());
		            jsonObject.addProperty("Asmens_kodas", user.getParentDetails().getPersonalCode());
		            jsonObject.addProperty("Vardas", user.getName());
		            jsonObject.addProperty("Pavardė", user.getSurname());
		            jsonObject.addProperty("Telefonas", user.getParentDetails().getPhone());
		            jsonObject.addProperty("El.pastas", user.getParentDetails().getEmail());
		            return jsonObject;
			}
	    }).setPrettyPrinting().create();
		
		String userInJson = gson.toJson(userData);
		return userInJson;
	}

}
