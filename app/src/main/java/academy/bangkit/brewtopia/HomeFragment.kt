package academy.bangkit.brewtopia

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DummyAdapter
    private lateinit var menuButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        menuButton = view.findViewById(R.id.menu)
        menuButton.setOnClickListener(this)

        adapter = DummyAdapter(getDummyData())
        recyclerView.adapter = adapter
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.menu -> showMenuDialog()
        }
    }

    private fun showMenuDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_menu, null)

        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.TransparentDialog)
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.window?.setGravity(Gravity.TOP or Gravity.START);

        val backIcon = dialogView.findViewById<ImageView>(R.id.menu)
        backIcon.setOnClickListener {
            dialog.dismiss()
        }

        val menuAccount = dialogView.findViewById<View>(R.id.view_account)
        val menuAbout = dialogView.findViewById<View>(R.id.view_about)
        val menuSettings = dialogView.findViewById<View>(R.id.view_settings)

        menuAbout.setOnClickListener {
            // Handle About menu item click
            dialog.dismiss()
        }

        menuAccount.setOnClickListener {
            // Handle Account menu item click
            dialog.dismiss()
        }

        menuSettings.setOnClickListener {
            // Handle Settings menu item click
            dialog.dismiss()
        }
    }

    private fun getDummyData(): List<String> {
        val data = ArrayList<String>()
        for (i in 1..20) {
            data.add("Data Dummy Article $i")
        }
        return data
    }
}