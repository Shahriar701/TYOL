package com.example.dragonsage.tyol;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    Context context=this;
    private static final int REQUEST_CODE = 1234;
    static TextView tvTranslatedText;
    EditText etUserText;
    Button buTranslate;
    Button buSpeak;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_activity_main);
            tvTranslatedText = (TextView)findViewById(R.id.tvTranslatedText);
            etUserText = (EditText)findViewById(R.id.etUserText);

            buTranslate = (Button)findViewById(R.id.buTranslate);

            buSpeak = (Button)findViewById(R.id.buSpeak);
        }



    public void buTranslate(View view) throws Exception {
        //Default variables for translation
        String textToBeTranslated = "";
        textToBeTranslated= etUserText.getText().toString();
        String languagePair = "en-bn"; //English to bengali ("<source_language>-<target_language>")
        //Executing the translation function
        Translate(textToBeTranslated,languagePair);

    }


    //Function for calling executing the Translator Background Task
    public String Translate(String textToBeTranslated, String languagePair) throws Exception{
        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(context);

        String translationResult = "";

        translationResult = String.valueOf(translatorBackgroundTask.execute(textToBeTranslated,languagePair)); // Returns the translated text as a String
        Log.d("Translation Result",translationResult); // Logs the result in Android Monitor
        return translationResult;

    }

    //Speak button activities

    public void buSpeak(View view) {
        startVoiceRecognitionActivity();
    }

    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to translate");
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {

                //pull all of the matches
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String topResult = matches.get(0);

                EditText AutoText = (EditText) findViewById(R.id.etUserText);
                AutoText.setText(topResult);

            }

        }
    }

}

