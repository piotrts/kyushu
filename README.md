# 九州
Kyushu is a data-driven approach to configuration.

# How to use
```clojure
(require '[kyushu.core :as k])

(def config
  {:some-data (k/memory {:a {:b 1}})
   :other-data (k/memory {:a {:c 2}})
   :a-file (k/file "config.txt")})

(k/ask config [:a :b])
;; => 1
```
