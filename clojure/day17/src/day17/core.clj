(ns day17.core
  (:require [clojure.math.combinatorics :as comb] [clojure.set])
)

(def initState (->>
  (->
    (slurp "resources/cubes.txt")
    (clojure.string/split #"\n")
  )
  (map #(clojure.string/split % #""))
  (map #(map-indexed vector %))
  (map-indexed (fn [idx itm] (map #(conj % idx) itm)))
  (reduce concat)
  (filter #(= "#" (second %)))
  (map #(hash-map :x (first %) :y 0 :z (last %) :w 0))
  (into [])
))

(defn getNeighboursOfPoint [point] (->>
 [:x :y :z :w]
 (map (fn [dir] (map #(hash-map dir %) [-1 0 1])))
 (apply comb/cartesian-product)
 (map #(apply merge %))
 (remove #(= {:x 0, :y 0, :z 0 :w 0} %))
 (map #(merge-with + point %))
))

(defn getNeighboursForState [state] (->>
 state
 (map getNeighboursOfPoint)
 (reduce into [])
 (frequencies)
))

(defn stillActive [state, neighbours] (->>
  neighbours
  (filter #(or (= 3 (last %)) (= 2 (last %))))
  (map first)
  (set)
  (clojure.set/intersection (set state))
))

(defn newlyActive [state, neighbours] (->
 (->>
  neighbours
  (filter #(= 3 (last %)))
  (map first)
  (set)
 )
 (clojure.set/difference (set state))
))

(defn runSim [state]
 (let [neighbours (getNeighboursForState state)]
  (clojure.set/union (newlyActive state neighbours) (stillActive state neighbours))
 )
)

(defn -main [& args] (->>
 (range 6)
 (reduce (fn [state _] (runSim state)) initState)
 (count)
 (println)
))
