# 九州
Kyushu is a data-driven approach to configuration. Current version: 0.1.0-SNAPSHOT.

# How to use
```clojure
(require '[kyushu.core :as k])

(def config
  {:some-data (k/memory {:a {:b 1}})
   :other-data (k/memory {:a {:c 2 :d [0 1 2]}})
   :a-resource (k/resource "some-resource.res")
   :a-file (k/file "config.txt")})

(k/ask config [:a :b])
;; => 1
```
Calling ```refresh``` on a configuration map updates every referenced file and resource.
