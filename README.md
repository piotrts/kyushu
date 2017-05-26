# 九州
Kyushu is a data-driven approach to configuration. Current version: 0.1.0-SNAPSHOT.

# How to use
```clojure
(require '[kyushu.core :as k])

(def config
  (k/refresh
    {:some-file {:type :kyushu/file :path "/home/user/config-file.cfg"}
     :whole-environment {:type :kyushu/environment}
     :another-source {:type :kyushu/memory :data {:a-key ["value 1" "value 2"]}}}))

(k/ask config [:a-key])
;; -> ["value 1" "value 2"]
```

Calling ```refresh``` on a configuration map by default updates every source when ```:type``` key is equal to ```:kyushu/file``` or ```:kyushu/resource```. New "types" can be created via ```load-handler``` multimethod:

```clojure
(defmethod k/load-handler :my-namespace/some-type [source]
  {:awesome? true})

(-> {:some-random-source-name {:type :my-namespace/some-type}}
    (k/refresh)
    (k/ask [:awesome?]))
;; -> true
```
