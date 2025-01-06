package vttp5a.paf.day22ws.restController;

import java.net.URI;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonArray;
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

    @GetMapping(path = "/rsvp", produces = "application/json")
    public ResponseEntity<String> getRsvpByName(@RequestParam String name) {
        JsonArray jArray = rsvpService.getRsvpByName(name);
        
        try {
            jArray.getJsonObject(0);
        } catch (ClassCastException e) {
            return ResponseEntity.status(404).body(jArray.toString());        
        }
        
        return ResponseEntity.ok(jArray.toString());
    }

    @PostMapping(path = "/rsvp", consumes = "application/json", produces = "application/json")
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

        URI url = UriComponentsBuilder.fromUriString("http://localhost:8080")
                .build().toUri();

        if(isAdded){
            return ResponseEntity.created(url).body("Successfully added!");
        }    
        return ResponseEntity.status(404).body("Failed to add rsvp!");
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
