(ns day8.core (:gen-class :main true))

(defn -main [& args]
  (defn nextIndex [op] (
    if (= "jmp" (first op))
    (+ (last op) (read-string (nth op 1)))
    (+ (last op) 1)
  ))

  (defn runSeq [ops]
    (defn parseOp [op seenOps]
      (if (and (< (nextIndex op) (count ops)) (>= (nextIndex op) 0))
        (if (some #(= op %) seenOps)
          seenOps
          (->> seenOps
               (cons op)
               (parseOp (nth ops (nextIndex op)))
               reverse
          )
        )
        (reverse (cons op seenOps))
      )
    )

    (parseOp (first ops) [])
  )

  (defn replaceOp [op] (
    case (first op)
    "jmp" ["nop" (nth op 1) (nth op 2)]
    "nop" ["jmp" (nth op 1) (nth op 2)]
    op
  ))

  (defn replaceAtIndex [idxToReplace ops] (
    map-indexed (fn [idx op] (
      if (= idxToReplace idx)
         (replaceOp op)
         op
    )) ops
  ))

  (def ops (->>
     (->
       (slurp "resources/ops.txt")
       (clojure.string/split #"\n")
     )
    (map #(clojure.string/split % #" "))
    (map-indexed (fn [idx itm] (conj itm idx)))
  ))

  (->>
    (count ops)
    (range)
    (apply vector)
    (map #(replaceAtIndex % ops))
    (frequencies)
    (map first)
    (map runSeq)
    (filter #(= (nextIndex (last %)) (count ops)))
    (first)
    (filter #(= "acc" (first %)))
    (reduce (fn [acc op] (->>
      (second op)
      (read-string)
      (+ acc)
    )) 0)
    (println)
  )
)
