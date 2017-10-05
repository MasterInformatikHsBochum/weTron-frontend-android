package de.hs_bochum.wetroncontroller.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import android.widget.EditText
import de.hs_bochum.wetron.wetroncontroller.MainActivity

import de.hs_bochum.wetroncontroller.R
import de.hs_bochum.wetroncontroller.ui.custom.WeTronButton


class GameinfoDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val dialogView:View = inflater.inflate(R.layout.dialog_gameinfo, null)
        val connectButton:WeTronButton = dialogView.findViewById(R.id.gameinfo_connect)
        val gameIdText:EditText = dialogView.findViewById(R.id.et_gameId)
        val playerIdText:EditText = dialogView.findViewById(R.id.et_playerid)
        connectButton.setOnClickListener({ (activity as MainActivity).connect(gameIdText.text.toString(), playerIdText.text.toString()) })

        builder.setView(dialogView)
        return builder.create()
    }
}
