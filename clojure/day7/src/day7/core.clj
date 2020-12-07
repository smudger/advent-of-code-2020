(ns day7.core (:gen-class :main true))

(defn -main [& args]
  (defn parseContents [first & rest] (
    ->> (map #(clojure.string/split % #"\s" 2) rest)
        (sequence)
        (hash-map first)
  ))

  (defn parseRule [rule] (
    ->> (re-seq #"((?:\d+\s)?(?:\w+\s){2})bag" rule)
        (map last)
        (map clojure.string/trimr)
        (filter #(not= "no other" %))
        (apply parseContents)
  ))

  (def bags (->>
    (->
      (slurp "resources/rules.txt")
      (clojure.string/split #"\.\n")
    )
    (map parseRule)
    (into (sorted-map))
  ))

  (defn countNested [outer] (
    ->> (get bags outer)
        (reduce (fn [acc bag] (
          ->> acc
              (+ (read-string (first bag)))
              (+ (* (read-string (first bag)) (countNested (last bag))))
        )) 0)
  ))

  (println (countNested "shiny gold"))
)
