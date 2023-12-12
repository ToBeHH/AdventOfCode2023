package util

/**
 * Cache to store things forever
 *
 * @see <a href="https://kezhenxu94.medium.com/how-to-build-your-own-cache-in-kotlin-1b0e86005591">How to build your own cache, in Kotlin</a>
 */
class PerpetualCache : Cache {
    private val cache = HashMap<Any, Any>()

    override val size: Int
        get() = cache.size

    override fun set(key: Any, value: Any) {
        this.cache[key] = value
    }

    override fun remove(key: Any) = this.cache.remove(key)

    override fun get(key: Any) = this.cache[key]

    override fun clear() = this.cache.clear()
}