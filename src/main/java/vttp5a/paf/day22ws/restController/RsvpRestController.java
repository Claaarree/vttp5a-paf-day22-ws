package vttp5a.paf.day22ws.restController;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObjectBuilder;
import vttp5a.paf.day22ws.service.RsvpService;

@RestController
@RequestMapping("/api")
public class RsvpRestController {
    
    @Autowired
    RsvpService rsvpService;

    @GetMapping(path = "/rsvps", produces = "application/json")
    public ResponseEntity<String> getAllRsvps(){
        ResponseEntity<String> res = ResponseEntity.ok()
        .body(rsvpService.getAllRsvps().toString());

        return res;
    }

    @GetMapping(path = "/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRsvpByName(@RequestParam (defaultValue = "adele") String name) {
        JsonArray jArray = rsvpService.getRsvpByName(name);
        
        try {
            jArray.getJsonObject(0);
        } catch (ClassCastException e) {
            return ResponseEntity.status(404).body(jArray.toString());        
        }
        
        return ResponseEntity.ok(jArray.toString());
    }

    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRsvp(@RequestBody String data) {
        Boolean isAdded = null;
        
        try {
            isAdded = rsvpService.addRsvp(data);
        } catch (ParseException e) {
            System.out.println("Error in parsing date! Post add!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectBuilder jObjectbBuilder = Json.createObjectBuilder();
    
        if(isAdded){
            jObjectbBuilder.add("status", "Successfully added!");
            return ResponseEntity.status(201).body(jObjectbBuilder.build().toString());
        }    
        jObjectbBuilder.add("status", "Failed to add rsvp!");
        return ResponseEntity.status(404).body(jObjectbBuilder.build().toString());
    }

    // TODO update
    // @PutMapping(path = "/rsvp/{email}", consumes = "application/json", produces = "application/json")
    // public ResponseEntity<String> updateRsvp(@RequestBody String dataToUpdate, @PathVariable String email){
        
    // }

    @GetMapping(path = "/rsvps/count", produces = "application/json")
    public ResponseEntity<String> getCount() {
        return ResponseEntity.ok().body(rsvpService.countPersons().toString());
    }
}
