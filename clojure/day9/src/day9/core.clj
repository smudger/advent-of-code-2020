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

  (->>
    (drop 25 nums)
    (map #(vector (last %) (map last (drop-last (- (count nums) (first %)) (drop (- (first %) 25) nums)))))
    (into (sorted-map))
    (remove #(apply hasSum %))
    (first)
    (first)
    (println)
  )
)
