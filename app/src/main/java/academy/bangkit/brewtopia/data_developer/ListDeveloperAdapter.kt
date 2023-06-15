package academy.bangkit.brewtopia.data_developer

import academy.bangkit.brewtopia.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListDeveloperAdapter(private val listDeveloper: ArrayList<Developer>) :
    RecyclerView.Adapter<ListDeveloperAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_dev_name)
        val ivLinkedin: ImageView = itemView.findViewById(R.id.iv_linkedin)
        val ivProfileDev: ImageView = itemView.findViewById(R.id.iv_developer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dev, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDeveloper.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, profile) = listDeveloper[position]
        holder.tvName.text = name
        holder.ivLinkedin.setImageResource(R.drawable.logo_linkedin)
        holder.ivProfileDev.setImageResource(profile)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloper[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Developer)
    }
}