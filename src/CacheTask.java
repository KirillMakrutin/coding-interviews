public class CacheTask {
    public static void main(String[] args) {

    }
}

// 1st implement cache
interface StringCache {
    void add(String id, String value);

    String get(String id);
}

// 2nd rewrite to be able to work with any object (not primitive) data types (any id and value types)
interface Cache<ID, VALUE> {
    void add(ID id, VALUE value);

    VALUE get(ID id);
}


 // 3rd
class Example3 {
    static void example() {
        // size = 3
        Cache<String, String> cache = new CacheImpl<>();
        cache.add("1", "1"); // [1,1]
        cache.add("2", "2"); // [1,1], [2,2]
        cache.add("3", "3"); // [1,1], [2,2], [3,3]

        cache.add("4", "4"); // [2,2], [3,3], [4,4]
        cache.add("5", "5"); // [3,3], [4,4], [5,5]
    }
 }

 // 4th
 class Example4 {
    static void example() {
        // size = 3
        Cache<String, String> cache = new CacheImpl<>();
        cache.add("1", "1"); // [1,1]
        cache.add("2", "2"); // [1,1], [2,2]
        cache.add("3", "3"); // [1,1], [2,2], [3,3]

        cache.add("4", "4"); // [2,2], [3,3], [4,4]
        cache.add("5", "5"); // [3,3], [4,4], [5,5]

        cache.get("3"); // [4,4], [5,5], [3,3]

        cache.add("6", "6"); // [5,5], [3,3], [6,6]
    }
 }
