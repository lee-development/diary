package lab.march.diary.util

sealed class ExecutionResult<out R> {

    object Loading: ExecutionResult<Nothing>()
    data class Success<out T>(val data: T): ExecutionResult<T>()
    data class Error(val exception: Exception): ExecutionResult<Nothing>()

    override fun toString(): String = when(this) {
        is Success<*> -> "Success[data=$data]"
        is Error -> "Error[exception=$exception]"
        Loading -> "Loading"
    }
}

val ExecutionResult<*>.succeeded
    get() = this is ExecutionResult.Success && data != null

fun <T> ExecutionResult<T>.successOrNull(): T? = (this as? ExecutionResult.Success<T>)?.data

fun <T> ExecutionResult<T>.successOr(fallback: T): T = (this as? ExecutionResult.Success<T>)?.data ?: fallback