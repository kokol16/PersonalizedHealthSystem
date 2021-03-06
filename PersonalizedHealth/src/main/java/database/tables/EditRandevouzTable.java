/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import mainClasses.Randevouz;
import database.init.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditRandevouzTable {

    public boolean addRandevouzFromJSON(String json) throws ClassNotFoundException {
        Randevouz r = jsonToRandevouz(json);
        return createNewRandevouz(r);
    }

    public static JsonArray getDoctorRandevouzByDate(int id, String _date) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        JsonArray js_arr = new JsonArray();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id=" + id + " AND date_time=\'" + _date + "\'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                JsonElement js = gson.fromJson(json, JsonElement.class);
                js_arr.add(js);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return js_arr;
    }

    public static JsonArray getDoctosDoneRandevouz(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        JsonArray js_arr = new JsonArray();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id=" + id + " AND status=" + "\"done\"" + "");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                JsonElement js = gson.fromJson(json, JsonElement.class);
                js_arr.add(js);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return js_arr;
    }

    public static ArrayList<Randevouz> getDateOfRandevouz(int user_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> rvl = new ArrayList<Randevouz>();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT date_time FROM randevouz WHERE user_id=" + user_id + "");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz bt = gson.fromJson(json, Randevouz.class);
                rvl.add(bt);
            }

            return rvl;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public static JsonArray getDoctosrandevouz(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        JsonArray js_arr = new JsonArray();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id=" + id + "");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                JsonElement js = gson.fromJson(json, JsonElement.class);
                js_arr.add(js);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return js_arr;
    }

    public static ArrayList<Randevouz> getAllrandevouz() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz_list = new ArrayList<Randevouz>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz bt = gson.fromJson(json, Randevouz.class);
                randevouz_list.add(bt);
            }
            stmt.close();
            con.close();
            return randevouz_list;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public static Randevouz databaseToRandevouz(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE randevouz_id= '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz bt = gson.fromJson(json, Randevouz.class);
            stmt.close();
            con.close();
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public Randevouz jsonToRandevouz(String json) {
        Gson gson = new Gson();
        Randevouz r = gson.fromJson(json, Randevouz.class);
        return r;
    }

    public static String randevouzToJSON(Randevouz r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Randevouz.class);
        return json;
    }

    public void updateRandevouzByDoctor(int randevouzID, int user_id, String info, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET status='" + status + "',doctor_info='" + info + "',user_id='" + user_id + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        System.out.println(updateQuery);
        stmt.close();
        con.close();
    }

    public void updateRandevouz(int randevouzID, int userID, String info, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET user_id='" + userID + "',status='" + status + "',user_info='" + info + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        System.out.println(updateQuery);
        stmt.close();
        con.close();
    }

    public static void reserveRandevouz(int randevouzID, int user_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET status = \"selected\", user_id = '" + user_id + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        System.out.println(updateQuery);
        stmt.close();
        con.close();
    }

    public static void rateRandevouz(int randevouzID, int rating) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET rating = '" + rating + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        System.out.println(updateQuery);
        stmt.close();
        con.close();
    }

    public static void updateUserMessageRandevouz(int randevouzID, String data) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET user_info = '" + data + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        System.out.println(updateQuery);
        stmt.close();
        con.close();
    }

    public boolean cancelRandevouz(int randevouzID) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "UPDATE  randevouz SET status=\"cancelled\" WHERE randevouz_id='" + randevouzID + "'";
        int deleted = stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
        return deleted != 0;
    }

    public static ArrayList<Randevouz> databaseToRandevouzArray() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<Randevouz>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz rand = gson.fromJson(json, Randevouz.class);
                randevouz.add(rand);
            }
            stmt.close();
            con.close();
            return randevouz;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Randevouz> showRandevouzOfID(int user_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<Randevouz>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id = '" + user_id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz rand = gson.fromJson(json, Randevouz.class);
                randevouz.add(rand);
            }
            stmt.close();
            con.close();
            return randevouz;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public static ArrayList<Randevouz> showRandevouzOfDocID(int doctor_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<Randevouz>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id = '" + doctor_id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz rand = gson.fromJson(json, Randevouz.class);
                randevouz.add(rand);
            }
            stmt.close();
            con.close();
            return randevouz;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public boolean deleteRandevouz(int randevouzID) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM randevouz WHERE randevouz_id='" + randevouzID + "'";
        int deleted = stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
        return deleted != 0;
    }

    public void createRandevouzTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE randevouz "
                + "(randevouz_id INTEGER not NULL AUTO_INCREMENT, "
                + " doctor_id INTEGER not NULL, "
                + " user_id INTEGER not NULL, "
                + " date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP not NULL, "
                + " price INTEGER  not NULL, "
                + " doctor_info VARCHAR(500),"
                + " user_info VARCHAR(500),"
                + " rating INTEGER,"
                + " status VARCHAR(15) not null,"
                + "FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id), "
                + " PRIMARY KEY ( randevouz_id  ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public boolean createNewRandevouz(Randevouz rand) throws ClassNotFoundException {
        int result = 0;
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " randevouz (doctor_id,user_id,date_time,price,doctor_info,user_info,rating,status)"
                    + " VALUES ("
                    + "'" + rand.getDoctor_id() + "',"
                    + "'" + rand.getUser_id() + "',"
                    + "'" + rand.getDate_time() + "',"
                    + "'" + rand.getPrice() + "',"
                    + "'" + rand.getDoctor_info() + "',"
                    + "'" + rand.getUser_info() + "',"
                    + "'" + rand.getRating() + "',"
                    + "'" + rand.getStatus() + "'"
                    + ")";
            //stmt.execute(table);

            result = stmt.executeUpdate(insertQuery);
            System.out.println("# The randevouz was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditRandevouzTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result != 0;
    }
}
