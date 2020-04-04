package lab.march.diary.core.mapper

interface Mapper<in T, out R> {

    fun map(origin: T): R

    fun map(origin: List<T>): List<R> = origin.map(::map)
}