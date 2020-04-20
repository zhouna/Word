package com.example.word.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.word.R;
import com.example.word.entity.Word;
import com.example.word.adapter.WordListAdapter;
import com.example.word.viewModel.WordViewModel;

import java.lang.reflect.Array;
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
        mViewModel = new ViewModelProvider(this.getActivity()).get(WordViewModel.class);

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        WordListAdapter wordListAdapter = new WordListAdapter();

        recyclerView.setAdapter(wordListAdapter);



        mViewModel.getList().observe(getActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordListAdapter.setList(mViewModel.getList().getValue());
                wordListAdapter.notifyDataSetChanged();
            }
        });

        View floatView = getView().findViewById(R.id.floatingActionButton2);
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_wordFragment_to_addFragment);
            }
        });
    }

}
