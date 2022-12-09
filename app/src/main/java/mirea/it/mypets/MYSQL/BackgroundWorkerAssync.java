package mirea.it.mypets.MYSQL;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import mirea.it.mypets.MYSQL.MYSQL_ENTITYS.Application;
import mirea.it.mypets.MYSQL.MYSQL_ENTITYS.Client;
import mirea.it.mypets.MYSQL.MYSQL_ENTITYS.Pet;
import mirea.it.mypets.MYSQL.MYSQL_GettingData.LoadingScreenActivity;
import mirea.it.mypets.mainactivities.AdminActivity;
import mirea.it.mypets.mainactivities.ApplicationActivity;
import mirea.it.mypets.mainactivities.LoginAdminActivity;
import mirea.it.mypets.mainactivities.MainActivity;

//uzorov02ma_mypet
//HPX6oK9FFPLt*VUh

public class BackgroundWorkerAssync extends AsyncTask<String, Void, String> {

    private NotifyThatNewItemWasAdded listener;

    public static ArrayList<Application> clientsApplications = new ArrayList<>();
    public static ArrayList<Pet> pets = new ArrayList<Pet>();

    static final String hostIP = "mypetapplication2.000webhostapp.com";

    public static Client currentClient = null;
    static String currentClientsName = "";
    static String currentClientsPassword = "";

    public static final char LOGIN_FAILED = '0';
    public static final char REGISTRATION_COMPLETE = '1';
    public static final char LOGIN_COMPLETE = '2';
    public static final char GET_CLIENTS_DATA = '3';
    public static final char GET_CLIENTS_APPS = '4';
    public static final char CLIENT_HAS_NO_APPS = '5';
    public static final char GET_APPS_PETS = '6';
    public static final char REGISTRATION_FAILED = '7';
    public static final char DATA_ADDED_SUCCESSFULLY = '8';
    public static final char APPLICATION_DELETED_SUCCESSFULLY = '9';
    public static final char DATA_UPDATED_SUCCESSFULLY = 'A';


    public static final String GET_USER_DATA_CODE = "Get data";
    public static final String GET_USERS_APPLICATIONS_CODE = "Get applications";
    public static final String GET_APPS_PETS_CODE = "Get pets";
    public static final String SIGN_UP_CODE = "Sign on";
    public static final String LOG_IN_CODE = "Log in";
    public static final String SEND_NEW_DATA_CODE = "Send data";
    public static final String DELETE_DATA_CODE = "Delete data";
    public static final String UPDATE_DATA_CODE = "Update data";


    String clientsName;
    String password;
    String clientsPhoneNumber;
    String clientsMail;

    Context context;
    AlertDialog alertDialog;

    TextView helloUser;

    public BackgroundWorkerAssync(Context context) {
        this.context = context;
        if (context.getClass() == ApplicationActivity.class)
            listener = (NotifyThatNewItemWasAdded) context;
    }

    public BackgroundWorkerAssync(Context context, TextView helloUser) {
        this.context = context;
        this.helloUser = helloUser;

    }

