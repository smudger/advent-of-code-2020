(ns day8.core (:gen-class :main true))

(defn -main [& args]
  (defn call [func & args] (
    -> func
       symbol
       resolve
       (apply args)
  ))

  (def ops (->>
     (->
       (slurp "resources/ops.txt")
       (clojure.string/split #"\n")
     )
    (map #(clojure.string/split % #" "))
    (map-indexed (fn [idx itm] (conj itm idx)))
  ))

  (defn jmp [chg cur] (->>
    (read-string chg)
    (+ cur)
    (nth ops)
  ))

  (defn acc [chg cur] (->>
    (+ cur 1)
    (nth ops)
  ))

  (defn nop [chg cur] (->>
    (+ cur 1)
    (nth ops)
  ))

  (defn sortByExecution [op seenOps]
    (if (some #(= op %) seenOps)
      seenOps
      (->> seenOps
           (cons op)
           (sortByExecution (apply call op))
           reverse
      )
    )
  )

  (->>
    (sortByExecution (first ops) ())
    (filter #(= "acc" (first %)))
    (reduce (fn [acc op] (->>
      (second op)
      (read-string)
      (+ acc)
    )) 0)
  )
)
