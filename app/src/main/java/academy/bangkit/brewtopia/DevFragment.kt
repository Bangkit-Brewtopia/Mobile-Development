package academy.bangkit.brewtopia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

class DevFragment(s: String) : Fragment() {
    private val page = s;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dev, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myTextView = view.findViewById<TextView>(R.id.page_name)
        when (page) {
            "fav" -> myTextView.text = "Favorite Page"
            "chat" -> myTextView.text = "Chat Page"
            "scan" -> myTextView.text = "Scan Page"
        }
    }
}