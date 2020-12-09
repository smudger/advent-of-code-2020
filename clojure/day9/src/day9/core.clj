(ns day9.core (:gen-class :main true))

(defn -main [& args]
  (def nums (->>
     (->
       (slurp "resources/nums.txt")
       (clojure.string/split #"\n")
     )
     (map-indexed vector)
  ))

  (defn hasSum [needle haystack]
    (defn hasComplement [itm]
      (some #(= (read-string %) (- (read-string needle) (read-string itm))) haystack)
    )

    (->
      (filter hasComplement haystack)
      (not-empty)
    )
  )

  (def weakness (->>
    (drop 25 nums)
    (map #(vector (last %) (map last (drop-last (- (count nums) (first %)) (drop (- (first %) 25) nums)))))
    (into (sorted-map))
    (remove #(apply hasSum %))
    (first)
    (first)
    (read-string)
  ))

  (defn findSum [sum]
    (defn addNum [acc num]
      (def result (+ (reduce + acc) (read-string (last num))))
      (if (< result sum)
         (cons (read-string (last num)) acc)
         (reduced (cons (read-string (last num)) acc))
      )
    )

    (defn countUp [startIdx] (
      ->> nums
          (drop startIdx)
          (reduce addNum [])
    ))

    (->>
      (map #(vector (last %) (countUp (first %))) nums)
      (filter #(> (count (last %)) 1))
      (filter #(= sum (reduce + (last %))))
      (map last)
      (map sort)
      (map #(+ (first %) (last %)))
      (first)
    )
  )

  (println (findSum weakness))
)
