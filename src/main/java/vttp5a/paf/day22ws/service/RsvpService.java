package vttp5a.paf.day22ws.service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp5a.paf.day22ws.model.Rsvp;
import vttp5a.paf.day22ws.repository.RsvpRepository;

@Service
public class RsvpService {
    
    @Autowired
    RsvpRepository rsvpRepository;

    public JsonArray getAllRsvps() {

        List<Rsvp> rsvpList = rsvpRepository.getAllRsvps();

        return toJsonArray(rsvpList);        
    }

    public JsonArray getRsvpByName(String name) {
        Optional<List<Rsvp>> opt = rsvpRepository.getRsvpByName(name);
        if(opt.isEmpty()){
            JsonArray error = Json.createArrayBuilder()
                    .add( "The name you have entered has no RSVPs found!")
                    .build();
            return error;
        } else {
            return toJsonArray(opt.get());
        }
    }

    public JsonArray toJsonArray(List<Rsvp> rsvpList) {
        JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();

        for(Rsvp r: rsvpList){
            JsonObject jObject = Rsvp.toJson(r);
            jArrayBuilder.add(jObject);
        }

        return jArrayBuilder.build();
    }

    public Boolean addRsvp(String data) throws ParseException{
        JsonReader jReader = Json.createReader(new StringReader(data));
        JsonObject jObject = jReader.readObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
        String email = jObject.getString("email");
        String phone = jObject.getString("phone");
        String comments = jObject.getString("comments");
        Date confirmDate = sdf.parse(jObject.getString("confirmDate"));
        
        return rsvpRepository.addRsvp(email, "default", phone, confirmDate, comments);
    }

    public JsonObject countPersons() {
        int personCount = rsvpRepository.countPersons();

        JsonObject jObject = Json.createObjectBuilder()
                .add("person_count", personCount)
                .build();

        return jObject;
    }
}
