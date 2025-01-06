package vttp5a.paf.day22ws.repository;

public class Queries {
    public static String SQL_GET_RSVPS = """
            select * from rsvp;
            """;

    public static String SQL_GET_RSVP_NAME = """
            select * from rsvp
            where name = ?
            """;

    public static String SQL_INSERT_RSVP = """
            replace into rsvp(email, name, phone, confirmation_date, comments)
            values(?, ?, ?, ?, ?);  
            """;

    public static String SQL_COUNT_PERSONS = """
            select count(distinct name) person_count
            from rsvp;
            """;
}
