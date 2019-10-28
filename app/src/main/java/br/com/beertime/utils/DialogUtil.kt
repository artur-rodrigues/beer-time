package br.com.beertime.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.beertime.R
import br.com.beertime.databinding.DialogBind


/**
 * Created by Artur on 27/10/2019.
 */
fun createPositiveDialog(context: Context, msg: String, clickPositive: (dialog: AlertDialog) -> Unit) {
    createCompleteDialog(context, msg,null, null, clickPositive, null)
}

fun createNegativeDialog(context: Context, msg: String, clickNegative: (dialog: AlertDialog) -> Unit) {
    createCompleteDialog(context, msg, null, null, null, clickNegative)
}

fun createCompleteDialog(context: Context, msg: String, btnPositiveText: String?, btnNegativeText: String?, clickPositive: ((dialog: AlertDialog) -> Unit)?, clickNegative: ((dialog: AlertDialog) -> Unit)?) {
    val inflater = LayoutInflater.from(context)
    val bind = DialogBind.inflate(inflater)

    val alertDialog = AlertDialog.Builder(context, R.style.CustomDialog)
        .setView(bind.root)
        .setCancelable(false)
        .create()

    bind.labelMessage.text = msg

    btnPositiveText?.let {
        bind.btnPositive.text = it
    }

    bind.btnPositive.setOnClickListener {
        clickPositive?.let { it1 -> it1(alertDialog) }
        alertDialog.dismiss()
    }

    btnNegativeText?.let {
        bind.btnNegative.text = it
    }

    bind.btnNegative.setOnClickListener {
        clickNegative?.let { it1 -> it1(alertDialog) }
        alertDialog.dismiss()
    }

    alertDialog.show()
    alertDialog.window?.setLayout(800, 800)
}