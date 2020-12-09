(ns day8.core (:gen-class :main true))

(defn -main [& args]
  (defn nextIndex [op] (
    if (= "jmp" (first op))
    (+ (last op) (read-string (nth op 1)))
    (+ (last op) 1)
  ))

  (defn runSeq [ops]
    (defn parseOp [op seenOps]
      (if (some #(= op %) seenOps)
        seenOps
        (->> seenOps
             (cons op)
             (parseOp (nth ops (nextIndex op)))
             reverse
        )
      )
    )

    (parseOp (first ops) [])
  )

  (def ops (->>
     (->
       (slurp "resources/ops.txt")
       (clojure.string/split #"\n")
     )
    (map #(clojure.string/split % #" "))
    (map-indexed (fn [idx itm] (conj itm idx)))
  ))

  (->>
    (runSeq ops)
    (filter #(= "acc" (first %)))
    (reduce (fn [acc op] (->>
      (second op)
      (read-string)
      (+ acc)
    )) 0)
    (println)
  )
)
