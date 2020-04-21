package com.example.word.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.word.R;
import com.example.word.entity.Word;
import com.example.word.adapter.WordListAdapter;
import com.example.word.viewModel.WordViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class WordFragment extends Fragment {

    private WordViewModel mViewModel;
    private WordListAdapter wordListAdapter;

    public static WordFragment newInstance() {
        return new WordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.word_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this.getActivity()).get(WordViewModel.class);

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        wordListAdapter = new WordListAdapter();
        recyclerView.setAdapter(wordListAdapter);

        ItemTouchHelper.SimpleCallback  simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                List<Word> wordList = mViewModel.getList().getValue();

                int position = viewHolder.getAdapterPosition();
                Word word = wordList.get(position);
                mViewModel.delete(word);
                wordListAdapter.setList(mViewModel.getList().getValue()); // TODO
                wordListAdapter.notifyDataSetChanged();

                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar
                        .make(getActivity().findViewById(R.id.mainLayout), "删除一条记录", Snackbar.LENGTH_LONG);
                snackbar.setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       mViewModel.add(position, word);
                        wordListAdapter.setList(mViewModel.getList().getValue()); // TODO
                        wordListAdapter.notifyDataSetChanged();
                    }
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        mViewModel.getList().observe(getActivity(), words -> {
            wordListAdapter.setList(mViewModel.getList().getValue());
            wordListAdapter.notifyDataSetChanged();
        });

        View floatView = getView().findViewById(R.id.floatingActionButton2);
        floatView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_wordFragment_to_addFragment));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                wordListAdapter.setList(mViewModel.query(newText));
                wordListAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearData:
                Toast.makeText(getContext(), "清空数据", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.changeView:
                Toast.makeText(getContext(), "切换视图", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
