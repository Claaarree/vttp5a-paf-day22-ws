package vttp5a.paf.day22ws.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp5a.paf.day22ws.model.Rsvp;

@Repository
public class RsvpRepository {
    
    @Autowired
    JdbcTemplate template;

    public List<Rsvp> getAllRsvps() {
        List<Rsvp> rsvpList = new ArrayList<>();
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_GET_RSVPS);

        while (rs.next()){
            rsvpList.add(Rsvp.toRsvp(rs));
        }

        return rsvpList;
    }

    public Optional<List<Rsvp>> getRsvpByName(String name){
        List<Rsvp> records = new ArrayList<>();
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_GET_RSVP_NAME, name);

        if (!rs.next()){
            return Optional.empty();
        } else {
            do {
                records.add(Rsvp.toRsvp(rs));
            } while (rs.next());

            return Optional.of(records);
        }
    }

    public Boolean addRsvp(String email, String name, String phone, Date confirmation_date, String comments) {
        Boolean isAdded = null;
        try {
            template.update(Queries.SQL_INSERT_RSVP, email, name, phone, confirmation_date, comments);
            isAdded = true;
        } catch (Exception e) {
            System.out.println("Error in adding to rsvp");
            isAdded = false;
        }

        return isAdded;
    }

    public int countPersons(){
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_COUNT_PERSONS);
        rs.next();

        int personCount = rs.getInt("person_count");

        return personCount;
    }

}
