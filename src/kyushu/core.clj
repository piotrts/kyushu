(ns kyushu.core
  (:require [clojure.java.io :as io]))

(defn file [path]
  {:type :file
   :source path
   :data (try
           (-> path io/file slurp)
           (catch Exception e
             {}))})

(defn resource [path]
  {:type :resource
   :source path
   :data (try
           (-> path io/resource slurp)
           (catch Exception e
             {}))})

(defn memory [data]
  {:type :memory
   :data data})

(defn environment []
  {:type :environment
   :data (System/getenv)})

(defn- merge-in
  ([a] a)
  ([a b]
   (if (and (map? a) (map? b))
     (merge-with merge-in a b)
     b))
  ([a b & more]
   (reduce merge-in (merge-in a b) more)))

(defn ask [source k & ks]
  (get-in (->> (vals source)
               (map :data)
               (apply merge-in))
          (into [k] ks)))

