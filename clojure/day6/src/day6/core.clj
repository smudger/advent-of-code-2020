(ns day6.core (:gen-class :main true))

(defn -main [& args]
  (defn groupSize [group] (
    ->> group
        (re-seq #"\n")
        (count)
        (+ 1)
  ))

  (defn parseGroup [group]
    (list (clojure.string/replace group #"\n" "") (groupSize group))
  )

  (defn allAnswered [group] (
    ->> (first group)
        (filter #(= (last group) (second %)))
  ))

  (->>
    (->
      (slurp "resources/declarations.txt")
      (clojure.string/split #"\n\n")
    )
    (map clojure.string/trim)
    (map parseGroup)
    (map #(list (frequencies (first %)) (last %)))
    (map allAnswered)
    (map keys)
    (map count)
    (reduce +)
    (println)
  )
)
