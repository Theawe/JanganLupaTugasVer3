package id.java.janganlupatugas.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import id.java.janganlupatugas.R;


public class HistoryAdapter extends ListAdapter<History, HistoryAdapter.HistoryHolder> {

    private OnItemClickListener listener;
    public HistoryAdapter(){super(DIFF_CALLBACK);}

    private static final DiffUtil.ItemCallback<History> DIFF_CALLBACK = new DiffUtil.ItemCallback<History>() {
        @Override
        public boolean areItemsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem.getJudul().equals(newItem.getJudul()) &&
                    oldItem.getDeskripsi().equals(newItem.getDeskripsi()) &&
                    oldItem.getTanggal().equals(newItem.getTanggal()) && oldItem.getWaktu().equals((newItem.getWaktu()));
        }
    };

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_row,parent,false);
        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        History currentHistory =getItem(position);
        holder.textViewTitle.setText(currentHistory.getJudul());
        holder.textViewDescription.setText(currentHistory.getDeskripsi());
        holder.textViewDeadline.setText(currentHistory.getTanggal());
        holder.textViewDate.setText(currentHistory.getWaktu());
    }


    public History getHistoryAt(int position){
        return getItem(position);
    }


    class HistoryHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDeadline;
        private TextView textViewDate;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_judul);
            textViewDescription = itemView.findViewById(R.id.text_view_deskripsi);
            textViewDeadline= itemView.findViewById(R.id.text_view_tanggal);
            textViewDate = itemView.findViewById(R.id.text_view_waktu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positon = getAdapterPosition();
                    if(listener != null && positon != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(positon),positon);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(History history,int position);
    }

    public void setOnItemClickListener(HistoryAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
