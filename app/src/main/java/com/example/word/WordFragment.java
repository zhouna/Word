package com.example.word;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class WordFragment extends Fragment {

    private WordViewModel mViewModel;

    public static WordFragment newInstance() {
        return new WordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.word_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        // TODO: Use the ViewModel
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Word> list = new ArrayList<>();
        Word word = new Word();
        word.setChinese("（使）不安");
        word.setEnglish("disquiet");
        list.add(word);
        word = new Word();
        word.setChinese("顺利地");
        word.setEnglish("smoothly");
        list.add(word);
        word = new Word();
        word.setChinese("上瘾的");
        word.setEnglish("addictive");
        list.add(word);
        word = new Word();
        word.setChinese("反叛的");
        word.setEnglish("defiant");
        list.add(word);


        WordListAdapter wordListAdapter = new WordListAdapter();
        wordListAdapter.setList(list);
        recyclerView.setAdapter(wordListAdapter);

        View floatView = getView().findViewById(R.id.floatingActionButton2);
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_wordFragment_to_addFragment);
            }
        });
    }

}
