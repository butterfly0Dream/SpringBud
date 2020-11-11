package com.fallgod.springbud.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.fallgod.springbud.BR
import com.fallgod.springbud.R
import com.fallgod.springbud.databinding.ActivitySettingBinding
import com.fallgod.springbud.ui.base.BaseActivity
import com.fallgod.springbud.ui.base.DataBindingConfig
import com.fallgod.springbud.ui.viewmodel.SettingViewModel
import com.fallgod.springbud.util.LogUtil
import kotlinx.coroutines.*

class SettingActivity : BaseActivity() {
    private val TAG = "SettingActivity"

    private lateinit var mSettingViewModel: SettingViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setStatusBarColor() {
        statusBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(R.color.colorPrimary)
        } else {
            resources.getColor(R.color.colorPrimary)
        }
    }

    override fun initViewModel() {
        mSettingViewModel = getViewModel(SettingViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_setting, mSettingViewModel)
                .addBindingParams(BR.event, ClickProxy())
    }

    fun binding(): ActivitySettingBinding {
        return getBinding() as ActivitySettingBinding
    }

    inner class ClickProxy {

        fun back() = finish()

        fun test1() = runBlocking {// 开始执行主协程
            GlobalScope.launch { // 在后台启动一个新的协程
                Log.d(TAG, "world")
                delay(1000L)
            }
            Log.d(TAG, "hello,") // 主协程在这里会立即执行
            delay(2000L) // 延迟2s来保证JVM存活
        }

        fun test2() = runBlocking {
            val job = GlobalScope.launch {  // 启动一个新的协程，并保持对它的引用
                delay(1000L)
                Log.d(TAG, "test2: world")
            }
            Log.d(TAG, "test2: hello,")
            job.join() // 等待子协程执行结束
        }

        // 我们可以在这个作用域中启动协程而无需显式 join 之，因为外部协程（示例中的 runBlocking）直到在其作用域中启动的所有协程都执行完毕后才会结束。
        fun test3() = runBlocking { 
            launch { // 在runBlocking作用域中启动新协程
                delay(1000L)
                Log.d(TAG, "test3: world!")
            }
            Log.d(TAG, "test3: hello,")
        }
        
        fun test4() = runBlocking { 
            launch { 
                delay(200L)
                Log.d(TAG, "test4: task from runBlocking")
            }
            
            coroutineScope { 
                launch { 
                    delay(500L)
                    Log.d(TAG, "test4: task from nested launch")
                }

                delay(100L)
                Log.d(TAG, "test4: task from coroutine scope") // 这一行会在内嵌launch前输出
            }
            Log.d(TAG, "test4: coroutine scope is over") // 这一行会在内嵌launch执行完毕后才输出
        }

    }

}