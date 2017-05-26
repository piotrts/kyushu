(ns kyushu.core
  (:require [clojure.java.io :as io]))

(defn- assoc-data [source data]
  (assoc-in source [1 :data] data))

(defmulti load-handler (comp :type val))

(defmethod load-handler :kyushu/file [source]
  (assoc-data source (try
                       (-> source val :path io/file slurp)
                       (catch Exception e
                         {}))))

(defmethod load-handler :kyushu/resource [source]
  (assoc-data source (try
                       (-> source val :path io/resource slurp)
                       (catch Exception e
                         {}))))

(defmethod load-handler :kyushu/environment [source]
  (assoc-data source (System/getenv)))

(defmethod load-handler :default [source]
  source)

(defn refresh [provider]
  (into {} (map load-handler provider)))

(defn- merge-in
  ([a] a)
  ([a b]
   (if (and (map? a) (map? b))
     (merge-with merge-in a b)
     b))
  ([a b & more]
   (reduce merge-in (merge-in a b) more)))

(defn- merge-sources [provider]
  (->> provider vals (map :data) (apply merge-in)))

(defn ask [provider ks]
  (get-in (merge-sources provider) ks))

