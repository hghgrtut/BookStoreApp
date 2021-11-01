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
import by.profs.bookstore.data.Shops
import by.profs.bookstore.data.ParseObject
import by.profs.bookstore.databinding.ItemBookBinding

class ListAdapter(private val items: List<ParseObject>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val context: Context by locateLazy()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBookBinding.bind(view)
        val logo = binding.logo
        val price = binding.price
        val shop = binding.shop
        val goTo = binding.link
        val copyLink = binding.copyLink
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.price.text = context.getString(R.string.price, item.price)
        holder.shop.text = item.shop
        holder.goTo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
        holder.logo.setImageResource(Shops.valueOf(item.shop.uppercase()).logo)
    }

    override fun getItemCount(): Int = items.size
}