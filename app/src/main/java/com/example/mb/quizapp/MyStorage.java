package com.example.mb.quizapp;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyStorage {
    static String filename = "scores.txt";
    int count = 0;
    int averageScore = 0;
    int total = 0;
    String data="";

    public void saveScoreToFile(String score, Context context) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_APPEND);
            fos.write((score + "+").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getScoreFromFile(Context context) {

        FileInputStream fis;
        StringBuilder returnedString = new StringBuilder();
        int i;
        try {
            fis = context.openFileInput(filename);
            while ((i = fis.read()) != -1) {
                returnedString.append((char) i);
                data = returnedString.toString();

            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return context.getString(R.string.nothing);
    }

    public int checkAverage() {
        checkTotalCount();
        total = 0;
        averageScore = 0;
        if(count>0){
            for (int i = 0; i < data.toCharArray().length; i++) {
                if (data.toCharArray()[i] == '+') {
                    total = total + Character.getNumericValue(data.toCharArray()[i - 1]);
                }
            }
            averageScore = total / count;
        }

        return averageScore;
    }

    public int checkTotalCount() {
        count = 0;
        for (int i = 0; i < data.toCharArray().length; i++) {
            if (data.toCharArray()[i] == '+') {
                count++;
            }
        }
        return count;
    }

    public void resetSavedResult(Context context) {
    /*   FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_APPEND);
         //   fos.flush();
            fos.write("".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
        context.deleteFile(filename);

    }
}
