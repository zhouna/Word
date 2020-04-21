package com.example.word.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.word.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class WordViewModel extends ViewModel {
    private MutableLiveData<List<Word>> list;

    public LiveData<List<Word>> getList() {
        if (list == null) {
            list = new MutableLiveData<>();

            List<Word> list1 = new ArrayList<>();
            Word word = new Word();
            word.setEnglish("hello");
            word.setChinese("你好");
            list1.add(word);

            list.setValue(list1);
        }
        return list;
    }

    public void add(Word word) {
        getList().getValue().add(0, word);
    }

    public void delete(Word word) {
        getList().getValue().remove(word);
    }

    public void add(int index, Word word) {
        getList().getValue().add(index, word);
    }

    public void clearAll() {
        getList().getValue().clear();
    }

    public List<Word> query(String key) {
        List<Word> list = getList().getValue();
        List<Word> filtered = new ArrayList<>();
        for (Word word: list) {
            if (word.getEnglish().contains(key)) {
                filtered.add(word);
            }
        }
        return filtered;
    }
}
