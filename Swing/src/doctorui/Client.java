/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author roshanantonyarunkumar
 */
public class Client extends Person {

    private int code;
    private int version_id;
    private int doctor_id;

    public float fov;
    public float brightness;
    public float size;
    public float pos_x;
    public float pos_y;
    public float off_x;
    public float off_y;

    public Client(int id) {
        setID(id);
        String jStr = getUserData();
        try {
            setUserData(jStr);
        } catch (JSONException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        jStr = getProgramData();
        try {
            setProgramData(jStr);
        } catch (JSONException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setVersionID(int version_id) {
        this.version_id = version_id;
    }

    public void setDoctorID(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getCode() {
        return code;
    }

    public int getVersionID() {
        return version_id;
    }

    public int getDoctorID() {
        return doctor_id;
    }

    public String getUserData() {
        String id = Integer.toString(getID());
        try {
            String link = "http://vrvision.a2hosted.com/getclientdata.php";
            String data = URLEncoder.encode("id", "UTF-8") + "="
                    + URLEncoder.encode(id, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    public void setUserData(String jsonStr) throws JSONException {
        JSONArray jsonarray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            setName(jsonobject.getString("name"));
            setUsername(jsonobject.getString("username"));
            code = jsonobject.getInt("code");
            doctor_id = jsonobject.getInt("doctor_id");
            version_id = jsonobject.getInt("version_id");

        }
    }

    public String getProgramData() {
        try {
            String user_id = Integer.toString(getID());
            String version_id = Integer.toString(getVersionID());

            String link = "http://vrvision.a2hosted.com/getprogramdata.php";
            String data = URLEncoder.encode("id", "UTF-8") + "="
                    + URLEncoder.encode(version_id, "UTF-8");
            data += "&" + URLEncoder.encode("user_id", "UTF-8") + "="
                    + URLEncoder.encode(user_id, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    public void setProgramData(String jsonStr) throws JSONException {
        JSONArray jsonarray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            fov = (float) jsonobject.getDouble("fov");
            brightness = (float) jsonobject.getDouble("brightness");
            size = (float) jsonobject.getDouble("size");
            pos_x = (float) jsonobject.getDouble("pos_x");
            pos_y = (float) jsonobject.getDouble("pos_y");
            off_x = (float) jsonobject.getDouble("off_x");
            off_y = (float) jsonobject.getDouble("off_y");

        }
    }

}
