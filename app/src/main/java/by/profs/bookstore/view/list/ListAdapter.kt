package by.profs.bookstore.view.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.profs.bookstore.R
import by.profs.bookstore.app.ServiceLocator.locateLazy
import by.profs.bookstore.data.ParseObject
import by.profs.bookstore.databinding.ItemBookBinding
import com.bumptech.glide.Glide
import coil.load

class ListAdapter(private val items: List<ParseObject>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private val context: Context by locateLazy()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBookBinding.bind(view)
        val author = binding.authorBook
        val image = binding.image
        val price = binding.price
        val title = binding.titleBook
        val goTo = binding.link
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = items[position]
        goTo.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
//        Glide.with(image)
//            .load(item.picUrl)
//            .error(R.drawable.animation_load)
//            .placeholder(R.drawable.animation_load)
//            .into(image)
        image.load(item.picUrl) {
            crossfade(true)
            placeholder(R.drawable.animation_load)
        }
        author.text = item.author
        price.text = context.getString(R.string.price, item.price)
        title.text = item.title
    }

    override fun getItemCount(): Int = items.size
}