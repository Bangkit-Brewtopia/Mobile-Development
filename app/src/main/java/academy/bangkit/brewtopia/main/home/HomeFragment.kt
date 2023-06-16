package academy.bangkit.brewtopia.main.home

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.about.AboutActivity
import academy.bangkit.brewtopia.account.ProfileActivity
import academy.bangkit.brewtopia.main.MainActivity
import academy.bangkit.brewtopia.data.local.Articles
import academy.bangkit.brewtopia.main.articles.DetailActivity
import academy.bangkit.brewtopia.main.articles.ListArticlesAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var menuButton: ImageView
    private lateinit var SearchButton: ImageView
    private lateinit var cvChat: CardView
    private lateinit var cvScan: CardView
    private lateinit var viewPager: ViewPager2
    private val list = ArrayList<Articles>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuButton = view.findViewById(R.id.menu)
        menuButton.setOnClickListener(this)

        SearchButton = view.findViewById(R.id.search)
        SearchButton.setOnClickListener {
            showSearchDialog()
        }

        cvChat = view.findViewById(R.id.cardView_chat)
        cvScan = view.findViewById(R.id.cardView_scan)
        viewPager = requireActivity().findViewById(R.id.ViewPager2)
        cvChat.setOnClickListener {
            (requireActivity() as MainActivity).navigateToChatFragment()
        }
        cvScan.setOnClickListener {
            (requireActivity() as MainActivity).navigateToScanFragment()
        }

        val getItNowButton = view.findViewById<Button>(R.id.get_it_now)
        getItNowButton.setOnClickListener {
            val url = "https://www.tokopedia.com/search?st=product&q=biji%20kopi"
            openUrlInBrowser(url)
        }

        list.addAll(getListArticles())
        showRecyclerArticles()
    }

    @SuppressLint("Recycle")
    private fun getListArticles(): ArrayList<Articles> {
        val dataTitle = resources.getStringArray(R.array.article_title)
        val dataAuthor = resources.getStringArray(R.array.authors)
        val dataLove = resources.getStringArray(R.array.love)
        val dataContent = resources.getStringArray(R.array.article_content)
        val dataCover = resources.obtainTypedArray(R.array.article_cover)
        val dataBanner = resources.obtainTypedArray(R.array.article_banner)
        val listArticles = ArrayList<Articles>()
        for (i in dataTitle.indices) {
            val articles = Articles(
                dataTitle[i],
                dataAuthor[i],
                dataLove[i],
                dataContent[i],
                dataCover.getResourceId(i, -1),
                dataBanner.getResourceId(i, -1))
            listArticles.add(articles)
        }
        return listArticles
    }

    private fun showRecyclerArticles(){
        val rvArticles = view?.findViewById<RecyclerView>(R.id.rv_articles)
        rvArticles?.layoutManager = LinearLayoutManager(requireContext())
        val listArticlesAdapter = ListArticlesAdapter(list)
        rvArticles?.adapter = listArticlesAdapter

        listArticlesAdapter.setOnItemClickCallback(object :
            ListArticlesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Articles) {
                val moveIntent = Intent(requireContext(), DetailActivity::class.java)
                moveIntent.putExtra("article", data)
                startActivity(moveIntent)
            }
        }
        )
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.menu -> showMenuDialog()
        }
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun showMenuDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_menu, null)

        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.TransparentDialog)
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.window?.setGravity(Gravity.TOP or Gravity.START)

        val backIcon = dialogView.findViewById<ImageView>(R.id.menu)
        backIcon.setOnClickListener {
            dialog.dismiss()
        }

        val menuAccount = dialogView.findViewById<View>(R.id.view_account)
        val menuAbout = dialogView.findViewById<View>(R.id.view_about)
        val menuSettings = dialogView.findViewById<View>(R.id.view_settings)

        menuAbout.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        menuAccount.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        menuSettings.setOnClickListener {
            // Handle Settings menu item click
            dialog.dismiss()
        }
    }

    private fun showSearchDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_search, null)

        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.TransparentDialog)
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.window?.setGravity(Gravity.TOP or Gravity.CENTER)

        val searchIcon = dialogView.findViewById<ImageView>(R.id.ic_search)
        searchIcon.setOnClickListener {
            val message = "This feature will be developed in the future"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}