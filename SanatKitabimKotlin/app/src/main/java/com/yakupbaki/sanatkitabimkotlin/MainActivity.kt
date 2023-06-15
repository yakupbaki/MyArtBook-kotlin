package com.yakupbaki.sanatkitabimkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.yakupbaki.sanatkitabimkotlin.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var artList : ArrayList<Art>
    private lateinit var binding : ActivityMainBinding
    private lateinit var artAdapter: ArtAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        artList = ArrayList<Art>()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        artAdapter = ArtAdaptor(artList)

        binding.recyclerView.adapter = artAdapter

        try {
            val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM arts", null)
            val artNameIx = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val name = cursor.getString(artNameIx)
                val id = cursor.getInt(idIx)
                val art = Art(name,id)
                artList.add(art)
            }
            artAdapter.notifyDataSetChanged()
            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuinflater = menuInflater
        menuinflater.inflate(R.menu.art_menu, menu )

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.add_art_item)
        {
            val intent = Intent(this@MainActivity, ArtActivity::class.java)
            intent.putExtra("info","new")

            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}