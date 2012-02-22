(ns clj-redis.test.client
  (:refer-clojure :exclude [get set keys type])
  (:use [clojure.test]
        [clj-redis.client]))

(deftest test-client
  (let [db (init :url "redis://localhost")
        [k v] ["foo" "bar"]]
    (is (= "PONG" (ping db)))
    (set db k v)
    (is (= v (get db k)))))

(deftest test-brpoplpush
  (let [db (init :url "redis://localhost")
        [k1 k2 v] ["foo" "foo2" "bar"]]
    (del db [k1])
    (lpush db k1 v)
    (is (= v (brpoplpush db k1 k2 0)))
    (is (= 0 (llen db k1)))
    (is (= v (lpop db k2)))))
