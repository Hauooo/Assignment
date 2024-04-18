package my.edu.utar.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoler> {
    private Context context;
    private ArrayList history_id, inputText_list,translatedText_list;

    public MyAdapter(Context context, ArrayList history_id, ArrayList inputText_list, ArrayList translatedText_list) {
        this.context = context;
        this.history_id = history_id;
        this.inputText_list = inputText_list;
        this.translatedText_list = translatedText_list;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        holder.history_id_txt.setText(String.valueOf(history_id.get(position)));
        holder.original_txt.setText(String.valueOf(inputText_list.get(position)));
        holder.translated_text.setText(String.valueOf(translatedText_list.get(position)));
    }

    @Override
    public int getItemCount() {
        return history_id.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {
        TextView history_id_txt,original_txt,translated_text;
        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            history_id_txt = itemView.findViewById(R.id.history_id_txt);
            original_txt = itemView.findViewById(R.id.original_txt);
            translated_text = itemView.findViewById(R.id.translated_text);

        }
    }
}
