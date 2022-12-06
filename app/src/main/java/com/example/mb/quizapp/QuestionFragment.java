package com.example.mb.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class QuestionFragment extends Fragment {



TextView textView;
    private int question;
    private int color;

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance(int questionId, int colorId) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("QueId", questionId);
        args.putInt("ColorId", colorId);
        fragment.setArguments(args);
        return fragment;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_question, container, false);
        textView =  view.findViewById(R.id.textViewQuestion);
        question = getArguments().getInt("QueId");
        color = getArguments().getInt("ColorId");
        textView.setText(question);
        textView.setBackgroundResource(color);
        return view;
    }
}