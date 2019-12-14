package tsisyk.app.diary.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.vansuita.materialabout.builder.AboutBuilder
import tsisyk.app.diary.R


@Suppress("DEPRECATION")
class AboutMeActivity : AppCompatActivity() {

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        loadAbout()
    }


    private fun loadAbout() {
        val view = AboutBuilder.with(this)
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setName("Roman Tsisyk")
                .addFeedbackAction("androiddeveloper@engineer.com", "tsisyk.app.diary")
                .setSubTitle("Mobile Developer")
                .setBrief("You can find me here")
                .addLinkedInLink("roman-tsisyk")
                .addEmailLink("androiddeveloper@engineer.com")
                .addWebsiteLink("https://romantsisyk.github.io/")
                .addMoreFromMeAction("Android Developer Engineering")
                .addFiveStarsAction("tsisyk.app.diary")
                .setLinksColumnsCount(3)
                .addPrivacyPolicyAction {
                    startActivity(Intent(this, PrivacyActivity::class.java))
                }
                .addLicenseAction {
                    startActivity(Intent(this, LicenseActivity::class.java))
                }
                .setAppIcon(R.mipmap.ic_launcher_round)
                .setAppName(R.string.app_name)
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build()
        addContentView(view, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))

    }


    fun onClickBack(view: Unit) {
        val intent = Intent(this, DiaryActivity::class.java)
        startActivity(intent)
    }
}
