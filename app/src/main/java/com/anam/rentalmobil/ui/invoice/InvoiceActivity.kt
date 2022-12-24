package com.anam.rentalmobil.ui.invoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.transaksi.DataTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksi
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.ui.a_FRAGMENT.FragmenttActivity
import com.anam.rentalmobil.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_invoice.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class InvoiceActivity : AppCompatActivity(), InvoiceContract.View {

    lateinit var presenter: InvoicePresenter
    lateinit var transaksi: DataTransaksi

    private var webView: WebView? = null
    lateinit var btnprint : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)
        presenter = InvoicePresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getDetail(Constant.TRANSAKSI_ID)
    }

    override fun initActivity() {
//        tv_nama.text ="Invoice"

        btnprint = findViewById(R.id.btnCetakprint)
        webView = findViewById(R.id.webView)

    }

    override fun initListener() {

//        ivKembali.setOnClickListener {
//            onBackPressed()
//        }

        btnprint.setOnClickListener(View.OnClickListener { // Code Here ==========
            presenter.getDetail(Constant.TRANSAKSI_ID)
            createWebPrintJob(webView!!)
        })
        webView!!.webChromeClient = WebChromeClient()
        webView!!.settings.javaScriptEnabled = true
        // WebView loading handling
        // WebView loading handling
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                //if WebView load successfully then VISIBLE fab Button
                btnprint.setVisibility(View.VISIBLE)
            }
        }
        webView!!.loadUrl("https://travel.ufomediategal.com/api/transaksi-invoice/" + Constant.TRANSAKSI_ID)

    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progresinvoice.visibility = View.VISIBLE
            }
            false -> {
                progresinvoice.visibility = View.GONE
            }
        }
    }

    override fun onResult(responseTransaksi: ResponseTransaksi) {
        transaksi = responseTransaksi.dataTransaksi!!

    }


    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun createWebPrintJob(webView: WebView) {
        val printManager = this.getSystemService(PRINT_SERVICE) as PrintManager
        val printAdapter = webView.createPrintDocumentAdapter()
        val jobName = getString(R.string.app_name) + " Print Test"
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
    }

}