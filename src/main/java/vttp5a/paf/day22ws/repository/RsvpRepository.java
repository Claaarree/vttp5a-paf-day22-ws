package vttp5a.paf.day22ws.repository;

import java.util.ArrayList;
import java.util.List;

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

}
