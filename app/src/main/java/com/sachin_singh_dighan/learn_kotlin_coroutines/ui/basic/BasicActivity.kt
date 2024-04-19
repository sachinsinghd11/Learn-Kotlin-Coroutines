package com.sachin_singh_dighan.learn_kotlin_coroutines.ui.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.sachin_singh_dighan.learn_kotlin_coroutines.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.withTestContext
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class BasicActivity : AppCompatActivity() {

    private val myActivityScope = CoroutineScope(Dispatchers.Main.immediate)

    companion object {
        private const val TAG = "BasicActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
    }

    fun testCoroutine(view: View) {
        testCoroutine()
    }

    fun testCoroutineWithMain(view: View) {
        testCoroutineWithMain()
    }

    fun testCoroutineWithMainImmediate(view: View) {
        testCoroutineWithMainImmediate()
    }

    fun testCoroutineEverything(view: View) {
        testCoroutineEverything()
    }

    fun usingGlobalScope(view: View) {
        usingGlobalScope()
    }

    fun usingMyActivityScope(view: View) {
        usingMyActivityScope()
    }

    fun globalScopeInsideLifecycleScope(view: View) {
        globalScopeInsideLifecycleScope()
    }

    fun launchInsideLifecycleScope(view: View) {
        launchInsideLifecycleScope()
    }

    fun twoLaunches(view: View) {
        twoLaunches()
    }

    fun twoWithContextInsideLifecycleScope(view: View) {
        twoWithContextInsideLifecycleScope()
    }


    fun twoAsyncInsideLifecycleScope(view: View) {
        twoAsyncInsideLifecycleScope()
    }

    fun twoTasks(view: View) {
        twoTasks()
    }


    fun parentAndChildTaskCancel(view: View){
        parentAndChildTaskCancel()
    }

    fun parentAndChildTaskCancelIsActive(view: View){
        parentAndChildTaskCancelIsActive()
    }

    fun lifecycleScopeWithHandlerException(view: View){
        lifecycleScopeWithHandlerException()
    }

    fun lifecycleScopeWithHandler(view: View){
        lifecycleScopeWithHandler()
    }

    fun myActivityScopeWithHandlerException(view: View){
        myActivityScopeWithHandlerException()
    }

    fun myActivityScopeWithHandler(view: View){
        myActivityScopeWithHandler()
    }

    fun exceptionInLaunchBlock(view: View){
        exceptionInLaunchBlock()
    }

    fun exceptionInAsyncBlockWithAwait(view: View){
        exceptionInAsyncBlockWithAwait()
    }

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    fun testSuspending(view: View){
        lifecycleScope.launch(dispatcher){
            Log.d(TAG, "testSuspending Before Task 1")
            timeTakingTask()
            Log.d(TAG, "testSuspending After Task 1")
        }
        lifecycleScope.launch(dispatcher) {
            Log.d(TAG, "testSuspending Before Task 2")
            timeTakingTask()
            Log.d(TAG, "testSuspending After Task 2")
        }
    }

    fun testBlocking(view: View) {
        lifecycleScope.launch(dispatcher) {
            runBlocking {
                Log.d(TAG, "testBlocking Before Task 1")
                timeTakingTask()
                Log.d(TAG, "testBlocking After Task 1")
            }
        }
        lifecycleScope.launch(dispatcher) {
            runBlocking {
                Log.d(TAG, "testBlocking Before Task 2")
                timeTakingTask()
                Log.d(TAG, "testBlocking After Task 2")
            }
        }
    }


    private suspend fun timeTakingTask() {
        withContext(Dispatchers.IO) {
            Thread.sleep(5000)
        }
    }

    private fun exceptionInAsyncBlockWithAwait() {
        lifecycleScope.launch {
            val deferred = lifecycleScope.async(Dispatchers.Default) {
                doSomethingAndThrowException()
                return@async 10
            }
            try {
                val result = deferred.await()
            } catch (e: Exception) {
                Log.d(TAG, "Exception Handler : $e")
            }
        }
    }

    fun exceptionInAsyncBlock(view: View){
        lifecycleScope.async {
            doSomethingAndThrowException()
        }
    }

    private fun exceptionInLaunchBlock() {
        lifecycleScope.launch {
            doSomethingAndThrowException()
        }
    }

    private fun doSomethingAndThrowException(){
        throw Exception("Some Exception")
    }


    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d(TAG, "Exception Handler: $e")
    }

    private fun myActivityScopeWithHandler() {
        Log.d(TAG, "Function Start")
        myActivityScope.launch(exceptionHandler) {
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private fun myActivityScopeWithHandlerException() {
        Log.d(TAG, "Function Start")
        myActivityScope.launch(exceptionHandler) {
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            throw Exception("Some Exception")
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private fun lifecycleScopeWithHandler() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch(exceptionHandler){
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private fun lifecycleScopeWithHandlerException() {
        Log.d(TAG, "Function Start")

        lifecycleScope.launch(exceptionHandler){
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            throw Exception("Some Exception")
            Log.d(TAG, "After Task")

        }
        Log.d(TAG, "Function End")
    }

    private fun parentAndChildTaskCancelIsActive() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch(Dispatchers.Main){
            Log.d(TAG, "Before Task")
            childTaskWithIsActive(coroutineContext[Job]!!)
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private suspend fun childTaskWithIsActive(parent: Job) {
        withContext(Dispatchers.Default) {
            Log.d(TAG, "childTask start")
            parent.cancel()
            if (isActive) {
                Log.d(TAG, "Child TAsk Parent Cancel")
            }
            delay(2000)
            Log.d(TAG, "childTask End")

        }
    }

    private fun parentAndChildTaskCancel() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch(Dispatchers.Main){
            Log.d(TAG, "Before Task")
            childTask(coroutineContext[Job]!!)
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private suspend fun childTask(parent: Job) {
        withContext(Dispatchers.Default) {
            Log.d(TAG, "childTask Start")
            parent.cancel()
            Log.d(TAG, "childTask Parent Cancel")
            // your code for doing a long running task
            // Added delay to simulate
            delay(2000)
            Log.d(TAG, "Child Task End")
        }
    }
    private fun twoTasks() {
        Log.d(TAG, "Function Start")
        val job = lifecycleScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Before Task1")
            doLongRunningTask()
            Log.d(TAG, "After Task1")
        }

        lifecycleScope.launch(Dispatchers.Main){
            Log.d(TAG, "Before Task2")
            job.cancel()
            doLongRunningTask()
            Log.d(TAG, "After Task2")
        }
        Log.d(TAG, "Function End")
    }

    private fun twoAsyncInsideLifecycleScope() {
        Log.d(TAG, "Function Start")

        lifecycleScope.launch {
            Log.d(TAG, "Before Task")

            val deferredOne = async {
                doLongRunningTaskOne()
            }

            val deferredTwo = async {
                doLongRunningTaskTwo()
            }

            val result = deferredOne.await() + deferredTwo.await()

            Log.d(TAG, "result = $result")
            Log.d(TAG, " After Task")

        }
        Log.d(TAG, "Function End")
    }

    private fun twoWithContextInsideLifecycleScope() {
        Log.d(TAG, "Function Start")

        lifecycleScope.launch {
            Log.d(TAG, "Before Task1")
            val resultOne = doLongRunningTaskOne()
            Log.d(TAG, "After Task1")
            Log.d(TAG, "Before Task2")
            val resultTwo = doLongRunningTaskTwo()
            Log.d(TAG, "After Task2")
            val result = resultOne + resultTwo
            Log.d(TAG, "result = $result")
        }
        Log.d(TAG, "Function End")
    }

    private suspend fun doLongRunningTaskOne(): Int {
        return withContext(Dispatchers.Default) {
            // your code for doing a long running task
            // Added delay to simulate
            delay(2000)
            return@withContext 10
        }
    }

    private suspend fun doLongRunningTaskTwo(): Int {
        return withContext(Dispatchers.Default) {
            // your code for doing a long running task
            // Added delay to simulate
            delay(2000)
            return@withContext 10
        }
    }


    private fun twoLaunches() {
        Log.d(TAG, "Function Start")

        lifecycleScope.launch {
            Log.d(TAG, "Before Delay1")
            delay(2000)
            Log.d(TAG, "After Delay1")
        }

        lifecycleScope.launch {
            Log.d(TAG, "Before Delay2")
            delay(2000)
            Log.d(TAG, "After Delay2 ")
        }

        Log.d(TAG, "Function End")
    }

    private fun launchInsideLifecycleScope() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch() {
            Log.d(TAG, "Before Task")
            launch() {
                Log.d(TAG, "Before Delay")
                delay(2000)
                Log.d(TAG, "After Delay")
            }
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private fun globalScopeInsideLifecycleScope() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch {
            Log.d(TAG, "Before Task")
            GlobalScope.launch(Dispatchers.Default) {
                Log.d(TAG, "Before Delay")
                delay(2000)
                Log.d(TAG, "After Delay")
            }
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private fun usingMyActivityScope() {
        Log.d(TAG, "Function Start")
        myActivityScope.launch {
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    //Never use GlobalScope - we have used it for education purpose.
    private fun usingGlobalScope() {
        Log.d(TAG, "Function Start")
        GlobalScope.launch {
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private fun testCoroutineEverything() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Before Task 1")
            doLongRunningTask()
            Log.d(TAG, "After Task 1")
        }

        lifecycleScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Before Task 2")
            doLongRunningTask()
            Log.d(TAG, "After Task 2")
        }

        Log.d(TAG, "Function End")
    }

    private fun testCoroutineWithMainImmediate() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            Log.d(TAG, "After task")
        }
        Log.d(TAG, "Function End")
    }

    private fun testCoroutineWithMain() {
        Log.d(TAG, "Function Start")
        lifecycleScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private fun testCoroutine() {
        Log.d(TAG, "Function Start")

        lifecycleScope.launch() {
            Log.d(TAG, "Before Task")
            doLongRunningTask()
            Log.d(TAG, "After Task")
        }
        Log.d(TAG, "Function End")
    }

    private suspend fun doLongRunningTask() {
        withContext(Dispatchers.Default) {
            Log.d(TAG, "Before Delay")
            delay(2000)
            Log.d(TAG, "After Delay")
        }
    }
}