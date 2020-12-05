(ns day5.core (:gen-class :main true))

(defn -main [& args]
  (defn exp [x n] (reduce * (repeat n x)))

  (defn addDigit [cur next] (->>
    (first next)
    (exp 2)
    (* (Character/digit (last next) 10))
    (+ cur)
  ))

  (defn toDecimal [binaryString] (
    ->>
      (map-indexed (fn [idx itm] [(- (count binaryString) (+ idx 1)) itm]) binaryString)
      (reduce addDigit 0)
  ))

  (defn toRowCol [boardingPass] (
    ->> (list (subs boardingPass 0 7) (subs boardingPass 7))
        (map #(clojure.string/replace % #"F" "0"))
        (map #(clojure.string/replace % #"B" "1"))
        (map #(clojure.string/replace % #"L" "0"))
        (map #(clojure.string/replace % #"R" "1"))
        (map toDecimal)
  ))

  (def boardingPasses (->>
    (->
      (slurp "resources/boarding_passes.txt")
      (clojure.string/split #"\n")
    )
    (map toRowCol)
  ))

  (def sortedSeatIds (->>
    boardingPasses
    (map #(+ (* (first %) 8) (last %)))
    (sort)
  ))

  (->>
    sortedSeatIds
    (map-indexed vector)
    (map #(list (+ (first %) (first sortedSeatIds)) (last %)))
    (filter #(not= (first %) (second %)))
    (first)
    (first)
    (println)
  )
)
