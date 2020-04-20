package com.example.word.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.word.R;
import com.example.word.entity.Word;
import com.example.word.viewModel.WordViewModel;

import org.apache.commons.lang3.StringUtils;

public class AddFragment extends Fragment {

    private WordViewModel mViewModel;

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this.getActivity()).get(WordViewModel.class);

        TextView enText = getActivity().findViewById(R.id.enText);
        TextView chText = getActivity().findViewById(R.id.chText);
        Button button = getActivity().findViewById(R.id.button);

        button.setEnabled(false);
        button.setTextColor(Color.GRAY);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String en = enText.getText().toString();
                final String ch = chText.getText().toString();

                if (StringUtils.isEmpty(en) || StringUtils.isEmpty(ch)) {
                    button.setEnabled(false);
                    button.setTextColor(Color.GRAY);
                } else {
                    button.setEnabled(true);
                    button.setTextColor(Color.BLACK);
                }
            }
        };

        enText.addTextChangedListener(textWatcher);
        chText.addTextChangedListener(textWatcher);

        chText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String en = enText.getText().toString();
                final String ch = chText.getText().toString();

                if (!StringUtils.isEmpty(en) && !StringUtils.isEmpty(ch)) {
                    button.callOnClick();
                }

                return false;
            }
        });

        button.setOnClickListener(v -> {
            TextView enText1 = getActivity().findViewById(R.id.enText);
            TextView chText1 = getActivity().findViewById(R.id.chText);

            String en = enText1.getText().toString();
            String ch = chText1.getText().toString();

            Word word = new Word();
            word.setChinese(ch);
            word.setEnglish(en);
            mViewModel.add(word);

            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }

            Navigation.findNavController(v).navigate(R.id.action_addFragment_to_wordFragment);
        });

    }

}
