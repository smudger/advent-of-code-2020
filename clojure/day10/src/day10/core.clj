(ns day10.core
  (:require [clojure.math.combinatorics :as comb] [clojure.set])
)

(defn -main [& args]
  (defn isValid [testSeq] (->>
    (->  testSeq
         (conj (+ 3 (last testSeq)))
         (sort)
         (zipmap (cons 0 testSeq))
    )
    (map #(- (first %) (last %)))
    (filter #(> % 3))
    empty?
  ))

  (def nums (->>
     (->
       (slurp "resources/jolts.txt")
       (clojure.string/split #"\n")
     )
     (map read-string)
     (sort)
  ))

  (def fullNums (
    ->  nums
        (conj (+ 3 (last nums)))
        (conj 0)
        sort
  ))

  (->> nums
       (map-indexed vector)
       (filter #(<= (- (nth fullNums (+ 2 (first %))) (nth fullNums (first %))) 3))
       (map last)
       (comb/subsets)
       (map (fn [complement] (clojure.set/difference (set nums) (set complement))))
       (map #(apply vector %))
       (map sort)
       (filter isValid)
       (count)
       (println)
  )
)
