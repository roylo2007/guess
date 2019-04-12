package com.roy.guess

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

    val secretNumber = SecretNumber()
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Log.d(TAG, "secret number is ${secretNumber.number}")

        fab.setOnClickListener { view ->
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

        counter.text = secretNumber.count.toString()
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
        val n = number.text.toString().toInt()
        Log.d(TAG, "number is $n")
        val diff = secretNumber.validate(n)
        var message = when(true){
            diff > 0 && secretNumber.count < 2 -> getString(R.string.smaller)
            diff < 0 && secretNumber.count < 2 -> getString(R.string.bigger)
            diff ==0 && secretNumber.count < 3 -> getString(R.string.yes_you_got_it) + n
            else -> getString(R.string.game_over ) + "\n" + getString(R.string.secret_number_is) + "${secretNumber.number}"
        }
        counter.text = secretNumber.count.toString()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }
}