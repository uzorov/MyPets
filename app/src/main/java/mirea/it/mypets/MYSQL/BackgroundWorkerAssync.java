package mirea.it.mypets.MYSQL;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

import mirea.it.mypets.MYSQL.MySQL_Login.LoginActivity;
import mirea.it.mypets.MYSQL.MySQL_Registration.RegistrationActivity;

public class BackgroundWorkerAssync extends AsyncTask<String, Void, String> {

    public static final String REGISTRATION_COMPLETE = "1";
    public static final String LOGIN_COMPLETE = "2";
    public static final String LOGIN_FAILED = "0";

    public static final String SIGN_UP_CODE = "Sign on";
    public static final String LOG_IN_CODE = "Log in";

    Context context;
    AlertDialog alertDialog;

    public BackgroundWorkerAssync(Context context) {
        this.context = context;

    }

    @Override
    protected String doInBackground(String... strings) {

        String typeOfReq = strings[0];

        if (typeOfReq.equals(SIGN_UP_CODE)) {
            //Регистрация нового пользователя
            String signup_url = "http://192.168.0.189/signUp.php";

            String clientsName = strings[1];
            String password = strings[2];
            String clientsPhoneNumber = strings[3];
            String clientsMail = strings[4];

            try {

                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

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
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (IOException e) {
                e.printStackTrace();

                Log.d("sign_out_debug", "Регистрация не удалась");

            }
        }
        else if (typeOfReq.equals(LOG_IN_CODE)) {
            String clientsName = strings[1];
            String password = strings[2];

            String signIn_url = "http://192.168.0.189/login.php";
            try {

                URL url = new URL(signIn_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

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
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {




        switch (result)
        {
            case REGISTRATION_COMPLETE:

            Toast.makeText(context, "Вы успешно зарегистрировались!", Toast.LENGTH_LONG).show();
            break;

            case LOGIN_COMPLETE:
                Toast.makeText(context, "Авторизация успешна!", Toast.LENGTH_LONG).show();
                break;

            case LOGIN_FAILED:
                Toast.makeText(context, "Аккаунт не найден! \n Проверьте введённые данные...", Toast.LENGTH_LONG).show();
                break;
            //Переход на страницу загрузки данных
        }

        //Если регистрация успешна вывести об этом сообщение и направить в аккаунт
        //Иначе вывести сообщение об ошибке
        // Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        // startActivity(intent);
        // В зависимости от кодового ответа сервера (создать константы)
        // Реализовать определённое поведение

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
