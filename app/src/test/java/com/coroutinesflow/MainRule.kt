package com.coroutinesflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runners.model.Statement
import org.mockito.MockitoAnnotations

class MainRule(private val clazz: Any) : TestRule {

    val testCoroutineDispatcher = TestCoroutineDispatcher()
    val scope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement?, description: org.junit.runner.Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)
                MockitoAnnotations.initMocks(clazz)
                base!!.evaluate()
                Dispatchers.resetMain()
                scope.cleanupTestCoroutines()
            }
        }
    }

}