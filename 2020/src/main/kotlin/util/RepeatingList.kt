package util

class RepeatingList<T> (private val list: List<T>) : List<T> by list {
  override fun get(index: Int): T {
    if (index < 0) {
      return this[list.size + (index % list.size)]
    }

    return list[index % list.size]
  }
}