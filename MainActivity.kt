import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvInput: TextView
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }



    fun onDigit(view: android.view.View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: android.view.View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: android.view.View) {
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: android.view.View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: android.view.View) {
        if (lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                when {
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        val result = splitValue[0].toDouble() + splitValue[1].toDouble()
                        tvInput.text = removeTrailingZero(result.toString())
                    }
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        val result = splitValue[0].toDouble() - splitValue[1].toDouble()
                        tvInput.text = removeTrailingZero(result.toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        val result = splitValue[0].toDouble() * splitValue[1].toDouble()
                        tvInput.text = removeTrailingZero(result.toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        val result = splitValue[0].toDouble() / splitValue[1].toDouble()
                        tvInput.text = removeTrailingZero(result.toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    private fun removeTrailingZero(result: String): String {
        return if (result.endsWith(".0")) {
            result.substring(0, result.length - 2)
        } else {
            result
        }
    }
}
