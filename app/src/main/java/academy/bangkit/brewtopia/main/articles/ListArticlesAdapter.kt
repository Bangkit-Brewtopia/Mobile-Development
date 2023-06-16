package academy.bangkit.brewtopia.main.articles

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.data.local.Articles
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListArticlesAdapter(private val listArticles: ArrayList<Articles>):
RecyclerView.Adapter<ListArticlesAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, author, love, content, cover) = listArticles[position]
        holder.tvTitle.text = title
        holder.tvAuthor.text = author
        holder.tvLove.text = love
        holder.tvContent.text = content
        holder.ivCover.setImageResource(cover)
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listArticles[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_cover)
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_author_cover)
        val tvLove: TextView = itemView.findViewById(R.id.tv_fav_count)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content_cover)
        val ivCover: ImageView = itemView.findViewById(R.id.iv_cover_article)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Articles)
    }
}