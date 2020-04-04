package lab.march.diary.core.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lab.march.diary.util.ExecutionResult

abstract class UseCase<in P, R> {

    operator fun invoke(
        coroutineScope: CoroutineScope,
        parameters: P,
        result: MutableLiveData<ExecutionResult<R>>
    ) {
        try {
            coroutineScope.launch {
                result.postValue(ExecutionResult.Loading)
                try {
                    result.postValue(
                        ExecutionResult.Success(
                            withContext(Dispatchers.IO) { execute(parameters) }
                        )
                    )
                } catch (exception: Exception) {
                    result.postValue(ExecutionResult.Error(exception))
                }
            }
        } catch (exception: Exception) {
            result.postValue(ExecutionResult.Error(exception))
        }
    }

    operator fun invoke(coroutineScope: CoroutineScope, parameters: P): LiveData<ExecutionResult<R>> {
        val liveCallback: MutableLiveData<ExecutionResult<R>> = MutableLiveData()
        this(coroutineScope, parameters, liveCallback)
        return liveCallback
    }

    /**
     *  Override this to set the code to be executed
     */
    protected abstract suspend fun execute(parameters: P): R
}

operator fun <R> UseCase<Unit, R>.invoke(coroutineScope: CoroutineScope): LiveData<ExecutionResult<R>> = this(coroutineScope, Unit)

operator fun <R> UseCase<Unit, R>.invoke(coroutineScope: CoroutineScope, result: MutableLiveData<ExecutionResult<R>>) = this(coroutineScope, Unit, result)
