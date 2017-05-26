(ns kyushu.core
  (:require [clojure.java.io :as io]))

(defmulti load-handler (comp :type val))

(defmethod load-handler :kyushu/file [source]
  (try
    (-> source val :path io/file slurp)
    (catch Exception e
      {})))

(defmethod load-handler :kyushu/resource [source]
  (try
    (-> source val :path io/resource slurp)
    (catch Exception e
      {})))

(defmethod load-handler :kyushu/environment [source]
  (System/getenv))

(defmethod load-handler :default [source]
  (:data (val source)))

(defn refresh [provider & [refresh-fn]]
  (let [refresh-fn (or refresh-fn load-handler)]
    (into {}
      (map (fn [source]
             (assoc-in source [1 :data] (refresh-fn source)))
           provider))))

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

