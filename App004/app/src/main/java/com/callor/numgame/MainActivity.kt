package com.callor.numgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.callor.numgame.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * private
     * MainActivity 에 포함된 함수, method 들에서만
     * 변수에 접근(읽거나 쓰기) 할수 있다
     * 접근제한자
     * 변수나, 함수 등을 은닉하여 다른 곳에서 보거나
     * 값을 변경하는 것을 허용, 제한 하는 키워드
     *
     * public : 허용
     * private : 제한
     * protected : 상황에 따라서
     *
     * lateinit var : 변수의 초기화(값 할당) 잠시 늦추겠다
     * Kotlin 은 변수를 선언함과 동시에 초기화(값 할당)을 하는 것이\
     * 원칙이다. 하지만 경우에 따라 선언부와 초기화 코드를 분리하고 할때
     * lateinit var 키워드로 시작한다
     *
     * 프로젝트 gradle 설정에 viewBinding 항목을 true 로 enable 해주면
     * layout / *.xml 파일들이 자동으로 ViewBinding Class 로 생성된다
     * ViewBinding class 는 xml 파일에 설정된 모든 view 를
     * 요소로 포함고 있다
     */
    private lateinit var binding: ActivityMainBinding
    private var rndNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // layout / activity_main.xml 과 연결하는 코드
        // binding 을 사용할수 있도록 만들어 주는 코드
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 1 부터 20까지 정수중에서 임의 숫자 한개를 추출하여
        // rndNumber 에 저장하라
        rndNumber = (1..20).random()

        // setContentView(R.layout.activity_main)
        // binding 으로 연결된 layout / *.xml 파일을 화면에 그려라
        setContentView(binding.root)

        binding.btnRdnNumber.setOnClickListener{
            rndNumber = (1..20).random()
            Snackbar
                .make(
                    it,
                    "게임을 다시시작합니다",
                    Snackbar.LENGTH_LONG)
                .show()
        }

        binding.txtNumber.setOnEditorActionListener { view, keyCode, event ->
            // input 입력된 문자열 추출
            val number = binding.txtNumber.text.toString()
            var message = "숫자를 입력해 주세요"
            var ret = true
            try {
                if (number.toInt() < 1 || number.toInt() > 20) {
                    message = "입력한 숫자 $number 는 1 부터 20 범위의 값이 아닙니다"
                    ret = false
                }
            } catch (e: NumberFormatException) {
                message = "입력한 값이 비어있거나, 숫자가 아닙니다"
                ret = false
            }
            if (!ret) {
                Snackbar
                    .make(
                        view, // 어디에 부착하여, TextInputEdit 에 부착
                        message, // 무엇을
                        Snackbar.LENGTH_LONG
                    ) // 얼마동안
                    .show() // Snack bar 보이기
            }

            // 유효성검사 완료
            if(ret) {
                val resultIntent = Intent(
                    this@MainActivity,
                    ResultActivity::class.java)
                    .apply {
                        putExtra("rndNumber",rndNumber)
                        putExtra("inputNumber",number.toInt())
                    }
                startActivity(resultIntent)
            }

            // input box 를 클릭하면 화면에 키보드가 보이는 상태가 된다
            // EditActionListener 이벤트를 설정하지 않으면
            // Enter 를 클릭했을때 자동으로 키보드가 감춰진다
            // EditActionListener 이벤트를 설정하고 제일 마지막에서
            // 일반적으로 true(return true) 를 사용하는데
            // true 를 return 하는 것은 키보드 상태를 그대로 유지하라 라는 표현
            // 키보드가 화면에 보이는 상태에서는 Snack bar를 표시할 수 없다
            // Snack bar 를 보이게 하기 위해서는 끝에서  false 를 사용한다
            ret
        }
    }
}