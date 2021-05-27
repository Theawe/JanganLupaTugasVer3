package id.java.janganlupatugas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class TugasAdapter extends ListAdapter<Tugas,TugasAdapter.TugasHolder> {

    private OnItemClickListener listener;
    public TugasAdapter(){super(DIFF_CALLBACK);}

    private static final DiffUtil.ItemCallback<Tugas> DIFF_CALLBACK = new DiffUtil.ItemCallback<Tugas>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tugas oldItem, @NonNull Tugas newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tugas oldItem, @NonNull Tugas newItem) {
            return oldItem.getJudul().equals(newItem.getJudul()) &&
                    oldItem.getDeskripsi().equals(newItem.getDeskripsi()) &&
                    oldItem.getTanggal().equals(newItem.getTanggal()) && oldItem.getWaktu().equals((newItem.getWaktu()));
        }
    };

    @NonNull
    @Override
    public TugasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_row,parent,false);
        return new TugasHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TugasHolder holder, int position) {
        Tugas currentTugas =getItem(position);
        holder.textViewTitle.setText(currentTugas.getJudul());
        holder.textViewDescription.setText(currentTugas.getDeskripsi());
        holder.textViewDeadline.setText(currentTugas.getTanggal());
        holder.textViewDate.setText(currentTugas.getWaktu());
    }


    public Tugas getTugasAt(int position){
        return getItem(position);
    }


    class TugasHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDeadline;
        private TextView textViewDate;

        public TugasHolder(@NonNull View itemView) {
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
        void onItemClick(Tugas tugas,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
