package com.roy.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_RECORD = 100
    val secretNumber = SecretNumber()
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Log.d(TAG, "secret number is ${secretNumber.number}")

        fab.setOnClickListener { view ->
            replay()
        }

        counter.text = secretNumber.count.toString()
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: $count/$nick")

    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.replay_game))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                secretNumber.reset()
                Log.d(TAG, "secret number is ${secretNumber.number}")
                counter.text = secretNumber.count.toString()
                number.setText("")
            }
            .setNeutralButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun check(view:View){
        val times = 11  //2
        val n = number.text.toString().toInt()
        Log.d(TAG, "number is $n")
        val diff = secretNumber.validate(n)
        var message = when(true){
            diff > 0 && secretNumber.count < times -> getString(R.string.smaller)
            diff < 0 && secretNumber.count < times -> getString(R.string.bigger)
            diff ==0 && secretNumber.count < times + 1 -> getString(R.string.yes_you_got_it) + n
            else -> getString(R.string.game_over ) + "\n" + getString(R.string.secret_number_is) + "${secretNumber.number}"
        }
        counter.text = secretNumber.count.toString()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)){ dialog, which ->
                if (diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
//                    startActivity(intent)
                    startActivityForResult(intent, REQUEST_RECORD)
                }
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD){
            when(resultCode){
                Activity.RESULT_OK -> {
                    val nick = data?.getStringExtra("NICK")
                    Log.d(TAG, "onActivityResult: $nick");
                    replay()
                }
            }
        }
    }
}