    @Override
    protected void onPreExecute() {
        //In UI thread, you can access UI here
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... strings) {

        String typeOfReq = strings[0];

        if (typeOfReq.equals(SIGN_UP_CODE)) {
            //Регистрация нового пользователя
            String signup_url = "https://" + hostIP + "/signUp.php";

            clientsName = strings[1];
            password = strings[2];
            clientsPhoneNumber = strings[3];
            clientsMail = strings[4];


            try {

                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("clientsName", "UTF-8") +
                        "=" + URLEncoder.encode(clientsName, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") +
                        "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("clientsPhoneNumber", "UTF-8") +
                        "=" + URLEncoder.encode(clientsPhoneNumber, "UTF-8") + "&" +
                        URLEncoder.encode("clientsMail", "UTF-8") +
                        "=" + URLEncoder.encode(clientsMail, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }

                Log.d("signUp", "Result: " + result);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_out_debug", "Регистрация не удалась");

            }
        } else if (typeOfReq.equals(LOG_IN_CODE)) {
            clientsName = strings[1];
            password = strings[2];

            String signIn_url = "https://" + hostIP + "/login.php";
            try {

                URL url = new URL(signIn_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("clientsName", "UTF-8") +
                        "=" + URLEncoder.encode(clientsName, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") +
                        "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_in_debug", "Авторизация не удалась");

            }
        } else if (typeOfReq.equals(GET_USER_DATA_CODE)) {

            String getUsersData_url = "https://" + hostIP + "/getUserData.php";

            try {

                URL url = new URL(getUsersData_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("clientsName", "UTF-8") +
                        "=" + URLEncoder.encode(currentClientsName, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") +
                        "=" + URLEncoder.encode(currentClientsPassword, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_in_debug", "Получение данных не удалось");

            }

        } else if (typeOfReq.equals(GET_USERS_APPLICATIONS_CODE)) {


            String getUsersApps_url = "https://" + hostIP + "/getUsersApps.php";

            try {

                URL url = new URL(getUsersApps_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("idClient", "UTF-8") +
                        "=" + URLEncoder.encode(currentClient.getId(), "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_in_debug", "Получение данных не удалось");

            }
        } else if (typeOfReq.equals(GET_APPS_PETS_CODE)) {
            String getUsersApps_url = "https://" + hostIP + "/getAppsPets.php";


            try {

                URL url = new URL(getUsersApps_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("idClient", "UTF-8") +
                        "=" + URLEncoder.encode(currentClient.getId(), "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_in_debug", "Получение данных не удалось");

            }
        } else if (typeOfReq.equals(SEND_NEW_DATA_CODE)) {
            String getUsersApps_url = "https://" + hostIP + "/sendNewData.php";
            ZoneId zonedId;
            String todayDate;
            String petsName = strings[1];
            String petsType = strings[2];
            String petsAge = strings[3];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                zonedId = ZoneId.of("Europe/Paris");
                todayDate = LocalDate.now(zonedId).toString();
                Log.d("application_getting", todayDate);
            } else {
                todayDate = "Не удалось получить дату";
            }

            try {

                URL url = new URL(getUsersApps_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("today_date", "UTF-8") +
                        "=" + URLEncoder.encode(todayDate, "UTF-8") + "&" +
                        URLEncoder.encode("pet_name", "UTF-8") +
                        "=" + URLEncoder.encode(petsName, "UTF-8") + "&" +
                        URLEncoder.encode("pet_type", "UTF-8") +
                        "=" + URLEncoder.encode(petsType, "UTF-8") + "&" +
                        URLEncoder.encode("pet_age", "UTF-8") +
                        "=" + URLEncoder.encode(petsAge, "UTF-8") + "&" +
                        URLEncoder.encode("current_client_id", "UTF-8") +
                        "=" + URLEncoder.encode(currentClient.getId(), "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }

                Log.d("sendingNewDataTest", "Result: " + result);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_in_debug", "Получение данных не удалось");

            }
        } else if (typeOfReq.equals(DELETE_DATA_CODE)) {
            String getUsersApps_url = "https://" + hostIP + "/deleteData.php";

            String idOfDeletingApplication = strings[1];

            try {

                URL url = new URL(getUsersApps_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("idOfDeletingApplication", "UTF-8") +
                        "=" + URLEncoder.encode(idOfDeletingApplication, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_in_debug", "Получение данных не удалось");

            }
        } else if (typeOfReq.equals(UPDATE_DATA_CODE)) {
            String getUsersApps_url = "https://" + hostIP + "/updateData.php";

            String newPetName = strings[1];
            String newPetType = strings[2];
            String idApplication = strings[3];

            try {

                URL url = new URL(getUsersApps_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("newPetName", "UTF-8") +
                        "=" + URLEncoder.encode(newPetName, "UTF-8") + "&" +
                        URLEncoder.encode("newPetType", "UTF-8") +
                        "=" + URLEncoder.encode(newPetType, "UTF-8") + "&" +
                        URLEncoder.encode("idApplication", "UTF-8") +
                        "=" + URLEncoder.encode(idApplication, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_in_debug", "Получение данных не удалось");

            }
        }

        return null;
    }


    @Override
    protected void onPostExecute(String result) {


        switch (result.charAt(0)) {
            case REGISTRATION_COMPLETE:

                Toast.makeText(context, "Вы успешно зарегистрировались!", Toast.LENGTH_LONG).show();
                currentClientsName = clientsName;
                currentClientsPassword = password;

                //Переход на страницу загрузки данных
                Intent intent1 = new Intent(context, LoadingScreenActivity.class);
                //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
                break;

            case REGISTRATION_FAILED:

                Toast.makeText(context, "Почта или номер уже используются!", Toast.LENGTH_LONG).show();
                Toast.makeText(context, "Попробуйте ещё раз!", Toast.LENGTH_LONG).show();

                break;

            case LOGIN_COMPLETE:
                Toast.makeText(context, "Авторизация успешна!", Toast.LENGTH_LONG).show();
                currentClientsName = clientsName;
                currentClientsPassword = password;

                //Переход на страницу загрузки данных
                Intent intent2 = new Intent(context, LoadingScreenActivity.class);
                //intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
                break;

            case LOGIN_FAILED:
                Toast.makeText(context, "Аккаунт не найден! \n Проверьте введённые данные...", Toast.LENGTH_LONG).show();
                break;
            case GET_CLIENTS_DATA:


                String jsonUserGeneralData = result.substring(1);


                try {
                    JSONObject clientsJSONData = new JSONObject(jsonUserGeneralData);
                    helloUser.setText(String.format("%s%s", helloUser.getText(), clientsJSONData.getString("Name")));
                    setCurrentUser(clientsJSONData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Ошибка получения данных... Проверьте подключение и повторите снова", Toast.LENGTH_LONG).show();
                }


                BackgroundWorkerAssync backgroundWorkerAssync_GettingUsersApps = new BackgroundWorkerAssync(context);
                backgroundWorkerAssync_GettingUsersApps.execute(GET_USERS_APPLICATIONS_CODE);
                break;

            case GET_CLIENTS_APPS:
                //String jsonClientsAppsData = getResultWithoutCode(result);

                //JSONObject applicationsJSONData = new JSONObject(jsonClientsAppsData);
                //setCurrentUser(applicationsJSONData);
                Log.d("currentClientTest", "Now he`s getting his applications: " + currentClient.toString());
                try {
                    ArrayList<JSONObject> jsonApps = separateApps(getResultWithoutCode(result));
                    addNewApps(jsonApps);
                    Toast.makeText(context, "Данные о пользователе успешно полученыhhuigui", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Log.d("application_getting", e.toString());
                    e.printStackTrace();
                    Toast.makeText(context, "Произошла ошибка структуры базы данных... Если у Вас есть возможность," +
                            "свяжитесь с разработчиком...", Toast.LENGTH_LONG).show();
                }

                BackgroundWorkerAssync backgroundWorkerAssync_GettingAppsPets = new BackgroundWorkerAssync(context);
                backgroundWorkerAssync_GettingAppsPets.execute(GET_APPS_PETS_CODE);


                break;
            case GET_APPS_PETS:
                Log.d("currentClientTest", "Now his applications is getting pets:" + currentClient.toString());
                try {
                    addNewPets(
                            separatePets(
                                    getResultWithoutCode(result)));
                    setEachApplicationForEachPet();
                    Toast.makeText(context, "Данные успешно получены", Toast.LENGTH_LONG).show();
                    if (ApplicationActivity.class == context.getClass()) {
                        listener.notifyThatItemAdded();

                        Intent newIntent = new Intent(context, ApplicationActivity.class);
                        context.startActivity(newIntent);
                    } else if (LoginAdminActivity.class == context.getClass()) {
                        Intent intent5 = new Intent(context, AdminActivity.class);
                        //intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent5);
                    } else if (AdminActivity.class == context.getClass()) {
                        Toast.makeText(context, "Заявка успешно удалена", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("getting_apps_test", context.getClass().toString());
                        Intent intent4 = new Intent(context, MainActivity.class);
                        //intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent4);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("application_getting", e.toString());
                    Toast.makeText(context, "Ошибка получения данных... Проверьте подключение и повторите снова", Toast.LENGTH_LONG).show();
                }
                break;
            case DATA_ADDED_SUCCESSFULLY:

                Toast.makeText(context, "Заявка успешно добавлена!", Toast.LENGTH_LONG).show();
                BackgroundWorkerAssync backgroundWorkerAssync_GettingUserApps = new BackgroundWorkerAssync(context);
                backgroundWorkerAssync_GettingUserApps.execute(GET_USERS_APPLICATIONS_CODE);


                break;
            case APPLICATION_DELETED_SUCCESSFULLY:

                Toast.makeText(context, "Заявка удалена", Toast.LENGTH_LONG).show();
                BackgroundWorkerAssync backgroundWorkerAssync_PostDeletingApp = new BackgroundWorkerAssync(context);
                backgroundWorkerAssync_PostDeletingApp.execute(GET_USERS_APPLICATIONS_CODE);


                break;

            case DATA_UPDATED_SUCCESSFULLY:

                Toast.makeText(context, "Данные успешно обновлены", Toast.LENGTH_LONG).show();
                BackgroundWorkerAssync backgroundWorkerAssync_DeletingApp = new BackgroundWorkerAssync(context);
                backgroundWorkerAssync_DeletingApp.execute(GET_USERS_APPLICATIONS_CODE);


                break;

            case CLIENT_HAS_NO_APPS:

                if (context.getClass() == ApplicationActivity.class) {
                    Toast.makeText(context, "У Вас не осталось заявок!", Toast.LENGTH_LONG).show();
                    clientsApplications = new ArrayList<Application>();

                } else if (LoginAdminActivity.class == context.getClass()) {
                    Toast.makeText(context, "В системе отсутствуют заявки!", Toast.LENGTH_LONG).show();
                    Intent intent5 = new Intent(context, AdminActivity.class);
                    //intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent5);
                }
                else if (AdminActivity.class == context.getClass()) {
                    Toast.makeText(context, "Вы удалили все заявки!", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(context, "Данные успешно получены", Toast.LENGTH_LONG).show();
                    Intent intent5 = new Intent(context, MainActivity.class);
                    //intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent5);

                }


                break;
        }


    }

    private void setEachApplicationForEachPet() {

        for (Application application : clientsApplications)
            for (Pet pet : pets) {
                if (application.getId().equals(pet.getIdApplication())) {
                    application.setAboutCurrentPet(pet.toString());
                }
            }

    }


    private void addNewPets(ArrayList<JSONObject> jsonPets) {
        try {
            pets.clear();
            for (int i = 0; i < jsonPets.size(); i++) {
                Pet pet = new Pet(
                        jsonPets.get(i).getString("idApplication"),
                        jsonPets.get(i).getString("idPet"),
                        jsonPets.get(i).getString("petName"),
                        jsonPets.get(i).getString("petType"),
                        jsonPets.get(i).getString("petAge")
                );
                pets.add(pet);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<JSONObject> separatePets(String result) throws JSONException {
        String[] separatedStringsPets = result.split("split");
        ArrayList<JSONObject> separatedJsonPets = new ArrayList<JSONObject>();

        for (String i : separatedStringsPets) {
            JSONObject jsonObject = new JSONObject(i);
            separatedJsonPets.add(jsonObject);
        }

        return separatedJsonPets;
    }


    private void addNewApps(ArrayList<JSONObject> jsonApps) {

        try {
            clientsApplications.clear();
            for (JSONObject jsonApp : jsonApps) {
                Application application = new Application(
                        jsonApp.getString("idApplication"),
                        jsonApp.getString("Date_of_receiving"),
                        jsonApp.getString("Date_of_perfoming")
                );
                clientsApplications.add(application);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<JSONObject> separateApps(String result) throws JSONException {
        String[] separatedStringsApps = result.split("split");
        ArrayList<JSONObject> separatedJsonApps = new ArrayList<JSONObject>();

        for (String i : separatedStringsApps) {
            JSONObject jsonObject = new JSONObject(i);
            separatedJsonApps.add(jsonObject);
        }

        return separatedJsonApps;
    }

    private void setCurrentUser(JSONObject clientsJSONData) {
        try {


            currentClient = new Client(
                    clientsJSONData.getString("idClient"),
                    clientsJSONData.getString("Name"),
                    clientsJSONData.getString("Phone_number"),
                    clientsJSONData.getString("Mail"),
                    clientsJSONData.getString("Password")
            );

            Log.d("currentClientTest", "New client was added:" + currentClient.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            currentClient = null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private String getResultWithoutCode(String resultWithCode) {
        return resultWithCode.substring(6);
    }


    public interface NotifyThatNewItemWasAdded {
        public void notifyThatItemAdded();
    }
}

