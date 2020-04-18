package com.example.word;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private List<Word> list;

    public List<Word> getList() {
        return list;
    }

    public void setList(List<Word> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_normal, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WordViewHolder holder, int position) {
        final Word word = list.get(position);
        holder.ch.setText(word.getChinese());
        holder.en.setText(word.getEnglish());
        holder.num.setText(String.valueOf(position+1));
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.ch.setText("");
                } else {
                    holder.ch.setText(word.getChinese());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        TextView en, ch, num;
        Switch aSwitch;
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            en = itemView.findViewById(R.id.en);
            ch = itemView.findViewById(R.id.ch);
            num = itemView.findViewById(R.id.num);
            aSwitch = itemView.findViewById(R.id.switchView);
        }
    }
}
