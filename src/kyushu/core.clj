(ns kyushu.core
  (:require [clojure.java.io :as io]))

(defn file [name path]
  {name (-> path io/file slurp)})

(defn resource [name path]
  {name (-> path io/resource slurp)})

(defn memory [name data]
  {name data})

(defn environment [name]
  {name (System/getenv)})

(defn- combine [& sources]
  (apply merge sources))

(defn- merge-in
  ([a] a)
  ([a b]
   (if (and (map? a) (map? b))
     (merge-with merge-in a b)
     b))
  ([a b & more]
   (reduce merge-in (merge-in a b) more)))

(defn ask [source k & ks]
  (get-in (apply merge-in (vals source)) (into [k] ks)))

