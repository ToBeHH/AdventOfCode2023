package util

/**
 * Generic cache interface
 *
 * @see <a href="https://kezhenxu94.medium.com/how-to-build-your-own-cache-in-kotlin-1b0e86005591">How to build your own cache, in Kotlin</a>
 */
interface Cache {
    /**
     * The cache size
     */
    val size: Int

    /**
     * Store something in the cache
     *
     * @param key The key to store
     * @param value The value to store
     */
    operator fun set(key: Any, value: Any)

    /**
     * Retrieve something from the cache
     *
     * @param key The key to retrieve
     * @return The value, or null if not found
     */
    operator fun get(key: Any): Any?

    /**
     * Remove something from the cache
     *
     * @param key The key to remove
     * @return The value, or null if not found
     */
    fun remove(key: Any): Any?

    /**
     * Clear the cache
     */
    fun clear()
}