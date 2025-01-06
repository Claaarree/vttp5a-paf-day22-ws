package vttp5a.paf.day22ws.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Rsvp {
    private String email;
    private String name;
    private String phoneNumber;
    private Date confirmationDate;
    private String comments;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Date getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public static Rsvp toRsvp(SqlRowSet rs){
        Rsvp r = new Rsvp();
        r.setEmail(rs.getString("email"));
        r.setName(rs.getString("name"));
        r.setPhoneNumber(rs.getString("phone"));
        r.setConfirmationDate(rs.getDate("confirmation_date"));
        r.setComments(rs.getString("comments"));

        return r;
    }

    public static JsonObject toJson(Rsvp r){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String confirmDateString = sdf.format(r.getConfirmationDate());

        JsonObject jObject = Json.createObjectBuilder()
            .add("email", r.getEmail())
            .add("phone", r.getPhoneNumber())
            .add("confirmDate", confirmDateString)
            .add("comments", r.getComments())
            .build();

        return jObject;
    }
}
